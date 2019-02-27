package airportmanager.service.api;


import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface PassengerService
{
    PassengerEntity createPassenger( String name, String surname, LocalDate birthday );

    void assignFlightToPassenger( FlightEntity flightEntity, Long passengerID );

    Map<String, List<String>> getDestinationsForOnePassenger( String name );

    void removePassengerFromEverything( String name );
}
