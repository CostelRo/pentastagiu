package week4.homework_4_3_final.character;


import week4.homework_4_3_final.map.MapPosition;


public class Animal extends GameCharacter implements Attackable
{
    // constructors

    public Animal( String name,
                   MapPosition position )
    {
        super( new AnimalStrategy(), name, 50, 20, 4, position );
    }


    // other methods


    @Override
    public void receiveAttack( GameCharacter source, int damage )
    {
        this.strategy.receiveAttack( source, damage, this );
    }
}
