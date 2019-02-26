package airportmanager.repository.api;


import airportmanager.FlightStatus;
import airportmanager.model.FlightEntity;

import java.time.LocalDateTime;
import java.util.List;


public interface FlightRepository
{
    FlightEntity create( FlightEntity newFlight );

    void save( FlightEntity flightEntity );

    FlightEntity findByName( String flightName );

    List findByDateTime( LocalDateTime departureDateTime );

    List findByDateTime( LocalDateTime departureDateTimeStart, LocalDateTime departureDateTimeEnd );

    List findByStatus( FlightStatus status );

    List findAll();

    void deleteByName( String flightName );
}
