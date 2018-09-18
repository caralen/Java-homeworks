package hr.fer.zemris.java.hw16.trazilica;

import static java.lang.Math.log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * The Class Konzola is the main class of the program which reads a foder of text files and creates a vocabulary of words.
 * Then for each text file that represents an article a vector is created.
 * Each vector stores occurrences of each word from the vocabulary in that text file for which the vector is built.
 * 
 *  <P>The program then interacts with the user.
 *  There are four different commands that user can type:
 *  <li>query - for searching articles that contain words that are written after the query keyword
 *  <li>type - for showing the text of the article on the screen 
 *  after the query is already done ("type [0,10]" is the correct format of the command)
 *  <li>results - for showing results of the query again (only after the query has already been done)
 *  <li>exit - for exiting the application
 */
public class Konzola {
	
	/** The vocabulary set. */
	private static Set<String> vocabularySet = new HashSet<>();
	
	/** The list of strings that represent vocabulary. */
	private static List<String> vocabulary = new ArrayList<>();
	
	/** The documents map, it contains path and vector of the document. */
	private static Map<Path, Vector> documents = new HashMap<>();
	
	/** The idf vector. */
	private static Vector idf;
	
	/** The map that contains result of the query. */
	private static Map<Double, Path> result;

	/**
	 * The main method which is called at the start of the program.
	 * Program must be started with a path to the folder containing text files in arguments.
	 *
	 * @param args the command line arguments, should contain path to the folder containing text files
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Application must be started with a single argument. "
					+ "Path to the root folder containing files that should be loaded");
			return;
		}
		
		Path rootDir = Paths.get(args[0]);
		Path stopWords = Paths.get("./src/main/resources/hrvatski_stoprijeci.txt");
		
		if(!Files.isDirectory(rootDir)) {
			System.out.println("Given path is not a directory");
		}
		
		try {
			buildVocabulary(rootDir, stopWords);
			buildVectors(rootDir);
			buildIDF();
			multiplyVectorsIDF();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		System.out.println("Veličina rječnika je " + vocabulary.size() + " riječi");
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			
			System.out.print("\nEnter command > ");
			String line = sc.nextLine();
			
			if (line.equals("exit")) {
				break;

			} else if (line.startsWith("query ")) {
				Vector tf = createVector(line);
				tf.multiplyElements(idf);
				result = findSimilar(tf);
				printSimilar(result);

			} else if (line.startsWith("type ")) {
				if(result == null) {
					System.out.println("Potrebno je prvo napraviti upit!");
				} else {
					printDocument(line);
				}
				
			} else if (line.equals("results")) {
				if(result == null) {
					System.out.println("Potrebno je prvo napraviti upit!");
				} else {
					printSimilar(result);
				}
				
			} else {
				System.out.println("Nepoznata naredba.");
			}
		}
		sc.close();
	}


	/**
	 * Reads files from the folder at the given path and builds the vocabulary.
	 *
	 * @param rootDir the path to the root directory containing text files
	 * @param stopWordsPath the path to the file containing stop words
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void buildVocabulary(Path rootDir, Path stopWordsPath) throws IOException {
		List<String> stopWords = Files.readAllLines(stopWordsPath);
		
		/**
		 * Anonymous class for building vocabulary.
		 */
		Builder vocabularyBuilder = new Builder() {
			@Override
			public void build(StringBuilder sb, Vector v) {
				if(sb.length() != 0 && !stopWords.contains(sb.toString())) {
					vocabularySet.add(sb.toString());
				}
				sb.setLength(0);
			}
		};
		
		Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				
				build(null, file, vocabularyBuilder);
				return FileVisitResult.CONTINUE;
			}
		});
		vocabulary.addAll(vocabularySet);
		vocabularySet.clear();
	}

	/**
	 * Iterates through each file in the folder at the given path and builds a vector for each text file.
	 *
	 * @param rootDir the path to the root directory containing text files
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void buildVectors(Path rootDir) throws IOException {
		
		/**
		 * Anonymous class for building vectors.
		 */
		Builder vectorBuilder = new Builder() {
			@Override
			public void build(StringBuilder sb, Vector v) {
				int index = vocabulary.indexOf(sb.toString());
				if(index != -1) {
					v.increment(vocabulary.indexOf(sb.toString()));
				}
				sb.setLength(0);
			}
		};
		
		
		Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Vector vector = new Vector(vocabulary.size());
				build(vector, file, vectorBuilder);
				documents.put(file, vector);
				return FileVisitResult.CONTINUE;
			}

		});
	}
	
	/**
	 * Reads the file at the given path and splits it into words.
	 * On each word that doesn't contain non-alphabetic symbols, method of the given builder is called.
	 *
	 * @param v the vector
	 * @param file the path to the file
	 * @param builder the implementation of the <code>Builder</code> interface
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void build(Vector v, Path file, Builder builder) throws IOException {
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		
		StringBuilder sb = new StringBuilder();
		for(String line : lines) {
			char[] chars = line.toLowerCase().toCharArray();
			
			for (int i = 0; i < chars.length; i++) {
				if (Character.isAlphabetic(chars[i])) {
					sb.append(chars[i]);
				} else {
					builder.build(sb, v);
				}
			}
			builder.build(sb, v);
		}
	}


	/**
	 * Builds the inverse document frequency vector.
	 * It is a vector that for each word in the vocabulary contains a value which is equal to the number of documents
	 * from which the vocabulary is built divided by the number of documents that contain that word.
	 */
	private static void buildIDF() {
		int numberOfDocs = documents.size();
		int vocabularySize = vocabulary.size();
		idf = new Vector(vocabularySize);
		
		int appereanceCounter;
		
		for(int i = 0; i < vocabularySize; i++) {
			appereanceCounter = 0;
			
			for(Vector vector : documents.values()) {
				if(vector.get(i) > 0) {
					appereanceCounter++;
				}
			}
			idf.setValue(i, log(numberOfDocs / (double) appereanceCounter));
		}
	}
	
	/**
	 * Parses given query line and prints the document that is stored at the given index which the line must contain.
	 * The correct format of the line should be "type [0,10]".
	 *
	 * @param line the line that contains the index of the document stored in the result map that should be printed.
	 */
	private static void printDocument(String line) {
		String parts[] = line.split("\\s+");
		
		if(parts.length != 2) {
			System.out.println("Invalid arguments for the type command, should be \"type number\"");
		}
		
		int index;
		try {
			index = Integer.parseInt(parts[1]);
		} catch(NumberFormatException e) {
			System.out.println("Invalid arguments for the type command, should be \"type number\"");
			return;
		}
		
		List<Path> list = new ArrayList<>(result.values());
		Path doc = list.get(index);
		try {
			List<String> lines = Files.readAllLines(doc);
			lines.stream().forEach(e -> System.out.println(e));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
	}


	/**
	 * Finds vectors that are similar to the given vector.
	 * The given term frequency is first multiplied with the {@link #idf} vector and then compared
	 * with vectors of all documents which are stored in the map {@link #documents}
	 *
	 * @param tf the term frequency vector
	 * @return the map that contains the result of the search
	 */
	private static Map<Double, Path> findSimilar(Vector tf) {
		Map<Double, Path> similarVectors = new TreeMap<>(Collections.reverseOrder());
		
		for(Map.Entry<Path, Vector> doc : documents.entrySet()) {
			double similarity = tf.cosAngle(doc.getValue());
			if(similarity > 0.0) {
				similarVectors.put(similarity, doc.getKey());
			}
		}
		return similarVectors;
	}


	/**
	 * Prints the top 10 results from the similar vectors map.
	 *
	 * @param similarVectors the similar vectors map
	 */
	private static void printSimilar(Map<Double, Path> similarVectors) {
		int counter = 0;
		
		System.out.println("Najboljih 10 rezultata:");
		for(Map.Entry<Double, Path> entry : similarVectors.entrySet()) {
			if(counter >= 10) break;
			System.out.println(String.format("[%d] %.4f %s", counter, entry.getKey(), entry.getValue()));
			counter++;
		}
	}


	/**
	 * Creates the vector from the given string line.
	 * The line should contain words separated by a space.
	 * For the given line a term frequency vector is built of the same size as the vocabulary
	 * which has the contains number of occurrences for each word in the line.
	 * Those words which are not in the vocabulary are dropped from the query.
	 *
	 * @param line the line
	 * @return the vector
	 */
	private static Vector createVector(String line) {
		String[] parts = line.split("\\s+");
		Vector tf = new Vector(vocabulary.size());
		List<String> query = new ArrayList<>();
		
		for(int i = 1; i < parts.length; i++) {
			int index = vocabulary.indexOf(parts[i]);
			if(index != -1) {
				tf.increment(index);
				query.add(parts[i]);
			}
		}
		System.out.println("Query is: " + query.toString());
		return tf;
	}
	
	/**
	 * Multiplies vectors from the documents map with the vectors inverse document frequency vector.
	 */
	private static void multiplyVectorsIDF() {
		for(Vector v : documents.values()) {
			v.multiplyElements(idf);
		}
	}
}
