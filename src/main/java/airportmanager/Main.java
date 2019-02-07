package airportmanager;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


        // SOLUTION 2 = prepare objects using Spring
        ApplicationContext context = new ClassPathXmlApplicationContext( "airport-application-context.xml" );
        AirportManager      airportManager    = context.getBean( AirportManager.class );


        // create & add the known airports
        System.out.println( "~~~ 1 ~~~" );
        List<Airport> knownAirports = Arrays.asList( new Airport( "OTP", "Bucharest", "Romania" ),
                                                     new Airport( "ORY", "Paris", "France" ),
                                                     new Airport( "NCE", "Nice", "France" ),
                                                     new Airport( "TXL", "Berlin", "Germany" ),
                                                     new Airport( "HAM", "Hamburg", "Germany" ) );

        knownAirports.forEach( airportManager::addAirport);

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getDestinationAirports()
                                                                                      .values() ),
                                                       "Destination airports",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // create & add new flights
        System.out.println( "~~~ 2 ~~~" );
        airportManager.addFlight( "KW345",
                                  knownAirports.get( 1 ),
                                  LocalDateTime.now().plusHours( 1 ),
                                  (2*60*60 + 50*60),
                                  150 );
        airportManager.addFlight( "DK2238",
                                  knownAirports.get( 3 ),
                                  LocalDateTime.now().plusHours( 1 ).plusMinutes( 15 ),
                                  (60*60 + 58*60),
                                  180 );
        airportManager.addFlight( "JW1869",
                                  knownAirports.get( 0 ),
                                  LocalDateTime.now().plusHours( 2 ).plusMinutes( 40 ),
                                  (54*60),
                                  100 );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // attempt to add a new Flight with unknown destination airport code
        // throws a RuntimeException and then stops the program
/*
        airportManager.addFlight( "GG666",
                                  null,
                                  LocalDateTime.now().plusHours( 4 ).plusMinutes( 10 ),
                                  (3*60*60),
                                  200 );
*/


        // create & add new Passengers to active flights
        Passenger passenger1 = new Passenger( "John",
                                              "Kelson",
                                              LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger1, "KW345" );

        Passenger passenger2 = new Passenger( "Harry",
                                              "Johnson",
                                              LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger2, "JW1869" );

        Passenger passenger3 = new Passenger( "Laura",
                                                "Digsby",
                                                LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger3, "KW345" );

        Passenger passenger4 = new Passenger( "Monica",
                                                "Brown",
                                                LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger4, "DK2238" );

        Passenger passenger5 = new Passenger( "James",
                                                "Shaw",
                                                LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger5, "KW345" );

        Passenger passenger6 = new Passenger( "Robert",
                                                "Law",
                                                LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger6, "DK2238" );


        // add known passenger to scheduled flight
        System.out.println( "~~~ 3 ~~~" );
        String flightName1 = "JW1869";
        Flight flightToEdit1 = airportManager.getFlightsManager().searchFlightByName( flightName1 );
        FlightStatus previousStatus1 = flightToEdit1.getStatus();
        flightToEdit1.setStatus( FlightStatus.SCHEDULED );

        airportManager.addPassengerToAvailableFlight( 5, flightName1 );
        System.out.println( "Was passenger (#5) added? = "
                            + ( airportManager.getFlightsManager().searchFlightByName( flightName1 ).getPassengers() )
                            + "\n" );

        flightToEdit1.setStatus( previousStatus1 );


        // show the flight history for each known passenger
        System.out.println( "~~~ 4 ~~~" );
        System.out.println( ReportCreator.buildReport( airportManager.getFlightHistoryForAllPassengers(),
                                                       "Passengers flight history",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // get current flights status report
        System.out.println( "~~~ 5 ~~~" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // automatically update all flights' status when the local time & date change
        // CAUTION: flight's status won't be reverted automatically to their proper state based on the present time!
        System.out.println( "~~~ 6 ~~~" );
        LocalDateTime newDateTime = airportManager.updateLocalDateTimeByAddingSeconds( 7200 );

        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .getFlightsByName().values() ),
                                                       "Flights",
                                                       newDateTime,
                                                       airportManager.getLocalAirport() ) );

        airportManager.updateLocalDateTimeByAddingSeconds( 0 );


        // attempts to add a Passenger (new or already known) to a departed Flight are ignored
        System.out.println( "~~~ 7 ~~~" );
        String flightName2 = "DK2238";
        Flight flightToEdit2 = airportManager.getFlightsManager().searchFlightByName( flightName2 );
        FlightStatus previousStatus2 = flightToEdit2.getStatus();
        flightToEdit2.setStatus( FlightStatus.DEPARTED );

        Passenger passenger7 = new Passenger( "Gina",
                                              "Drobson",
                                              LocalDate.of( 1998, 8, 8 ) );
        airportManager.addPassengerToAvailableFlight( passenger7, flightToEdit2.getFlightName() );
        airportManager.addPassengerToAvailableFlight( 5, flightName2 );
        System.out.println( "Was new passenger added? = "
                            + (airportManager.getPassengersManager()
                                             .searchPassengersByName( "drobson" ).size() > 0) );
        System.out.println( "Was passenger (#5) added? = "
                            + ( airportManager.getFlightsManager()
                                              .searchFlightByName( flightName2 ).getPassengers() )
                            + "\n" );

        flightToEdit2.setStatus( previousStatus2 );


        // search flights using various criteria
        System.out.println( "~~~ 8 ~~~" );
        LocalDate day1 = LocalDate.now();
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .searchFlightsByDate( day1 ) ),
                                                       "SEARCH: Flights from " + day1,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 9 ~~~" );
        String flightName = "Jw1869";
        System.out.println( "SEARCH: Flight named: \"" + flightName + "\"\n" );
        System.out.println( airportManager.getFlightsManager().searchFlightByName( flightName ) );


        System.out.println( "~~~ 10 ~~~" );
        FlightStatus searchedStatus = FlightStatus.SCHEDULED;
        System.out.println( ReportCreator.buildReport( new ArrayList<>(airportManager.getFlightsManager()
                                                                                     .searchFlightsByStatus( searchedStatus ) ),
                                                       "SEARCH: All " + searchedStatus.toString() + " flights",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 11 ~~~" );
        LocalDateTime moment1 = LocalDateTime.now().minusMinutes( 30 );
        LocalDateTime moment2 = LocalDateTime.now().plusMinutes( 120 );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .searchFlightsByDateInterval( moment1, moment2 ) ),
                                                       "SEARCH: Flights with departure: " + moment1 + " --> " + moment2,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        System.out.println( "~~~ 12 ~~~" );
        int passengerID_1 = 5;
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .searchFlightsByPassengerID( passengerID_1 ) ),
                                                       "SEARCH: Flights of passenger #" + passengerID_1,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // remove a flight
        System.out.println( "~~~ 13 ~~~" );
        airportManager.getFlightsManager().removeFlight( "DK2238" );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .getFlightsByName().values() ),
                                                       "Flights",
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .searchFlightsByPassengerID( 4 ) ),
                                                       "SEARCH: Flights of passenger #" + 4,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );
        System.out.println( ReportCreator.buildReport( new ArrayList<>( airportManager.getFlightsManager()
                                                                                      .searchFlightsByPassengerID( 6 ) ),
                                                       "SEARCH: Flights of passenger #" + 6,
                                                       LocalDateTime.now(),
                                                       airportManager.getLocalAirport() ) );


        // search passengers using various criteria
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



        // finally, stop the application
        System.exit( 0 );
    }
}
