package airportmanager.repository.api;


import airportmanager.model.PassengerEntity;

import java.time.LocalDate;
import java.util.List;


public interface PassengerRepository
{
    PassengerEntity create( PassengerEntity newPassenger );

    void save( PassengerEntity passengerEntity );

    PassengerEntity findById( Long id );

    List findByName( String name );

    List findByBirthday( LocalDate birthday );

    List findAll();

    void deleteByName( String name );
}
