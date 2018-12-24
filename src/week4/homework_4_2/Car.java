package week4.homework_4_2;


public class Car implements Connectable
{
    // state

    private String  maker;
    private String  model;
    private String  color;
    private double  length;


    // constructors

    public Car( String maker, String model, String color, double length )
    {
        this.maker = maker;
        this.model = model;
        this.color = color;
        this.length = length;
    }


    // getters & setters

    public String getMaker()
    {
        return this.maker;
    }

    public void setMaker( String maker )
    {
        if( maker != null && maker.length() > 0 )
        {
            this.maker = maker;
        }
    }

    public String getModel()
    {
        return this.model;
    }

    public void setModel( String model )
    {
        if( model != null && model.length() > 0 )
        {
            this.model = model;
        }
    }

    public String getColor()
    {
        return this.color;
    }

    public void setColor( String color )
    {
        if( color != null && color.length() > 0 )
        {
            this.color = color;
        }
    }

    public double getLength()
    {
        return this.length;
    }

    public void setLength( double length )
    {
        if( length > 0 )
        {
            this.length = length;
        }
    }


    // other methods

    @Override
    public void connectToBluetooth()
    {
        System.out.println( "Connect to Bluetooth in progress... [" + this.toString() + "]" );
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName().toUpperCase() + " "
                + this.maker + " " + this.model
                + " (color " + this.color + ", length " + this.length + " mm)";
    }
}
