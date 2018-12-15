package week3.homework_1;


public class Triangle extends Shape
{
    // state
    private double side_1;
    private double side_2;
    private double side_3;


    // getters & setters
    public double getSide_1()
    {
        return this.side_1;
    }

    public double getSide_2()
    {
        return this.side_2;
    }

    public double getSide_3()
    {
        return this.side_3;
    }


    // constructors
    /*
     * This constructor creates a Triangle object using the provided legal parameters or some default values.
     */
    public Triangle( double side1, double side2, double side3 )
    {
        if( (side1 > 0) && (side2 > 0) && (side3 > 0)
            && ( (side1 + side2) > side3 || (side1 + side3) > side2 || (side2 + side3) > side1 ) )
        {
            this.side_1 = side1;
            this.side_2 = side2;
            this.side_3 = side3;
        }
        else
        {
            this.side_1 = 3.0;
            this.side_2 = 4.0;
            this.side_3 = 5.0;
        }
    }


    // other methods
    public double computePerimeter()
    {
        return ( this.side_1 + this.side_2 + this.side_3 );
    }

    /*
     * The method uses Heron's Formula for a generic triangle.
     * (https://en.wikipedia.org/wiki/Heron%27s_formula).
     */
    public double computeArea()
    {
        double semiperimeter = this.computePerimeter() / 2;

        return ( Math.sqrt( semiperimeter
                            * (semiperimeter - side_1) * (semiperimeter - side_2) * (semiperimeter - side_3) )
               );
    }
}
