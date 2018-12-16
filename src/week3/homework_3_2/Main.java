package week3.homework_3_2;


public class Main
{
    public static void main( String[] args )
    {
        DebitAccount da1 = new DebitAccount( "Vasile Vasilescu", 500.0 );

        System.out.println( da1.toString() );
        System.out.println( da1.depositIntoAccount( 1000 ) );
        System.out.println( da1.toString() );
        System.out.println( da1.withdrawFromAccount( 425 ) );
        System.out.println( da1.toString() );
        System.out.println( da1.withdrawFromAccount( 0 ) );
        System.out.println( da1.toString() );
        System.out.println( da1.withdrawFromAccount( -200 ) );
        System.out.println( da1.toString() );
        System.out.println( da1.depositIntoAccount( -400 ) );
        System.out.println( da1.toString() );
    }
}
