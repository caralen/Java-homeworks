package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The Class SmartHttpServer is a server program which listens to client requests and writes answers to the client.
 * <p>It supports usage of smart scripts. It parses a smart script using the <code>SmartScriptParser</code> 
 * and executes it using the <code>SmartScriptEngine</code>.
 * Properties files stored in config folder are used for modification 
 * of the server properties, mime properties and workers properties.
 * The MVC design pattern is used in implementation of the web application.
 * <li>Workers are used for performing some computations (the processing part).
 * <li>Smart scripts are used for html rendering (rendering part).
 */
public class SmartHttpServer {
	
	/** The address of the server. */
	private String address;
	
	/** The domain name. */
	private String domainName;
	
	/** The port at which the server listens for requests. */
	private int port;
	
	/** The number of worker threads in thread pool. */
	private int workerThreads;
	
	/** The number of seconds for the session timeout. */
	private int sessionTimeout;
	
	/** The map of mime types. */
	private Map<String, String> mimeTypes = new HashMap<>();
	
	/** The server thread. */
	private ServerThread serverThread;
	
	/** The thread pool. */
	private ExecutorService threadPool;
	
	/** The document root where the server is looking for files. */
	private Path documentRoot;
	
	/** The map of workers. */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/** The map of sessions. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/** Used for generating a random SID. */
	private Random sessionRandom = new Random();
	
	private static final int FIVE_MINUTES = 5*60*1000;
	
	/**
	 * The main method of the program, it is called upon the start of the program.
	 *
	 * @param args the command line arguments, path to the configuration file is expected
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		if(args.length != 1) {
			System.out.println("Was expecting configuration file name");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Write \"stop\" if you wish to stop the server");
		if(sc.nextLine().equalsIgnoreCase("stop")) {
			server.stop();
			sc.close();
		}
	}
	

	/**
	 * Instantiates a new smart http server.
	 * Reads property files and puts them in appropriate maps.
	 *
	 * @param configFileName the path for the config file name
	 */
	public SmartHttpServer(String configFileName) {
		
		Properties serverProperties = new Properties();
		Properties mimeProperties = new Properties();
		Properties workerProperties = new Properties();
		try {
			serverProperties.load(Files.newInputStream(Paths.get(configFileName)));
			mimeProperties.load(Files.newInputStream(Paths.get(serverProperties.getProperty("server.mimeConfig"))));
			workerProperties.load(Files.newInputStream(Paths.get(serverProperties.getProperty("server.workers"))));
			
			for(Object property : workerProperties.keySet()) {
				String path = (String) property;
				String fqcn = workerProperties.getProperty(path);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(path, iww);
			}
			
		} catch (IOException e) {
			System.err.println("Cannot load server properties file");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (InstantiationException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (InvocationTargetException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (NoSuchMethodException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (SecurityException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		address = serverProperties.getProperty("server.address");
		domainName = serverProperties.getProperty("server.domainName");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		
		for(Object key : mimeProperties.keySet()) {
			mimeTypes.put((String)key, (String)mimeProperties.get(key));
		}
	}

	/**
	 * Starts a new server thread if it is not already running.
	 * Creates a new thread pool with {@link #workerThreads} threads.
	 */
	protected synchronized void start() {
		if(serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Stops the server thread.
	 * Shuts down the thread pool.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException 
	 */
	protected synchronized void stop() throws InterruptedException, IOException {
		serverThread.kill();
		threadPool.shutdown();
	}
	

	/**
	 * The Class ServerThread represents a server thread.
	 * It creates a new server socket at the current address and port and waits for the client requests.
	 * It instantiates a new client worker and delegates work to the thread pool.
	 */
	protected class ServerThread extends Thread {
		
		/** The server socket. */
		ServerSocket serverSocket;
		
		/**
		 * Creates new thread which sleeps for five minutes and then removes sessions that are not valid anymore.
		 * Instantiates a server socket binds it to the address and port and accepts clients in for loop.
		 * New client worker is instantiated and given to the thread pool.
		 */
		@Override
		public void run() {
			try {
				Thread cleaner = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(true) {
							try {
								sleep(FIVE_MINUTES);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							sessions.entrySet().removeIf(e -> e.getValue().getValidUntil() < (new Date().getTime()*1000));
						}
					}
				});
				cleaner.setDaemon(true);
				cleaner.start();
				
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while(true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.execute(cw);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}

		/**
		 * Closes the {@link #serverSocket}
		 * @throws IOException
		 */
		public void kill() throws IOException {
			serverSocket.close();
		}
	}
	
	/**
	 * The Class SessionMapEntry represents a single session.
	 */
	private static class SessionMapEntry {
		
		/** The session id. */
		@SuppressWarnings("unused")
		String sid;
		
		/** The host. */
		String host;
		
		/** The time in seconds until the session is valid. */
		long validUntil;
		
		/** The map. */
		Map<String,String> map;
		
		/**
		 * Instantiates a new session map entry.
		 *
		 * @param sid {@link #sid}
		 * @param host {@link #host}
		 * @param validUntil {@link #validUntil}
		 * @param map {@link #map}
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

		/**
		 * Sets the valid until property.
		 *
		 * @param validUntil the new valid until value
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}
		
		/**
		 * Gets the valid until property.
		 *
		 * @return the valid until
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Gets the host.
		 *
		 * @return the host
		 */
		public String getHost() {
			return host;
		}

		/**
		 * Gets the map.
		 *
		 * @return the map
		 */
		public Map<String, String> getMap() {
			return map;
		}
	}

	/**
	 * The Class ClientWorker is instantiated when there is a new client request at the server.
	 * It is used for processesing of the client request.
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/** The client socket. */
		private Socket csocket;
		
		/** The input stream from which the request is read. */
		private PushbackInputStream istream;
		
		/** The output stream where response will be written. */
		private OutputStream ostream;
		
		/** The version of the protocol. */
		private String version;
		
		/** The method of the request. */
		private String method;
		
		/** The host. */
		private String host;
		
		/** The parameters map. */
		private Map<String, String> params = new HashMap<String, String>();
		
		/** The temporary parameters map. */
		private Map<String, String> tempParams = new HashMap<>();
		
		/** The permanent parameters map. */
		private Map<String, String> permParams = new HashMap<>();
		
		/** The output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/** The 20 random upper-case letter session id. */
		private String SID;
		
		/** The context. */
		private RequestContext context = null;

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket the client socket
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(new BufferedInputStream(csocket.getInputStream()));
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				
				List<String> request = readRequest();
				if (request == null || request.isEmpty()) {
					sendError(ostream, 400, "Bad Request");
					return;
				}

				String firstLine = request.get(0);
				if(firstLine==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String[] firstLineParts = firstLine.split(" ");
				method = firstLineParts[0].toUpperCase();
				String requestedPath = firstLineParts[1];
				version = firstLineParts[2].toUpperCase().trim();
				
				if (!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				for (String line : request) {

					if (line.startsWith("Host:")) {
						line = line.replaceFirst("Host: ", "").trim();
						if (line.contains(":")) {
							line = line.split(":")[0];
						}
						host = line;
					}
				}
				if (host == null) {
					host = domainName;
				}

				String path;
				String paramString;
				
				if(requestedPath.contains("?")) {
					path = requestedPath.split("\\?")[0];
					paramString = requestedPath.split("\\?")[1];
				} else {
					path = requestedPath;
					paramString = null;
				}
				
				checkSession(request);
				
				parseParameters(paramString);
				
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
				}
				
				if (path.startsWith("/ext/")) {
					String[] pathParts = path.split("/");
					String fqcn = "hr.fer.zemris.java.webserver.workers." + pathParts[pathParts.length - 1];
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
					IWebWorker worker = (IWebWorker) newObject;
					worker.processRequest(context);
					ostream.flush();
					return;
					
				} else {
					IWebWorker worker = workersMap.get(path);
					if (worker != null) {
						worker.processRequest(context);
						ostream.flush();
						return;
					}
				}
				
				requestedPath = documentRoot.resolve(path.substring(1)).toAbsolutePath().toString();
				
				if (!requestedPath.startsWith(documentRoot.toString())) {
					sendError(ostream, 403, "Forbidden");
					return;
				}
				
				if (!Files.isRegularFile(Paths.get(requestedPath)) || !Files.isReadable(Paths.get(requestedPath))) {
					sendError(ostream, 404, "File not regular");
					return;
				}
				
				if(path.split("\\.")[1].equals("smscr")) {
					internalDispatchRequest(requestedPath, true);
					return;
				}

				String extension = requestedPath.split("\\.")[1];
				
				String mimeType = mimeTypes.get(extension);

				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				
				context.setMimeType(mimeType);
				context.setStatusCode(200);

				try (InputStream fis = Files.newInputStream(Paths.get(requestedPath))) {

					byte[] buf = new byte[1024];
					while (true) {
						int r = fis.read(buf);
						if (r < 1)
							break;
						context.write(buf, 0, r);
					}
					ostream.flush();
				}

				
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return;
			} catch(Exception e){
				System.out.println(e.getMessage());
				return;
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Goes through headers and checks if there is a cookie header.
		 * If there is a cookie header checks if the session is still valid,
		 * otherwise new session is created. If there is a cookie, but the session is not valid,
		 * a new session is created
		 * This method is thread safe. Only one thread can enter this method.
		 *
		 * @param request the list of strings representing the request from the client
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			long now = System.currentTimeMillis() / 1000;

			for (String line : request) {
				if (!line.startsWith("Cookie:"))
					continue;

				String cookieLine = line.replaceFirst("Cookie: ", "").replaceAll("\n", "");
				String[] cookies = cookieLine.split(";");
				for (String cookie : cookies) {
					if (cookie.startsWith("sid") || cookie.startsWith(" sid")) {
						sidCandidate = cookie.split("=")[1].replaceAll("\"", "");
					}
				}
			}
			if (sidCandidate == null) {
				sidCandidate = createNewSession(now);
			} else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry == null) {
					// server je restartan, a browser zapamtio cookieje
					sidCandidate = createNewSession(now);
					entry = sessions.get(sidCandidate);
				}

				if (!entry.getHost().equals(host)) {
					sidCandidate = createNewSession(now);
				} else if (entry.getValidUntil() < now) {
					sessions.remove(sidCandidate);
					sidCandidate = createNewSession(now);
				} else {
					entry.setValidUntil(now + sessionTimeout);
				}
			}
			SID = sidCandidate;
			SessionMapEntry entry = sessions.get(sidCandidate);
			permParams = entry.getMap();
			outputCookies.add(new RCCookie("sid", SID, null, host, "/", "HttpOnly"));
		}

		/**
		 * Creates the new session id and a new session map entry which are then put in the sessions map.
		 *
		 * @param now the current time in seconds
		 * @return the session id value
		 */
		private synchronized String createNewSession(long now) {
			String sid = createSid();
			sessions.put(sid, new SessionMapEntry(sid, host, now + sessionTimeout, new ConcurrentHashMap<>()));
			return sid;
		}
		
		/**
		 * Creates the session id. It takes random 20 upper-case letters and concatenates them into a string.
		 *
		 * @return the session id
		 */
		private synchronized String createSid() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 20; i++) {
				char c = (char) (sessionRandom.nextInt(25) + 65);
				sb.append(c);
			}
			return sb.toString();
		}

		/**
		 * Parses the parameters from the string given in arguments.
		 *
		 * @param paramString the parameters string
		 */
		private void parseParameters(String paramString) {
			if(paramString == null) return;
			
			String[] parameters = paramString.split("&");
			
			for(String parameter : parameters) {
				String key = parameter.split("=")[0];
				String value = parameter.split("=")[1];
				params.put(key, value);
			}
		}

		/**
		 * Reads request and returns a list of strings where each string represents a single header.
		 *
		 * @return the list of headers
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private List<String> readRequest() throws IOException {
			byte[] request = readBytes();
			List<String> list = new ArrayList<>();
			
			if(request==null) {
				return null;
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			for(int i = 0; i < request.length; i++) {
				bos.write(request[i]);
				
				if(request[i] == 0xA) {
					String line = new String(bos.toByteArray(), StandardCharsets.US_ASCII);
					list.add(line);
					bos = new ByteArrayOutputStream();
				}
			}
			return list;
		}

		/**
		 * Reads every byte from the input stream and returns a byte array.
		 *
		 * @return the byte array
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private byte[] readBytes() throws IOException {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = istream.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(documentRoot.resolve(urlPath.substring(1)).toAbsolutePath().toString(), false);
		}
		
		/**
		 * Processes script at the given url path. 
		 * Uses SmartScriptParser for parsing the script
		 * and SmartScriptEngine for executing the script.
		 *
		 * @param urlPath the url path to the script that has to be executed.
		 * @param directCall flag indicating if the call is direct or via dispatchRequest method
		 * @throws Exception the exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if(context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this); 
			}
			if(directCall) {
				if(urlPath.contains("private")) {
					sendError(ostream, 404, "Unavailable");
					return;
				}
			}
			String fileContent = new String(Files.readAllBytes(Paths.get(urlPath)), StandardCharsets.UTF_8);
			SmartScriptParser parser = new SmartScriptParser(fileContent);
			context.setMimeType("application/octet-stream");
			context.setStatusCode(200);
			new SmartScriptEngine(parser.getDocumentNode(), context).execute();
		}
		
	}

	/**
	 * Writes error answer to the given output stream (to the client).
	 * Answer contains all necessary headers and status code and status text to indicate which error happened
	 *
	 * @param ostream the output stream to which the error is written
	 * @param statusCode the status code of the error
	 * @param statusText the status text of the error
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void sendError(OutputStream ostream, int statusCode, String statusText) throws IOException {
		ostream.write(
				("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
				"Server: simple java server\r\n"+
				"Content-Type: text/plain;charset=UTF-8\r\n"+
				"Content-Length: 0\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
		ostream.flush();
	}
}
