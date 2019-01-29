package weeks_5_6_final;


import java.time.LocalDate;
import java.util.ArrayList;
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
    private                 Map<Integer, Set<String>>   flightsNamesByPassengerID;
    private                 Set<Passenger>              deletedPassengers;


    //constructors
    private PassengersManager( PassengerValidator passengerValidator )
    {
        this.passengerValidator         = passengerValidator;
        this.passengersByID             = new HashMap<>();
        this.flightsNamesByPassengerID  = new HashMap<>();
        this.deletedPassengers          = new HashSet<>();
    }


    /**
     * This method creates if needed and returns a unique instance of the class, implementing the Singleton pattern.
     * After the creation of the singleton instance, any attempts to call this method with other parameters
     * will be ignored and the existing singleton instance will be returned unchanged.
     * @param passengerValidator the objects used for validations of the passengers
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


    /**
     *
     * This method simply returns the currently defined instance of this class.
     * @return the already created singleton instance of this class, or null
     */
    public static PassengersManager getSingleton()
    {
        return singleton;
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

    public Map<Integer, Set<String>> getFlightsNamesByPassengerID()
    {
        return flightsNamesByPassengerID;
    }

    public Set<Passenger> getDeletedPassengers()
    {
        return deletedPassengers;
    }


    // other methods

    public void addPassengerToAvailableFlight( String    name,
                                               String    surname,
                                               LocalDate birthday,
                                               String    codeOfAvailableFlight )
    {
        if( this.canValidFlightAcceptOneNewPassenger( codeOfAvailableFlight ) )
        {
            int newPassengerID = this.addPassenger( name,
                    surname,
                    birthday );

            FlightsManager.getSingleton().getFlightsByName().get( codeOfAvailableFlight ).addPassenger( newPassengerID );

            this.passengersByID.get( newPassengerID ).getFlightHistory().add( codeOfAvailableFlight );

            this.flightsNamesByPassengerID.putIfAbsent( newPassengerID, new HashSet<>() );
            this.flightsNamesByPassengerID.get( newPassengerID ).add( codeOfAvailableFlight );
        }
    }


    private boolean canValidFlightAcceptOneNewPassenger( String flightNumber )
    {
        boolean result = false;

        if( flightNumber != null
                && FlightsManager.getSingleton().getFlightsByName().containsKey( flightNumber ) )
        {
            Flight flight = FlightsManager.getSingleton().getFlightsByName().get( flightNumber );

            result = ( (flight.getStatus() == FlightStatus.SCHEDULED)
                    && (flight.getAvailableSeats() > 0) );
        }

        return result;
    }


    private int addPassenger( String    name,
                              String    surname,
                              LocalDate birthday )
    {
        if( passengerValidator.isNameValid( name )
            && passengerValidator.isNameValid( surname )
            && passengerValidator.isBirthdayValid( birthday ) )
        {
            Passenger newPassenger = new Passenger( name,
                                                    surname,
                                                    birthday );

            if( passengerValidator.isPassengerNew( newPassenger ) )
            {
                this.passengersByID.put( newPassenger.getId(), newPassenger );

                return newPassenger.getId();
            }
            else
            {
                List<Passenger> preexistingPassenger = this.passengersByID.values()
                                                                .stream()
                                                                .filter( passenger -> passenger.equals( newPassenger ) )
                                                                .collect( Collectors.toList() );
                return preexistingPassenger.get(0).getId();
            }
        }
        else
        {
            throw new IllegalArgumentException( "Incorrect parameter value(s) in Passenger constructor!" );
        }
    }


    public Set<Passenger> searchPassengersByPartialName( String nameOrSurnamePart )
    {
        Set<Passenger> result = new HashSet<>();

        if( nameOrSurnamePart != null && nameOrSurnamePart.length() > 1 )
        {
            String searchStringLowercased = nameOrSurnamePart.toLowerCase();

            result = this.passengersByID.values().stream()
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


    public Set<Airport> searchDestinationsForOnePassenger( int passengerID )
    {
        Set<Airport> result = new HashSet<>();

        if( passengerID >= 0 )
        {
            Set<String> flightsOfPassenger = this.getFlightsNamesByPassengerID().get( passengerID );

            for( String flightName : flightsOfPassenger )
            {
                Flight flight = FlightsManager.getSingleton().getFlightsByName().get( flightName );
                Airport destination = AirportManager.getSingleton()
                                                            .getAirportByCode( flight.getDestinationAirportCode() );

                result.add( destination );
            }
        }

        return  result;
    }


    public Set<Passenger> getPassengersFromOneFlight( String flightName )
    {
        Set<Passenger> result = new HashSet<>();

        if( flightName != null )
        {
            Flight flight = FlightsManager.getSingleton().getFlightsByName().get( flightName );

            for( int id : flight.getPassengers() )
            {
                result.add( this.getPassengersByID().get( id ) );
            }
        }

        return  result;
    }


    public List<Object> getFlightHistoryForAllPassengers()
    {
        return this.getPassengersByID().values().stream()
                                                .map( passenger -> passenger.getName()
                                                                        + " " + passenger.getSurname().toUpperCase()
                                                                        + " (id: " + passenger.getId() + ")"
                                                                        + " = "
                                                                        + passenger.getFlightHistory() )
                                                .collect( Collectors.toList() );
    }


    public List<Object> getPassengersForEachFlight()
    {
        return FlightsManager.getSingleton().getFlightsByName().values().stream()
                                                                        .map( flight -> flight.getFlightNumber()
                                                                                            + " = "
                                                                                            + flight.getPassengers() )
                                                                        .collect( Collectors.toList() );
    }


    public void removePassengerByIDFromEverything( int passengerID )
    {
        if( passengerID >= 0 )
        {
            Passenger passengerToDelete = this.passengersByID.get( passengerID );

            // remove passenger from each flight's passengers list
            for( String flightName : this.getFlightsNamesByPassengerID().get( passengerID ) )
            {
                List<Flight> flightsAsList = new ArrayList<>( FlightsManager.getSingleton()
                                                                            .searchFlightsByPartialNumber(flightName) );
                for( Flight f : flightsAsList )
                {
                    f.removePassengerByIDFromThisFlight( passengerID );
                }
            }

            // remove passenger from the general archives in the system
            this.passengersByID.remove( passengerID );
            this.getFlightsNamesByPassengerID().remove( passengerID );

            // add deleted passenger to the deleted passengers archive
            this.deletedPassengers.add( passengerToDelete );
        }
    }
}
