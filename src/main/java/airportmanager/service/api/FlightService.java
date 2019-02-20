package airportmanager.service.api;


import airportmanager.Airport;
import airportmanager.model.FlightEntity;

import java.time.LocalDateTime;


public interface FlightService
{
    FlightEntity createFight( String        flightName,
                              Airport       destination,
                              LocalDateTime departure,
                              int           duration,
                              int           capacity );
}
