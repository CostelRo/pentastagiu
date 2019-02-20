package airportmanager.repository;


import airportmanager.model.FlightEntity;
import airportmanager.repository.api.FlightRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class FlightRepositoryImpl implements FlightRepository
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
    public FlightEntity create( FlightEntity newFlight )
    {
        entityManager.persist( newFlight );

        return newFlight;
    }
}
