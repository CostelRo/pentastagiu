package week3.homework_3_4;


import java.util.Objects;


public class User
{
    // state
    private         String name;
    private         String email;


    // getters and setters
    public String getName()
    {
        return this.name;
    }

    public void setName( String newName )
    {
        if( User.isValidNameFormat( newName ) )
        {
            this.name = newName;
        }
    }


    public String getEmail()
    {
        return this.email;
    }

    public  void setEmail( String newEmail )
    {
        if( User.isValidEmailFormat( newEmail ) )
        {
            this.email = newEmail;
        }
    }


    // constructors
    public User( String name, String email )
    {
        this.name = name;
        this.email = email;
    }


    // other methods
    /*
     * This method does a very simplistic name format validation.
     * Minimum string length allowed: 3.
     * Any other Unicode characters can be used and not only letters.
     */
    public static boolean isValidNameFormat( String source )
    {
        int minNameLength = 3;

        return (source != null) && (source.length() >= minNameLength);
    }


    /*
     * This method does a very simplistic email format validation.
     * Minimum string allowed: "a@b.c"
     * Any other Unicode characters can be used instead of the letters.
     */
    public static boolean isValidEmailFormat( String source )
    {
        int minEmailLength = 5;

        return (source != null)
                && (source.length() >= minEmailLength)
                && (source.indexOf('@') == source.lastIndexOf('@'));
    }


    @Override
    public boolean equals( Object otherObject )
    {
        if( this == otherObject )
        {
            return true;
        }

        if( otherObject == null )
        {
            return false;
        }

        if( this.getClass() != otherObject.getClass() )
        {
            return false;
        }

        User other = (User) otherObject;
        return ( Objects.equals( this.name, other.name ) );
    }


    @Override
    public int hashCode()
    {
        return Objects.hash( this.name );
    }


    @Override
    public String toString()
    {
        return this.name.toUpperCase() + " (" + this.email + ")";
    }
}
