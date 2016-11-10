import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative5 {

	@Test
	public void testDerivative() {
		Polynomial polynomial=new Polynomial("0*x");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("0",result);
	}

}
