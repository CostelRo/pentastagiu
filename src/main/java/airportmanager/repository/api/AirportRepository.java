package airportmanager.repository.api;


import airportmanager.model.AirportEntity;


public interface AirportRepository
{
    AirportEntity createAirport( AirportEntity newAirport );
}
