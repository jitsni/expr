import java.util.*;

/*
Given two strings s1 and s2, combine the characters in the strings and maintain the sequence
of characters
Follow-up: If s1 has a length of m and s2 has a length of n, how many ways the strings
could be merged. Figure out the formula F(m, n) = ?
*/

/*
    (m+n)!
    ------
    m! n!
*/

public class InterleaveStrings {

    public static void main(String ... args) {
        List<String> all = new ArrayList<>();
        combinations("123", 0, "45", 0, "", all);
        System.out.println(all);
    }

    private static void combinations(String str1, int i, String str2, int j, String current, List<String> all) {
        if (current.length() == str1.length() + str2.length()) {
            all.add(current);
            return;
        }
        if (i < str1.length()) {
            combinations(str1, i+1, str2, j, current + str1.charAt(i), all);
        }
        if (j < str2.length()) {
            combinations(str1, i, str2, j + 1, current + str2.charAt(j), all);
        }
    }

}
