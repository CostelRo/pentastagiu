package airportmanager.service.api;


import airportmanager.model.FlightEntity;

import java.time.LocalDateTime;


public interface FlightService
{
    FlightEntity create( String         flightName,
                         Long           destinationID,
                         LocalDateTime  departure,
                         int            duration,
                         int            capacity );
}
