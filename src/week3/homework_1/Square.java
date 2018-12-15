package week3.homework_1;


public class Square extends Shape
{
    // state
    private double length;


    // constructors
    /*
     * This constructor creates a Square object using the provided legal parameters or some default values.
     */
    public Square( double length )
    {
        this.length = ( length > 0 )
                        ? length
                        : 10.0;
    }


    // getters & setters
    public double getLength()
    {
        return this.length;
    }

    public void setLength( double newLength )
    {
        if( newLength > 0 )
        {
            this.length = newLength;
        }
    }


    // other methods
    public double computePerimeter()
    {
        return ( this.length * 4 );
    }

    public double computeArea()
    {
        return ( this.length * this.length );
    }
}
