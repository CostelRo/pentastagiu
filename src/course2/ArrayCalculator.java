package course2;


/*
 * HOMEWORK 7:
 * Write a java program that will return the min, max and average values in a given, unsorted, array.
 *
 * For example: Given the following array [13,20,14,5,2,8] the output should be:
 *     Max : 20
 *     Min : 2
 *     Average: 10.3
 */


public class ArrayCalculator
{
    public static void main( String[] args )
    {
        int[] arr1 = new int[]{ -13, -20, -14, 5, 2, 8 };
        System.out.println( ArrayCalculator.fancyDisplayResults( ArrayCalculator.processArray( arr1 ) ) );

        int[] arr2 = new int[0];
        System.out.println( ArrayCalculator.fancyDisplayResults( ArrayCalculator.processArray( arr2 ) ) );

        int[] arr3 = null;
        System.out.println( ArrayCalculator.fancyDisplayResults( ArrayCalculator.processArray( arr3 ) ) );

        int[] arr4 = new int[]{ -13, 20, 14, 5, 2, 8 };
        System.out.println( ArrayCalculator.fancyDisplayResults( ArrayCalculator.processArray( arr4 ) ) );

        int[] arr5 = new int[]{ -13, -20, -14, 5, 2, 8 };
        System.out.println( ArrayCalculator.fancyDisplayResults( ArrayCalculator.processArray( arr5 ) ) );
    }


    private static int[] processArray( int[] source )
    {
        if( source == null ) { return null; }
        if( source.length == 0 ) { return new int[0]; }

        int[] result = new int[4];

        int max = source[0];
        int min = source[0];
        int total = 0;
        for( int number : source )
        {
            if( number < min )
            {
                min = number;
            }
            else if( number > max )
            {
                max = number;
            }
            total += number;
        }
        result[0] = max;
        result[1] = min;
        result[2] = total;
        result[3] = source.length;

        return result;
    }


    private static String fancyDisplayResults( int[] source )
    {
        if( (source == null) || ( source.length < 4 ) ) { return "\n(No data.)"; }

        return "\nMax : " + source[0] + "\n"
               + "Min : " + source[1] + "\n"
               + "Average : " + ( (double) source[2] / source[3] );
    }
}
