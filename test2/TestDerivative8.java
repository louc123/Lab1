import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative8 {

	@Test
	public void testDerivative() {
		Polynomial polynomial=new Polynomial("x*y+2*x");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("2+y",result);
	}

}
