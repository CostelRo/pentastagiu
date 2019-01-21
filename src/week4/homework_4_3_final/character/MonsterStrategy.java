package week4.homework_4_3_final.character;


public class MonsterStrategy extends CharacterStrategy
{
    @Override
    public void receiveAttack( GameCharacter source, int damage, GameCharacter target )
    {
        // the Monster receives the attack and only then attacks the human hero (once) in return
        if( (target instanceof Monster) && (damage >= 0) )
        {
            Monster monster = (Monster) target;
            monster.setHealth( monster.getHealth() - damage );

            if( (monster.health > 0) && (monster.energy >= Monster.getAttackEnergyCost()) )
            {
                monster.setEnergy( monster.energy - Monster.getAttackEnergyCost() );

                if( source instanceof Human )
                {
                    ( (Human) source ).receiveAttack( monster, Monster.getAttackHealthDamage() );
                }
            }
        }
    }
}
