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
    @Autowired
    private                  FlightValidator      flightValidator;
    private                  Airport              localAirport;
    private                  LocalDateTime        currentDateTime;
    private                  Map<String, Flight>  flightsByName;
    private                  Set<Flight>          deletedFlights;


    // constructors

    @Autowired
    private FlightsManager( Airport localAirport,
                            LocalDateTime currentDateTime,
                            FlightValidator flightValidator )
    {
        this.flightValidator = flightValidator;
        this.localAirport    = localAirport;
        this.currentDateTime = currentDateTime;
        this.flightsByName   = new HashMap<>();
        this.deletedFlights  = new HashSet<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @param localAirport the Airport instance representing the airport administered by this instance of this class
     * @param currentDateTime the LocalDateTime used as current by the entire app.
     * @param flightValidator the objects used for validations of the flights
     * @return the singleton instance of this class
     */
    public static FlightsManager getSingleton( Airport localAirport,
                                               LocalDateTime currentDateTime,
                                               FlightValidator flightValidator )
    {
        if( FlightsManager.singleton == null
                && localAirport != null
                && currentDateTime != null )
        {
            synchronized( FlightsManager.class )
            {
                if( FlightsManager.singleton == null )
                {
                    FlightsManager.singleton = new FlightsManager( localAirport, currentDateTime, flightValidator );
                }
            }
        }

        return FlightsManager.singleton;
    }


    /**
     *
     * This method simply returns the currently defined instance of this class.
     * @return the already created singleton instance of this class, or null
     */
    public static FlightsManager getSingleton()
    {
        return FlightsManager.singleton;
    }


    // getters & setters

    public FlightValidator getFlightValidator()
    {
        return flightValidator;
    }

    public Airport getLocalAirport()
    {
        return localAirport;
    }

    public LocalDateTime getCurrentDateTime()
    {
        return currentDateTime;
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

        if( searchPassengerID > 0  & PassengersManager.getSingleton().getFlightsNamesByPassengerID()
                                                                     .get( searchPassengerID ) != null )
        {
            result = PassengersManager.getSingleton().getFlightsNamesByPassengerID().get( searchPassengerID )
                     .stream()
                     .map( flightNumber -> this.flightsByName.get( flightNumber ) )
                     .collect( Collectors.toSet() );
        }

        return result;
    }


    public Map<Passenger, Set<Flight>> searchFlightsByPassengerPartialName( String nameFragment )
    {
        Map<Passenger, Set<Flight>> result = new HashMap<>();

        for( Passenger psgr : PassengersManager.getSingleton().searchPassengersByPartialName( nameFragment ) )
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


    public boolean isFlightFinished( Flight flight )
    {
        return flight.getDepartureDateTime().plusSeconds( flight.getDurationInSeconds() )
                                            .isBefore( AirportManager.getSingleton().getCurrentDateTime() );
    }


    public boolean isScheduledFlightDeparted( Flight flight )
    {
        return flight.getDepartureDateTime().isBefore( AirportManager.getSingleton().getCurrentDateTime() );
    }
}
