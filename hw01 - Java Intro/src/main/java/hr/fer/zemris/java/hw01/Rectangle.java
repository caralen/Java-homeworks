package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji za unos 2 realna broja koji predstavljaju širinu i visinu pravokutnika,
 * računa njegovu površinu i opseg.
 * @author Alen Carin
 *
 */
public class Rectangle {
	
	public static Scanner sc = new Scanner(System.in);

	/**
	 * Početna metoda programa koja poziva druge metode za pronalazak parametara
	 * i računanje i ispisivanje površine i opsega.
	 * @param args - prima 2 realna broja preko naredbenog retka
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			izracunajIIspisi(args[0], args[1]);
		} else if (args.length == 0) {
			String sirina = nadjiParametar("širinu");
			String visina = nadjiParametar("visinu");
			izracunajIIspisi(sirina, visina);
		} else {
			System.out.println("Unesen krivi broj argumenata");
		}
		sc.close();
	}

	/**
	 * Metoda koja prima 2 parametra u obliku Stringa, ako može pretvara ih u double
	 * i računa opseg i površinu te ih ispisuje, a ako ne može ispisuje prikladnu poruku.
	 * @param stringSirina - referenca na širinu
	 * @param stringVisina - referenca na visinu
	 */
	public static void izracunajIIspisi(String stringSirina, String stringVisina) {
		try {
			double sirina = Double.parseDouble(stringSirina);
			double visina = Double.parseDouble(stringVisina);
			System.out.println("Pravokutnik širine " + sirina + " i visine " + visina + " ima površinu "
					+ (sirina * visina) + " te opseg " + (2*sirina + 2*visina));
		} catch (NumberFormatException ex) {
			System.out.println("Parametri se ne mogu protumaciti kao broj");
		}
	}

	/**
	 * Metoda koja od korisnika traži da upiše širinu i visinu u obliku pozitivnog realnog broja, 
	 * inače ispisuje prikladnu poruku.
	 * @param stranica - referenca na ime stranice koju traži da korisnik unese
	 * @return vraća objekt tipa String - referenca na unesen parametar od strane korisnika
	 */
	public static String nadjiParametar(String stranica) {
		String unos = null;
		do {
			System.out.printf("%s%s%s", "Unesite ", stranica, " > ");
			try {
				unos = sc.next();
				double rezultat = Double.parseDouble(unos);
				if (rezultat < 0.0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else {
					break;
				}
			} catch (NumberFormatException ex) {
				System.out.printf("'%s' se ne može protumačiti kao broj.\n", unos);
			}
		} while (true);
		return unos;
	}

}
