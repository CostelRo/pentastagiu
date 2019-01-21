package week4.homework_4_3_final.character;


public interface Attackable
{
    /*
     * Describes the reaction to attack specific to each subclass of Character that can be attacked.
     * In the current implementation:
     * - Human, Monster and Animal classes can be attacked;
     * - the Human class initiates each attack against Animal and Monster classes;
     * - the Monster class can attack back the Human that attacked it first.
     * In later implementations, many other classes might be able to attack each other
     * and the result might depend on the two classes involved.
     */
    void receiveAttack( GameCharacter source, int damage );
}
