import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative1 {

	@Test
	public void testDerivative1() {
		Polynomial polynomial=new Polynomial("x*x+x*x");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("4*x",result);
	}

}
