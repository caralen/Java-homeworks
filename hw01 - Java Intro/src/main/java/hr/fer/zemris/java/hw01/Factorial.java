package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji racuna faktorijele u rasponu od 1 do 20
 * @author Alen Carin
 *
 */
public class Factorial {

	/**
	 * Glavna metoda koja se poziva pri pokretanju programa
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int broj;
		String unos;
		
		System.out.printf("%s", "Unesite broj > ");
		while(!(unos = sc.nextLine()).equals("kraj")) {
			try {
				broj = Integer.parseInt(unos);
				if(provjeraZaIspravanRaspon(broj)) {
					System.out.println(broj + "! = " + racunajFaktorijele(broj));
				}
			} catch(NumberFormatException ex) {
				System.out.println("'" + unos + "'" + " nije cijeli broj.");
			}
			System.out.printf("%s", "Unesite broj > ");
		}
		System.out.println("Doviđenja.");
		sc.close();
	}
	
	/**
	 * Metoda koja racuna faktorijele za zadani broj.
	 * @param broj za koji racuna faktorijele tipa int
	 * @return vraca rezultat tipa long
	 */
	public static long racunajFaktorijele(int broj) {
		if(broj == 0) {
			return 1;
		}
		return broj * racunajFaktorijele(broj - 1);
	}
	
	/**
	 * Metoda koja za primljeni parametar provjerava je li u dozvoljenom rasponu.
	 * @param broj
	 * @return
	 */
	public static boolean provjeraZaIspravanRaspon(int broj) {
		if(broj < 1 || broj > 20) {
			System.out.println(broj + " nije broj u dozvoljenom rasponu.");
			return false;
		}
		return true;
	}

}
