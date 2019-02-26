package airportmanager;


import airportmanager.model.AirportEntity;
import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;
import airportmanager.repository.AirportRepositoryImpl;
import airportmanager.repository.FlightRepositoryImpl;
import airportmanager.repository.PassengerRepositoryImpl;
import airportmanager.repository.api.AirportRepository;
import airportmanager.repository.api.FlightRepository;
import airportmanager.repository.api.PassengerRepository;
import airportmanager.service.api.AirportService;
import airportmanager.service.api.FlightService;
import airportmanager.service.api.PassengerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class Main
{
    public static void main( String[] args )
    {
        /* SOLUTION 1 = prepare objects using pure Java, with singletons */
//        Airport localAirport = new Airport( "IAS", "Iasi", "Romania" );
//
//        AirportManager    airportManager    = AirportManager.getSingleton(
//                                                  localAirport,
//                                                  FlightsManager.getSingleton( FlightValidator.getSingleton() ),
//                                                  PassengersManager.getSingleton( PassengerValidator.getSingleton() ) );


        /* SOLUTION 2 = using Spring */
//        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );
//        AirportManager airportManager = context.getBean( AirportManager.class );


        /* SOLUTION 3 = using Spring + Hibernate */
        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );
        AirportService      airportService      = context.getBean( AirportService.class );
        AirportRepository   airportRepository   = context.getBean( AirportRepositoryImpl.class );
        FlightService       flightService       = context.getBean( FlightService.class );
        FlightRepository    flightRepository    = context.getBean( FlightRepositoryImpl.class );
        PassengerService    passengerService    = context.getBean( PassengerService.class );
        PassengerRepository passengerRepository = context.getBean( PassengerRepositoryImpl.class );



        /*
         * Create & add the known airports
         * NOTE: test only once with SOLUTION-3 to avoid duplicates in the database!
         */
        System.out.println( "~~~ 1 ~~~" );
        List<Airport> knownAirports = Arrays.asList( new Airport( "OTP", "Bucharest", "Romania" ),
                                                     new Airport( "ORY", "Paris", "France" ),
                                                     new Airport( "NCE", "Nice", "France" ),
                                                     new Airport( "TXL", "Berlin", "Germany" ),
                                                     new Airport( "HAM", "Hamburg", "Germany" ) );

        // for SOLUTION 1 & 2
//        knownAirports.forEach( airportManager::addAirport );

        // for SOLUTION 3
//        Airport localAirport = new Airport( "IAS", "Iasi", "Romania" );
//        airportService.createAirport( localAirport.getCode(),
//                                      localAirport.getCity(),
//                                      localAirport.getCountry() );
//
//        knownAirports.forEach( airport -> airportService.createAirport( airport.getCode(),
//                                                                        airport.getCity(),
//                                                                        airport.getCountry() ) );


        // reporting for: SOLUTION 1 & 2
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationAirports()
//                                                                                      .values() ),
//                                                       "Destination airports",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // reporting for: SOLUTION 3
        AirportEntity localAirportEntity  = airportRepository.findByCode( "IAS" );
        System.out.println( "\n>> Local airport: "
                            + localAirportEntity.getCode()
                            + " " + localAirportEntity.getCity()
                            + " " + localAirportEntity.getCountry() + "\n" );
        System.out.println( "\n>> Destination airports: "
                            + airportRepository.findAll() + "\n" );



        /*
         * Create & add new flights
         * NOTE: test only once with SOLUTION-3 to avoid duplicates in the database!
         */

        System.out.println( "~~~ 2 ~~~" );

        // for SOLUTION 1 & 2
//        airportManager.addFlight( "KW345",
//                                  knownAirports.get( 1 ),
//                                  LocalDateTime.now().plusHours( 1 ),
//                                  (2*60*60 + 50*60),
//                                  150 );
//        airportManager.addFlight( "DK2238",
//                                  knownAirports.get( 3 ),
//                                  LocalDateTime.now().plusHours( 1 ).plusMinutes( 15 ),
//                                  (60*60 + 58*60),
//                                  180 );
//        airportManager.addFlight( "JW1869",
//                                  knownAirports.get( 0 ),
//                                  LocalDateTime.now().plusHours( 2 ).plusMinutes( 40 ),
//                                  (54*60),
//                                  100 );

        // for SOLUTION 3
//        flightService.create( "KW345",
//                              airportRepository.findById( 8L ),
//                              LocalDateTime.now().plusHours( 1 ),
//                              (2*60*60 + 50*60),
//                              150 );
//        flightService.create( "DK2238",
//                              airportRepository.findById( 10L ),
//                              LocalDateTime.now().plusHours( 1 ).plusMinutes( 15 ),
//                              (60*60 + 58*60),
//                              180 );
//        flightService.create( "JW1869",
//                              airportRepository.findById( 7L ),
//                              LocalDateTime.now().plusHours( 2 ).plusMinutes( 40 ),
//                              (54*60),
//                              100 );


        // reporting for: SOLUTION 1 & 2
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .getFlightsByName().values() ),
//                                                       "Flights",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );


        // reporting for: SOLUTION 3 (operation )
        System.out.println( "\n>> Flight DK2238 departure: "
                            + flightRepository.findByName( "DK2238" ).getDepartureDateTime() +"\n" );


        /*
         * Attempt to add a new Flight with unknown destination airport code
         * throws a RuntimeException.
         */

        // for SOLUTION 1 & 2
//        airportManager.addFlight( "GG666",
//                                  null,
//                                  LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
//                                  (3*60*60),
//                                  200 );

        // for SOLUTION 3
//        flightService.create( "VD555",
//                              null,
//                              LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
//                              (3*60*60),
//                              200 );



        /*
         * Create & add new Passengers to active flights
         * NOTE: test only once with SOLUTION-3 to avoid duplicates in the database!
         */

        // for SOLUTION 1 & 2
//        Passenger passenger1 = new Passenger( "John", "Kelson", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger1, "KW345" );
//
//        Passenger passenger2 = new Passenger( "Harry", "Johnson", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger2, "JW1869" );
//
//        Passenger passenger3 = new Passenger( "Laura", "Digsby", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger3, "KW345" );
//
//        Passenger passenger4 = new Passenger( "Monica", "Brown", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger4, "DK2238" );
//
//        Passenger passenger5 = new Passenger( "James", "Shaw", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger5, "KW345" );
//
//        Passenger passenger6 = new Passenger( "Robert", "Law", LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger6, "DK2238" );


        // for SOLUTION 3
//        PassengerEntity newPassenger1 = passengerService.createPassenger( "John",
//                                                                          "Kelson",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "KW345" ), newPassenger1.getId() );
//        flightService.assignPassengerToFlight( newPassenger1, "KW345" );
//
//        PassengerEntity newPassenger2 = passengerService.createPassenger( "Harry",
//                                                                          "Johnson",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "JW1869" ), newPassenger2.getId() );
//        flightService.assignPassengerToFlight( newPassenger2, "JW1869" );
//
//        PassengerEntity newPassenger3 = passengerService.createPassenger( "Laura",
//                                                                          "Digsby",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "KW345" ), newPassenger3.getId() );
//        flightService.assignPassengerToFlight( newPassenger3, "KW345" );
//
//        PassengerEntity newPassenger4 = passengerService.createPassenger( "Monica",
//                                                                          "Brown",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "DK2238" ), newPassenger4.getId() );
//        flightService.assignPassengerToFlight( newPassenger4, "DK2238" );
//
//        PassengerEntity newPassenger5 = passengerService.createPassenger( "James",
//                                                                          "Shaw",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "KW345" ), newPassenger5.getId() );
//        flightService.assignPassengerToFlight( newPassenger5, "KW345" );
//
//        PassengerEntity newPassenger6 = passengerService.createPassenger( "Robert",
//                                                                          "Law",
//                                                                          LocalDate.of( 1998, 8, 8 ) );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "DK2238" ), newPassenger6.getId() );
//        flightService.assignPassengerToFlight( newPassenger6, "DK2238" );



        /*
         * Add a known passenger to a scheduled flight
         */

        System.out.println( "~~~ 3 ~~~" );

        // for SOLUTION 1 & 2
//        String flightName1 = "JW1869";
//        Flight flightToEdit1 = airportManager.getFlightsManager().searchFlightByName( flightName1 );
//        FlightStatus previousStatus1 = flightToEdit1.getStatus();
//        flightToEdit1.setStatus( FlightStatus.SCHEDULED );
//
//        airportManager.addPassengerToAvailableFlight( 5, flightName1 );
//        System.out.println( "Was passenger (#5) added? = "
//                            + ( airportManager.getFlightsManager().searchFlightByName( flightName1 ).getPassengers() )
//                            + "\n" );
//
//        flightToEdit1.setStatus( previousStatus1 );

        // for SOLUTION 3
//        flightService.assignPassengerToFlight( passengerRepository.findByName( "James" ),
//                                               "JW1869" );
//        passengerService.assignFlightToPassenger( flightRepository.findByName( "JW1869" ),
//                                                  passengerRepository.findByName( "James" ).getId() );



        /*
         * Show the flight history for each known passenger
         */

        System.out.println( "~~~ 4 ~~~" );

        // for SOLUTION 1 & 2
//        System.out.println( ReportCreator.buildReport( airportManager.getFlightHistoryForAllPassengers(),
//                                                       "Passengers flight history",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        for( Object obj : passengerRepository.findAll() )
        {
            PassengerEntity passengerEntity = (PassengerEntity) obj;

            System.out.print( ">> passenger #" + passengerEntity.getId() + ": " );
            passengerEntity.getFlightsHistory().forEach( flightEntity -> System.out.print( flightEntity.getName() + ", " ) );
            System.out.println();
        }



        /*
         * Get current flights status report
         */

        System.out.println( "~~~ 5 ~~~" );

        // for SOLUTION 1 & 2
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .getFlightsByName().values() ),
//                                                       "Flights",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        for( Object obj : flightRepository.findAll() )
        {
            FlightEntity flightEntity = (FlightEntity) obj;
            System.out.println( flightEntity.getName()
                                + " to " + flightEntity.getDestinationAirport().getCode()
                                + ", at: " + flightEntity.getDepartureDateTime()
                                + ", " + flightEntity.getStatus()
                                + ", with " + flightEntity.getPassengers().size() + " passengers" );
        }



        /*
         * Automatically update all flights' status when the local time & date changes towards the future
         * CAUTION: After testing, statuses need to be reverted manually to their proper state based on present time!
        */

        System.out.println( "~~~ 6 ~~~" );

        // for SOLUTION 1 & 2
//        LocalDateTime newDateTime = airportManager.updateLocalDateTimeByAddingSeconds( 7200 );
//
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .getFlightsByName().values() ),
//                                                       "Flights",
//                                                       newDateTime,
//                                                       airportManager.getLocalAirport() ) );
//
//        airportManager.updateLocalDateTimeByAddingSeconds( 0 );



        /*
         * Attempts to add a Passenger (new or already known) to a departed Flight are ignored
         */

        System.out.println( "~~~ 7 ~~~" );

        // for SOLUTION 1 & 2
//        String flightName2 = "DK2238";
//        Flight flightToEdit2 = airportManager.getFlightsManager().searchFlightByName( flightName2 );
//        FlightStatus previousStatus2 = flightToEdit2.getStatus();
//        flightToEdit2.setStatus( FlightStatus.DEPARTED );
//
//        Passenger passenger7 = new Passenger( "Gina",
//                                              "Drobson",
//                                              LocalDate.of( 1998, 8, 8 ) );
//        airportManager.addPassengerToAvailableFlight( passenger7, flightToEdit2.getFlightName() );
//        airportManager.addPassengerToAvailableFlight( 5, flightName2 );
//        System.out.println( "Was new passenger added? = "
//                            + (airportManager.getPassengersManager()
//                                             .searchPassengersByName( "drobson" ).size() > 0) );
//        System.out.println( "Was passenger (#5) added? = "
//                            + ( airportManager.getFlightsManager()
//                                              .searchFlightByName( flightName2 ).getPassengers() )
//                            + "\n" );
//
//        flightToEdit2.setStatus( previousStatus2 );



        /*
         * Search flights using various criteria
         */

        System.out.println( "~~~ 8 ~~~" );

        // for SOLUTION 1 & 2
//        LocalDate day1 = LocalDate.now();
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .searchFlightsByDate( day1 ) ),
//                                                       "SEARCH: Flights from " + day1,
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        LocalDateTime dateTime1 = LocalDateTime.of( 2019, 2, 24, 0, 0, 0 );
        for( Object obj : flightRepository.findByDateTime( dateTime1 ) )
        {
            FlightEntity flightEntity = (FlightEntity) obj;
            System.out.println( flightEntity.getName() + ", departure:  " + flightEntity.getDepartureDateTime() );
        }



        System.out.println( "~~~ 9 ~~~" );

        // for SOLUTION 1 & 2
//        String flightName = "Jw1869";
//        System.out.println( "SEARCH: Flight named: \"" + flightName + "\"\n" );
//        System.out.println( airportManager.getFlightsManager().searchFlightByName( flightName ) );

        // for SOLUTION 3
        String flightName = "Jw1869";
        System.out.println( flightRepository.findByName( flightName ).getName() );



        System.out.println( "~~~ 10 ~~~" );

        // for SOLUTION 1 & 2
//        FlightStatus searchedStatus = FlightStatus.SCHEDULED;
//        System.out.println( ReportCreator.buildReport( new ArrayList<>(airportManager.getFlightsManager()
//                                                                                     .searchFlightsByStatus( searchedStatus ) ),
//                                                       "SEARCH: All " + searchedStatus.toString() + " flights",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        FlightStatus status = FlightStatus.SCHEDULED;
        System.out.println( "Flights with status " + status.toString() + ": " );
        for( Object obj : flightRepository.findByStatus( status ) )
        {
            System.out.println( ( (FlightEntity) obj ).getName() );
        }



        System.out.println( "~~~ 11 ~~~" );

        // for SOLUTION 1 & 2
//        LocalDateTime moment1 = LocalDateTime.now().minusMinutes( 30 );
//        LocalDateTime moment2 = LocalDateTime.now().plusMinutes( 120 );
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .searchFlightsByDateInterval( moment1, moment2 ) ),
//                                                       "SEARCH: Flights with departure: " + moment1 + " --> " + moment2,
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        LocalDateTime startDate = LocalDateTime.now().minusDays( 10 );
        LocalDateTime endDate = LocalDateTime.now();
        for( Object obj : flightRepository.findByDateTime( startDate, endDate ) )
        {
            FlightEntity flightEntity = (FlightEntity) obj;
            System.out.println( flightEntity.getName() + ", departure: " + flightEntity.getDepartureDateTime() );
        }



        System.out.println( "~~~ 12 ~~~" );

        // for SOLUTION 1 & 2
//        int passengerID_1 = 5;
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .searchFlightsByPassengerID( passengerID_1 ) ),
//                                                       "SEARCH: Flights of passenger #" + passengerID_1,
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        Long passengerID_1 = 35L;
        System.out.println( "SEARCH: Flights of passenger #" + passengerID_1 + ": ");
        passengerRepository.findById( passengerID_1 ).getFlightsHistory()
                                                     .forEach( flightEntity -> System.out.println( flightEntity.getName() ) );




        /*
         * Remove a flight
         */

        System.out.println( "~~~ 13 ~~~" );

        // for SOLUTION 1 & 2
//        airportManager.getFlightsManager().removeFlight( "DK2238" );
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .getFlightsByName().values() ),
//                                                       "Flights",
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .searchFlightsByPassengerID( 4 ) ),
//                                                       "SEARCH: Flights of passenger #" + 4,
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );
//        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
//                                                                                      .searchFlightsByPassengerID( 6 ) ),
//                                                       "SEARCH: Flights of passenger #" + 6,
//                                                       LocalDateTime.now(),
//                                                       airportManager.getLocalAirport() ) );

        // for SOLUTION 3
        String flightToDelete = "DK2238";
        flightService.delete( flightToDelete );

        for( Object obj : flightRepository.findAll() )
        {
            System.out.println( ( (FlightEntity) obj ).getName() );
        }




/*        // search passengers using various criteria
        System.out.println( "~~~ 14 ~~~" );
        String partialName2 = "john";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getPassengersManager()
                                                                                      .searchPassengersByName( partialName2 ) ),
                                                       "SEARCH: Passengers named like: \"" + partialName2 + "\"",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 15 ~~~" );
        LocalDate birthday = LocalDate.of( 1998, 8, 8 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getPassengersManager()
                                                                                      .searchPassengersByBirthday( birthday ) ),
                                                       "SEARCH: Passengers with birthday: " + birthday,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 16 ~~~" );
        int passengerID_2 = 3;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationsForOnePassenger( passengerID_2 )),
                                                                        "SEARCH: Destinations for passenger #" + passengerID_2,
                                                                        LocalDateTime.now(),
                                                                        airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 17 ~~~" );
        String flightName3 = "KW345";
        System.out.println( ReportCreator.buildReport( airportManager.getPassengersManager().getPassengersByID().values()
                                                                        .stream()
                                                                        .filter( passenger -> passenger.getFlightsHistory()
                                                                                                       .contains( flightName3 ) )
                                                                        .collect( Collectors.toList() ),
                                                       "SEARCH: Passengers from flight " + flightName3,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // remove a passenger
        System.out.println( "~~~ 18 ~~~" );
        int passengerID = 2;
        airportManager.removePassengerFromEverything( passengerID );
        System.out.println( "Passengers data: " + airportManager.getPassengersManager().getPassengersByID() );
*/


        // finally, stop the application
        System.exit( 0 );
    }
}
