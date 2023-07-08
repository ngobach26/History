package helper;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
	
	//check whether a string starts with digits
	public static boolean startsWithDigit(String text) {
        Pattern pattern = Pattern.compile("^\\d+\\.");
        return pattern.matcher(text).find();
    }
	
	//convert year String to int
	public static int convertYearStringToInt(String yearStr) {
		try {
			int yearInt;
			if (yearStr.contains("TCN")) {
				yearInt = -(Integer.parseInt(yearStr.replaceAll("[^\\d]", "")));
			}
			else if (yearStr.contains("nay")) {
				yearInt = Integer.MAX_VALUE - 1;
			}
			else {
				yearInt = Integer.parseInt(yearStr.replace("CN", "").trim());
			}
			return yearInt;
		} catch (Exception e) {
			e.printStackTrace();
			return Integer.MIN_VALUE;
		}
		
	}
	
	//remove String part that contains unwanted substring
	public static String removeUnwanted(String text, String unwanted) {
		String textArr[] = text.split("[-:\\?]");
		if (textArr.length == 2) {
			if (textArr[0].contains(unwanted)) {
				return textArr[1].trim();
			}
		}
		return textArr[0].trim();
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
	
	public static boolean containString(Set<String> set, String comparedString) {
		for (String string : set) {
			if (string.equalsIgnoreCase(comparedString)) {
				return true;
			}
		}
		return false;
	}

}

