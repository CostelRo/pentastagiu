package course2;


 /*
 * HOMEWORK 10:
 * Write a program that test if a given String contains another String.
 *
 * For example:
 *     Given the following base String “Hello java world” and the target test String “java”,
 *     the output should be: TRUE
 */


public class StringInclusionChecker
{
    public static void main( String[] args )
    {
        String target = "java";

        String source1 = "Hello java world";
        System.out.println( checkStringInclusion_1( source1, target ) );
        System.out.println( checkStringInclusion_2( source1, target ) + "\n" );

        String source2 = "Hello javascript world";
        System.out.println( checkStringInclusion_1( source2, target ) );
        System.out.println( checkStringInclusion_2( source2, target ) + "\n" );

        String source3 = "Hello juva world";
        System.out.println( checkStringInclusion_1( source3, target ) );
        System.out.println( checkStringInclusion_2( source3, target ) + "\n" );

        String source4 = "";
        System.out.println( checkStringInclusion_1( source4, target ) );
        System.out.println( checkStringInclusion_2( source4, target ) + "\n" );

        String source5 = null;
        System.out.println( checkStringInclusion_1( source5, target ) );
        System.out.println( checkStringInclusion_2( source5, target ) );
    }


    private static boolean checkStringInclusion_1( String source, String target )
    {
        boolean result = false;

        if( source != null && target != null && source.length() != 0 && target.length() != 0 )
        {
             int index = 0;
             while( index <= (source.length() - target.length()) )
             {
                 if( target.equals( source.substring( index, index + target.length() ) ) )
                 {
                     result = true;
                     break;
                 }

                 index++;
             }
        }

        return result;
    }


    private static boolean checkStringInclusion_2( String source, String target )
    {
        return (source != null) && ( source.contains( target ) );
    }
}
