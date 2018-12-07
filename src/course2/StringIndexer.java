package course2;


 /*
  * HOMEWORK 13:
  * Write a program that will return a different string representing the alphabet index of each letter.
  *
  * For example:
  *     Assuming we have the following alphabet encoding: a = 0, b = 1, c = 2...
  *     For the given string “abcd”, the output should be: “0123”
  */


public class StringIndexer
{
    public static void main( String[] args )
    {
        String source1 = "abcd";
        System.out.println( StringIndexer.replaceCharsWithIndexes( source1 ) );

        String source2 = "abcdk";
        System.out.println( StringIndexer.replaceCharsWithIndexes( source2 ) );

        String source3 = "";
        System.out.println( StringIndexer.replaceCharsWithIndexes( source3 ) );

        String source4 = null;
        System.out.println( StringIndexer.replaceCharsWithIndexes( source4 ) );
    }


    private static String replaceCharsWithIndexes( String source )
    {
        if( source == null ) { return null; }
        if( source.length() == 0 ) { return ""; }

        StringBuilder result = new StringBuilder("");

        for( char c : source.toCharArray() )
        {
            result.append( c - 'a' );
        }

        return result.toString();
    }
}
