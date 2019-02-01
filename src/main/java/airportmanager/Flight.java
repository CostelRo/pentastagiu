package airportmanager;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class Flight implements Comparable<Flight>
{
    // state

    private String         flightNumber;                // used in this simple app. as the flight's unique identifier
    private String         destinationAirportCode;
    private LocalDateTime  departureDateTime;
    private int            durationInSeconds;
    private int            maxPassengersCapacity;
    private FlightStatus   status;
    private Set<Integer>   passengers;


    // constructors

    public Flight( String         flightNumber,
                   String         destinationAirportCode,
                   LocalDateTime  departureDateTime,
                   int            durationInSeconds,
                   int            maxPassengersCapacity )
    {
        this.flightNumber           = flightNumber;
        this.destinationAirportCode = destinationAirportCode;
        this.departureDateTime      = departureDateTime;
        this.durationInSeconds      = durationInSeconds;
        this.maxPassengersCapacity  = maxPassengersCapacity;
        this.status                 = FlightStatus.SCHEDULED;
        this.passengers             = new HashSet<>();
    }


    // getters & setters

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber( String newFlightNumber )
    {
        if( newFlightNumber != null )
        {
            this.flightNumber = newFlightNumber;
        }
    }


    public String getDestinationAirportCode()
    {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode( String newDestinationAirportCode )
    {
        FlightsManager flightsManager = FlightsManager.getSingleton( FlightValidator.getSingleton() );
        PassengersManager passengersManager = PassengersManager.getSingleton( PassengerValidator.getSingleton() );
        AirportManager airportManager = AirportManager.getSingleton( flightsManager, passengersManager );
        if( newDestinationAirportCode != null
            && airportManager.isAirportRegistered( newDestinationAirportCode ) )
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


    public void removePassengerByIDFromThisFlight( int passengerID )
    {
        if( this.status == FlightStatus.SCHEDULED )
        {
            // remove from current Flight
            this.passengers.remove(passengerID);

            // remove this flight from passenger's flight history
            PassengersManager.getSingleton( PassengerValidator.getSingleton() )
                             .getFlightsNamesByPassengerID().get( passengerID ).remove( this.flightNumber );
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

        return this.departureDateTime.compareTo( other.departureDateTime);
    }


    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof Flight) ) { return false; }
        if( obj == this ) { return true; }

        Flight other = (Flight) obj;

        return this.flightNumber.equals( other.flightNumber );
    }


    @Override
    public int hashCode()
    {
        return this.flightNumber.hashCode();
    }


    @Override
    public String toString()
    {
        FlightsManager flightsManager       = FlightsManager.getSingleton( FlightValidator.getSingleton() );
        PassengersManager passengersManager = PassengersManager.getSingleton( PassengerValidator.getSingleton() );
        Optional<Airport> destination = AirportManager.getSingleton( flightsManager, passengersManager )
                                                      .getDestinationAirports()
                                             .stream()
                                             .filter( airport -> airport.getCode().equals(this.destinationAirportCode) )
                                             .findFirst();

        return this.flightNumber
               + " to " + destination.orElse( null )
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
