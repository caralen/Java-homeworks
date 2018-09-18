package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The Class TreeWriter is a program which reads a single file, parses it with the <code>SmartScriptParser</code>
 * instantiates a new visitor and calls accept method on the document file which parser generated with
 * an instance of <code>WriterVisitor</code> as argument. The given file should be a smart script.
 * Visitor design pattern is used here.
 */
public class TreeWriter {

	/**
	 * The main method which is called upon the start of the program.
	 *
	 * @param args the command line arguments, path to a smart script file is expected
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Was expecting file path");
			return;
		}
		if(!args[0].endsWith(".smscr")) {
			System.out.println("The file should be a smart script");
			return;
		}
		
		String docBody;
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			System.out.println("Cannot read from the file");
			return;
		}
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	/**
	 * The Class WriterVisitor is used to accomplish visitor design pattern. It implements <code>INodeVisitor</code>.
	 * For every node it has a visit method which collects the text from that node and its children.
	 */
	public static class WriterVisitor implements INodeVisitor {
		
		/** The string builder used to collect all strings which should be written to output. */
		StringBuilder sb;
		
		/**
		 * Instantiates a new writer visitor.
		 */
		public WriterVisitor() {
			this.sb = new StringBuilder();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			sb.append(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			sb.append("{$FOR ");
			try {
				sb.append(node.getVariable().asText()).append(" ");
				sb.append(node.getStartExpression().asText()).append(" ");
				sb.append(node.getEndExpression().asText()).append(" ");
			} catch(NullPointerException ex) {
				throw new SmartScriptParserException("Invalid number of arguments in for loop");
			}
			if(node.getStepExpression() != null) {
				sb.append(node.getStepExpression().asText());
			}
			sb.append("$}");
			
			for(int i = 0; i< node.numberOfChildren(); i++) {
				node.getChild(i).accept(WriterVisitor.this);
			}
			
			sb.append("{$ END $}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			sb.append("{$=");
			for(Element element : node.getElements()) {
				sb.append(element.asText()).append(" ");
			}
			sb.append("$}");
		}

		/**
		 * {@inheritDoc}
		 * Outputs the text that string builder collected from each node in the tree.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			
			for(int i = 0; i < node.numberOfChildren(); i++ ) {
				node.getChild(i).accept(WriterVisitor.this);
			}
			
			System.out.println(sb.toString());
		}
		
	}
}
