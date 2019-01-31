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

    private static volatile       AirportManager    singleton               = null;
    private static                LocalDateTime     currentDateTime         = LocalDateTime.now();
    private                 final Airport           localAirport;
    private                       FlightsManager    flightsManager;
    private                       PassengersManager passengersManager;
    private                       Set<Airport>      destinationAirports;


    // constructors

    @Autowired
    private AirportManager( Airport localAirport )
    {
        this.localAirport           = localAirport;
        this.flightsManager         = FlightsManager.getSingleton( localAirport,
                                                                   AirportManager.currentDateTime.toString() );
        this.passengersManager      = PassengersManager.getSingleton();
        this.destinationAirports    = new HashSet<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @param localAirport the Airport instance representing the airport administered by this instance of this class
     * @return the singleton instance of this class
     */
    public static AirportManager getSingleton( Airport localAirport )
    {
        if( AirportManager.singleton == null
            && localAirport != null )
        {
            synchronized( AirportManager.class )
            {
                if( AirportManager.singleton == null )
                {
                    AirportManager.singleton = new AirportManager( localAirport );
                }
            }
        }

        return AirportManager.singleton;
    }


    /**
     * This method simply returns the currently defined instance of this class.
     * @return the already created singleton instance of this class, or null
     */
    public static AirportManager getSingleton()
    {
        return AirportManager.getSingleton( null );
    }


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

    public LocalDateTime getCurrentDateTime()
    {
        return currentDateTime;
    }

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
                if( a.getAirportCode().equals( airportCode )  )
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
                                          .anyMatch( airport -> airport.getAirportCode().equals( airportCode ) );
    }


    /**
     * Moves the current moment (date & time) as used by the application a number of seconds into the future.
     * This time update also signals an automatic status update of all active flights based on the new time & date.
     * @param seconds the number of seconds to be added to the current local time
     * @throws IllegalArgumentException if using a negative number of seconds
     */
    public void updateLocalDateTimeByAddingSeconds( int seconds ) throws IllegalArgumentException
    {
        if( seconds > 0 )
        {
            AirportManager.currentDateTime = AirportManager.currentDateTime.plusSeconds( seconds );

            this.updateAllFlightsStatusByTime();
        }
        else
        {
            throw new IllegalArgumentException( "Can't change current date & time using negative seconds!" );
        }
    }


    private void updateAllFlightsStatusByTime()
    {
        this.flightsManager.getFlightsByName().values()
                                .stream()
                                .filter( flight -> flight.getStatus() == FlightStatus.SCHEDULED
                                                   || flight.getStatus() == FlightStatus.DEPARTED )
                                .forEach( flight -> {
                                                        if( flightsManager.isFlightFinished( flight ) )
                                                        {
                                                            flight.setStatus( FlightStatus.FINISHED );
                                                        }
                                                        else if( flightsManager.isScheduledFlightDeparted( flight ) )
                                                        {
                                                            flight.setStatus( FlightStatus.DEPARTED );
                                                        }
                                                    } );
    }
}
