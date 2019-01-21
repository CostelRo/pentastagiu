package week4.homework_4_3_final.character;


public abstract class CharacterStrategy
{

    /*
     * Defines the specific way each type of Character receives and reacts to an attack.
     */
    public abstract void receiveAttack( GameCharacter source, int damage, GameCharacter target );
}
