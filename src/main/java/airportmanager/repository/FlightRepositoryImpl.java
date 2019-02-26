package airportmanager.repository;


import airportmanager.FlightStatus;
import airportmanager.model.FlightEntity;
import airportmanager.repository.api.FlightRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;


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


    @Override
    public List findByDateTime( LocalDateTime departureDateTime )
    {
        if( departureDateTime != null )
        {
            Query query = this.entityManager.createQuery( "SELECT fl FROM FlightEntity fl"
                                                             + " WHERE fl.departureDateTime = :departureDateTime" );
            query.setParameter( "departureDateTime", departureDateTime );

            return query.getResultList();
        }

        return null;
    }


    public List findByDateTime( LocalDateTime departureDateTimeStart, LocalDateTime departureDateTimeEnd )
    {
        if( departureDateTimeStart != null && departureDateTimeEnd != null )
        {
            Query query = this.entityManager.createQuery( "SELECT fl FROM FlightEntity fl"
                                                          + " WHERE fl.departureDateTime BETWEEN :start AND :end" );
            query.setParameter( "start", departureDateTimeStart );
            query.setParameter( "end", departureDateTimeEnd );

            return query.getResultList();
        }

        return  null;
    }


    @Override
    public List findByStatus( FlightStatus status )
    {
        if( status != null )
        {
            Query query = this.entityManager.createQuery( "SELECT fl FROM FlightEntity fl"
                                                          + " WHERE fl.status = :status" );
            query.setParameter( "status", status );

            return query.getResultList();
        }

        return null;
    }


    @Override
    public List findAll()
    {
        Query query = this.entityManager.createQuery( "FROM FlightEntity" );

        return query.getResultList();
    }


    @Override
    public void deleteByName( String flightName )
    {
        if( flightName != null )
        {
            Query query = this.entityManager.createQuery( "DELETE FROM FlightEntity fl"
                                                          + " WHERE fl.name = :flightName" );
            query.setParameter( "flightName", flightName );

            query.executeUpdate();
        }
    }
}
