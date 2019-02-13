package airportmanager;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class FlightValidator
{
   // state
   private static volatile       FlightValidator singleton;
   private static          final String          VALID_FLIGHT_NUMBER_FORMAT  = "\\b[a-zA-Z]{2}[ -]?[0-9]{1,4}\\b";


    // constructors

    private FlightValidator() { }


    public static FlightValidator getSingleton()
    {
        if( FlightValidator.singleton == null )
        {
            synchronized( FlightValidator.class )
            {
                if( FlightValidator.singleton == null )
                {
                    FlightValidator.singleton = new FlightValidator();
                }
            }
        }

        return FlightValidator.singleton;
    }


    // other methods

    public boolean isValidFlightNameFormat( String flightNumber )
    {
        return (flightNumber != null)
                && flightNumber.toUpperCase().matches( FlightValidator.VALID_FLIGHT_NUMBER_FORMAT );
    }


    public boolean isValidDepartureDateTime( LocalDateTime departureDateTime )
    {
        return departureDateTime != null
               && departureDateTime.isAfter( LocalDateTime.now() ) ;
    }


    public boolean isValidDuration( long flightDurationInSeconds )
    {
        return (flightDurationInSeconds > 0);
    }


    public boolean isValidPassengersCapacity( int passengerCapacity )
    {
        return (passengerCapacity > 0);
    }
}
