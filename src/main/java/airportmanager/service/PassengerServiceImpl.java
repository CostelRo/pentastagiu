package airportmanager.service;


import airportmanager.model.FlightEntity;
import airportmanager.model.PassengerEntity;
import airportmanager.repository.api.PassengerRepository;
import airportmanager.service.api.PassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class PassengerServiceImpl implements PassengerService
{
    // fields

    @Resource
    private PassengerRepository passengerRepository;


    // other methods


    @Override
    public PassengerEntity createPassenger( String    name,
                                            String    surname,
                                            LocalDate birthday )
    {
        if( name != null
            && surname != null
            && birthday != null )
        {
            PassengerEntity newPassenger = new PassengerEntity( name, surname, birthday );

            return passengerRepository.create( newPassenger );
        }
        else
        {
            throw new IllegalArgumentException( "Incorrect parameter value(s) in PassengerEntity constructor!" );
        }
    }


    @Override
    public void assignFlightToPassenger( FlightEntity flightEntity, Long passengerID )
    {
        if( flightEntity != null && passengerID != null )
        {
            PassengerEntity passengerEntity = passengerRepository.findById( passengerID );
            passengerEntity.getFlightsHistory().add( flightEntity );

            passengerRepository.save( passengerEntity );
        }
    }


    @Override
    public Map<String, List<String>> getDestinationsForOnePassenger( String name )
    {
        Map<String, List<String>> result = new HashMap<>();

        if( name != null )
        {
            for( Object objPassenger : passengerRepository.findByName( name ) )
            {
                PassengerEntity passengerEntity = (PassengerEntity) objPassenger;

                String fullName = passengerEntity.getName() + " " + passengerEntity.getSurname().toUpperCase();
                result.put( fullName, new ArrayList<>() );

                passengerEntity.getFlightsHistory().forEach( flight -> result.get( fullName )
                                                                             .add( flight.getDestinationAirport()
                                                                                         .getCity() ) );
            }
        }

        return result;
    }


    @Override
    public void removePassengerFromEverything( String passengerName )
    {
        if( passengerName != null )
        {
            for( Object obj : this.passengerRepository.findByName( passengerName ) )
            {

                PassengerEntity passengerEntity = (PassengerEntity) obj;
                passengerEntity.getFlightsHistory().forEach( flightEntity -> flightEntity.getPassengers()
                                                                                         .remove( passengerEntity ) );
            }

            this.passengerRepository.deleteByName( passengerName );
        }
    }
}
