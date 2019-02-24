package airportmanager.repository;


import airportmanager.model.AirportEntity;
import airportmanager.repository.api.AirportRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


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
    public AirportEntity create( AirportEntity newAirport )
    {
        entityManager.persist( newAirport );

        return newAirport;
    }

//    @Override
//    public AbstractBaseEntity create( AbstractBaseEntity newEntity )
//    {
//        if( newEntity instanceof AirportEntity )
//        {
//            AirportEntity newAirport = (AirportEntity) newEntity;
//
//            entityManager.persist( newAirport );
//
//            return newAirport;
//        }
//
//        return null;
//    }


    @Override
    public void save( AirportEntity airportEntity )
    {
        if( airportEntity != null )
        {
            this.entityManager.merge( airportEntity );
        }
    }


    @Override
    public AirportEntity findByCode( String airportCode )
    {
        if( airportCode != null )
        {
            Query query = this.entityManager.createQuery( "SELECT port FROM AirportEntity port"
                                                             + " WHERE port.code = :airportCode" );
            query.setParameter( "airportCode", airportCode );

            return (AirportEntity) query.getSingleResult();
        }

        return null;
    }


    @Override
    public List findAll()
    {
        Query query = this.entityManager.createQuery( "FROM AirportEntity" );

        return query.getResultList();
    }
}
