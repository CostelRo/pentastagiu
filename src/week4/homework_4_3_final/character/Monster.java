package week4.homework_4_3_final.character;


import week4.homework_4_3_final.map.MapPosition;


public class Monster extends GameCharacter implements Attackable
{
    // state

    private static int attackEnergyCost = 3;        // sustained by the Monster
    private static int attackHealthDamage = 15;     // sustained by the target of the attack


    // constructors

    public Monster( String name,
                    MapPosition position )
    {
        super( new MonsterStrategy(), name, 50, 20, 4, position );
    }


    // getters & setters

    public static int getAttackEnergyCost()
    {
        return Monster.attackEnergyCost;
    }

    public static int getAttackHealthDamage()
    {
        return Monster.attackHealthDamage;
    }


    // other methods

    @Override
    public void receiveAttack( GameCharacter source, int damage )
    {
        this.strategy.receiveAttack( source, damage, this );
    }
}
