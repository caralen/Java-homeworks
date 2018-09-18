package hr.fer.zemris.java.hw01;

/**
 * Pomocna klasa koja predstavlja strukturu stabla.
 * Ima 3 clanske varijable koje predstavljaju lijevo i desno dijete te vrijednost.
 * @author Alen Carin
 *
 */
public class TreeNode {
	TreeNode left;
	TreeNode right;
	int value;

	/**
	 * Metoda za dodavanje cvora u stablo.
	 * @param head - glava stabla
	 * @param newValue - vrijednost koju je potrebno unijet u stablo
	 * @return metoda vraca referencu na pocetni element stabla
	 */
	public static TreeNode addNode(TreeNode head, int newValue) {
		if (head == null) {
			head = new TreeNode();
			head.value = newValue;
			System.out.println("Dodano.");
		} else if (newValue > head.value) {
			head.right = addNode(head.right, newValue);
		} else if (newValue < head.value) {
			head.left = addNode(head.left, newValue);
		} else {
			System.out.println("Cvor koji ima vrijednost " + newValue + " vec postoji." + " Preskačem.");
		}
		return head;
	}

	/**
	 * Metoda izracunava koliko cvorova ima u stablu
	 * @param head - glava stabla
	 * @return vraca int - broj cvorova u stablu
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		return 1 + treeSize(head.left) + treeSize(head.right);
	}

	/**
	 * Metoda koja provjerava postoji li cvor u stablu sa zadanom vrijednoscu.
	 * @param head - glava stabla
	 * @param value - vrijednost za koju pretrazuje stablo
	 * @return vraca boolean - ako postoji cvor s navedenom vrijednoscu vraca true, inace false
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		if (head.value == value) {
			return true;
		}
		if (value < head.value) {
			return containsValue(head.left, value);
		} 
		else {
			return containsValue(head.right, value);
		}
	}

	/**
	 * Ispis stabla od najmanjeg elementa do najveceg.
	 * @param head - glava stabla
	 */
	public static void printMinToMax(TreeNode head) {
		if (head == null) {
			return;
		}
		printMinToMax(head.left);
		System.out.printf("%s ", head.value);
		printMinToMax(head.right);
	}

	/**
	 * Ispis stabla od najveceg elementa do najmanjeg.
	 * @param head - glava stabla
	 */
	public static void printMaxToMin(TreeNode head) {
		if (head == null) {
			return;
		}
		printMaxToMin(head.right);
		System.out.printf("%s ", head.value);
		printMaxToMin(head.left);
	}
}