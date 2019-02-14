package airportmanager.model;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class AbstractBaseEntity
{
    // fields

    @Id
    @GeneratedValue
    private Long id;


    // constructors

    public AbstractBaseEntity()
    {
    }

    public AbstractBaseEntity( Long id )
    {
        this.id = id;
    }


    // getters & setters

    public Long getId()
    {
        return id;
    }
}
