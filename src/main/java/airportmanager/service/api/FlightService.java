package airportmanager.service.api;


import airportmanager.model.AirportEntity;
import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;

import java.time.LocalDateTime;


public interface FlightService
{
    FlightEntity create( String         flightName,
                         AirportEntity  destination,
                         LocalDateTime  departure,
                         int            duration,
                         int            capacity );

    void assignPassengerToFlight( PassengerEntity   passengerEntity,
                                  String            flightName );

    void delete( String flightName );
}
