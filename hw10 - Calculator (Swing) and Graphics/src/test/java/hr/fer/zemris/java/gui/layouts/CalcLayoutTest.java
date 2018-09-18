package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {

	@Test
	public void test1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void test2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test3() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(1, 1));
		p.add(new JButton("y"), new RCPosition(1, 1));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test4() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(6, 1));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test5() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(0, 1));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test6() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(2, 0));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test7() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(2, 8));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test8() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(1, 2));
	}
	
	@Test(expected = CalcLayoutException.class)
	public void test9() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JButton("x"), new RCPosition(1, 5));
	}
}
