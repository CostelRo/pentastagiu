package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class AirportManager
{
    // state

    private static volatile AirportManager       singleton              = null;
    private                 Airport              localAirport;
    private                 Map<String, Airport> destinationAirports;
    private                 FlightsManager       flightsManager;
    private                 PassengersManager    passengersManager;


    // constructors

    private AirportManager( Airport           localAirport,
                            FlightsManager    flightsManager,
                            PassengersManager passengersManager )
    {
        this.localAirport           = localAirport;
        this.destinationAirports    = new HashMap<>();
        this.flightsManager         = flightsManager;
        this.passengersManager      = passengersManager;
    }


    @Autowired
    public static AirportManager getSingleton( @Qualifier( "localAirport" ) Airport localAirport,
                                               FlightsManager       flightsManager,
                                               PassengersManager    passengersManager )
    {
        if( AirportManager.singleton == null )
        {
            synchronized( AirportManager.class )
            {
                if( AirportManager.singleton == null )
                {
                    AirportManager.singleton = new AirportManager( localAirport,
                                                                   flightsManager,
                                                                   passengersManager );
                }
            }
        }

        return AirportManager.singleton;
    }


    // getters & setters

    public Airport getLocalAirport()
    {
        return localAirport;
    }

    public Map<String, Airport> getDestinationAirports()
    {
        return  destinationAirports;
    }

    public FlightsManager getFlightsManager()
    {
        return this.flightsManager;
    }

    public PassengersManager getPassengersManager()
    {
        return this.passengersManager;
    }


    // other methods

    public void addAirport( Airport newAirport )
    {
        if( newAirport != null && !this.isAirportRegistered( newAirport ) )
        {
            this.destinationAirports.put( newAirport.getCode(), newAirport );
        }
        else
        {
            System.out.println( "Airport already registered, will not be overwritten with the new data!" );
        }
    }


    public Airport getAirportByCode( String airportCode )
    {
        Airport result = null;

        if( airportCode != null )
        {
            result = this.destinationAirports.get( airportCode );
        }

        return result;
    }


    public boolean isAirportRegistered( Airport airport )
    {
        return airport != null
               && this.destinationAirports.containsValue( airport );
    }


    public void addFlight( String           flightName,
                           Airport          destinationAirport,
                           LocalDateTime    departureDateTime,
                           int              flightDuration ,
                           int              passengerCapacity )
    {
        if( this.isAirportRegistered( destinationAirport ) )
        {
            this.flightsManager.addFlight( flightName,
                                           destinationAirport,
                                           departureDateTime,
                                           flightDuration,
                                           passengerCapacity );
        }
    }


    public void addPassengerToAvailableFlight( Passenger passenger, String flightName )
    {
        if( passenger != null
            && flightName != null )
        {
            this.passengersManager.addPassengerToAvailableFlight( this.flightsManager, passenger, flightName );
        }
    }


    public void addPassengerToAvailableFlight( int passengerID, String flightName )
    {
        if( passengerID >= 0
            && flightName != null )
        {
            this.passengersManager.addPassengerToAvailableFlight( this.flightsManager, passengerID, flightName );
        }
    }


    public Set<Airport> getDestinationsForOnePassenger( int passengerID )
    {
        Set<Airport> result = new HashSet<>();

        if( passengerID >= 0 )
        {
            Set<String> passengerFlightHistory = this.passengersManager.getFlightsHistoryForOnePassenger( passengerID );

            for( String flightName : passengerFlightHistory )
            {
                result.add( this.flightsManager.getFlightsByName().get( flightName ).getDestinationAirport() );
            }
        }

        return  result;
    }


    public List<Object> getFlightHistoryForAllPassengers()
    {
        return this.passengersManager.getFlightHistoryForAllPassengers();
    }


    public void removePassengerFromEverything( int passengerID )
    {
        this.passengersManager.removePassengerFromEverything( this.flightsManager, passengerID );
    }


    /**
     * Moves the current moment (date & time) as used by the application a number of seconds into the future.
     * This time update also signals an automatic status update of all active flights based on the new time & date.
     * @param seconds the number of seconds to be added to the current local time (0 reverts to the current time)
     * @return the new LocalDateTime used for checking flights' status
     * @throws IllegalArgumentException if using a negative number of seconds
     */
    public LocalDateTime updateLocalDateTimeByAddingSeconds( int seconds ) throws IllegalArgumentException
    {
        if( seconds >= 0 )
        {
            LocalDateTime newLocalDateTime = LocalDateTime.now().plusSeconds( seconds );
            this.updateAllFlightsStatusToNewDateTime( newLocalDateTime );

            return newLocalDateTime;
        }
        else
        {
            throw new IllegalArgumentException( "Can't change current date & time using negative seconds!" );
        }
    }


    private void updateAllFlightsStatusToNewDateTime( LocalDateTime newLocalDateTime )
    {
        this.flightsManager.getFlightsByName().values()
                                .stream()
                                .filter( flight -> flight.getStatus() == FlightStatus.SCHEDULED
                                                   || flight.getStatus() == FlightStatus.DEPARTED )
                                .forEach( flight -> {
                                                        if( flightsManager.isFlightFinished( flight,
                                                                                             newLocalDateTime ) )
                                                        {
                                                            flight.setStatus( FlightStatus.FINISHED );
                                                        }
                                                        else if( flightsManager.isScheduledFlightDeparted( flight,
                                                                                                    newLocalDateTime ) )
                                                        {
                                                            flight.setStatus( FlightStatus.DEPARTED );
                                                        }
                                                        // this might happen after certain tests, when time is reverted
                                                        // back towards the past to the current time
                                                        else if( flight.getDepartureDateTime()
                                                                       .isAfter( newLocalDateTime ) )
                                                        {
                                                            flight.setStatus( FlightStatus.SCHEDULED );
                                                        }
                                                    } );
    }
}
