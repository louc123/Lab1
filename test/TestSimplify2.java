import static org.junit.Assert.*;

import org.junit.Test;


public class TestSimplify2 {

	@Test
	public void testSimplify() {
		Polynomial polynomial=new Polynomial("3*x*y+2*z");
		polynomial.Order("!simplify x=1 y=2");
		String result=polynomial.simplify("!simplify x=1 y=2");
		assertEquals("6+2*z",result);
	}

}
