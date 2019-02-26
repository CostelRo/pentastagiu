package airportmanager.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table( name = "t_passenger" )
public class PassengerEntity extends AbstractBaseEntity
{
    // fields

    @Column( name = "name" )
    private String name;

    @Column( name = "surname" )
    private String surname;

    @Column( name = "birthday" )
    private LocalDate birthday;

    @ManyToMany( targetEntity = FlightEntity.class, fetch = FetchType.EAGER )
    private Set<FlightEntity> flightsHistory = new HashSet<>();


    // constructors

    public PassengerEntity()
    {
        // default entity constructor
    }

    public PassengerEntity( String name,
                            String surname,
                            LocalDate birthday )
    {
        this.name           = name;
        this.surname        = surname;
        this.birthday       = birthday;
    }


    // getters & setters

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        if( name != null && name.length() > 1 )
        {
            this.name = name;
        }
    }


    public String getSurname()
    {
        return surname;
    }

    public void setSurname( String surname )
    {
        if( surname != null && surname.length() > 1 )
        {
            this.surname = surname;
        }
    }


    public LocalDate getBirthday()
    {
        return birthday;
    }

    public void setBirthday( LocalDate birthday )
    {
        if( birthday != null )
        {
            this.birthday = birthday;
        }
    }


    public Set<FlightEntity> getFlightsHistory()
    {
        return flightsHistory;
    }

    public void setFlightsHistory( Set<FlightEntity> flightsHistory )
    {
        if( flightsHistory != null )
        {
            this.flightsHistory = flightsHistory;
        }
    }
}
