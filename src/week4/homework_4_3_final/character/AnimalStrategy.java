package week4.homework_4_3_final.character;


public class AnimalStrategy extends CharacterStrategy
{
    @Override
    public void receiveAttack( GameCharacter source, int damage, GameCharacter target )
    {
        // the Animal receives the attack and does nothing else
        if( (target instanceof Animal) && (damage >= 0) )
        {
            Animal animal = (Animal) target;
            animal.setHealth( animal.getHealth() - damage );
        }
    }
}
