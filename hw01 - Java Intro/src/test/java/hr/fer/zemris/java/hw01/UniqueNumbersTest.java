package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;
import static hr.fer.zemris.java.hw01.TreeNode.*;

public class UniqueNumbersTest {

	@Test
	public void praznoStabloVelicina() {
		Assert.assertEquals(0, treeSize(null));
	}
	
	@Test
	public void ispravnaVelicinaStabla() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 35);
		Assert.assertEquals(4, treeSize(head));
	}
	
	@Test
	public void provjeraZaUnosIstihCvorova() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 42);
		head = addNode(head, 42);
		head = addNode(head, 42);
		Assert.assertEquals(1, treeSize(head));
	}
	
	@Test
	public void ispravnoDodavanjeCvora() {
		TreeNode head = null;
		head = addNode(head, 5);
		Assert.assertNotNull(head);
	}
	
	@Test
	public void provjeraPretrazivanjaStabla() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 35);
		head = addNode(head, 5);
		Assert.assertTrue(containsValue(head, 21));
	}
}
