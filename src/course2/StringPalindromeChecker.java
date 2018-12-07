package course2;


 /*
  * HOMEWORK 15:
  * Write a program to test whether a given string is a palindrome or not.
  *
  * For example:
  *     Given the following string “aaabccbaaa” should return: TRUE
  *     Given the following string “abccbb” should return: FALSE
  */


public class StringPalindromeChecker
{
    public static void main( String[] args )
    {
        String source1 = "aaabccbaaa";
        System.out.println( source1 + " = " + StringPalindromeChecker.isPalindrome( source1 ) );

        String source2 = "abccbb";
        System.out.println( source2 + " = " + StringPalindromeChecker.isPalindrome( source2 ) );

        String source3 = "abc5cba";
        System.out.println( source3 + " = " + StringPalindromeChecker.isPalindrome( source3 ) );

        String source4 = "abc5cbb";
        System.out.println( source4 + " = " + StringPalindromeChecker.isPalindrome( source4 ) );

        String source5 = "";
        System.out.println( source5 + " = " + StringPalindromeChecker.isPalindrome( source5 ) );

        String source6 = null;
        System.out.println( source6 + " = " + StringPalindromeChecker.isPalindrome( source6 ) );
    }


    private static boolean isPalindrome( String source )
    {
        boolean result = true;

        if( source == null )
        {
            result = false;
        }
        else
        {
            char[] sourceAsArray = source.toCharArray();
            int sourceLength = sourceAsArray.length;
            for( int i=0; i < sourceLength / 2; i++ )
            {
                if( sourceAsArray[i] != sourceAsArray[sourceLength - 1 - i] )
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
