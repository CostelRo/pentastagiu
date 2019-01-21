package week4.homework_4_3_final.character;


import week4.homework_4_3_final.map.GameMap;
import week4.homework_4_3_final.map.MapPosition;


public abstract class GameCharacter
{
    // state

    protected final CharacterStrategy strategy;
    protected final String            name;
    protected       int               health;
    protected       int               energy;
    protected       int               range;              // max. distance to an entity at which the Character reacts
    protected       MapPosition       position;
    protected       boolean           isAlreadyDiscovered = false;
    protected       boolean           isAlreadyDiscoveredDead = false;



    // constructors

    public GameCharacter( CharacterStrategy strategy,
                          String name,
                          int health,
                          int energy,
                          int range,
                          MapPosition position )
    {
        this.strategy = strategy;
        this.name = name;
        this.health = health;
        this.energy = energy;
        this.range = range;
        this.position = position;
    }


    // getters & setters

    public CharacterStrategy getStrategy()
    {
        return this.strategy;
    }


    public String getName()
    {
        return this.name;
    }


    public int getHealth()
    {
        return this.health;
    }

    public void setHealth( int newHealthLevel )
    {
        this.health = (newHealthLevel >= 0)
                      ? newHealthLevel
                      : 0;
    }


    public int getEnergy()
    {
        return this.energy;
    }

    public void setEnergy( int newEnergyLevel )
    {
        this.energy = (newEnergyLevel >= 0)
                      ? newEnergyLevel
                      : 0;
    }


    public int getRange()
    {
        return this.range;
    }


    public MapPosition getPosition()
    {
        return this.position;
    }

    public void setPosition( MapPosition newPosition )
    {
        if( newPosition != null )
        {
            this.position = newPosition;
        }
    }


    public boolean getIsAlreadyDiscovered()
    {
        return this.isAlreadyDiscovered;
    }

    public void setIsAlreadyDiscovered( boolean newStatus )
    {
        this.isAlreadyDiscovered = newStatus;
    }


    public boolean getIsAlreadyDiscoveredDead()
    {
        return this.isAlreadyDiscoveredDead;
    }

    public void setIsAlreadyDiscoveredDead( boolean newStatus )
    {
        this.isAlreadyDiscoveredDead = newStatus;
    }


    // other methods

    /**
     * Determines the area of the game map around the current game character
     * which can contain other game entities (e.g. Artifacts or GameCharacters) to which
     * the character will react (e.g. by using the Artifacts and reacting to characters).
     * @return an array with { lowerXLimit, upperXLimit, lowerYLimit, upperYLimit } (inclusive coordinates)
     */
    public int[] getRangeLimits()
    {
        int lowerXLimit = (this.position.getXCoordinate() - this.range) < 0
                        ? 0
                        : this.position.getXCoordinate() - this.range;

        int upperXLimit = (this.position.getXCoordinate() + this.range) >= GameMap.getSize()
                         ? GameMap.getSize() - 1
                         : this.position.getXCoordinate() + this.range;

        int lowerYLimit = (this.position.getYCoordinate() - this.range) < 0
                       ? 0
                       : this.position.getYCoordinate() - this.range;

        int upperYLimit = (this.position.getYCoordinate() + this.range) >= GameMap.getSize()
                          ? GameMap.getSize() - 1
                          : this.position.getYCoordinate() + this.range;

        return new int[]{ lowerXLimit, upperXLimit, lowerYLimit, upperYLimit };
    }


    @Override
    public String toString()
    {
        return this.getClass().getSimpleName().toUpperCase()
               + " " + this.name
               + " (health: " + this.health + ", energy: " + this.energy + ")";
    }
}
