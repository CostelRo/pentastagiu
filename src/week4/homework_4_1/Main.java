package week4.homework_4_1;


import java.util.Formatter;


public class Main
{
    public static void main( String[] args )
    {
        Restaurant r1 = new Restaurant( "Bubbles", RestaurantType.FASTFOOD, 150, 50 );
        Restaurant r2 = new Restaurant( "Clouds", RestaurantType.VEGETARIAN, 80, 65 );

        r1.addNewClients( 4 );
        System.out.println( Main.prettyFormattingIncome( r1 ) );
        System.out.println( Main.prettyFormattingTaxesOwed( r1 ) );

        r1.addNewClients( 6 );
        System.out.println( Main.prettyFormattingIncome( r1 ) );
        System.out.println( Main.prettyFormattingTaxesOwed( r1 ) );

        r2.addNewClients( 5 );
        System.out.println( Main.prettyFormattingIncome( r2 ) );
        System.out.println( Main.prettyFormattingTaxesOwed( r2 ) );

        r2.addNewClients( 2 );
        System.out.println( Main.prettyFormattingIncome( r2 ) );
        System.out.println( Main.prettyFormattingTaxesOwed( r2 ) );
    }


    private static String prettyFormattingIncome( Restaurant restaurant )
    {
        return new Formatter()
                    .format( "%S %s = %.2f",
                             restaurant.getName(),
                             "Total Income today",
                             restaurant.getIncome() )
                    .toString();
    }


    private static String prettyFormattingTaxesOwed( Restaurant restaurant )
    {
        return new Formatter()
                    .format( "%S %s%n(tax: %.2f%%, tax discount: %.2f%%)%n= %.2f%n",
                             restaurant.getName(),
                             "Total Taxes owed for today",
                             restaurant.getType().getTaxStandardLevel() * 100,
                             restaurant.getType().getTaxDiscount() * 100,
                             restaurant.calculatePayableTax() )
                    .toString();
    }
}
