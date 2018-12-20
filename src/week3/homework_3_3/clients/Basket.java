package week3.homework_3_3.clients;


import week3.homework_3_3.products.Product;

import java.util.ArrayList;
import java.util.List;

public class Basket
{
    // state

    private Customer        owner;
    private List<Product>   contents;
    private double          totalPrice;


    // constructors

    public Basket( Customer owner )
    {
        this.owner = owner;
        this.contents = new ArrayList<>();
        this.totalPrice = 0.0 ;
    }


    // getters & setters

    public Customer getOwner()
    {
        return this.owner;
    }

    public void setOwner( Customer owner )
    {
        if( owner != null )
        {
            this.owner = owner;
        }
    }


    public List<Product> getContents()
    {
        return this.contents;
    }


    public double getTotalPrice()
    {
        return this.totalPrice;
    }


    // other methods

    public void addProduct( Product newProduct )
    {
        if( newProduct != null )
        {
            this.contents.add( newProduct );

            this.totalPrice += ( newProduct.getPricePerUnit() * newProduct.getQuantity() );
        }
    }


    public double getTotalPriceAfterDiscount()
    {
        return this.totalPrice * ( 1 - this.owner.getMembershipLevel().getDiscount() );
    }


    public String getReceipt()
    {
        StringBuilder products = new StringBuilder();
        for( Product p : this.contents )
        {
            products.append( p.toString() ).append( "\n" );
        }

        String separator1 = "========================================\n";
        String separator2 = "----------------------------------------\n";

        return separator1
               + "Basket of " + this.owner.toString() + "\n"
               + separator2
               + products
               + separator2
               + "TOTAL (after discount " + (this.owner.getMembershipLevel().getDiscount() * 100 ) + "%) = "
               + String.format( "%.2f", this.getTotalPriceAfterDiscount() ) + "\n"
               + separator1;
    }
}
