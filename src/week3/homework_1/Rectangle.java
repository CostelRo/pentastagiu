package week3.homework_1;


public class Rectangle extends Shape
{
    // state
    private double length;
    private double width;


    // constructors
    /*
     * This constructor creates a Rectangle object using the provided legal parameters or some default values.
     */
    public Rectangle( double length, double width )
    {
        this.length = ( length > 0 )
                        ? length
                        : 20.0;
        this.width = ( width > 0 )
                        ? width
                        : 10.0;
    }


    // getters & setters
    public double getLength()
    {
        return this.length;
    }

    public void setLength( double newLength )
    {
        if(  newLength > 0 )
        {
            this.length = newLength;
        }
    }

    public double getWidth()
    {
        return this.width;
    }

    public void setWidth( double newWidth )
    {
        if( newWidth > 0 )
        {
            this.width = newWidth;
        }
    }


    // other methods
    public double computePerimeter()
    {
        return ( this.length * 2 + this.width * 2 );
    }

    public double computeArea()
    {
        return ( this.length * this.width );
    }
}
