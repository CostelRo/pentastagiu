package airportmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Qualifier( "localAirport" )
public class Airport implements Comparable<Airport>
{
    // state

    private String code;

    private String city;

    private String country;


    // constructors

    @Autowired
    public Airport( @Value( "IAS" ) String code,
                    @Value( "Iasi" ) String city,
                    @Value( "Romania" ) String country )
    {
        this.code    = code.toUpperCase();
        this.city    = city;
        this.country = country;
    }


    // getters & setters

    public String getCode()
    {
        return code;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String newCity )
    {
        if( newCity != null && newCity.length() > 0 )
        {
            this.city = newCity;
        }
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry( String newCountry )
    {
        if( newCountry != null && newCountry.length() > 0 )
        {
            this.country = newCountry;
        }
    }


    // other methods

    @Override
    public int compareTo( Airport other )
    {
        if( other == null ) { return 1; }
        if( other == this ) { return 0; }

        return (this.city.compareTo( other.city) != 0)
               ? this.city.compareTo( other.city)
               : this.code.compareTo( other.code);
    }


    @Override
    public boolean equals( Object obj )
    {
        if( (obj == null) || !(obj instanceof Airport) )
        {
            return false;
        }

        Airport other = (Airport) obj;

        return this.code.equals( other.code);
    }


    @Override
    public int hashCode()
    {
        return this.code.hashCode();
    }


    @Override
    public String toString()
    {
        return this.city + " " + this.code + " (" + this.country + ")";
    }
}