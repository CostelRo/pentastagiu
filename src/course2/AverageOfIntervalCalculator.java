package course2;


/*
 * HOMEWORK 2:
 * Write a program that will calculate the average and sum of all numbers between the range of 2 numbers.
 * For example, for range numbers 1 to 100 output should be:
 *     Sum for range 1 to 100 : 5050
 *     Average for range 1 to 100 : 50.5
 * NOTE: All numbers will be positive integers.
 */


public class AverageOfIntervalCalculator
{
    public static void main(String[] args)
    {
        int start = 1;
        int end = 100;

        String intervalAsString = start + " to " + end;
        // the average of consecutive integers always ends in .5 or .0
        System.out.printf( "Sum for range %s : %d\nAverage for range %s : %.1f",
                           intervalAsString, SumOfIntervalCalculator.computeSumOfIntegerInterval( start, end ),
                           intervalAsString, computeAverageOfIntegerInterval( start, end ) );
    }


    private static double computeAverageOfIntegerInterval( int start, int end )
    {
        double result;

        double intervalLength = end - start + 1;
        result = (double) SumOfIntervalCalculator.computeSumOfIntegerInterval( start, end ) / intervalLength;

        return result;
    }
}
