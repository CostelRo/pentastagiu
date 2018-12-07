package course2;


 /*
  * HOMEWORK 14:
  * Write a program that will insert a "#" between any duplicate letters that are placed right next to each other.
  *
  * For example:
  *     Given the following string "Hello world" the output should be: "Hel#lo world"
  */


public class DuplicateLettersSeparator
{
    public static void main( String[] args )
    {
        String separator = "#";

        String source1 = "Hello world";
        System.out.println( DuplicateLettersSeparator.separateDuplicateLetters( source1, separator ) );

        String source2 = "Hello world buzzz!";
        System.out.println( DuplicateLettersSeparator.separateDuplicateLetters( source2, separator ) );

        String source3 = "Hey world";
        System.out.println( DuplicateLettersSeparator.separateDuplicateLetters( source3, separator ) );

        String source4 = "";
        System.out.println( DuplicateLettersSeparator.separateDuplicateLetters( source4, separator ) );

        String source5 = null;
        System.out.println( DuplicateLettersSeparator.separateDuplicateLetters( source5, separator ) );
    }


    private static String separateDuplicateLetters( String source, String separator )
    {
        if( source == null || source.length() == 0 ) { return source; }

        char[] sourceAsArray = source.toCharArray();
        StringBuilder result = new StringBuilder("");
        result.append( sourceAsArray[0] );
        for( int i=1; i < sourceAsArray.length; i++ )
        {
            result.append( (sourceAsArray[i] == sourceAsArray[i-1])
                            ? separator
                            : "" );
            result.append( sourceAsArray[i] );
        }

        return result.toString();
    }
}
