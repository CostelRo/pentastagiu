package airportmanager.model;


import airportmanager.FlightStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table( name = "t_flight_status" )
public class FlightStatusEntity extends AbstractBaseEntity
{
    // fields

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private FlightStatus status;


    // constructors

    public FlightStatusEntity()
    {
        // default entity constructor
    }

    public FlightStatusEntity( FlightStatus status )
    {
        this.status = status;
    }


    // getters & setters

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
}
