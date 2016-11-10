import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative2 {

	@Test
	public void testDerivative() {
		Polynomial polynomial=new Polynomial("x*y+x*z");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("y+z",result);
	}

}
