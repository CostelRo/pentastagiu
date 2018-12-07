package course2;


 /*
  * HOMEWORK 12:
  * Write a program that will test whether a string ends with another string.
  *
  * For example:
  *     Given the following strings “Hello java world” and “orld” the output should be: TRUE
  *
  */


public class StringEndChecker
{
    public static void main( String[] args )
    {
        String target = "orld";

        String source1 = "Hello java world";
        System.out.println( StringEndChecker.checkStringEnd_1( source1, target ) );
        System.out.println( StringEndChecker.checkStringEnd_2( source1, target ) + "\n");

        String source2 = "Hello java world!";
        System.out.println( StringEndChecker.checkStringEnd_1( source2, target ) );
        System.out.println( StringEndChecker.checkStringEnd_2( source2, target ) + "\n");

        String source3 = "orld";
        System.out.println( StringEndChecker.checkStringEnd_1( source3, target ) );
        System.out.println( StringEndChecker.checkStringEnd_2( source3, target ) + "\n");

        String source4 = "";
        System.out.println( StringEndChecker.checkStringEnd_1( source4, target ) );
        System.out.println( StringEndChecker.checkStringEnd_2( source4, target ) + "\n");

        String source5 = null;
        System.out.println( StringEndChecker.checkStringEnd_1( source5, target ) );
        System.out.println( StringEndChecker.checkStringEnd_2( source5, target ) + "\n");
    }


    private static boolean checkStringEnd_1(  String source, String target )
    {
        if( source != null && target != null && source.length() > 0 && target.length() > 0 )
        {
            return target.equals( source.substring( source.length()-target.length(), source.length() ) );
        }
        else
        {
            return false;
        }
    }


    private static boolean checkStringEnd_2(  String source, String target )
    {
        return (source != null) && ( source.endsWith( target ) );
    }
}
