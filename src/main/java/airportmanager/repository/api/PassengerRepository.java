package airportmanager.repository.api;


import airportmanager.model.PassengerEntity;


public interface PassengerRepository
{
    PassengerEntity create( PassengerEntity newPassenger );

    void save( PassengerEntity passengerEntity );

    PassengerEntity findById( Long id );

    PassengerEntity findByName( String name );
}
