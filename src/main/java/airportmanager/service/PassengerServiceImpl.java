package airportmanager.service;


import airportmanager.model.PassengerEntity;
import airportmanager.repository.api.PassengerRepository;
import airportmanager.service.api.PassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class PassengerServiceImpl implements PassengerService
{
    // fields

    private PassengerRepository passengerRepository;


    // other methods


    @Override
    public PassengerEntity createPassenger( String name,
                                            String surname,
                                            LocalDate birthday )
    {
        PassengerEntity newPassenger = new PassengerEntity( name, surname, birthday );

        return passengerRepository.createPassenger( newPassenger );
    }
}
