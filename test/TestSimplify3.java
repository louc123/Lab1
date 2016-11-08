import static org.junit.Assert.*;

import org.junit.Test;


public class TestSimplify3 {

	@Test
	public void test() {
		Polynomial polynomial=new Polynomial("3*x*y+2*z");
		polynomial.Order("!simplify x=2 y=3 z=0");
		String result=polynomial.simplify("!simplify x=2 y=3 z=0");
		assertEquals("18",result);
	}

}
