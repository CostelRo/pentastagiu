package airportmanager.service;


import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;
import airportmanager.repository.api.PassengerRepository;
import airportmanager.service.api.PassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;


@Service
@Transactional
public class PassengerServiceImpl implements PassengerService
{
    // fields

    @Resource
    private PassengerRepository passengerRepository;


    // other methods


    @Override
    public PassengerEntity createPassenger( String name,
                                            String surname,
                                            LocalDate birthday )
    {
        PassengerEntity newPassenger = new PassengerEntity( name, surname, birthday );

        return passengerRepository.create( newPassenger );
    }


    public void assignFlightToHistory( FlightEntity flightEntity, Long passengerID )
    {
        if( flightEntity != null && passengerID != null )
        {
            PassengerEntity passengerEntity = passengerRepository.findById( passengerID );
            passengerEntity.getFlightsHistory().add( flightEntity );

            passengerRepository.save( passengerEntity );
        }
    }
}
