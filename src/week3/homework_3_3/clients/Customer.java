package week3.homework_3_3.clients;


import java.util.ArrayList;
import java.util.List;

public class Customer
{
    // state

    private static  int             counter = 1;
    private         int             id;
    private         String          name;
    private         String          email;
    private         Membership      membershipLevel;
    private         List<Integer>   buyingHistory;    // all the Basket-IDs associated with each Customer


    // constructors

    public Customer( String name, String email, Membership membershipLevel )
    {
        this.id = counter;
        counter++;

        this.name = name;
        this.email = email;
        this.membershipLevel = membershipLevel;
        this.buyingHistory = new ArrayList<>();
    }


    // getters & setters

    public int getId()
    {
        return this.id;
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


    public String getEmail()
    {
        return this.email;
    }

    public void setEmail( String newEmail )
    {
        if( Customer.isValidEmailFormat( newEmail ) )
        {
            this.email = newEmail;
        }
    }


    public Membership getMembershipLevel()
    {
        return this.membershipLevel;
    }

    public void setMembershipLevel( Membership membershipLevel )
    {
        if( membershipLevel != null )
        {
            this.membershipLevel = membershipLevel;
        }
    }


    public List<Integer> getBuyingHistory()
    {
        return this.buyingHistory;
    }


    // other methods

    /*
     * This method does a very simplistic email format validation.
     * Minimum string length allowed: 5 (e.g. "a@b.c")
     * Any other Unicode characters can be used instead of the letters and the period.
     */
    private static boolean isValidEmailFormat( String source )
    {
        int minEmailLength = 5;

        return (source != null)
                && (source.length() >= minEmailLength)
                && (source.indexOf('@') == source.lastIndexOf('@'));
    }


    public void addToBuyingHistory( int newBasketID )
    {
        if( newBasketID > 0 )
        {
            this.buyingHistory.add( newBasketID );
        }
    }


    @Override
    public String toString()
    {
        return this.name.toUpperCase() + " (" + this.email + ")";
    }
}
