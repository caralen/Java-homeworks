package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class used for testing the implementation of the <code>PrimListDemo</code>.
 * @author Alen Carin
 *
 */
public class PrimListModelTest {
	
	@Test
	public void test1() {
		PrimListModel model = new PrimListModel();
		
		assertTrue(model.getSize() == 1);
	}
	
	@Test
	public void test2() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(Integer.valueOf(1), model.getElementAt(0));
	}
	
	@Test
	public void test3() {
		PrimListModel model = new PrimListModel();
		
		int[] array = {1, 2, 3, 5, 7, 11, 13};
		
		for(int i = 0; i < 7; i++) {
			model.next();
		}
		
		for(int i = 0; i < 7; i++) {
			assertEquals(Integer.valueOf(array[i]), model.getElementAt(i));
		}
	}
}
