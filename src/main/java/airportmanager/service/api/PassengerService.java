package airportmanager.service.api;


import airportmanager.model.PassengerEntity;
import java.time.LocalDate;


public interface PassengerService
{
    PassengerEntity createPassenger( String name,
                                     String surname,
                                     LocalDate birthday );
}
