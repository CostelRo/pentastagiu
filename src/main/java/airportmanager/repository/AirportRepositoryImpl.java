package airportmanager.repository;


import airportmanager.model.AirportEntity;
import airportmanager.repository.api.AirportRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class AirportRepositoryImpl implements AirportRepository
{
    /*
     * fields
     */

    @PersistenceContext
    private EntityManager entityManager;


    /*
     * other methods
     */

    @Override
    public AirportEntity createAirport( AirportEntity newAirport )
    {
        entityManager.persist( newAirport );

        return newAirport;
    }
}
