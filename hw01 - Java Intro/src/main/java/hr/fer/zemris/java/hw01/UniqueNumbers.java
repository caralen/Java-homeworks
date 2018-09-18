package hr.fer.zemris.java.hw01;

import java.util.Scanner;
import static hr.fer.zemris.java.hw01.TreeNode.addNode;
import static hr.fer.zemris.java.hw01.TreeNode.printMaxToMin;
import static hr.fer.zemris.java.hw01.TreeNode.printMinToMax;

/**
 * Program koji za unesene vrijednosti gradi i ispisuje stablo
 * @author Alen Carin
 *
 */
public class UniqueNumbers {

	/**
	 * Glavna metoda koja se poziva pri pokretanju programa. 
	 * Ona trazi od korisnika da unosi vrijednosti 
	 * pa zove ostale metode za operacije nad stablom.
	 * @param args
	 */
	public static void main(String[] args) {

		TreeNode head = null;
		Scanner sc = new Scanner(System.in);
		String unos = null;
		do {
			System.out.printf("%s", "Unesite broj > ");
			try {
				unos = sc.next();
				if(unos.equals("kraj")) {
					break;
				}
				int broj = Integer.parseInt(unos);
				head = addNode(head, broj);
			} catch (NumberFormatException ex) {
				System.out.printf("'%s' nije cijeli broj\n", unos);
			}
		} while(true);
		
		System.out.printf("\n%s", "Ispis od najmanjeg: ");
		printMinToMax(head);

		System.out.printf("\n%s", "Ispis od najveceg: ");
		printMaxToMin(head);
		
		sc.close();
	}
}
