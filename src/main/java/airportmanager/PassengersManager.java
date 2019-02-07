package airportmanager;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class PassengersManager
{
    // state
    private static volatile PassengersManager           singleton                   = null;
    private                 PassengerValidator          passengerValidator;
    private                 Map<Integer, Passenger>     passengersByID;


    //constructors
    private PassengersManager( PassengerValidator passengerValidator )
    {
        this.passengerValidator = passengerValidator;
        this.passengersByID     = new HashMap<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @return the singleton instance of this class
     */
    public static PassengersManager getSingleton( PassengerValidator passengerValidator )
    {
        if( PassengersManager.singleton == null )
        {
            synchronized( PassengersManager.class )
            {
                if( PassengersManager.singleton == null )
                {
                    PassengersManager.singleton = new PassengersManager( passengerValidator );
                }
            }
        }

        return PassengersManager.singleton;
    }


    // getters & setters

    public PassengerValidator getPassengerValidator()
    {
        return passengerValidator;
    }

    public Map<Integer, Passenger> getPassengersByID()
    {
        return passengersByID;
    }


    // other methods

    public void addPassengerToAvailableFlight( FlightsManager   flightsManager,
                                               Passenger        passenger,
                                               String           flightName )
    {
        if( passenger != null
            && flightName != null
            && flightsManager.canValidFlightAcceptOneNewPassenger( flightName ) )
        {
            if( !this.isPassengerAlreadyRegistered( passenger ) )
            {
                this.passengersByID.put( passenger.getId(), passenger );
            }

            flightsManager.getFlightsByName().get( flightName ).getPassengers().add( passenger.getId() );

            passenger.getFlightsHistory().add( flightName );
        }
        else
        {
            System.out.println( "Incorrect passenger or flight data!" );
        }
    }


    public void addPassengerToAvailableFlight( FlightsManager   flightsManager,
                                               int              passengerID,
                                               String           flightName )
    {
        if( passengerID >= 0
            && flightName != null )
        {
            this.addPassengerToAvailableFlight( flightsManager,
                                                this.passengersByID.get( passengerID ),
                                                flightName );
        }
        else
        {
            System.out.println( "Incorrect passenger ID or flight name!" );
        }
    }


    private boolean isPassengerAlreadyRegistered( Passenger newPassenger )
    {
        boolean result = false;

        if( newPassenger != null )
        {
            result = this.passengersByID.containsKey( newPassenger.getId() )
                     || this.passengersByID.values().stream()
                                                    .anyMatch( passenger -> passenger.equals( newPassenger ) );
        }

        return  result ;
    }


    public Set<Passenger> searchPassengersByName( String nameOrSurnamePart )
    {
        Set<Passenger> result = new HashSet<>();

        int minSearchStringLength = 1;
        if( nameOrSurnamePart != null && nameOrSurnamePart.length() > minSearchStringLength )
        {
            String searchStringLowercased = nameOrSurnamePart.toLowerCase();

            result = this.passengersByID.values()
                                        .stream()
                                        .filter( passenger -> passenger.getSurname().toLowerCase()
                                                                                    .contains(searchStringLowercased)
                                                              || passenger.getName().toLowerCase()
                                                                                    .contains(searchStringLowercased) )
                                        .collect( Collectors.toSet() );
        }

        return result;
    }


    public Set<Passenger> searchPassengersByBirthday( LocalDate day )
    {
        Set<Passenger> result = new HashSet<>();

        if( day != null )
        {
            result = this.passengersByID.values().stream()
                                                 .filter( passenger -> passenger.getBirthday().isEqual( day ) )
                                                 .collect(Collectors.toSet());
        }

        return  result;
    }


    public Set<String> getFlightsHistoryForOnePassenger( int passengerID )
    {
        Set<String> result = new HashSet<>();

        if( passengerID >= 0 )
        {
            if( this.passengersByID.get( passengerID ) != null )
            {
                result = this.passengersByID.get( passengerID ).getFlightsHistory();
            }
            else
            {
                System.out.println( "Incorrect passenger ID provided!" );
            }
        }

        return  result;
    }


    public List<Object> getFlightHistoryForAllPassengers()
    {
        return this.passengersByID.values().stream()
                                           .map( passenger -> passenger.toString()
                                                              + " = "
                                                              + passenger.getFlightsHistory() )
                                           .collect( Collectors.toList() );
    }


    public void removePassengerFromEverything( FlightsManager flightsManager, int passengerID )
    {
        if( passengerID >= 0 )
        {
            Passenger passengerToDelete = this.passengersByID.get( passengerID );

            for( String flightName : passengerToDelete.getFlightsHistory() )
            {
                flightsManager.getFlightsByName().get( flightName ).removePassenger( passengerID );
            }

            this.passengersByID.remove( passengerID );
        }
    }
}
