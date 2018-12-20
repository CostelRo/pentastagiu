package week3.homework_3_3;


import week3.homework_3_3.clients.Basket;
import week3.homework_3_3.clients.Customer;
import week3.homework_3_3.clients.Membership;
import week3.homework_3_3.products.Book;
import week3.homework_3_3.products.Candy;
import week3.homework_3_3.products.Product;

public class Shop
{
    public static void main( String[] args )
    {
        Product book1 = Book.addBook( "Dictionar tehnic",
                                      150.0,
                                      1,
                                      "9376887224",
                                      "Daniel F." );
        Product book2 = Book.addBook( "Limbajul Java",
                                      75.0,
                                      1,
                                      "9573382991",
                                      "Marcel T." );
        Product book3 = Book.addBook( "Duplicate-ISBN book",
                                      90.0,
                                      1,
                                      "9573382991",
                                      "Mistery Author" );
        Product candy1 = new Candy( "Bomboane dietetice", 23.5, 0.1 );

        Customer customer1 = new Customer( "Vasile Popa",
                                           "vailep@yahoo.net",
                                           Membership.SILVER );
        Basket basket1 = new Basket( customer1 );

        basket1.addProduct( book1 );
        System.out.println( "(full price: " + basket1.getTotalPrice() + ")" );

        basket1.addProduct( book2 );
        System.out.println( "(full price: " + basket1.getTotalPrice() + ")" );

        basket1.addProduct( book3 );
        System.out.println( "(full price: " + basket1.getTotalPrice() + ")" );

        basket1.addProduct( candy1 );
        System.out.println( "(full price: " + basket1.getTotalPrice() + ")\n" );

        System.out.println( basket1.getReceipt() + "\n" );

        customer1.setMembershipLevel( Membership.GOLD );
        System.out.println( basket1.getReceipt() + "\n" );

        customer1.setMembershipLevel( Membership.NONE );
        System.out.println( basket1.getReceipt() + "\n" );
    }
}
