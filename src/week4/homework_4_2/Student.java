package week4.homework_4_2;


public class Student
{
    // state
    private String name;
    private int year;
    private double averageScore;


    // constructors

    public Student( String name, int year, double averageScore )
    {
        this.name = name;
        this.year = year;
        this.averageScore = averageScore;
    }


    // getters & setters

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        if( name != null && name.length() > 0 )
        {
            this.name = name;
        }
    }

    public int getYear()
    {
        return this.year;
    }

    public void setYear( int year )
    {
        if( year >= 1 && year <= 5 )
        {
            this.year = year;
        }
    }

    public double getAverageScore()
    {
        return this.averageScore;
    }

    public void setAverageScore( double averageScore )
    {
        if( averageScore >= 1 && averageScore <= 10 )
        {
            this.averageScore = averageScore;
        }
    }


    // other methods

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName().toUpperCase() + " "
                + this.name
                + " (year " + this.year + ", average score " + this.averageScore + ")";
    }
}
