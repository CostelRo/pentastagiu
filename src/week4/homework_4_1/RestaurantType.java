package week4.homework_4_1;


public enum RestaurantType
{
    // enum values

    FASTFOOD( 0.2, 0.0 ),
    VEGETARIAN( 0.2, 0.3 );


    // state

    private double taxStandardLevel;
    private double taxDiscount;


    // constructors

    RestaurantType( double taxStandardLevel, double taxDiscount )
    {
        this.taxStandardLevel = taxStandardLevel;
        this.taxDiscount = taxDiscount;
    }


    // getters

    public double getTaxStandardLevel()
    {
        return this.taxStandardLevel;
    }


    public double getTaxDiscount()
    {
        return this.taxDiscount;
    }
}
