package course2;


/*
 * HOMEWORK 3:
 * Write a program that will print the first X fibonacci numbers.
 * Also calculate the average value.
 * For example, for an input of X = 20 the output should be:
 *
 *     The first 20 Fibonacci numbers are:
 *     1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584 4181 6765
 *     The average is 885.5
 */


public class FibonacciAverageCalculator
{
   public static void main( String[] args )
   {
       int limit = 20;
       System.out.println( createFancyAnswer( limit ) );
   }


   private static String createFancyAnswer( int limit )
   {
       String answerStart = "The first " + limit + " Fibonacci numbers are:\n";

       int[] numbers = generateFibonacciNumbers( limit );
       StringBuilder arrayAsString = new StringBuilder("");
       for( int i=0; i < numbers.length; i++ )
       {
           arrayAsString.append( numbers[i] ).append( (i != numbers.length - 1) ? " " : "" );
       }

       String answerEnd = "The average is " + computeIntegerArrayAverage( numbers );

       return ( answerStart
                + arrayAsString.toString() + "\n"
                + answerEnd );

   }


   private static int[] generateFibonacciNumbers( int limit )
   {
       int[] result = new int[limit];

       int first = 0;
       int second = 1;
       for( int i=0; i < limit; i++ )
       {
           result[i] = second;
           second = first + second;
           first = result[i];
       }

       return result;
   }


    private static double computeIntegerArrayAverage( int[] source )
    {
        return ( (double) FibonacciAverageCalculator.computeSumOfIntegers( source ) ) / source.length;
    }


   private static int computeSumOfIntegers( int[] source )
   {
       int result = 0;

       for( int number : source )
       {
           result += number;
       }

       return result;
   }
}
