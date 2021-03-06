package airportmanager.repository;


import airportmanager.model.PassengerEntity;
import airportmanager.repository.api.PassengerRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;


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
    public PassengerEntity create( PassengerEntity newPassenger )
    {
        entityManager.persist( newPassenger );

        return newPassenger;
    }


//    @Override
//    public AbstractBaseEntity create( AbstractBaseEntity newEntity )
//    {
//        if( newEntity instanceof PassengerEntity )
//        {
//            PassengerEntity newPassenger = (PassengerEntity) newEntity;
//
//            entityManager.persist( newPassenger );
//
//            return newPassenger;
//        }
//
//        return null;
//    }


    public void save( PassengerEntity passengerEntity )
    {
        if( passengerEntity != null )
        {
            this.entityManager.merge( passengerEntity );
        }
    }


    @Override
    public PassengerEntity findById( Long id )
    {
        if( id > 0 )
        {
            Query query = this.entityManager.createQuery( "SELECT psgr FROM PassengerEntity psgr"
                                                             + " WHERE psgr.id = :id" );
            query.setParameter( "id", id );

            return (PassengerEntity) query.getSingleResult();
        }

        return null;
    }


    public List findByName( String name )
    {
        if( name != null )
        {
            Query query = this.entityManager.createQuery( "SELECT psgr FROM PassengerEntity psgr"
                                                             + " WHERE psgr.name = :name" );
            query.setParameter( "name", name );

            return query.getResultList();
        }

        return  null;
    }


    @Override
    public List findByBirthday( LocalDate birthday )
    {
        if( birthday != null )
        {
            Query query = this.entityManager.createQuery( "SELECT psgr FROM PassengerEntity psgr"
                                                          + " WHERE psgr.birthday = :birthday" );
            query.setParameter( "birthday", birthday );

            return query.getResultList();
        }

        return null;
    }


    @Override
    public List findAll()
    {
        Query query = this.entityManager.createQuery( "FROM PassengerEntity" );

        return query.getResultList();
    }


    @Override
    public void deleteByName( String name )
    {
        if( name != null )
        {
            Query query = this.entityManager.createQuery( "DELETE FROM PassengerEntity psgr"
                                                          + " WHERE psgr.name = :name" );
            query.setParameter( "name", name );

            query.executeUpdate();
        }
    }
}
