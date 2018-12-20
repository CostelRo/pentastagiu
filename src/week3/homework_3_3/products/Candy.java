package week3.homework_3_3.products;


public class Candy extends Product
{
    // state

    private double quantity;


    // getters & setters

    public double getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity( int quantity )
    {
        if( quantity > 0 )
        {
            this.quantity = quantity;
        }
    }


    // constructors

    public Candy( String name, double pricePerUnit, double quantity )
    {
        super( name, pricePerUnit, quantity );
        this.quantity = quantity;
    }


    // other methods

    public String toString()
    {
        return this.name + ", " + (this.quantity * 1000) + " grams = " + (this.pricePerUnit * this.quantity);
    }
}
