package week3.homework_1;


public class Circle extends Shape
{
    // state
    private double radius;


    // constructors
    /*
     * This constructor creates a Circle object using the provided legal parameters or some default values.
     */
    public Circle( double radius )
    {
        this.radius = ( radius > 0 )
                        ? radius
                        : 10.0;
    }


    // getters & setters
    public double getRadius()
    {
        return this.radius;
    }

    public void setRadius( double newRadius )
    {
        if( newRadius > 0 )
        {
            this.radius = newRadius;
        }
    }


    // other methods
    public double computePerimeter()
    {
        return ( 2 * Math.PI * this.radius );
    }

    public double computeArea()
    {
        return ( Math.PI * this.radius * this.radius );
    }
}
