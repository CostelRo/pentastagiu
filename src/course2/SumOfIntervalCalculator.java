package course2;

/*
 * HOMEWORK 1:
 * Write a program that will sum up all numbers from 0 up to a target number.
 * For example, for an input number 5, result should be 15 (0+1+2+3+4+5)
 * NOTE: All numbers will be integers.
 */


public class SumOfIntervalCalculator
{
    public static void main(String[] args)
    {
        int start = 0;
        int end = 10;

        System.out.printf( "Sum of closed interval [%d..%d] = %d\n",
                           start, end, computeSumOfIntegerInterval( start, end ) );
    }


    private static int computeSumOfIntegerInterval(int startLimit, int endLimit )
    {
        int result = 0;

        for( int counter = startLimit; counter <= endLimit; counter++ )
        {
            result += counter;
        }

        return result;
    }
}
