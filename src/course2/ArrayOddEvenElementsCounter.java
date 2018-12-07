package course2;


 /*
 * HOMEWORK 9:
 * Write a Java program to find the number of even and odd integers in a given array of integers.
 *
 * For example:
 *     Given the following array [13,20,14,5,2,8] the output should be:
 *         Odd elements count : 2
 *         Even elements count : 4
 */


public class ArrayOddEvenElementsCounter
{
    public static void main( String[] args )
    {
        int[] arr1 = new int[]{ 13, 20, 14, 5, 2, 8 };
        System.out.println( ArrayOddEvenElementsCounter.displayPrettyResults(
                                                ArrayOddEvenElementsCounter.countOddAndEvenArrayElements( arr1 ) ) );

        int[] arr2 = new int[]{ 20, 14, 2, 8 };
        System.out.println( ArrayOddEvenElementsCounter.displayPrettyResults(
                                                ArrayOddEvenElementsCounter.countOddAndEvenArrayElements( arr2 ) ) );

        int[] arr3 = new int[]{ 13, 5, -77777 };
        System.out.println( ArrayOddEvenElementsCounter.displayPrettyResults(
                                                ArrayOddEvenElementsCounter.countOddAndEvenArrayElements( arr3 ) ) );

        int[] arr4 = new int[0];
        System.out.println( ArrayOddEvenElementsCounter.displayPrettyResults(
                                                ArrayOddEvenElementsCounter.countOddAndEvenArrayElements( arr4 ) ) );

        int[] arr5 = null;
        System.out.println( ArrayOddEvenElementsCounter.displayPrettyResults(
                                                ArrayOddEvenElementsCounter.countOddAndEvenArrayElements( arr5 ) ) );
    }


    private static int[] countOddAndEvenArrayElements( int[] source )
    {
        if( source == null || source.length == 0 ) { return null; }

        int[] result = new int[2];

        int counterEven = 0;
        for( int number : source )
        {
            if( number % 2 == 0 )
            {
                counterEven++;
            }
        }

        result[0] = counterEven;
        result[1] = source.length - counterEven;

        return result;
    }


    private static String displayPrettyResults( int[] source )
    {
        if( source == null || source.length == 0 ) { return "(No data.)\n"; }

        return "Odd elements count : " + source[1] + "\n"
               + "Even elements count : " + source[0] + "\n";
    }
}
