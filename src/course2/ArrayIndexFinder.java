package course2;

import java.util.ArrayList;
import java.util.List;


/*
 * HOMEWORK 8:
 * Write a java program to return the index of a target element in an unsorted array.
 *
 * For example: Given the following array [13,20,14,5,2,8] and a target element 5 the output should be:
 *     3 (because 5 is located at index 3 in the array)
 */


public class ArrayIndexFinder
{
    public static void main( String[] args )
    {
        int target = 5;

        int[] source1 = new int[]{ 13, 20, 14, 5, 2, 8 };
        System.out.println( ArrayIndexFinder.displayPrettyArray( findIndex( source1, target ) ) );

        int[] source2 = new int[]{ -13, 5, 14, 5, 2, 8 };
        System.out.println( ArrayIndexFinder.displayPrettyArray( findIndex( source2, target ) ) );

        int[] source3 = new int[]{ 5, 5, 5 };
        System.out.println( ArrayIndexFinder.displayPrettyArray( findIndex( source3, target ) ) );

        int[] source4 = new int[0];
        System.out.println( ArrayIndexFinder.displayPrettyArray( findIndex( source4, target ) ) );

        int[] source5 = null;
        System.out.println( ArrayIndexFinder.displayPrettyArray( findIndex( source5, target ) ) );
    }


    private static List<Integer> findIndex( int[] source, int target )
    {
        if( source == null ) { return null; }

        List<Integer> result = new ArrayList<>();

        for( int i=0; i < source.length; i++ )
        {
            if( source[i] == target )
            {
                result.add( i );
            }
        }

        return result;
    }


    private static String displayPrettyArray( List<Integer> source )
    {
        if( source == null || source.size() == 0 ) { return "(No data.)\n"; }

        StringBuilder result = new StringBuilder("");
        for( int i=0; i < source.size(); i++ )
        {
            result.append( source.get( i ) ).append( (i != source.size() - 1) ? ", " : "\n" );
        }

        return result.toString();
    }
}
