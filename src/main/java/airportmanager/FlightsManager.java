package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class FlightsManager
{
    // state

    private static volatile FlightsManager       singleton           = null;
    private                 FlightValidator      flightValidator;
    private                 Map<String, Flight>  flightsByName;


    // constructors

    private FlightsManager( FlightValidator flightValidator )
    {
        this.flightValidator = flightValidator;
        this.flightsByName   = new HashMap<>();
    }


    @Autowired
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


    // getters & setters

    public FlightValidator getFlightValidator()
    {
        return flightValidator;
    }

    public Map<String, Flight> getFlightsByName()
    {
        return flightsByName;
    }


    // other methods

    public void addFlight( String           flightName,
                           Airport          destinationAirport,
                           LocalDateTime    departureDateTime,
                           int              flightDuration,
                           int              passengerCapacity )
    {
        if( flightValidator.isValidFlightNameFormat( flightName )
            && !this.isFlightAlreadyRegistered( flightName )
            &&  flightValidator.isValidDepartureDateTime(departureDateTime)
            &&  flightValidator.isValidDuration( flightDuration )
            &&  flightValidator.isValidPassengersCapacity( passengerCapacity ) )
        {
            Flight newFlight = new Flight( flightName.toUpperCase(),
                                           destinationAirport,
                                           departureDateTime,
                                           flightDuration,
                                           passengerCapacity );

            this.flightsByName.put( flightName, newFlight );
        }
        else
        {
            throw new IllegalArgumentException( "Incorrect parameter value(s) in Flight constructor!" );
        }
    }


    private boolean isFlightAlreadyRegistered( String flightNumber )
    {
        return flightNumber != null
               && this.flightsByName.containsKey( flightNumber.toUpperCase() );
    }


    public boolean canValidFlightAcceptOneNewPassenger( String flightName )
    {
        boolean result = false;

        if( flightName != null
            && this.flightValidator.isValidFlightNameFormat( flightName )
            && this.flightsByName.containsKey( flightName ) )
        {
            Flight flight = this.flightsByName.get( flightName.toUpperCase() );

            result = (flight.getStatus() == FlightStatus.SCHEDULED
                     && flight.getAvailableSeats() > 0);
        }

        return result;
    }


    public Flight searchFlightByName( String searchString )
    {
        Flight result = null;

        if( searchString != null && searchString.length() > 0 )
        {
            String searchStringUppercased = searchString.toUpperCase();
            List<String> validFlightNumber = this.flightsByName.keySet()
                                                    .stream()
                                                    .filter( flightName -> flightName.equals( searchStringUppercased ) )
                                                    .collect( Collectors.toList() );

            result = this.flightsByName.get( validFlightNumber.get( 0 ) );
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
            result = this.flightsByName.values()
                        .stream()
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
        Set<Flight> result = new HashSet<>();

        if( searchPassengerID >= 0 )
        {
            result = this.flightsByName.values().stream()
                                                .filter( flight -> flight.getPassengers().contains( searchPassengerID ) )
                                                .collect( Collectors.toSet() );
        }
        else
        {
            System.out.println( "Incorrect passenger ID provided!" );
        }

        return result;
    }


    /**
     * This method removes only a flight that hasn't started yet (so it isn't departed or already finished).
     * Basically, only scheduled or canceled flights can be deleted.
     * @param flightName the flight name
     */
    public void removeFlight( String flightName )
    {
        // the flight is still kept in its passengers' flight histories
        if( flightName != null
            && this.isFlightAlreadyRegistered( flightName ) )
        {
            Flight flightToRemove = this.flightsByName.get( flightName );

            if( flightToRemove.getStatus() != FlightStatus.DEPARTED
                && flightToRemove.getStatus() != FlightStatus.FINISHED )
            {
                this.flightsByName.remove( flightName );
            }
        }
        else
        {
            System.out.println( "Flight with provided name not found!" );
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
