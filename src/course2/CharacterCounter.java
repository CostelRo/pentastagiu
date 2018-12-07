package course2;


 /*
  * HOMEWORK 11:
  * Write a program that will count how many times a character appears in a String.
  *
  * For example:
  *     Given the string “Hello java world” and the target character ‘a’ the output should be:
  *         2 (since ‘a’ appears twice in “Hello java world”)
  *
  */


public class CharacterCounter
{
    public static void main( String[] args )
    {
        char target = 'a';

        String source1 = "Hello java world";
        System.out.println( CharacterCounter.charCounter( source1, target ) + "\n" );

        String source2 = "Hello juju world";
        System.out.println( CharacterCounter.charCounter( source2, target ) + "\n" );

        String source3 = "";
        System.out.println( CharacterCounter.charCounter( source3, target ) + "\n" );

        String source4 = null;
        System.out.println( CharacterCounter.charCounter( source4, target ) + "\n" );
    }


    private static int charCounter( String source, char target )
    {
        if( source == null || source.length() == 0 ) { return 0; }

        int result = 0;

        for( char c : source.toCharArray() )
        {
            result += (c == target)
                       ? 1
                       : 0;
        }

        return result;
    }
}
