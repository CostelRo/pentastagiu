package airportmanager.repository.api;


import airportmanager.model.AirportEntity;

import java.util.List;


public interface AirportRepository
{
    AirportEntity create( AirportEntity newAirport );

    void save( AirportEntity airportEntity );

    AirportEntity findById( Long airportId );

    AirportEntity findByCode( String airportCode );

    List findAll();
}
