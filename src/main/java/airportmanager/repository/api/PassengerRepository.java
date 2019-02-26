package airportmanager.repository.api;


import airportmanager.model.PassengerEntity;

import java.util.List;


public interface PassengerRepository
{
    PassengerEntity create( PassengerEntity newPassenger );

    void save( PassengerEntity passengerEntity );

    PassengerEntity findById( Long id );

    PassengerEntity findByName( String name );

    List findAll();
}
