package week3.homework_3_3.clients;


public enum Membership
{
    GOLD(0.2),
    SILVER(0.1),
    NONE(0);


    private double discount;


    Membership( double discountLevel )
    {
        this.discount = discountLevel;
    }


    public double getDiscount()
    {
        return this.discount;
    }
}
