package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.StringHelper;

public class Test {

	public static void main(String[] args) {
		String input = ("I am Lê Xuân Hiếu and I am 20");
		List<String> list = new ArrayList<>();
		list.add("sadfsagsag");
		list.add("lê Xuân hiếua");
		
        

       

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
