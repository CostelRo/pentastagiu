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

    public boolean isValidFlightNumberFormat( String flightNumber )
    {
        return (flightNumber != null)
                && flightNumber.toUpperCase().matches( FlightValidator.VALID_FLIGHT_NUMBER_FORMAT );
    }


    public boolean isFlightAlreadyRegistered( String flightNumber )
    {
        return flightNumber != null
               && FlightsManager.getSingleton( FlightValidator.getSingleton() )
                                .getFlightsByName().containsKey( flightNumber.toUpperCase() );
    }


    public boolean isValidDestination( String airportCode )
    {
        FlightsManager flightsManager = FlightsManager.getSingleton( FlightValidator.getSingleton() );
        PassengersManager passengersManager = PassengersManager.getSingleton( PassengerValidator.getSingleton() );



        System.out.println( AirportManager.getSingleton( flightsManager, passengersManager ).getDestinationAirports() );



        return (airportCode != null)
                && AirportManager.getSingleton( flightsManager, passengersManager )
                                 .getDestinationAirports().stream()
                                                          .anyMatch( airport -> airport.getCode()
                                                                                       .equals(airportCode.toUpperCase()) );
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
