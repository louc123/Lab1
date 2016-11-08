import static org.junit.Assert.*;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class TestSimplify4 {

	@Test
	public void test() {
		Polynomial polynomial=new Polynomial("3*x*y+2*z");
		
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		PrintStream cacheStream = new PrintStream(baoStream);
		PrintStream oldStream = System.out;	 
        System.setOut(cacheStream);
        polynomial.Order("!s");
        String message = baoStream.toString();
        System.setOut(oldStream);
        assertEquals("Order is Wrong!",message.trim());
	}

}
