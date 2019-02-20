package airportmanager.service;


import airportmanager.model.AirportEntity;
import airportmanager.repository.api.AirportRepository;
import airportmanager.service.api.AirportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AirportServiceImpl implements AirportService
{
    // fields

    @Resource
    private AirportRepository airportRepository;


    // other methods

    @Override
    public AirportEntity createAirport( String code,
                                        String city,
                                        String country )
    {
        AirportEntity newAirport = new AirportEntity( code, city, country );

        return airportRepository.createAirport( newAirport );
    }
}
