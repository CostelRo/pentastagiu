package airportmanager;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Main
{
    public static void main( String[] args )
    {
        // SOLUTION 1 = prepare objects using pure Java
//        Airport localAirport = new Airport( "IAS", "Iasi", "Romania" );
//
//        AirportManager    airportManager    = AirportManager.getSingleton(
//                                                  localAirport,
//                                                  FlightsManager.getSingleton( FlightValidator.getSingleton() ),
//                                                  PassengersManager.getSingleton( PassengerValidator.getSingleton() ) );
//
//        FlightsManager    flightsManager    = airportManager.getFlightsManager();
//        PassengersManager passengersManager = airportManager.getPassengersManager();


        // SOLUTION 2 = prepare objects using Spring
        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );

        AirportManager      airportManager    = context.getBean( AirportManager.class );
        FlightsManager      flightsManager    = airportManager.getFlightsManager();
        PassengersManager   passengersManager = airportManager.getPassengersManager();


        // create & add the known airports
        System.out.println( "~~~ 1 ~~~" );
        Set<Airport> knownAirports = new HashSet<>( Arrays.asList(
                                                        new Airport( "OTP", "Bucharest", "Romania" ),
                                                        new Airport( "ORY", "Paris", "France" ),
                                                        new Airport( "NCE", "Nice", "France" ),
                                                        new Airport( "TXL", "Berlin", "Germany" ),
                                                        new Airport( "HAM", "Hamburg", "Germany" ) ) );

        knownAirports.forEach( airportManager::addAirport);

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationAirports().values() ),
                                                       "Destination airports",
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
        flightsManager.addFlight( "GG666",
                                  "xxx",
                                  LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
                                  (3*60*60),
                                  200 );
*/


        // create & add new Passengers to active flights
        Passenger passenger1 = new Passenger( "John",
                                              "Kelson",
                                              LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger1, "KW345" );

        Passenger passenger2 = new Passenger( "Harry",
                                              "Johnson",
                                              LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger2, "JW1869" );

        Passenger passenger3 = new Passenger( "Laura",
                                                "Digsby",
                                                LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger3, "KW345" );

        Passenger passenger4 = new Passenger( "Monica",
                                                "Brown",
                                                LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger4, "DK2238" );

        Passenger passenger5 = new Passenger( "James",
                                                "Shaw",
                                                LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger5, "KW345" );

        Passenger passenger6 = new Passenger( "Robert",
                                                "Law",
                                                LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger6, "DK2238" );


        // add known passenger to scheduled flight
        System.out.println( "~~~ 3 ~~~" );
        String flightName1 = "JW1869";
        Flight flightToEdit1 = flightsManager.searchFlightByName( flightName1 );
        FlightStatus previousStatus1 = flightToEdit1.getStatus();
        flightToEdit1.setStatus( FlightStatus.SCHEDULED );

        passengersManager.addPassengerToAvailableFlight( 5, flightName1 );
        System.out.println( "Was passenger (#5) added? = "
                            + ( flightsManager.searchFlightByName( flightName1 ).getPassengers() )
                            + "\n" );

        flightToEdit1.setStatus( previousStatus1 );


        // show the flight history of each known passengers
        System.out.println( "~~~ 4 ~~~" );
        System.out.println( ReportCreator.buildReport( passengersManager.getFlightHistoryForAllPassengers(),
                                                       "Passengers flight history",
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
        // attempt to add a known Passenger to an already departed Flight is ignored
        System.out.println( "~~~ 7 ~~~" );
        String flightName2 = "DK2238";
        Flight flightToEdit2 = flightsManager.searchFlightByName( flightName2 );
        FlightStatus previousStatus2 = flightToEdit2.getStatus();
        flightToEdit2.setStatus( FlightStatus.DEPARTED );

        Passenger passenger7 = new Passenger( "Gina",
                                                "Drobson",
                                                LocalDate.of( 1998, 8, 8 ) );
        passengersManager.addPassengerToAvailableFlight( passenger7, flightToEdit2.getFlightName() );
        passengersManager.addPassengerToAvailableFlight( 5, flightName2 );
        System.out.println( "Was new passenger added? = "
                            + (passengersManager.searchPassengersByName( "drobson" ).size() > 0) );
        System.out.println( "Was passenger (#5) added? = "
                            + ( flightsManager.searchFlightByName( flightName2 ).getPassengers() )
                            + "\n" );

        flightToEdit2.setStatus( previousStatus2 );


        // search flights using various criteria
        System.out.println( "~~~ 8 ~~~" );
        LocalDate day1 = LocalDate.now();
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.searchFlightsByDate( day1 ) ),
                                                       "SEARCH: Flights from " + day1,
                                                       LocalDateTime.now()) );


        System.out.println( "~~~ 9 ~~~" );
        String flightName = "Jw1869";
        System.out.println( "SEARCH: Flight named: \"" + flightName + "\"\n" );
        System.out.println( flightsManager.searchFlightByName( flightName ) );


        System.out.println( "~~~ 10 ~~~" );
        FlightStatus searchedStatus = FlightStatus.SCHEDULED;
        System.out.println( ReportCreator.buildReport( new ArrayList<>(flightsManager.searchFlightsByStatus( searchedStatus )),
                                                       "SEARCH: All " + searchedStatus.toString() + " flights",
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


        // remove a flight
        System.out.println( "~~~ 13 ~~~" );
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


        // search passengers using various criteria
        System.out.println( "~~~ 14 ~~~" );
        String partialName2 = "john";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                    .searchPassengersByName( partialName2 ) ),
                                                       "SEARCH: Passengers named like: \"" + partialName2 + "\"",
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 15 ~~~" );
        LocalDate birthday = LocalDate.of( 1998, 8, 8 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                            .searchPassengersByBirthday( birthday ) ),
                                                       "SEARCH: Passengers with birthday: " + birthday,
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 16 ~~~" );
        int passengerID_2 = 3;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager
                                                                .getDestinationsForOnePassenger( passengerID_2 )),
                                                       "SEARCH: Destinations for passenger #" + passengerID_2,
                                                       LocalDateTime.now() ) );


        System.out.println( "~~~ 17 ~~~" );
        String flightName3 = "KW345";
        System.out.println( ReportCreator.buildReport( passengersManager.getPassengersByID().values()
                                                                                            .stream()
                                                            .filter( passenger -> passenger.getFlightsHistory()
                                                                                           .contains( flightName3 ) )
                                                            .collect( Collectors.toList() ),
                                                       "SEARCH: Passengers from flight " + flightName3,
                                                       LocalDateTime.now() ) );


        // remove a passenger
        System.out.println( "~~~ 18 ~~~" );
        int passengerID = 2;
        passengersManager.removePassengerFromEverything( passengerID );
        System.out.println( "Passengers data: " + passengersManager.getPassengersByID() );



        // finally, stop the application
        System.exit( 0 );
    }
}
