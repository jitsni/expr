/*
The count-and-say sequence is the sequence of integers beginning as follows:

1, 11, 21, 1211, 111221, ...
1 is read off as one 1 or 11.
11 is read off as two 1s or 21.

21 is read off as one 2, then one 1 or 1211.

Given an integer n, generate the nth sequence.

Note: The sequence of integers will be represented as a string.

Example:

if n = 2,
the sequence is 11.
*/

public class CountAndSay {

    public static void main(String... args) {
        int n = 5;
        String cur = "1";
        System.out.println(cur);
        for(int i=0; i < n; i++) {
            String next = next(cur);
            System.out.println(next);
            cur = next;
        }
    }

    static String next(String str) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while(i < str.length()) {
            int count = times(str, i);
            sb.append(count);
            sb.append(str.charAt(i));
            i += count;
        }
        return sb.toString();
    }

    static int times(String str, int i) {
        int count = 0;
        char ch = str.charAt(i);
        while(i < str.length() && str.charAt(i) == ch) {
            i++;
            count++;
        }
        return count;
    }

}
