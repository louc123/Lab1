import static org.junit.Assert.*;

import org.junit.Test;


public class TestDerivative4 {

	@Test
	public void test() {
		Polynomial polynomial=new Polynomial("x*x*y+x*x*z");
		polynomial.Order("!d/d x");
		String result=polynomial.derivative("!d/d x");
		assertEquals("2*x*y+2*x*z",result);
	}

}
