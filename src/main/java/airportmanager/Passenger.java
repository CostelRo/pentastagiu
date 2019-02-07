package airportmanager;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public class Passenger implements Comparable<Passenger>
{
    // state

    private static  int         count = 1;
    private         int         id;
    private         String      name;
    private         String      surname;
    private         LocalDate   birthday;
    private         Set<String> flightsHistory;


    // constructors

    public Passenger( String name,
                      String surname,
                      LocalDate birthday )
    {
        this.id             = Passenger.count;
        Passenger.count++;

        this.name           = name;
        this.surname        = surname;
        this.birthday       = birthday;
        this.flightsHistory = new HashSet<>();
    }


    // getters & setters

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String newName )
    {
        if( newName != null && newName.length() > 0 ) { this.name = newName; }
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname( String newSurname )
    {
        if( surname != null && surname.length() > 0 )
        {
            this.surname = newSurname;
        }
    }

    public LocalDate getBirthday()
    {
        return birthday;
    }

    public void setBirthday( LocalDate correctedBirthday )
    {
        if( correctedBirthday != null
            && correctedBirthday.isBefore( LocalDate.now() ) )
        {
            this.birthday = correctedBirthday;
        }
    }

    public Set<String> getFlightsHistory()
    {
        return this.flightsHistory;
    }


    // other methods

    @Override
    public int compareTo( Passenger other )
    {
        if( other == null ) { return 1; }
        if( other == this ) { return 0; }

        if( this.surname.compareTo( other.surname ) == 0 )
        {
            if( this.name.compareTo( other.name ) == 0 )
            {
                return this.birthday.compareTo( other.birthday );
            }
            else
            {
                return this.name.compareTo( other.name );
            }
        }
        else
        {
            return this.surname.compareTo( other.surname );
        }
    }


    @Override
    public boolean equals( Object obj )
    {
        if( obj == null || !(obj instanceof Passenger) ) { return false; }
        if( obj == this ) { return true; }

        Passenger other = (Passenger) obj;

        return this.surname.equals( other.surname )
               && this.name.equals( other.name )
               && this.birthday.isEqual( other.birthday ) ;
    }


    @Override
    public int hashCode()
    {
        return this.surname.hashCode()
               + 17 * this.name.hashCode()
               + 19 * this.birthday.hashCode();
    }


    @Override
    public String toString()
    {
        return "[#" + this.id + "]"
                + " " + this.surname.toUpperCase()
                + ", " + this.name
               + " (birthday: "
               + this.birthday.format( DateTimeFormatter.ofPattern( ReportCreator.getDateFormatter() ) ) + ")";
    }
}
