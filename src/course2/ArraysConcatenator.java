package course2;


/*
 * HOMEWORK 6:
 * Write a java program that will concatenate 2 arrays.
 *
 * For example:
 *     Given 2 arrays [1,2,3,4] and [5,6,7,8] the output should be a new array containing [1,2,3,4,5,6,7,8]
 */


public class ArraysConcatenator
{
    public static void main( String[] args )
    {
        int[] arr1_1 = new int[]{ 1, 2, 3, 4 };
        int[] arr1_2 = new int[]{ 5, 6, 7, 8 };
        System.out.println( ArraysConcatenator.fancyDisplayArrayContents(
                                                        ArraysConcatenator.concatenateTwoArrays( arr1_1, arr1_2 ) ) );

        int[] arr2_1 = new int[0];
        int[] arr2_2 = new int[]{ 15, 17, 28 };
        System.out.println( ArraysConcatenator.fancyDisplayArrayContents(
                ArraysConcatenator.concatenateTwoArrays( arr2_1, arr2_2 ) ) );


        int[] arr3_1 = new int[]{ 1, 2, 3, 4 };
        int[] arr3_2 = null;
        System.out.println( ArraysConcatenator.fancyDisplayArrayContents(
                ArraysConcatenator.concatenateTwoArrays( arr3_1, arr3_2 ) ) );
    }


    private static int[] concatenateTwoArrays( int[] arr1, int[] arr2 )
    {
        int[] result = null;

        if( arr1 == null )
        {
            if( arr2 == null )
            {
                return result;
            }
            else
            {
                result = arr2;
            }
        }
        else if( arr2 == null )
        {
            result = arr1;
        }
        else
        {
            result = new int[ arr1.length + arr2.length ];
            for( int i=0; i < result.length; i++ )
            {
                result[i] = (i < arr1.length)
                            ? arr1[i]
                            : arr2[i - arr1.length];
            }
        }

        return result;
    }


    private static String fancyDisplayArrayContents( int[] source )
    {
        if( source == null ) { return ""; }
        if( source.length == 0 ) { return "[]"; }

        StringBuilder result = new StringBuilder("");

        for( int i=0; i < source.length; i++ )
        {
            result.append( source[i] ).append( (i < source.length-1) ? ", " : "" );
        }

        return result.insert( 0, "[" ).append( "]" ).toString();
    }
}
