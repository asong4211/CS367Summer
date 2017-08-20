import java.util.*;


public class Palindrome {

	public static boolean isPalindrome (String s){
		
		if(s.length() <= 1) return true; //base case
		return s.charAt(0) == s.charAt(s.length() - 1) &&
				isPalindrome(s.substring(1, s.length() -1));
		
	}
	
	public static void main(String[] args) {
		System.out.println(isPalindrome("eye"));
		System.out.println(isPalindrome("Frank"));
		System.out.println(isPalindrome("radar"));
		System.out.println(isPalindrome("racecar"));
		System.out.println(isPalindrome("Risetovotesir"));
		System.out.println(isPalindrome("efdsfe"));
		
	}

}
