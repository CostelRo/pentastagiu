package airportmanager.repository;


import airportmanager.model.AbstractBaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public abstract class AbstractEntityRepository
{
    /*
     * fields
     */

    @PersistenceContext
    protected EntityManager entityManager;


    /*
     * other methods
     */

//    public abstract AbstractBaseEntity create( AbstractBaseEntity newEntity );
    public <T extends AbstractBaseEntity> T create( T newEntity )
    {
        if( newEntity != null )
        {
            entityManager.persist( newEntity );

            return newEntity;
        }

        return null;
    }

//    public abstract <T extends AbstractBaseEntity> T findById( Long id );

//    public abstract <T extends AbstractBaseEntity> List<T> findAll();

//    public abstract void deleteById( Long id );
}
