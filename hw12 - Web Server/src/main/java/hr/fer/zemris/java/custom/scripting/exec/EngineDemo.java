package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The Class EngineDemo is a demo class used for testing the system for executing a smart script and printing out the result.
 * The system is composed of: SmartScriptParser, SmartScriptEngine and RequestContext.
 */
public class EngineDemo {

	/**
	 * The main method which is called upon the start of the program.
	 *
	 * @param args the command line arguments, path to a file should be passed
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("File path was expected");
			return;
		}
		
		String documentBody;
		try {
			documentBody = readFromDisk(args[0]);
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		
//		persistentParameters.put("brojPoziva", "3");
		
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				rc
		).execute();
//		System.out.println("\nVrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Reads contents of the file from disk.
	 *
	 * @param path the path to a file
	 * @return the contents of the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readFromDisk(String path) throws IOException {
		return new String(
				Files.readAllBytes(Paths.get(path)),
				StandardCharsets.UTF_8
		);
	}

}
