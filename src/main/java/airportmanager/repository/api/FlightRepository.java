package airportmanager.repository.api;


import airportmanager.model.FlightEntity;


public interface FlightRepository
{
    FlightEntity create( FlightEntity newFlight );

    void save( FlightEntity flightEntity );

    FlightEntity findByName( String flightName );


}
