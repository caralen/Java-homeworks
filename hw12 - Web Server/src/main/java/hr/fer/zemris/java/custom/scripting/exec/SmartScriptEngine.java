package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class SmartScriptEngine is a class which takes a document node which is a root of the parsed tree.
 * This engine tries to compile the source code which is stored in the tree of nodes.
 */
public class SmartScriptEngine {

	/** The document node is the root of the parsed tree. */
	private DocumentNode documentNode;
	
	/** The request context which is used to form the output text and write it to output stream. */
	private RequestContext requestContext;
	
	/** The multistack is a stack-like collection made from lists. */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode the document node
	 * @param requestContext the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/** The visitor has a visit method for each of the nodes in the tree 
	 * and calls the write method for text from each node on {@link RequestContext}. */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Pushes variable to the multistack. 
		 * Iterates in a loop while variable value is less than or equal to end value.
		 * In each iteration method accept with this visitor as argument is called on each child.
		 * Variable is popped from the multistack summed with the step and pushed back to the stack.
		 * When the loop finishes variable is popped from the stack.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper value = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper endValue = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper stepValue = new ValueWrapper(node.getStepExpression().asText());
			ElementVariable variable = node.getVariable();
			
			multistack.push(variable.getName(), value);
			
			while(value.numCompare(endValue.getValue()) <= 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor); 
				}
				value = multistack.pop(variable.getName());
				value.add(stepValue.getValue());
				multistack.push(variable.getName(), value);
			}
			multistack.pop(variable.getName());
		}

		/**
		 * For each element that this EchoNode contains appropriate operation is performed.
		 * After actions are done for all elements contents of the stack is written to the output.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();
			
			for (Element element : node.getElements()) {
				if (element instanceof ElementConstantInteger) {
					tempStack.push(((ElementConstantInteger)element).getValue());
					
				} else if (element instanceof ElementConstantDouble) {
					tempStack.push(((ElementConstantDouble)element).getValue());
					
				} else if(element instanceof ElementString) {
					tempStack.push(((ElementString)element).getValue());
					
				} else if (element instanceof ElementVariable) {
					tempStack.push(multistack.peek(((ElementVariable)element).getName()));
					
				} else if(element instanceof ElementOperator) {
					ValueWrapper value1 = new ValueWrapper(tempStack.pop());
					ValueWrapper value2 = new ValueWrapper(tempStack.pop());
					ElementOperator operator = (ElementOperator) element;
					doOperation(value1, value2, operator);
					tempStack.push(value1.getValue());
					
				} else if(element instanceof ElementFunction) {
					ElementFunction function = (ElementFunction) element;
					resolveFunction(function, tempStack);
				}
			}
			int stackSize = tempStack.size();
			List<Object> list = new ArrayList<>();
			
			for(int i = 0; i < stackSize; i++) {
				list.add(tempStack.pop());
			}
			java.util.Collections.reverse(list);
			
			list.forEach(object -> {
				try {
					requestContext.write(object.toString().replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}


		/**
		 * Method accept is called on each child of the DocumentNode with this visitor in arguments.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(visitor);
			}
			
		}
	};
	
	/**
	 * Performs operation on the given values which defines the given operator.
	 *
	 * @param value1 the first value
	 * @param value2 the second value
	 * @param operator the operator
	 */
	private void doOperation(ValueWrapper value1, ValueWrapper value2, ElementOperator operator) {
		switch(operator.getSymbol()) {
		
		case "+":
			value1.add(value2);
			break;
			
		case "-":
			value1.subtract(value2);
			break;
		
		case "*":
			value1.multiply(value2);
			break;
			
		case "/":
			value1.divide(value2);
			break;
		}
	}

	/**
	 * Resolves given function. The given stack which contains values is used for performing the function.
	 * The result is then pushed to stack
	 *
	 * @param function the function that will be performed
	 * @param stack the stack used for performing the function
	 */
	private void resolveFunction(ElementFunction function, Stack<Object> stack) {
		
		switch(function.getName()) {
		
		case "sin":
			stack.push(Math.sin(Math.toRadians(Double.parseDouble(stack.pop().toString()))));
			break;
			
		case "decfmt":
			DecimalFormat f = new DecimalFormat(stack.pop().toString());
			ValueWrapper x = new ValueWrapper(stack.pop());
			stack.push(f.format(x.getValue()));
			break;
			
		case "dup":
			stack.push(stack.peek());
			break;
		
		case "swap":
			Object o1 = stack.pop();
			Object o2 = stack.pop();
			stack.push(o1);
			stack.push(o2);
			break;
			
		case "setMimeType":
			requestContext.setMimeType(stack.pop().toString());
			break;
			
		case "paramGet":
			Object defValue = stack.pop();
			String value = requestContext.getParameter(stack.pop().toString());
			stack.push(value == null ? defValue : value);
			break;
			
		case "pparamGet":
			Object pdefValue = stack.pop();
			String pvalue = requestContext.getPersistentParameter(stack.pop().toString());
			stack.push(pvalue == null ? pdefValue : pvalue);
			break;
			
		case "pparamSet":
			String name = stack.pop().toString();
			String pvalueSet = stack.pop().toString();
			requestContext.setPersistentParameter(name, pvalueSet);
			break;
			
		case "pparamDel":
			String pname = stack.pop().toString();
			requestContext.removePersistentParameter(pname);
			break;
			
		case "tparamGet":
			Object tdefValue = stack.pop();
			String tvalue = requestContext.getTemporaryParameter(stack.pop().toString());
			stack.push(tvalue == null ? tdefValue : tvalue);
			break;
			
		case "tparamSet":
			String tnameSet = stack.pop().toString();
			String tvalueSet = stack.pop().toString();
			requestContext.setTemporaryParameter(tnameSet, tvalueSet);
			break;
			
		case "tparamDel":
			String tname = stack.pop().toString();
			requestContext.removePersistentParameter(tname);
			break;
			
		default:
			throw new IllegalArgumentException("Invalid function name");
		}
	}


	/**
	 * Calls accept method on document node with visitor in arguments.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
