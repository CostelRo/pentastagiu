package week3.homework_3_3.products;


import java.util.ArrayList;
import java.util.List;


public class Book extends Product
{
    // state

    private static  List<Book>  inventory = new ArrayList<>();
    private         String      uniqueISBN;
    private         String      authors;


    // constructors

    private Book( String name,
                  double pricePerUnit,
                  double quantity,
                  String uniqueISBN,
                  String authors )
    {
        super( name, pricePerUnit, quantity );
        this.uniqueISBN = uniqueISBN;
        this.authors = authors;
    }


    // getters & setters

    public String getUniqueISBN()
    {
        return this.uniqueISBN;
    }

    public void setUniqueISBN( String newUniqueISBN )
    {
        if( Book.isISBNValidAndAbsent( newUniqueISBN ) )
        {
            this.uniqueISBN = newUniqueISBN;
        }
    }


    public String getAuthors()
    {
        return this.authors;
    }


    public void setAuthors( String authors )
    {
        if( authors != null && authors.length() > 0 )
        {
            this.authors = authors;
        }
    }


    // other methods

    private static boolean isISBNValidAndAbsent( String source )
    {
        // check for ISBN format validity
        // (for details see: https://en.wikipedia.org/wiki/International_Standard_Book_Number)
        if( source == null || (source.length() != 10 && source.length() != 13) )
        {
            return false;
        }

        // check that the ISBN number has not been used by another book already in inventory
        boolean result = true;

        if( Book.inventory.size() > 0 )
        {
            for( Book b : Book.inventory )
            {
                if( source.equals( b.uniqueISBN ) )
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }


    public static Book addBook( String name,
                                double pricePerUnit,
                                double quantity,
                                String uniqueISBN,
                                String authors )
    {
        Book result = null;

        if( name != null && name.length() > 0
            && pricePerUnit > 0
            && Book.isISBNValidAndAbsent( uniqueISBN )
            && authors != null && authors.length() >= 0 )
        {
            quantity = 1.0;

            result = new Book( name, pricePerUnit, quantity, uniqueISBN, authors );

            Book.inventory.add( result );
        }

        return result;
    }


    public String toString()
    {
        return this.name + " (by " + this.authors + ")" + " = " + this.pricePerUnit;
    }
}
