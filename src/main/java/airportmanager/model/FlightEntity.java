package airportmanager.model;


import airportmanager.Airport;
import airportmanager.FlightStatus;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table( name = "t_flight" )
public class FlightEntity extends AbstractBaseEntity
{
    // fields

    @Column( name = "name" )
    private String name;

//    @Column( name = "destination" )
    @Embedded
    private Airport destinationAirport;

    @Column( name = "departure" )
    private LocalDateTime departureDateTime;

    @Column( name = "duration" )
    private int durationInSeconds;

    @Column( name = "capacity" )
    private int maxPassengersCapacity;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private FlightStatus status = FlightStatus.SCHEDULED;

    @ManyToMany( targetEntity = PassengerEntity.class )
    private Set<PassengerEntity> passengers = new HashSet<>();


    // constructors

    public FlightEntity()
    {
        // default entity constructor
    }

    public FlightEntity( String name,
                         Airport destinationAirport,
                         LocalDateTime departureDateTime,
                         int durationInSeconds,
                         int maxPassengersCapacity )
    {
        this.name                   = name;
        this.destinationAirport     = destinationAirport;
        this.departureDateTime      = departureDateTime;
        this.durationInSeconds      = durationInSeconds;
        this.maxPassengersCapacity  = maxPassengersCapacity;
    }


    // getters & setters

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        if( name != null && name.length() >= 1 )
        {
            this.name = name;
        }
    }


    public Airport getDestinationAirport()
    {
        return destinationAirport;
    }

    public void setDestinationAirport( Airport destinationAirport )
    {
        if( destinationAirport != null )
        {
            this.destinationAirport = destinationAirport;
        }
    }


    public LocalDateTime getDepartureDateTime()
    {
        return departureDateTime;
    }

    public void setDepartureDateTime( LocalDateTime departureDateTime )
    {
        if( departureDateTime != null )
        {
            this.departureDateTime = departureDateTime;
        }
    }


    public int getDurationInSeconds()
    {
        return durationInSeconds;
    }

    public void setDurationInSeconds( int durationInSeconds )
    {
        if( durationInSeconds > 0 )
        {
            this.durationInSeconds = durationInSeconds;
        }
    }


    public int getMaxPassengersCapacity()
    {
        return maxPassengersCapacity;
    }

    public void setMaxPassengersCapacity( int maxPassengersCapacity )
    {
        if( maxPassengersCapacity >= 0 )
        {
            this.maxPassengersCapacity = maxPassengersCapacity;
        }
    }


    public FlightStatus getStatus()
    {
        return status;
    }

    public void setStatus( FlightStatus status )
    {
        if( status != null )
        {
            this.status = status;
        }
    }


    public Set<PassengerEntity> getPassengers()
    {
        return passengers;
    }

    public void setPassengers( Set<PassengerEntity> passengers )
    {
        if( passengers != null )
        {
            this.passengers = passengers;
        }
    }
}
