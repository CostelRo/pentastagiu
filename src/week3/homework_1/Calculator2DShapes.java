package week3.homework_1;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator2DShapes
{
    public static void main( String[] args )
    {
        // Test #1
        Shape[] shapes1 = new Shape[]{ new Circle( 1 ),
                                       new Square( 1 ),
                                       new Rectangle( 1, 2 ),
                                       new Triangle( 3, 4, 5 )
                                     };
        Shape largestShape = Calculator2DShapes.identifyLargest2DShapeByArea( shapes1 );
        System.out.println( "1) " + Calculator2DShapes.displayLargest2DShapeDetails( largestShape ));

        // Test #2
        Shape[] shapes2 = new Shape[]{ new Circle( 11 ),
                                       new Square( 11 ),
                                       new Rectangle( 11, 22 ),
                                       new Triangle( 30, 40, 50 ),
                                       new Circle( 111 ),
                                       new Square( 6 ),
                                       new Rectangle( 16, 23 ),
                                     };
        largestShape = Calculator2DShapes.identifyLargest2DShapeByArea( shapes2 );
        System.out.println( "2) " + Calculator2DShapes.displayLargest2DShapeDetails( largestShape ));

        // Test #3
        while( true )
        {
            System.out.println("\nShapes to compare: ? (1 --> 1000000, 0 to stop)");
            Scanner sc = new Scanner(System.in);
            int numberOfShapes;
            try
            {
                numberOfShapes = sc.nextInt();
            }
            catch( InputMismatchException imex )
            {
                continue;
            }

            if( numberOfShapes == 0 )
            {
                System.out.println( "Good bye! (closing...)" );
                System.exit( 0 );
            }
            else if( numberOfShapes < 0 || numberOfShapes > 1_000_000 )
            {
                continue;
            }

            Shape[] shapes = RandomShapeGenerator.generateShapesArray( numberOfShapes );
            largestShape = Calculator2DShapes.identifyLargest2DShapeByArea( shapes );
            System.out.println( "Out of " + numberOfShapes + " shapes:\n"
                                + Calculator2DShapes.displayLargest2DShapeDetails( largestShape )  + "\n" );
        }
    }


    private static Shape identifyLargest2DShapeByArea( Shape[] source )
    {
        Shape result = null;
        double largestArea = 0.0;

        if( (source != null) && (source.length > 0) )
        {
            int counter = 0;
            for( Shape sh : source )
            {
                if( result != null )
                {
                    if( sh.computeArea() > largestArea )
                    {
                        result = sh;
                        largestArea = sh.computeArea();
                    }
                }
                else
                {
                    result = sh;
                    largestArea = sh.computeArea();
                }

                counter++;
                if( counter %10_000 == 0 )
                {
                    System.out.println( "...another 10000...");
                }
            }
        }

        return result;
    }


    private static String displayLargest2DShapeDetails( Shape source )
    {
        if( source == null )
        {
            return "(No data)";
        }

        String result;

        String shapeType = source.getClass().getSimpleName();
        String begin = "Largest shape is a " + shapeType;

        String end;
        switch( shapeType )
        {
            case "Circle":
                Circle c1 = (Circle) source;
                end = "area = " + source.computeArea() + ", with radius " + c1.getRadius();
                break;

            case "Square":
                Square s1 = (Square) source;
                end = "area = " + source.computeArea() + ", with side " + s1.getLength();
                break;

            case "Rectangle":
                Rectangle r1 = (Rectangle) source;
                end = "area = " + r1.computeArea()
                      + ", with length " + r1.getLength() + " and width " + r1.getWidth();
                break;

            case "Triangle":
                Triangle t1 = (Triangle) source;
                end = "area = " + t1.computeArea()
                      + ", with sides " + t1.getSide_1() + ", " + t1.getSide_2() + " and " + t1.getSide_3();
                break;

            default:
                return "(Unknown shape)";
        }

        result = begin + " (" + end + ")";
        return result;
    }
}
