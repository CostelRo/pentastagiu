package week4.homework_4_2;


public class Main
{
    public static void main( String[] args )
    {
        Car car1 = new Car( "Toyota", "Rav6", "Bleu Ciel", 4740.5 );
        Car car2 = new Car( "Audi", "Q9", "Black Midnight", 5680 );
        Phone phone1 = new Phone( "Apple", "Perfection", 64, 246.75 );
        Phone phone2 = new Phone( "Samsung", "A9", 16, 223.5 );
        SmartRefrigerator fridge1 = new SmartRefrigerator( "Samsung", "ACX-410", "White", 480 );
        SmartRefrigerator fridge2 = new SmartRefrigerator( "Daewoo", "PT12N", "White", 330 );
        Student stud1 = new Student( "Laurian Traianescu", 3, 9.75 );
        Student stud2 = new Student( "Milena Bobulescu", 2, 8.50 );

        Object[] arr1 = new Object[]{ car1, phone1, fridge1, stud1, car2, fridge2, phone2, stud2 };

        for( Object obj : arr1 )
        {
            if( obj instanceof Connectable )
            {
                Connectable con = (Connectable) obj;
                con.connectToBluetooth();
            }
            else
            {
                System.out.println( "(No Bluetooth connection available. Yet.)" );
            }
        }
    }
}
