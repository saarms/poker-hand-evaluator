package armstrong.sa.pokerHandEvaluator;

import java.util.Arrays;
import java.util.Iterator;

public class TestHelper {

	/**
	 * Get the method name for a depth in call stack. <br />
	 * Utility function
	 * @param depth depth in the call stack (0 means current method, 1 means call method, ...)
	 * @return method name
	 */
	public static String getCurrentMethodName()
	{
	  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

	  //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
	  // return ste[ste.length - depth].getMethodName();  //Wrong, fails for depth = 0
	  // return ste[ste.length - 1 - depth].getMethodName(); //Thank you Tom Tresansky
	  
//	  Iterator<StackTraceElement> iter = Arrays.asList(ste).iterator();
//	  while (iter.hasNext()) {
//		  System.out.println(iter.next());
//	  }
	  // Method name at point of invoking this call is two levels down:
	  // 0 is java.lang.Thread.getStackTrace
	  // 1 is [wherever is calling this].getMethodName
	  return ste[2].getMethodName();
	}
	
}
