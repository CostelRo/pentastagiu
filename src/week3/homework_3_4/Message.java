package week3.homework_3_4;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Comparable<Message>
{
    // state
    private static  int counter = 1;
    private         int id;
    private         LocalDateTime date;
    private         User author;
    private         String description;


    // getters and setters
    public int getId()
    {
        return this.id;
    }


    public LocalDateTime getDate()
    {
        return this.date;
    }


    public User getAuthor()
    {
        return this.author;
    }


    public String getDescription()
    {
        return this.description;
    }

    public void setDescription( String newDescription )
    {
        if( newDescription != null && newDescription.length() > 0 )
        {
            this.description = newDescription;
        }
    }


    // constructors
    public Message( User author, String description )
    {
        this.id = Message.counter;
        Message.counter++;

        this.date = LocalDateTime.now();   // in a real application, it would use server's time

        this.author = author;
        this.description = description;
    }


    // other methods
    @Override
    public int compareTo( Message other )
    {
        if( other == null )
        {
            return 1;
        }

        if( this == other )
        {
            return 0;
        }

        return this.getId() - other.getId();
    }


    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyy-MM-dd, HH:mm:ss" );

        return "[" + this.id + "]"
               + " From: " + this.author.toString() + " (" + this.date.format( formatter ) + ")\n"
               + this.description;
    }
}
