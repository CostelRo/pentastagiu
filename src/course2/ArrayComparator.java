package course2;


/*
 * HOMEWORK 5:
 * Write a program that tests whether 2 int arrays are equal:
 * - both arrays have the same number of elements;
 * - an element on a specific index in first array is equal to the element on the same index in the second array.
 *
 * For example:
 *     For the following arrays [1,2,3,4] and [1,2,3,4] the output should be TRUE
 *     For the following arrays [] and [] the output should be TRUE
 *     For the following arrays [1,3,2] and [1,3] the output should be FALSE
 *     For the following arrays [1,2,3,4] and [1,2,4,3] the output should be FALSE
 */


public class ArrayComparator
{
    public static void main( String[] args )
    {
        int[] arr1_1 = new int[]{ 1, 2, 3, 4 };
        int[] arr1_2 = new int[]{ 1, 2, 3, 4 };
        System.out.println( ArrayComparator.compareTwoArrays( arr1_1, arr1_2 ) );

        int[] arr2_1 = new int[0];
        int[] arr2_2 = new int[0];
        System.out.println( ArrayComparator.compareTwoArrays( arr2_1, arr2_2 ) );

        int[] arr3_1 = new int[]{ 1, 3, 2 };
        int[] arr3_2 = new int[]{ 1, 3 };
        System.out.println( ArrayComparator.compareTwoArrays( arr3_1, arr3_2 ) );

        int[] arr4_1 = new int[]{ 1, 2, 3, 4 };
        int[] arr4_2 = new int[]{ 1, 2, 4, 3 };
        System.out.println( ArrayComparator.compareTwoArrays( arr4_1, arr4_2 ) );

        int[] arr5_1 = null;
        int[] arr5_2 = null;
        System.out.println( ArrayComparator.compareTwoArrays( arr5_1, arr5_2 ) );
    }


    private static boolean compareTwoArrays( int[] array1, int[] array2 )
    {
        boolean result = true;

        if( (array1 == null) || (array2 == null) || (array1.length != array2.length) )
        {
            result = false;
        }
        else
        {
            for( int i=0; i < array1.length; i++ )
            {
                if( array1[i] != array2[i] )
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
