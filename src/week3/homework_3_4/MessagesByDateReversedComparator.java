package week3.homework_3_4;


import java.util.Comparator;


public class MessagesByDateReversedComparator implements Comparator<Message>
{
    public int compare( Message m1, Message m2 )
    {
        return m2.getDate().compareTo( m1.getDate() );
    }
}
