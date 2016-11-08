import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;


public class TestSimplify9 {

	@Test
	public void test() {
		Polynomial polynomial=new Polynomial("3*x*y+2*z");
		
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		PrintStream cacheStream = new PrintStream(baoStream);
		PrintStream oldStream = System.out;	 
        System.setOut(cacheStream);
        polynomial.Order("!simplify x=-2");
        String message = baoStream.toString();
        System.setOut(oldStream);
        assertEquals("Order is Wrong!",message.trim());
	}

}
