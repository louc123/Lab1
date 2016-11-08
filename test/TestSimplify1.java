import static org.junit.Assert.*;
import org.junit.Test;


public class TestSimplify1 {

	@Test
	public void testSimplify() {
		Polynomial polynomial=new Polynomial("3*x*y+2*z");
		polynomial.Order("!simplify");
		String result=polynomial.simplify("!simplify");
		assertEquals("3*x*y+2*z",result);
	}

}
