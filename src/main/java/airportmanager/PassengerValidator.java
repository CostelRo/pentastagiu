package airportmanager;


import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class PassengerValidator
{
    // state
    private static volatile       PassengerValidator singleton;


    // constructors

    private PassengerValidator() { }


    public static PassengerValidator getSingleton()
    {
        if( PassengerValidator.singleton == null )
        {
            synchronized( PassengerValidator.class )
            {
                if( PassengerValidator.singleton == null )
                {
                    PassengerValidator.singleton = new PassengerValidator();
                }
            }
        }

        return PassengerValidator.singleton;
    }


    // other methods

    public boolean isNameValid( String name )
    {
        return (name != null) && (name.length() > 0);
    }


    public boolean isBirthdayValid( LocalDate birthday )
    {
        return (birthday!= null) && birthday.isBefore( AirportManager.getSingleton().getCurrentDateTime()
                                                                                    .toLocalDate() );
    }


    public boolean isPassengerNew( Passenger passenger )
    {
        return passenger != null
               && !PassengersManager.getSingleton().getPassengersByID().values().contains( passenger );
    }
}
