package airportmanager.repository;


import airportmanager.model.PassengerEntity;
import airportmanager.repository.api.PassengerRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class PassengerRepositoryImpl implements PassengerRepository
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
    public PassengerEntity createPassenger( PassengerEntity newPassenger )
    {
        entityManager.persist( newPassenger );

        return newPassenger;
    }
}
