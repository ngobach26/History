package helper;

import java.util.List;

public class StringHelper {
	
	//check whether a string contains any number
	public static boolean containsNumber(String str) {
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {
				return true; 
			}
		}
		return false;
	}
	
	//check whether a string contains any given substrings (case insensitive)
	public static boolean containsSubstrings(String str, String... substrings) {
		for (String substr : substrings) {
	        if (str.toLowerCase().contains(substr.toLowerCase())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	//check whether a list of strings contains a given string (case insensitive)
	public static boolean containString(List<String> list, String comparedString) {
		for (String string : list) {
			if (string.equalsIgnoreCase(comparedString)) {
				return true;
			}
		}
		return false;
	}

}
