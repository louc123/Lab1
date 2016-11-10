import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative6 {

	@Test
	public void testDerivative() {
		Polynomial polynomial=new Polynomial("x*y+0*x");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("y",result);
	}

}
