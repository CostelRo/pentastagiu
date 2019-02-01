package airportmanager;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Main
{
    public static void main( String[] args )
    {
//        Airport localAirport = new Airport( "IAS", "Iasi", "Romania" );

        // SOLUTION 1 = prepare objects using pure Java
//        FlightValidator flightValidator  = FlightValidator.getSingleton();
//        FlightsManager  flightsManager   = FlightsManager.getSingleton( flightValidator );
//        PassengerValidator passengerValidator = PassengerValidator.getSingleton();
//        PassengersManager  passengersManager  = PassengersManager.getSingleton( passengerValidator );
//        AirportManager    airportManager    = Main.startAirportManager( flightsManager,
//                                                                        passengersManager,
//                                                                        localAirport );


        // SOLUTION 2 = prepare objects using Spring
        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );
        AirportManager airportManager = (AirportManager) context.getBean( "airportManager" );
//        airportManager.setLocalAirportOnce( localAirport );  // used by the ReportCreator class
        FlightsManager flightsManager       = (FlightsManager) context.getBean( "flightsManager" );
        PassengersManager passengersManager = (PassengersManager) context.getBean( "passengersManager" );


        // create & add the known airports
        System.out.println( "~~~ 1 ~~~" );
        Set<Airport> knownAirports = new HashSet<>( Arrays.asList(
                                                        new Airport( "OTP", "Bucharest", "Romania" ),
                                                        new Airport( "ORY", "Paris", "France" ),
                                                        new Airport( "NCE", "Nice", "France" ),
                                                        new Airport( "TXL", "Berlin", "Germany" ),
                                                        new Airport( "HAM", "Hamburg", "Germany" ) ) );

        knownAirports.forEach( airportManager::addNewAirport );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationAirports() ),
                                                       "Airports",
                                                       LocalDateTime.now() ) );


        // create & add new flights
        System.out.println( "~~~ 2 ~~~" );
        flightsManager.addFlight( "KW345",
                                  "ORY",
                                  LocalDateTime.now().plusHours( 1 ),
                                  (2*60*60 + 50*60),
                                  150 );
        flightsManager.addFlight( "DK2238",
                                  "TXL",
                                  LocalDateTime.now().plusHours( 1 ).plusMinutes( 15 ),
                                  (60*60 + 58*60),
                                  180 );
        flightsManager.addFlight( "JW1869",
                                  "OTP",
                                  LocalDateTime.now().plusHours( 2 ).plusMinutes( 40 ),
                                  (54*60),
                                  100 );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now() ) );


        // attempt to add a new Flight with unknown destination airport code
        // throws a RuntimeException and then stops the program
/*
        airportManager.addFlight( "GG666",
                                  "xxx",
                                  LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
                                  (3*60*60),
                                  200 );
*/


        // create & add new Passengers to active flights
        passengersManager.addPassengerToAvailableFlight( "John",
                                                          "Kelson",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW345" );
        passengersManager.addPassengerToAvailableFlight( "Harry",
                                                          "Johnson",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "JW1869" );
        passengersManager.addPassengerToAvailableFlight( "Laura",
                                                          "Digsby",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW345" );
        passengersManager.addPassengerToAvailableFlight( "Monica",
                                                          "Brown",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "DK2238" );
        passengersManager.addPassengerToAvailableFlight( "James",
                                                          "Shaw",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW345" );
        passengersManager.addPassengerToAvailableFlight( "Robert",
                                                          "Law",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "DK2238" );


        // show the flight history of each known passengers
        System.out.println( "~~~ 3 ~~~" );
        System.out.println( ReportCreator.buildReport( passengersManager.getFlightHistoryForAllPassengers(),
                                                       "Passengers flight history",
                                                       LocalDateTime.now() ) );


        // show the passengers list for each known flight
        System.out.println( "~~~ 4 ~~~" );
        System.out.println( ReportCreator.buildReport( passengersManager.getPassengersForEachFlight(),
                                                       "Passenger-ID list per flight",
                                                       LocalDateTime.now() ) );


        // get current flights status report
        System.out.println( "~~~ 5 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now() ) );


        // automatically update all flights' status when the local time & date change
        // CAUTION: flights' status won't be reverted to their proper state based on the present time!
        System.out.println( "~~~ 6 ~~~" );
        LocalDateTime newDateTime = airportManager.updateLocalDateTimeByAddingSeconds( 7200 );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       newDateTime ) );

        airportManager.updateLocalDateTimeByAddingSeconds( 0 );


        // attempt to add a new Passenger to an already departed Flight is ignored
        System.out.println( "~~~ 7 ~~~" );
        String flightNumber1 = "dk2238";
        Flight flightToEdit = new ArrayList<>( flightsManager.searchFlightsByPartialNumber( flightNumber1 ) ).get( 0 );
        FlightStatus previousStatus = flightToEdit.getStatus();
        flightToEdit.setStatus( FlightStatus.DEPARTED );

        passengersManager.addPassengerToAvailableFlight( "Gina",
                                                         "Drobson",
                                                         LocalDate.of( 1998, 8, 8 ),
                                                         flightToEdit.getFlightNumber() );
        System.out.println( "Was new passenger found? = "
                            + (passengersManager.searchPassengersByPartialName( "drobson" ).size() > 0)
                            + "\n" );

        flightToEdit.setStatus( previousStatus );


        // search flights using various criteria
        System.out.println( "~~~ 8 ~~~" );
        LocalDate day1 = LocalDate.now();
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.searchFlightsByDate( day1 ) ),
                                                       "SEARCH: Flights from " + day1,
                                                       LocalDateTime.now()) );


        System.out.println( "~~~ 9 ~~~" );
        String flightNameFragment = "3";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                .searchFlightsByPartialNumber( flightNameFragment ) ),
                                                       "SEARCH: Flight names containing: \"" + flightNameFragment + "\"",
                                                       LocalDateTime.now() ) );

        System.out.println( "~~~ 10 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                  .searchFlightsByStatus( FlightStatus.SCHEDULED ) ),
                                                       "SEARCH: Scheduled Flights",
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 11 ~~~" );
        LocalDateTime moment1 = LocalDateTime.now().minusMinutes( 30 );
        LocalDateTime moment2 = LocalDateTime.now().plusMinutes( 120 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                    .searchFlightsByDateInterval( moment1, moment2 ) ),
                                              "SEARCH: Flights with departure: " + moment1 + " --> " + moment2,
                                              LocalDateTime.now() ) );


        System.out.println( "~~~ 12 ~~~" );
        int passengerID_1 = 5;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                        .searchFlightsByPassengerID( passengerID_1 ) ),
                                                       "SEARCH: Flights of passenger #" + passengerID_1,
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 13 ~~~" );
        String partialName1 = "aw";
        Map<Passenger, Set<Flight>> searchResults = flightsManager.searchFlightsByPassengerPartialName( partialName1 );
        List<Object> reportData = new ArrayList<>();
        for( Passenger psgr : searchResults.keySet() )
        {
            List<Object> passengerReportData = new ArrayList<>( searchResults.get( psgr ) );
            passengerReportData.add( 0, psgr );
            reportData.add( passengerReportData );
        }
        System.out.println( "SEARCH: Flights by Passenger named like: \"" + partialName1 + "\"\n\n"
                            + reportData + "\n" );


        // remove a flight
        System.out.println( "~~~ 14 ~~~" );
        flightsManager.removeFlight( "DK2238" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                    .searchFlightsByPassengerID( 4 ) ),
                                                       "SEARCH: Flights of passenger #" + 4,
                                                       LocalDateTime.now() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                    .searchFlightsByPassengerID( 6 ) ),
                                                       "SEARCH: Flights of passenger #" + 6,
                                                       LocalDateTime.now() ) );
        System.out.println( flightsManager.getDeletedFlights() + "\n" );


        // search passengers using various criteria
        System.out.println( "~~~ 15 ~~~" );
        String partialName2 = "john";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                    .searchPassengersByPartialName( partialName2 ) ),
                                                       "SEARCH: Passengers named like: \"" + partialName2 + "\"",
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 16 ~~~" );
        LocalDate birthday = LocalDate.of( 1998, 8, 8 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                            .searchPassengersByBirthday( birthday ) ),
                                                       "SEARCH: Passengers with birthday: \"" + birthday + "\"",
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 17 ~~~" );
        int passengerID_2 = 3;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                .searchDestinationsForOnePassenger( passengerID_2 )),
                                                       "SEARCH: Destinations for passenger #" + passengerID_2,
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 18 ~~~" );
        String flightNumber2 = "KW345";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                        .getPassengersFromOneFlight( flightNumber2 ) ),
                                                       "SEARCH: Passengers from flight " + flightNumber2,
                                                       LocalDateTime.now() ) );



        // remove a passenger
        System.out.println( "~~~ 19 ~~~" );
        passengersManager.removePassengerByIDFromEverything( 2 );
        System.out.println( ReportCreator.buildReport( passengersManager.getPassengersForEachFlight(),
                                                       "Passenger-ID list per flight",
                                                       LocalDateTime.now() ) );
        System.out.println( passengersManager.getDeletedPassengers() + "\n" );




        // finally, stop the application
        Main.stopAirportManager();
    }


    private static AirportManager startAirportManager( FlightsManager flightsManager,
                                                       PassengersManager passengersManager,
                                                       Airport localAirport )
    {
        AirportManager newAirportManager = AirportManager.getSingleton( flightsManager, passengersManager );
//        newAirportManager.setLocalAirportOnce( localAirport );

        return newAirportManager;
    }


    private static void stopAirportManager()
    {
        System.out.println( ">> Everything OK? Let's stop!" );

        System.exit( 0 );
    }
}
