package airportmanager;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public class Flight implements Comparable<Flight>
{
    // state

    private String         flightName;                // used in this simple app. as the flight's unique identifier
    private String         destinationAirportCode;
    private LocalDateTime  departureDateTime;
    private int            durationInSeconds;
    private int            maxPassengersCapacity;
    private FlightStatus   status;
    private Set<Integer>   passengers;


    // constructors

    public Flight( String         flightName,
                   String         destinationAirportCode,
                   LocalDateTime  departureDateTime,
                   int            durationInSeconds,
                   int            maxPassengersCapacity )
    {
        this.flightName             = flightName;
        this.destinationAirportCode = destinationAirportCode;
        this.departureDateTime      = departureDateTime;
        this.durationInSeconds      = durationInSeconds;
        this.maxPassengersCapacity  = maxPassengersCapacity;
        this.status                 = FlightStatus.SCHEDULED;
        this.passengers             = new HashSet<>();
    }


    // getters & setters

    public String getFlightName()
    {
        return flightName;
    }

    public void setFlightName( String newFlightName )
    {
        if( newFlightName != null && FlightValidator.getSingleton().isValidFlightNameFormat( flightName ) )
        {
            this.flightName = newFlightName;
        }
    }


    public String getDestinationAirportCode()
    {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode( String newDestinationAirportCode )
    {
        if( newDestinationAirportCode != null
            && AirportManager.getSingleton().isAirportRegistered( newDestinationAirportCode ) )
        {
            this.destinationAirportCode = newDestinationAirportCode;
        }
    }


    public LocalDateTime getDepartureDateTime()
    {
        return departureDateTime;
    }

    public void setDepartureDateTime( LocalDateTime newDepartureDateTime )
    {
        if( newDepartureDateTime != null )
        {
            this.departureDateTime = newDepartureDateTime;
        }
    }


    public int getDurationInSeconds()
    {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int newDurationInSeconds )
    {
        if( newDurationInSeconds > 1 )
        {
            this.durationInSeconds = newDurationInSeconds;
        }
    }


    public int getMaxPassengersCapacity()
    {
        return maxPassengersCapacity;
    }

    public void setMaxPassengersCapacity( int newMaxPassengersCapacity )
    {
        if( newMaxPassengersCapacity > 0 )
        {
            this.maxPassengersCapacity = newMaxPassengersCapacity;
        }
    }


    public FlightStatus getStatus()
    {
        return status;
    }

    public void setStatus( FlightStatus newStatus )
    {
        if( newStatus != null )
        {
            this.status = newStatus;
        }
    }


    public Set<Integer> getPassengers()
    {
        return passengers;
    }


    // other methods

    public void addPassenger( int newPassengerID )
    {
        if( this.getAvailableSeats() > 0 )
        {
            this.passengers.add( newPassengerID );
        }
    }


    public void removePassenger( int passengerID )
    {
        if( passengerID >= 0
            && this.status == FlightStatus.SCHEDULED )
        {
            this.passengers.remove( passengerID );
        }
    }


    public int getAvailableSeats()
    {
        return this.maxPassengersCapacity - this.passengers.size();
    }


    @Override
    public int compareTo( Flight other )
    {
        if( other == null ) { return 1; }
        if( other == this ) { return 0; }

        return this.departureDateTime.compareTo( other.departureDateTime );
    }


    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof Flight) ) { return false; }
        if( obj == this ) { return true; }

        Flight other = (Flight) obj;

        return this.flightName.equals( other.flightName );
    }


    @Override
    public int hashCode()
    {
        return this.flightName.hashCode();
    }


    @Override
    public String toString()
    {
        Airport destinationAirport = AirportManager.getSingleton().getAirportByCode( this.destinationAirportCode );

        return this.flightName
               + " to " + destinationAirport
               + "\n= " + this.status.toString()
               + "\n(departure: "
               + this.departureDateTime.format( DateTimeFormatter
                                                            .ofPattern( ReportCreator.getDateTimeFormatter() ) ) + ")"
               + "\n(duration: " + this.convertDuration( this.durationInSeconds)[0] + "h, "
               + this.convertDuration( this.durationInSeconds)[1] + "m)"
               + "\n(" + this.passengers.size() + " passengers; " + this.maxPassengersCapacity + " seats)\n";
    }


    /**
     * Converts a duration in seconds into hours, minutes, seconds.
     * @param durationInSeconds the period expressed in seconds
     * @return a long[] containing: { hours, minutes, seconds }
     */
    private long[] convertDuration( long durationInSeconds )
    {
        long hours   = durationInSeconds / (60 * 60);
        long minutes = ( durationInSeconds % (60 * 60) ) / 60;
        long seconds = durationInSeconds % 60;

        return new long[]{ hours, minutes, seconds };
    }
}
