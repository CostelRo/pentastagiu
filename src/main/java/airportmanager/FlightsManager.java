package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class FlightsManager
{
    // state

    private static volatile  FlightsManager       singleton           = null;
    private                  FlightValidator      flightValidator;
    private                  Map<String, Flight>  flightsByName;
    private                  Set<Flight>          deletedFlights;


    // constructors

    @Autowired
    private FlightsManager( FlightValidator flightValidator )
    {
        this.flightValidator = flightValidator;
        this.flightsByName   = new HashMap<>();
        this.deletedFlights  = new HashSet<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @return the singleton instance of this class
     */
    public static FlightsManager getSingleton( FlightValidator flightValidator )
    {
        if( FlightsManager.singleton == null )
        {
            synchronized( FlightsManager.class )
            {
                if( FlightsManager.singleton == null )
                {
                    FlightsManager.singleton = new FlightsManager( flightValidator );
                }
            }
        }

        return FlightsManager.singleton;
    }


//    /**
//     * This method returns the current singleton instance of this class, if it has already been defined.
//     * @return the current singleton instance, if defined, or null
//     */
//    public static FlightsManager getSingleton()
//    {
//        return FlightsManager.singleton;
//    }


    // getters & setters

    public FlightValidator getFlightValidator()
    {
        return flightValidator;
    }

    public Map<String, Flight> getFlightsByName()
    {
        return flightsByName;
    }

    public Set<Flight> getDeletedFlights()
    {
        return deletedFlights;
    }


    // other methods

    public void addFlight( String        flightNumber,
                           String        destinationAirportCode,
                           LocalDateTime departureDateTime,
                           int           flightDuration,
                           int           passengerCapacity )
    {
        System.out.println( "[ping] " + flightNumber + " = " + flightValidator.isValidFlightNumberFormat( flightNumber ) + ", "
                                                     + !flightValidator.isFlightAlreadyRegistered( flightNumber ) + "; "
                            + destinationAirportCode + " = " + flightValidator.isValidDestination( destinationAirportCode ) + "; "
                            + departureDateTime + " = " + flightValidator.isValidDepartureDateTime(departureDateTime) + "; "
                            + flightDuration + " = " + flightValidator.isValidDuration( flightDuration ) + "; "
                            + passengerCapacity + " = " + flightValidator.isValidPassengersCapacity( passengerCapacity ) );

        if( flightValidator.isValidFlightNumberFormat( flightNumber )
            && !flightValidator.isFlightAlreadyRegistered( flightNumber )
            &&  flightValidator.isValidDestination( destinationAirportCode )
            &&  flightValidator.isValidDepartureDateTime(departureDateTime)
            &&  flightValidator.isValidDuration( flightDuration )
            &&  flightValidator.isValidPassengersCapacity( passengerCapacity ) )
        {
            Flight newFlight = new Flight( flightNumber,
                                           destinationAirportCode,
                                           departureDateTime,
                                           flightDuration,
                                           passengerCapacity );

            this.flightsByName.put( flightNumber, newFlight );
        }
        else
        {
            throw new IllegalArgumentException( "Incorrect parameter value(s) in Flight constructor!" );
        }
    }


    public Set<Flight> searchFlightsByPartialNumber( String searchString )
    {
        Set<Flight> result = new HashSet<>();

        if( searchString != null && searchString.length() > 0 )
        {
            String searchStringLowercased = searchString.toLowerCase();

            Set<String> flightNumbers = this.flightsByName.keySet()
                                                          .stream()
                                                          .filter( flightNumber -> flightNumber.toLowerCase()
                                                                                   .contains( searchStringLowercased ) )
                                                          .collect( Collectors.toSet() );

            for (String flightNumber : flightNumbers)
            {
                result.add( this.flightsByName.get( flightNumber ) );
            }
        }

        return result;
    }


    public Set<Flight> searchFlightsByStatus( FlightStatus searchedStatus )
    {
        Set<Flight> result = new HashSet<>();

        if( searchedStatus != null )
        {
            result = this.flightsByName.values().stream()
                                                .filter( flight -> flight.getStatus() == searchedStatus )
                                                .collect( Collectors.toSet() );
        }

        return result;
    }


    public Set<Flight> searchFlightsByDate( LocalDate searchDate )
    {
        Set<Flight> result = new HashSet<>();

        if( searchDate != null )
        {
            result = this.flightsByName.values().stream()
                                                .filter( flight -> flight.getDepartureDateTime().toLocalDate()
                                                                                                .isEqual(searchDate) )
                                                .collect( Collectors.toSet() );


        }

        return result;
    }


    public Set<Flight> searchFlightsByDateInterval( LocalDateTime searchStartDate, LocalDateTime searchEndDate )
    {
        Set<Flight> result = new HashSet<>();

        if( searchStartDate != null && searchEndDate != null )
        {
            result = this.flightsByName.values().stream()
                    .filter( flight ->
                    {
                        LocalDateTime flightDate = flight.getDepartureDateTime();

                        return flightDate.isEqual( searchStartDate )
                               || flightDate.isEqual( searchEndDate )
                               || ( flightDate.isAfter( searchStartDate ) && flightDate.isBefore( searchEndDate ) );
                    } )
                    .collect( Collectors.toSet() );
        }

        return result;
    }


    public Set<Flight> searchFlightsByPassengerID( int searchPassengerID )
    {
        Set<Flight> result = new HashSet<>(0);

        PassengersManager passengersManager = PassengersManager.getSingleton( PassengerValidator.getSingleton() );
        if( searchPassengerID > 0  & passengersManager.getFlightsNamesByPassengerID()
                                                      .get( searchPassengerID ) != null )
        {
            result = passengersManager.getFlightsNamesByPassengerID().get( searchPassengerID )
                                                         .stream()
                                                         .map( flightNumber -> this.flightsByName.get( flightNumber ) )
                                                         .collect( Collectors.toSet() );
        }

        return result;
    }


    public Map<Passenger, Set<Flight>> searchFlightsByPassengerPartialName( String nameFragment )
    {
        Map<Passenger, Set<Flight>> result = new HashMap<>();

        for( Passenger psgr : PassengersManager.getSingleton( PassengerValidator.getSingleton() )
                                               .searchPassengersByPartialName( nameFragment ) )
        {
            for( String flightNumber : psgr.getFlightHistory() )
            {
                result.putIfAbsent( psgr, new HashSet<>() );
                result.get( psgr ).add( this.flightsByName.get( flightNumber ) );
            }
        }

        return result;
    }


    public void removeFlight( String flightName )
    {
        // the flight is kept in an archive and is still referenced from its passengers' flight histories
        if( flightName != null )
        {
            Flight flightToRemove = this.getFlightsByName().get( flightName );

            this.flightsByName.remove( flightName );

            this.deletedFlights.add( flightToRemove );
        }
    }


    public boolean isFlightFinished( Flight flight, LocalDateTime newLocalDateTime )
    {
        return flight.getDepartureDateTime().plusSeconds( flight.getDurationInSeconds() ).isBefore( newLocalDateTime );
    }


    public boolean isScheduledFlightDeparted( Flight flight, LocalDateTime newLocalDateTime )
    {
        return flight.getDepartureDateTime().isBefore( newLocalDateTime );
    }
}
