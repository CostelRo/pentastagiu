package airportmanager.repository.api;


import airportmanager.model.PassengerEntity;


public interface PassengerRepository
{
    PassengerEntity createPassenger( PassengerEntity newPassenger );
}
