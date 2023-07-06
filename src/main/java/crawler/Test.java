package crawler;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		String input = "Ông Trịnh Khắc Phục".replaceAll("(?i)(ông|cụ)", "");
		System.out.println(input);
        

       

	}
	
	public static boolean containsAny(String str, String... substrings) {
		for (String substr : substrings) {
	        if (str.toLowerCase().contains(substr.toLowerCase())) {
	            return true;
	        }
	    }
	    return false;
	}

}
