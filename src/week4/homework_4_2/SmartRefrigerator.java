package week4.homework_4_2;


public class SmartRefrigerator implements Connectable
{
    // state

    private String maker;
    private String model;
    private String color;
    private double capacity;


    // constructors

    public SmartRefrigerator( String maker, String model, String color, double capacity )
    {
        this.maker = maker;
        this.model = model;
        this.color = color;
        this.capacity = capacity;
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

    public double getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity( double capacity )
    {
        if( capacity > 0 )
        {
            this.capacity = capacity;
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
                + this.getMaker() + " " + this.getModel()
                + " (color " + this.color + ", capacity " + this.capacity + " litres)";
    }
}
