import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative7 {

	@Test
	public void testDerivative() {
		Polynomial polynomial=new Polynomial("2*x");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("2",result);
	}

}
