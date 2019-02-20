package airportmanager.service;


import airportmanager.Airport;
import airportmanager.model.FlightEntity;
import airportmanager.repository.api.FlightRepository;
import airportmanager.service.api.FlightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service
@Transactional
public class FlightServiceImpl implements FlightService
{

    // fields

    @Resource
    private FlightRepository flightRepository;


    // other methods

    @Override
    public FlightEntity createFight( String          flightName,
                                     Airport         destination,
                                     LocalDateTime   departure,
                                     int             duration,
                                     int             capacity )
    {
        FlightEntity newFlight = new FlightEntity( flightName, destination, departure, duration, capacity );

        return flightRepository.create( newFlight );
    }
}
