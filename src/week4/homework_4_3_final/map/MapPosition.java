package week4.homework_4_3_final.map;


public class MapPosition
{
    // state
    private       int xCoordinate;
    private       int yCoordinate;


    // constructors

    /**
     * Creates an instance of Position with the provided coordinates.
     * @param xCoordinate the position on the X-axis
     * @param yCoordinate the position on the Y-axis
     */
    public MapPosition( int xCoordinate, int yCoordinate )
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }


    // getters & setters

    public int getXCoordinate()
    {
        return this.xCoordinate;
    }

    public void setXCoordinate( int newCoord )
    {
        this.xCoordinate = newCoord;
    }


    public int getYCoordinate()
    {
        return this.yCoordinate;
    }

    public void setYCoordinate( int newCoord )
    {
        this.yCoordinate = newCoord;
    }


    // other methods
    @Override
    public String toString()
    {
        return "(" + this.xCoordinate + ", " + this.yCoordinate + ")" ;
    }


    @Override
    public boolean equals( Object obj )
    {
        if( obj == this )
        {
            return true;
        }

        if( obj == null || !(obj instanceof MapPosition) )
        {
            return false;
        }

        MapPosition other = (MapPosition) obj;

        return ( this.xCoordinate == other.xCoordinate)
                 && (this.yCoordinate == other.yCoordinate );
    }
}
