package airportmanager;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class FlightValidator
{
   // state
   private static volatile       FlightValidator singleton;
   private static          final String          VALID_FLIGHT_NUMBER_FORMAT  = "\\b[a-z]{2}[ -]?[0-9]{1,4}\\b";


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

    public boolean isValidFlightNumberFormat( String flightNumber )
    {
        return (flightNumber != null)
                && flightNumber.toLowerCase().matches( FlightValidator.VALID_FLIGHT_NUMBER_FORMAT );
    }


    public boolean isFlightAlreadyRegistered( String flightNumber )
    {
        return flightNumber != null
               && FlightsManager.getSingleton().getFlightsByName().containsKey( flightNumber );
    }


    public boolean isValidDestination( String airportCode )
    {
        return (airportCode != null)
                && AirportManager.getSingleton().getDestinationAirports().stream()
                                                                         .anyMatch( airport -> airport.getAirportCode()
                                                                                               .equals( airportCode ) );
    }


    public boolean isValidDepartureDateTime( LocalDateTime departureDateTime )
    {
        return departureDateTime != null
               && departureDateTime.isAfter ( AirportManager.getSingleton().getCurrentDateTime() ) ;
    }


    public boolean isValidDuration( long flightDurationInSeconds )
    {
        return (flightDurationInSeconds > 1);
    }


    public boolean isValidPassengersCapacity( int passengerCapacity )
    {
        return (passengerCapacity > 0);
    }
}
