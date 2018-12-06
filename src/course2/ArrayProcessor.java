package course2;


/*
 * HOMEWORK 4:
 * Write a java program that will test whether the first and the last element of an int array are the same.
 * We can assume the array has 2 or more elements.
 *
 * For example:
 *     For the following input [13,6,2,8,2,3] the output should be : FALSE
 *     For the following input [13,6,2,8,2,13] the output should be : TRUE
 */


public class ArrayProcessor
{
    public static void main(String[] args)
    {
        int[] arr1 = new int[]{ 13, 6, 2, 8, 2, 3 };
        System.out.println( ArrayProcessor.compareArrayEnds( arr1 ) );

        int[] arr2 = new int[]{ 13, 6, 2, 8, 2, 13 };
        System.out.println( ArrayProcessor.compareArrayEnds( arr2 ) );
    }


    private static boolean compareArrayEnds( int[] source )
    {
        return source[0] == source[ source.length-1 ];
    }
}
