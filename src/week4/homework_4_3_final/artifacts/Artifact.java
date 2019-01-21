package week4.homework_4_3_final.artifacts;


import week4.homework_4_3_final.map.MapPosition;


public class Artifact
{
    // state

    protected final String      type;         // name of non-static non-final Character field affected by this artifact
    protected final String      name;
    protected       int         strength;     // effect strength
    protected       MapPosition position;
    protected       boolean     isAlreadyUsed;


    // constructors

    public Artifact( String type, String name, int strength, MapPosition position )
    {
        this.type = type;
        this.name = name;
        this.strength = strength;
        this.position = position;
        this.isAlreadyUsed = false;
    }


    // getters & setters

    public String getType()
    {
        return this.type;
    }


    public String getName()
    {
        return this.name;
    }


    public int getStrength()
    {
        return this.strength;
    }


    public MapPosition getPosition()
    {
        return this.position;
    }


    public boolean getIsAlreadyUsed() { return this.isAlreadyUsed; }

    public void setIsAlreadyUsed( boolean newStatus )
    {
        this.isAlreadyUsed = newStatus;
    }


    // other methods

    @Override
    public String toString()
    {
        return this.name + ": +" + this.strength + " " + this.type + ")";
    }
}
