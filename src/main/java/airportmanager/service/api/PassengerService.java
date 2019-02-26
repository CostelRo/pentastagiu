package airportmanager.service.api;


import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;

import java.time.LocalDate;


public interface PassengerService
{
    PassengerEntity createPassenger( String name, String surname, LocalDate birthday );

    void assignFlightToPassenger( FlightEntity flightEntity, Long passengerID );
}
