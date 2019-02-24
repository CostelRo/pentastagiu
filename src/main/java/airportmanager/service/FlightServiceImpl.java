package airportmanager.service;


import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;
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
    public FlightEntity create( String          flightName,
                                Long            destinationId,
                                LocalDateTime   departure,
                                int             duration,
                                int             capacity )
    {
        FlightEntity newFlight = new FlightEntity( flightName, destinationId, departure, duration, capacity );

        return flightRepository.create( newFlight );
    }


    public void assignPassengerToFlight( PassengerEntity passengerEntity, String flightName )
    {
        if( passengerEntity != null && flightName != null )
        {
            FlightEntity flightEntity = flightRepository.findByName( flightName );
            flightEntity.getPassengers().add( passengerEntity );

            flightRepository.save( flightEntity );
        }
    }
}
