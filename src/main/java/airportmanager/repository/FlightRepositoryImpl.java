package airportmanager.repository;


import airportmanager.model.FlightEntity;
import airportmanager.repository.api.FlightRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


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

//    @Override
//    public AbstractBaseEntity create( AbstractBaseEntity newEntity )
//    {
//        if( newEntity instanceof FlightEntity )
//        {
//            FlightEntity newFlight = (FlightEntity) newEntity;
//
//            entityManager.persist( newFlight );
//
//            return newFlight;
//        }
//
//        return null;
//    }


    @Override
    public void save( FlightEntity flightEntity )
    {
        if( flightEntity != null )
        {
            this.entityManager.merge( flightEntity );
        }
    }


    @Override
    public FlightEntity findByName( String flightName )
    {
        if( flightName != null )
        {
            Query query = this.entityManager.createQuery( "SELECT fl FROM FlightEntity fl"
                                                             + " WHERE fl.name = :flightName" );
            query.setParameter( "flightName", flightName );

            return (FlightEntity) query.getSingleResult();
        }

        return null;
    }
}
