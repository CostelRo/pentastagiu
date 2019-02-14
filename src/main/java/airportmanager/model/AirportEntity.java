package airportmanager.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table( name = "t_airport" )
public class AirportEntity extends AbstractBaseEntity
{
    // fields

    @Column( name = "code" )
    private String code;

    @Column( name = "city" )
    private String city;

    @Column( name = "country" )
    private String country;


    // constructors

    public AirportEntity()
    {
        // default entity constructor
    }

    public AirportEntity( String code,
                          String city,
                          String country )
    {
        this.code = code.toUpperCase();
        this.city = city;
        this.country = country;
    }


    // getters & setters

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        if( code != null )
        {
            this.code = code;
        }
    }


    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        if( city != null )
        {
            this.city = city;
        }
    }


    public String getCountry()
    {
        return country;
    }

    public void setCountry( String country )
    {
        if( country != null)
        {
            this.country = country;
        }
    }
}
