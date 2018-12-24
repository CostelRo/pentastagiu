package week4.homework_4_2;


public class Phone implements Connectable
{
    // state

    private String maker;
    private String model;
    private int memory;
    private double weight;


    // constructors

    public Phone( String maker, String model, int memory, double weight )
    {
        this.maker = maker;
        this.model = model;
        this.memory = memory;
        this.weight = weight;
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

    public int getMemory()
    {
        return this.memory;
    }

    public void setMemory( int memory )
    {
        if( memory > 0 )
        {
            this.memory = memory;
        }
    }

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight( double weight )
    {
        if( weight > 0 )
        {
            this.weight = weight;
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
                + " (memory " + this.memory + " GB, weight " + this.weight + " g)";
    }
}
