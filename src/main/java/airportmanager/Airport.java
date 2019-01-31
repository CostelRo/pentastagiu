package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
//@Qualifier( "localAirport" )
public class Airport implements Comparable<Airport>
{
    // state

    @Qualifier( "airportCode" )
    private String airportCode;

    @Qualifier( "airportCity" )
    private String airportCity;

    @Qualifier( "airportCountry" )
    private String airportCountry;


    // constructors

    @Autowired
    public Airport( String airportCode, String airportCity, String airportCountry )
    {
        this.airportCode    = airportCode.toUpperCase();
        this.airportCity    = airportCity;
        this.airportCountry = airportCountry;
    }


    // getters & setters

    public String getAirportCode()
    {
        return airportCode;
    }

    public String getAirportCity()
    {
        return airportCity;
    }

    public String getAirportCountry()
    {
        return airportCountry;
    }


    // other methods

    @Override
    public int compareTo( Airport other )
    {
        if( other == null ) { return 1; }
        if( other == this ) { return 0; }

        return (this.airportCity.compareTo( other.airportCity) != 0)
               ? this.airportCity.compareTo( other.airportCity)
               : this.airportCode.compareTo( other.airportCode);
    }


    @Override
    public boolean equals( Object obj )
    {
        if( (obj == null) || !(obj instanceof Airport) )
        {
            return false;
        }

        Airport other = (Airport) obj;

        return this.airportCode.equals( other.airportCode );
    }


    @Override
    public int hashCode()
    {
        return this.airportCode.hashCode();
    }


    @Override
    public String toString()
    {
        return this.airportCity + " " + this.airportCode + " (" + this.airportCountry + ")";
    }
}