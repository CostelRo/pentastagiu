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
        // prepare objects for testing

        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );

        Airport             localAirport         = new Airport( "IAS",
                                                                "Iasi",
                                                                "Romania");
        AirportManager      airportManager       = Main.startAirportManager( localAirport );
        FlightsManager    flightsManager    = FlightsManager.getSingleton();
        PassengersManager passengersManager = PassengersManager.getSingleton();


        // create & add the known airports
        System.out.println( "~~~ 1 ~~~" );
        Set<Airport> knownAirports = new HashSet<>( Arrays.asList(
                                        new Airport( "OTP", "Bucharest", "Romania"),
                                        new Airport( "ORY", "Paris", "France"),
                                        new Airport( "NCE", "Nice", "France"),
                                        new Airport( "TXL", "Berlin", "Germany"),
                                        new Airport( "HAM", "Hamburg", "Germany") ) );

        knownAirports.forEach( airportManager::addNewAirport );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationAirports() ),
                                                       "Airports",
                                                       airportManager.getCurrentDateTime() ) );


        // create & add new flights
        System.out.println( "~~~ 2 ~~~" );
        flightsManager.addFlight( "KW-345",
                                  "ORY",
                                  LocalDateTime.now().plusHours( 1 ),
                                  (2*60*60 + 50*60),
                                  150 );
        flightsManager.addFlight( "DK-2238",
                                  "TXL",
                                  LocalDateTime.now().plusHours( 1 ).plusMinutes( 15 ),
                                  (1*60*60 + 58*60),
                                  180 );
        flightsManager.addFlight( "JW-1869",
                                  "OTP",
                                  LocalDateTime.now().plusHours( 2 ).plusMinutes( 40 ),
                                  (54*60),
                                  100 );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       airportManager.getCurrentDateTime() ) );


        // attempt to add a new Flight with unknown destination airport code
        // throws a RuntimeException and then stops the program
/*
        airportManager.addFlight( "GG-666",
                                  "xxx",
                                  LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
                                  (3*60*60),
                                  200 );
*/


        // create & add new Passengers to active flights
        passengersManager.addPassengerToAvailableFlight( "John",
                                                          "Kelson",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW-345" );
        passengersManager.addPassengerToAvailableFlight( "Harry",
                                                          "Johnson",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "JW-1869" );
        passengersManager.addPassengerToAvailableFlight( "Laura",
                                                          "Digsby",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW-345" );
        passengersManager.addPassengerToAvailableFlight( "Monica",
                                                          "Brown",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "DK-2238" );
        passengersManager.addPassengerToAvailableFlight( "James",
                                                          "Shaw",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "KW-345" );
        passengersManager.addPassengerToAvailableFlight( "Robert",
                                                          "Law",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "DK-2238" );


        // show the flight history of each known passengers
        System.out.println( "~~~ 3 ~~~" );
        System.out.println( ReportCreator.buildReport( passengersManager.getFlightHistoryForAllPassengers(),
                                                       "Passengers flight history",
                                                       airportManager.getCurrentDateTime() ) );


        // show the passengers list for each known flight
        System.out.println( "~~~ 4 ~~~" );
        System.out.println( ReportCreator.buildReport( passengersManager.getPassengersForEachFlight(),
                                                       "Passenger-ID list per flight",
                                                       airportManager.getCurrentDateTime() ) );


        // get current flights status report
        System.out.println( "~~~ 5 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       airportManager.getCurrentDateTime() ) );


        // update the local date & time used by the airport
        AirportManager.getSingleton().updateLocalDateTimeByAddingSeconds( 7200 );

        System.out.println( "~~~ 6 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       airportManager.getCurrentDateTime() ) );


        // attempt to add a new Passenger to an already departed Flight is ignored
        passengersManager.addPassengerToAvailableFlight( "Gina",
                                                          "Drobson",
                                                          LocalDate.of( 1998, 8, 8 ),
                                                          "DK-2238" );


        // search flights using various criteria
        System.out.println( "~~~ 7 ~~~" );
        LocalDate day1 = LocalDate.now();
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.searchFlightsByDate( day1 ) ),
                                                      "SEARCH: Flights from " + day1,
                                                       airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 8 ~~~" );
        String flightNameFragment = "3";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                              .searchFlightsByPartialNumber( flightNameFragment ) ),
                                              "SEARCH: Flight names containing: \"" + flightNameFragment + "\"",
                                              airportManager.getCurrentDateTime() ) );

        System.out.println( "~~~ 9 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                              .searchFlightsByStatus( FlightStatus.SCHEDULED ) ),
                                                              "SEARCH: Scheduled Flights",
                                                              airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 10 ~~~" );
        LocalDateTime moment1 = LocalDateTime.now().minusMinutes( 30 );
        LocalDateTime moment2 = LocalDateTime.now().plusMinutes( 120 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                              .searchFlightsByDateInterval( moment1, moment2 ) ),
                                              "SEARCH: Flights with departure: " + moment1 + " --> " + moment2,
                                              airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 11 ~~~" );
        int passengerID_1 = 5;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                                .searchFlightsByPassengerID( passengerID_1 ) ),
                                                                "SEARCH: Flights of passenger #" + passengerID_1,
                                                                airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 12 ~~~" );
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
        System.out.println( "~~~ 13 ~~~" );
        flightsManager.removeFlight( "DK-2238" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager.getFlightsByName().values() ),
                                                       "Flights",
                                                       airportManager.getCurrentDateTime() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                               .searchFlightsByPassengerID( 4 ) ),
                                                               "SEARCH: Flights of passenger #" + 4,
                                                               airportManager.getCurrentDateTime() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( flightsManager
                                                               .searchFlightsByPassengerID( 6 ) ),
                                                               "SEARCH: Flights of passenger #" + 6,
                                                               airportManager.getCurrentDateTime() ) );
        System.out.println( flightsManager.getDeletedFlights() + "\n" );


        // search passengers using various criteria
        System.out.println( "~~~ 14 ~~~" );
        String partialName2 = "john";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                    .searchPassengersByPartialName( partialName2 ) ),
                                                "SEARCH: Passengers named like: \"" + partialName2 + "\"",
                                                airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 15 ~~~" );
        LocalDate birthday = LocalDate.of( 1998, 8, 8 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                            .searchPassengersByBirthday( birthday ) ),
                                                    "SEARCH: Passengers with birthday: \"" + birthday + "\"",
                                                    airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 16 ~~~" );
        int passengerID_2 = 3;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                .searchDestinationsForOnePassenger( passengerID_2 )),
                                                       "SEARCH: Destinations for passenger #" + passengerID_2,
                                                       airportManager.getCurrentDateTime() ) );


        System.out.println( "~~~ 17 ~~~" );
        String flightNumber = "KW-345";
        System.out.println( ReportCreator.buildReport( new ArrayList<>( passengersManager
                                                                        .getPassengersFromOneFlight( flightNumber ) ),
                                                               "SEARCH: Passengers from flight " + flightNumber,
                                                               airportManager.getCurrentDateTime() ) );



        // remove a passenger
        System.out.println( "~~~ 18 ~~~" );
        passengersManager.removePassengerByIDFromEverything( 2 );
        System.out.println( ReportCreator.buildReport( passengersManager.getPassengersForEachFlight(),
                                                       "Passenger-ID list per flight",
                                                       airportManager.getCurrentDateTime() ) );
        System.out.println( passengersManager.getDeletedPassengers() + "\n" );




        // finally, stop the application
        Main.stopAirportManager();
    }


    private static AirportManager startAirportManager( Airport localAirport )
    {
        return AirportManager.getSingleton( localAirport );
    }


    private static void stopAirportManager()
    {
        System.out.println( ">> Everything OK? Let's stop!" );

        System.exit( 0 );
    }
}
