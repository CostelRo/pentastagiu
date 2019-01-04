package week4.homework_4_1;


public class Restaurant
{
    // state

    private String name;
    private RestaurantType type;
    private int capacity;           // maximum number of clients
    private double priceOfFixedMenu;
    private int clients;            // current number of clients
    private double income;


    // constructors

    public Restaurant( String name, RestaurantType type, int capacity, double priceOfFixedMenu )
    {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.priceOfFixedMenu = priceOfFixedMenu;
        this.clients = 0;
        this.income = 0.0;
    }


    // getters & setters

    public String getName()
    {
        return this.name;
    }

    public void setName( String newName )
    {
        if( newName != null && newName.length() > 0 )
        {
            this.name = newName;
        }
    }


    public RestaurantType getType()
    {
        return this.type;
    }

    public void setType( RestaurantType type )
    {
        if( type != null )
        {
            this.type = type;
        }
    }


    public int getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity( int newCapacity )
    {
        if( newCapacity > 0 )
        {
            this.capacity = newCapacity;
        }
    }


    public double getPriceOfFixedMenu()
    {
        return this.priceOfFixedMenu;
    }

    public void setPriceOfFixedMenu( double priceOfFixedMenu )
    {
        if( priceOfFixedMenu > 0 )
        {
            this.priceOfFixedMenu = priceOfFixedMenu;
        }
    }


    public int getClients()
    {
        return this.clients;
    }


    public double getIncome()
    {
        return this.income;
    }


    // other methods

    public void addNewClients( int newClients )
    {
        if( (newClients > 0) && (this.clients + newClients <= this.capacity) )
        {
            this.clients += newClients;
            this.increaseIncome( newClients );
        }
    }


    private void increaseIncome( int newClients )
    {
        if( newClients > 0 )
        {
            this.income += ( newClients * this.getPriceOfFixedMenu() );
        }
    }


    public double calculatePayableTax()
    {
        return this.income * this.type.getTaxStandardLevel() * ( 1 - this.type.getTaxDiscount() );
    }
}
