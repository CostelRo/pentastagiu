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


    /**
     * This method returns the current singleton instance of this class, if it has already been defined.
     * @return the current singleton instance, if defined, or null
     */
    public static PassengersManager getSingleton()
    {
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

    public void addPassengerToAvailableFlight( Passenger passenger,
                                               String    flightName )
    {
        if( passenger != null
            && flightName != null
            && FlightsManager.getSingleton().canValidFlightAcceptOneNewPassenger( flightName ) )
        {
            if( !this.isPassengerAlreadyRegistered( passenger ) )
            {
                this.passengersByID.put( passenger.getId(), passenger );
            }

            FlightsManager.getSingleton().getFlightsByName().get( flightName ).getPassengers()
                                                                              .add( passenger.getId() );

            passenger.getFlightsHistory().add( flightName );
        }
        else
        {
            System.out.println( "Incorrect passenger or flight data!" );
        }


//        if( this.canValidFlightAcceptOneNewPassenger( codeOfMaybeAvailableFlight ) )
//        {
//            if( passengerValidator.isNameValid( name )
//                && passengerValidator.isNameValid( surname )
//                && passengerValidator.isBirthdayValid( birthday ) )
//            {
//                Passenger newPassenger = new Passenger( name,
//                                                        surname,
//                                                        birthday );
//
//                if( passengerValidator.isPassengerNew( newPassenger ) )
//                {
//
//
//                    this.passengersArchive.addPassenger( newPassenger );
//                }
//                else
//                {
//                    List<Passenger> preexistingPassenger = passengersArchive.getPassengersByID().values()
//                                                                .stream()
//                                                                .filter( passenger -> passenger.equals( newPassenger ) )
//                                                                .collect( Collectors.toList() );
//
//                    preexistingPassenger.get( 0 ).getFlightsHistory().add( codeOfMaybeAvailableFlight );
//                }
//            }
//            else
//            {
//                throw new IllegalArgumentException( "Incorrect parameter value(s) in Passenger constructor!" );
//            }
//
//
//            int newPassengerID = this.addPassenger( name,
//                                                    surname,
//                                                    birthday );
//
//            // add the passenger to the flight
//            FlightsArchive.getSingleton().getFlightsByName().get( codeOfMaybeAvailableFlight )
//                                                            .addPassenger( newPassengerID );
//
////            FlightsManager.getSingleton( FlightValidator.getSingleton() ).getFlightsByName()
////                                                                         .get( codeOfMaybeAvailableFlight )
////                                                                         .addPassenger( newPassengerID );
//
//            // add information into the archive of flights and the archive of passengers
//            this.passengersArchive.addPassenger( newPassengerID );
//            this.passengersByID.get( newPassengerID ).getFlightsHistory().add( codeOfMaybeAvailableFlight );
//
//            this.flightsNamesByPassengerID.putIfAbsent( newPassengerID, new HashSet<>() );
//            this.flightsNamesByPassengerID.get( newPassengerID ).add( codeOfMaybeAvailableFlight );


    }


    public void addPassengerToAvailableFlight( int     passengerID,
                                               String  flightName )
    {
        if( passengerID >= 0
            && flightName != null )
        {
            this.addPassengerToAvailableFlight( this.passengersByID.get( passengerID ),
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


//    private boolean canValidFlightAcceptOneNewPassenger( String flightName )
//    {
//        boolean result = false;
//
//        if( flightName != null
//            &&  flightsArchive.getFlightsByName().containsKey( flightName ) )
//        {
//            Flight flight = flightsArchive.getFlightsByName().get( flightName );
//
//            result = ( flight.getStatus() == FlightStatus.SCHEDULED
//                      && flight.getAvailableSeats() > 0 );
//        }
//
////        FlightsManager flightsManager = FlightsManager.getSingleton( FlightValidator.getSingleton() );
////        if( flightName != null
////            && flightsManager.getFlightsByName().containsKey( flightName ) )
////        {
////            Flight flight = flightsManager.getFlightsByName().get( flightName );
////
////            result = ( (flight.getStatus() == FlightStatus.SCHEDULED)
////                    && (flight.getAvailableSeats() > 0) );
////        }
//
//        return result;
//    }


//    private int addPassenger( String    name,
//                              String    surname,
//                              LocalDate birthday )
//    {
//        int result;
//
//        if( passengerValidator.isNameValid( name )
//            && passengerValidator.isNameValid( surname )
//            && passengerValidator.isBirthdayValid( birthday ) )
//        {
//            Passenger newPassenger = new Passenger( name,
//                                                    surname,
//                                                    birthday );
//
//            if( passengerValidator.isPassengerNew( newPassenger ) )
//            {
//                this.passengersArchive.addPassenger( newPassenger );
//
//                result = newPassenger.getId();
//            }
//            else
//            {
//                List<Passenger> preexistingPassenger = passengersArchive.getPassengersByID().values()
//                                                                .stream()
//                                                                .filter( passenger -> passenger.equals( newPassenger ) )
//                                                                .collect( Collectors.toList() );
//                result = preexistingPassenger.get(0).getId();
//            }
//        }
//        else
//        {
//            throw new IllegalArgumentException( "Incorrect parameter value(s) in Passenger constructor!" );
//        }
//
//        return  result;
//    }


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
//
//
////        AirportManager airportManager = AirportManager.getSingleton(
////                                                AirportsArchive.getSingleton(),
////                                                FlightsManager.getSingleton( FlightValidator.getSingleton(),
////                                                                             FlightsArchive.getSingleton() ),
////                                                PassengersManager.getSingleton( PassengerValidator.getSingleton() ) );
//
//
////        AirportManager airportManager = AirportManager.getSingleton();
////        FlightsArchive flightsArchive = FlightsArchive.getSingleton();
////        if( passengerID >= 0 )
////        {
////            Set<String> flightsOfPassenger = this.passengersArchive.getFlightsNamesByPassengerID().get( passengerID );
////
////            for( String flightName : flightsOfPassenger )
////            {
////                Flight flight = flightsArchive.getFlightsByName().get( flightName );
//////                Flight flight = flightsManager.getFlightsByName().get( flightName );
////
////                Airport destination = airportManager.getAirportByCode( flight.getDestinationAirportCode() );
////
////                result.add( destination );
////            }
////        }

        return  result;
    }


//    public Set<Passenger> getPassengersFromOneFlight( String flightName )
//    {
//        Set<Passenger> result = new HashSet<>();
//
//        if( flightName != null )
//        {
//            Flight flight = FlightsArchive.getSingleton().getFlightsByName().get( flightName.toUpperCase() );
////            Flight flight = FlightsManager.getSingleton( FlightValidator.getSingleton() )
////                                          .getFlightsByName().get( flightName.toUpperCase() );
//
//            if( flight != null )
//            {
//                for (int id : flight.getPassengers())
//                {
//                    result.add(this.passengersArchive.getPassengersByID().get(id));
//                }
//            }
//        }
//
//        return  result;
//    }


    public List<Object> getFlightHistoryForAllPassengers()
    {
        return this.passengersByID.values().stream()
                                           .map( passenger -> passenger.getName()
                                                               + " " + passenger.getSurname().toUpperCase()
                                                               + " (id: " + passenger.getId() + ")"
                                                               + " = "
                                                               + passenger.getFlightsHistory() )
                                           .collect( Collectors.toList() );
    }


//    public List<Object> getPassengersForEachFlight()
//    {
////        return FlightsManager.getSingleton( FlightValidator.getSingleton() )
////                             .getFlightsByName().values().stream()
//
//        return FlightsArchive.getSingleton().getFlightsByName().values().stream()
//                                                                        .map( flight -> flight.getFlightName()
//                                                                                        + " = "
//                                                                                        + flight.getPassengers() )
//                                                                        .collect( Collectors.toList() );
//    }


    public void removePassengerFromEverything( int passengerID )
    {
        if( passengerID >= 0 )
        {
            Passenger passengerToDelete = this.passengersByID.get( passengerID );

            for( String flightName : passengerToDelete.getFlightsHistory() )
            {
                FlightsManager.getSingleton().getFlightsByName().get( flightName ).removePassenger( passengerID );
            }

            this.passengersByID.remove( passengerID );
        }
    }



}
