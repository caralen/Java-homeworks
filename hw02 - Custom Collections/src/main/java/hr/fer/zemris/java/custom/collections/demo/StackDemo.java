package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demo class used to test operations with collection StackObject.
 * This class deals with postfix operations with numbers.
 * @author Alen Carin
 *
 */
public class StackDemo {

	/**
	 * Method which is called upon start of the programme
	 * @param args (array of arguments) - enclose your expression in quotation marks, use only one argument
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}
		else {
			ObjectStack stack = new ObjectStack();
			
			String[] entry = args[0].split(" +");
			
			for(int i = 0; i < entry.length; i++) {
				if(isNumber(entry[i])) {
					try{
						stack.push(Integer.parseInt(entry[i]));
					} catch(NumberFormatException ex) {
						System.out.println("The push() method did not get correct argument");
					}
				}
				else {
					int first = 0;
					int second = 0;
					try {
						first = (int) stack.pop();
						second = (int) stack.pop();
					} catch(EmptyStackException ex) {
						System.out.println(ex.getMessage());
						System.exit(1);
					}

					switch (entry[i]) {
					
					case "+":
						stack.push(second + first);
						break;

					case "-":
						stack.push(second - first);
						break;

					case "/":
						if(first == 0) {
							System.out.println("Dear user, division by 0 is prohibited");
							System.exit(1);
						}
						stack.push(second / first);
						break;

					case "*":
						stack.push(second * first);
						break;

					default:
						System.out.println("Invalid expression is entered");
						System.exit(1);
					}
				}
			}
			System.out.println(stack.pop());
		}
	}
	
	public static boolean isNumber(String entry) {
		entry = entry.replace("-", "");
		for(char c : entry.toCharArray()){
			if(!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
}
