package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The Class RequestContext is used for writing headers and file contents to output stream.
 * The first time the method write is called, header is genereated. After that it is not legal to call set methods
 * on properties which are part of the header. There are three versions of write method.
 * One which takes string, the other takes array of bytes and the third takes array of bytes, offset and length.
 */
public class RequestContext {
	
	/** The output stream. */
	private OutputStream outputStream;
	
	/** The charset. */
	private Charset charset;
	
	/** The encoding. */
	private String encoding;
	
	/** The status code. */
	private int statusCode;
	
	/** The status text. */
	private String statusText;
	
	/** The mime type. */
	private String mimeType;
	
	/** The parameters map. */
	private Map<String,String> parameters;
	
	/** The temporary parameters map. */
	private Map<String,String> temporaryParameters;
	
	/** The persistent parameters map. */
	private Map<String,String> persistentParameters;
	
	/** The output cookies. */
	private List<RCCookie> outputCookies;
	
	/** The header generated. */
	private boolean headerGenerated;
	
	/** The dispatcher. */
	private IDispatcher dispatcher;
	
	/** The content length. */
	private Long contentLength;
	
	
	{
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
		temporaryParameters = new HashMap<>();
	}
	
	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream the output stream
	 * @param parameters the parameters
	 * @param persistentParameters the persistent parameters
	 * @param outputCookies the output cookies
	 * @param temporaryParameters the temporary parameters
	 * @param dispatcher the dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, 
			Map<String,String> temporaryParameters, IDispatcher dispatcher) {
		
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream the output stream
	 * @param parameters the parameters
	 * @param persistentParameters the persistent parameters
	 * @param outputCookies the output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		
		Objects.requireNonNull(outputStream);
		
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}
	
	/**
	 * Sets the encoding value.
	 *
	 * @param encoding the new encoding
	 * @throws RuntimeException is thrown if headers are already generated
	 */
	public void setEncoding(String encoding) throws RuntimeException {
		if(headerGenerated) throw new RuntimeException();
		this.encoding = encoding;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 * @throws RuntimeException is thrown if headers are already generated
	 */
	public void setStatusCode(int statusCode) throws RuntimeException {
		if(headerGenerated) throw new RuntimeException();
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText the new status text
	 * @throws RuntimeException is thrown if headers are already generated
	 */
	public void setStatusText(String statusText) throws RuntimeException {
		if(headerGenerated) throw new RuntimeException();
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType the new mime type
	 * @throws RuntimeException is thrown if headers are already generated
	 */
	public void setMimeType(String mimeType) throws RuntimeException {
		if(headerGenerated) throw new RuntimeException();
		this.mimeType = mimeType;
	}
	
	/**
	 * Gets the parameters map.
	 *
	 * @return the parameters map
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
	

	/**
	 * Gets the temporary parameters map.
	 *
	 * @return the temporary parameters map
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}


	/**
	 * Sets the temporary parameters map.
	 *
	 * @param temporaryParameters the temporary parameters map
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}


	/**
	 * Gets the persistent parameters map.
	 *
	 * @return the persistent parameters map
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}


	/**
	 * Sets the persistent parameters map.
	 *
	 * @param persistentParameters the persistent parameters map
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	
	/**
	 * Gets the parameter in the map that has the given name as a key.
	 *
	 * @param name the name representing a key
	 * @return the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Gets the parameter unmodifiable key set from the parameters map.
	 *
	 * @return the set of keys, i.e. parameter names
	 */
	public Set<String> getParameterNames(){
		return java.util.Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Gets the persistent parameter in the map that has the given name as a key.
	 *
	 * @param name the name representing a key
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Gets the persistent parameter unmodifiable key set.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames(){
		return java.util.Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Sets the persistent parameter.
	 *
	 * @param name the name representing a key
	 * @param value the string value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the persistent parameter from the map.
	 *
	 * @param name the name representing a key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Gets the temporary parameter from the map.
	 *
	 * @param name the name representing a key
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Gets the temporary parameter unmodifiable key set.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Sets the temporary parameter.
	 *
	 * @param name the name representing a key
	 * @param value the value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the temporary parameter from the map.
	 *
	 * @param name the name representing a key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Gets the dispatcher.
	 *
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Sets the content length.
	 *
	 * @param contentLength the new content length
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) throw new RuntimeException();
		this.contentLength = contentLength;
	}

	/**
	 * Generates header if it isn't already generated.
	 * Writes bytes array to output stream.
	 *
	 * @param data the data that has to be written
	 * @return the request context, this
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Generates header if it isn't already generated.
	 * Calls write method on output stream with byte array, offset and length.
	 *
	 * @param data the data
	 * @param offset the offset
	 * @param len the length of the data array
	 * @return the request context
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	

	/**
	 * Generates header if it isn't already generated.
	 * Writes text to the output stream using the current charset, {@link #charset}.
	 *
	 * @param text the text
	 * @return the request context
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			writeHeader();
		}
		outputStream.write(text.getBytes(charset));
		outputStream.flush();
		return this;
	}
	
	/**
	 * Generates and writes headers to output stream.
	 * Sets the flag {@link #headerGenerated} to true.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		
		String header = constructFirstLine() + 
				constructContentType() + 
				constructContentLength() +
				constructSetCookie() + 
				"\r\n";
				
		outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}
	

	/**
	 * Construct first line of the headers.
	 *
	 * @return the string
	 */
	private String constructFirstLine() {
		return "HTTP/1.1" + " " + statusCode + " " + statusText + "\r\n";
	}

	/**
	 * Construct content type for headers.
	 *
	 * @return the string representing Content-type header
	 */
	private String constructContentType() {
		String mime = mimeType.startsWith("text/") ? mimeType + "; charset=" + encoding : mimeType;
		return "Content-type: " + mime + "\r\n";
	}
	
	/**
	 * Construct content length line for headers.
	 *
	 * @return the string representing Content-length header
	 */
	private String constructContentLength() {
		if(contentLength != null) {
			return "Content-length: " + contentLength + "\r\n";
		} else {
			return "";
		}
	}
	
	/**
	 * Construct set cookie header.
	 *
	 * @return the string representing Set-cookie header
	 */
	private String constructSetCookie() {
		if(outputCookies.isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder();
		for(RCCookie cookie : outputCookies) {
			sb.append("Set-cookie: ");
			if(cookie.getName() != null) sb.append(cookie.getName() + "=" + "\"" + cookie.getValue() + "\"");
			if(cookie.getDomain() != null) sb.append("; " + "Domain=" + cookie.getDomain());
			if(cookie.getPath() != null) sb.append("; " + "Path=" + cookie.getPath());
			if(cookie.getMaxAge() != null) sb.append("; " + "Max-Age=" + cookie.getMaxAge());
			if(cookie.getConstraint() != null) sb.append("; " + cookie.getConstraint());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * Adds the given cookie to the internal list of cookies.
	 *
	 * @param rcCookie the request context cookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if(headerGenerated) throw new RuntimeException();
		outputCookies.add(rcCookie);
	}


	/**
	 * The Class RCCookie represents a single cookie with all of its properties.
	 */
	public static class RCCookie {
		
		/** The name of the cookie. */
		String name;
		
		/** The value of the cookie. */
		String value;
		
		/** The max age of the cookie. */
		Integer maxAge;
		
		/** The domain name. */
		String domain;
		
		/** The path where cookie is used. */
		String path;
		
		/** The constraint for the cookie. */
		String constraint;
		
		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name {@link #name}
		 * @param value {@link #value}
		 * @param maxAge {@link #maxAge}
		 * @param domain {@link #domain}
		 * @param path {@link #path}
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}
		
		

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name {@link #name}
		 * @param value {@link #value}
		 * @param maxAge {@link #maxAge}
		 * @param domain {@link #domain}
		 * @param path {@link #path}
		 * @param constraint {@link #constraint}
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, String constraint) {
			this(name, value, maxAge, domain, path);
			this.constraint = constraint;
		}



		/**
		 * Gets the name of the cookie.
		 *
		 * @return {@link #name}
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value of the cookie.
		 *
		 * @return {@link #value}
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Gets the max age of the cookie.
		 *
		 * @return {@link #maxAge}
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Gets the domain name where cookie is valid.
		 *
		 * @return {@link #domain}
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path where cookie is valid.
		 *
		 * @return {@link #path}
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the constraint for the cookie.
		 *
		 * @return {@link #constraint}
		 */
		public String getConstraint() {
			return constraint;
		}

	}

}
