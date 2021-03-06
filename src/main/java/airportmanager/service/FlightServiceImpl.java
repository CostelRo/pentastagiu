package airportmanager.service;


import airportmanager.FlightStatus;
import airportmanager.model.AirportEntity;
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
                                AirportEntity   destination,
                                LocalDateTime   departure,
                                int             duration,
                                int             capacity )
    {
        if( flightName != null
            && destination != null
            && departure != null
            && duration > 0
            && capacity >= 0 )
        {
            FlightEntity newFlight = new FlightEntity( flightName, destination, departure, duration, capacity );

            return flightRepository.create( newFlight );
        }
        else
        {
            throw new IllegalArgumentException( "Incorrect parameter value(s) in FlightEntity constructor!" );
        }
    }


    @Override
    public void assignPassengerToFlight( PassengerEntity passengerEntity, String flightName )
    {
        if( passengerEntity != null && flightName != null )
        {
            FlightEntity flightEntity = flightRepository.findByName( flightName );

            if( flightEntity.getStatus() == FlightStatus.SCHEDULED
                && flightEntity.getPassengers().size() < flightEntity.getMaxPassengersCapacity() )
            {
                flightEntity.getPassengers().add( passengerEntity );

                flightRepository.save( flightEntity );
            }
        }
        else
        {
            System.out.println( "Not allowed to add passenger to the flight!" );
        }
    }


    @Override
    public void delete( String flightName )
    {
        if( flightName != null )
        {
            flightRepository.deleteByName( flightName );
        }
    }
}
