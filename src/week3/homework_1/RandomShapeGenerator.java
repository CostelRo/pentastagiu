package week3.homework_1;


import java.util.Random;


public class RandomShapeGenerator
{
    // state
    private static char previousType = 'C';


    // other methods
    public static Shape[] generateShapesArray( int size )
    {
        Shape[] result = new Shape[size];

        for( int i=0; i < size; i++ )
        {
            result[i] = RandomShapeGenerator.generateShape( RandomShapeGenerator.previousType );
            RandomShapeGenerator.previousType = RandomShapeGenerator.getNextShapeType(
                                                RandomShapeGenerator.previousType );
        }

        return result;
    }


    /*
     * This methods uses pseudo-random numbers in the interval [0..100) to generate the following shapes:
     *   - Circle;
     *   - Square;
     *   - Rectangle;
     *   - Triangle.
     */
    public static Shape generateShape( char shapeType )
    {
        Shape result = null;

        Random rand = new Random();
        switch( shapeType )
        {
            case 'C':
                double radius = rand.nextDouble() * 100;
                result = new Circle( radius );
                break;

            case 'S':
                double side = rand.nextDouble() * 100;
                result = new Square( side );
                break;

            case 'R':
                double length = rand.nextDouble() * 100;
                double width = rand.nextDouble() * 100;
                result = new Rectangle( length, width );
                break;

            /*
             * For details see, foe example:
             * https://en.wikipedia.org/wiki/Solution_of_triangles#Two_sides_and_the_included_angle_given_(SAS)
             */
            case 'T':
                double side1 = rand.nextDouble() * 100;
                double side2 = rand.nextDouble() * 100;
                double angle1 = rand.nextDouble() * 100;
                double side3 = Math.sqrt( (side1 * side1) + (side2 * side2)
                                          - ( 2 *  side1 * side2 * Math.cos( angle1 ) )
                                         );
                result = new Triangle( side1, side2, side3 );
                break;
        }

        return result;
    }


    private static char getNextShapeType( char lastType )
    {
        String types = "CSRT";

        return types.charAt( (types.indexOf( lastType ) + 1) % types.length() );
    }
}
