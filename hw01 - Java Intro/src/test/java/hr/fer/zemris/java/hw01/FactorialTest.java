package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {

	
	@Test
	public void zaVelikiBroj() {
		Assert.assertEquals(2432902008176640000L, Factorial.racunajFaktorijele(20));
	}
	
	@Test
	public void zaMaliBroj() {
		Assert.assertEquals(1, Factorial.racunajFaktorijele(1));
	}
	
	@Test
	public void provjeraZaUnosNegativnogBroja() {
		Assert.assertEquals(false, Factorial.provjeraZaIspravanRaspon(-1));
	}
	
	@Test
	public void provjeraZaUnosBrojaUIntervalu() {
		Assert.assertTrue(Factorial.provjeraZaIspravanRaspon(10));
	}
	
	@Test
	public void provjeraZaUnosNaRubuIntervala() {
		Assert.assertTrue(Factorial.provjeraZaIspravanRaspon(20));
	}
}
