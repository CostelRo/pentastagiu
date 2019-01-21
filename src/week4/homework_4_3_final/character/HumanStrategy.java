package week4.homework_4_3_final.character;


import week4.homework_4_3_final.Main;
import week4.homework_4_3_final.TextGenerator;


public class HumanStrategy extends CharacterStrategy
{
    @Override
    public void receiveAttack( GameCharacter source, int damage, GameCharacter target )
    {
        System.out.println( TextGenerator.monsterAttacksHumanMsg() );

        // the Human has already attacked, so now only receives the response from his target
        if( target instanceof Human )
        {
            Human human = (Human) target;

            if( damage >= 0 )
            {
                human.setHealth( human.getHealth() - damage );

                System.out.println( "     -" + damage + " health"
                                    + " (hero: health " + human.getHealth()
                                    + ", energy " + human.getEnergy() + ")" );

                Main.pauseToEvaluateGameProgress();
                }
            }
        }
    }
