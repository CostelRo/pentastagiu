package airportmanager.service.api;


import airportmanager.model.AirportEntity;


public interface AirportService
{
    AirportEntity createAirport( String code,
                                 String city,
                                 String country );
}
