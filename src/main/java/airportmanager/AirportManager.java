package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Component
public class AirportManager
{
    // state

    private static volatile AirportManager    singleton    = null;
    private                 Airport           localAirport = new Airport( "IAS", "Iasi", "Romania" );
    private                 FlightsManager    flightsManager;
    private                 PassengersManager passengersManager;
    private                 Set<Airport>      destinationAirports;


    // constructors

    @Autowired
    private AirportManager( FlightsManager flightsManager, PassengersManager passengersManager )
    {
        this.flightsManager         = flightsManager;
        this.passengersManager      = passengersManager;
        this.destinationAirports    = new HashSet<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @return the singleton instance of this class
     */
    public static AirportManager getSingleton( FlightsManager flightsManager, PassengersManager passengersManager )
    {
        if( AirportManager.singleton == null )
        {
            synchronized( AirportManager.class )
            {
                if( AirportManager.singleton == null )
                {
                    AirportManager.singleton = new AirportManager( flightsManager, passengersManager );
                }
            }
        }

        return AirportManager.singleton;
    }


//    /**
//     * This method returns the current singleton instance of this class, if it has already been defined.
//     * @return the current singleton instance, if defined, or null
//     */
//    public static AirportManager getSingleton()
//    {
//        return AirportManager.singleton;
//    }


    // getters & setters

    public FlightsManager getFlightsManager()
    {
        return this.flightsManager;
    }

    public PassengersManager getPassengersManager()
    {
        return this.passengersManager;
    }

    public Airport getLocalAirport()
    {
        return localAirport;
    }

//    /**
//     * Sets only once the unique Airport instance representing the local airport administered by this application
//     * (as opposed to the other airports, used only as possible destinations for flights).
//     * @param localAirport the local airport
//     */
//    public void setLocalAirportOnce( Airport localAirport )
//    {
//        if( this.localAirport == null && localAirport != null )
//        {
//            this.localAirport = localAirport;
//        }
//    }

    public Set<Airport> getDestinationAirports()
    {
        return destinationAirports;
    }


    // other methods

    public void addNewAirport( Airport newAirport )
    {
        if( newAirport != null )
        {
            if( !this.isAirportRegistered( newAirport ) )
            {
                this.destinationAirports.add( newAirport );
            }
            else
            {
                System.out.println( "Airport already registered, will not be overwritten with the new data!" );
            }
        }
    }


    public Airport getAirportByCode( String airportCode )
    {
        Airport result = null;

        if( airportCode != null )
        {
            for( Airport a : this.destinationAirports )
            {
                if( a.getCode().equals( airportCode )  )
                {
                    result = a;
                }
            }
        }

        return result;
    }


    private boolean isAirportRegistered( Airport newAirport )
    {
        return newAirport != null
               && this.destinationAirports.contains( newAirport );
    }


    public boolean isAirportRegistered( String airportCode )
    {
        return airportCode != null
               && this.destinationAirports.stream()
                                          .anyMatch( airport -> airport.getCode().equals( airportCode ) );
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
                                                        // the following might happen when
                                                        // time is reverted to the current time after certain tests
                                                        else if( flight.getDepartureDateTime()
                                                                       .isAfter( newLocalDateTime ) )
                                                        {
                                                            flight.setStatus( FlightStatus.SCHEDULED );
                                                        }
                                                    } );
    }
}
