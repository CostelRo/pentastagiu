package week3.homework_3_3.products;


public abstract class Product
{
    // state

    private static  int     counter = 1;
    protected       int     id;
    protected       String  name;
    protected       double  pricePerUnit;    // expressed per: piece, kg or litre (depending on product)
    protected       double  quantity;        // expressed in: pieces, kg or litres (depending on product)


    // constructors

    public Product( String name,
                    double pricePerUnit,
                    double quantity )
    {
        this.id = counter;
        counter++;

        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }


    // getters & setters

    public int getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        if( name != null && name.length() > 0 )
        {
            this.name = name;
        }
    }


    public double getPricePerUnit()
    {
        return pricePerUnit;
    }

    public void setPricePerUnit( double pricePerUnit )
    {
        if( pricePerUnit > 0 )
        {
            this.pricePerUnit = pricePerUnit;
        }
    }


    public double getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity( double quantity )
    {
        if( quantity > 0 )
        {
            this.quantity = quantity;
        }
    }
}
