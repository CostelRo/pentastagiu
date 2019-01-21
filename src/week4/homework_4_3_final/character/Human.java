package week4.homework_4_3_final.character;


import week4.homework_4_3_final.Main;
import week4.homework_4_3_final.TextGenerator;
import week4.homework_4_3_final.artifacts.Artifact;
import week4.homework_4_3_final.map.GameMap;
import week4.homework_4_3_final.map.MapPosition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Human extends GameCharacter implements Attackable
{
    // state

    private static int attackEnergyCost = 5;        // sustained by the Human
    private static int attackHealthDamage = 10;     // sustained by the target of the attack


    // constructors

    public Human( String name,
                  MapPosition position )
    {
        super( new HumanStrategy(), name, 50, 20, 4, position );
    }


    // getters & setters

    public static int getAttackEnergyCost()
    {
        return Human.attackEnergyCost;
    }

    public static int getAttackHealthDamage()
    {
        return Human.attackHealthDamage;
    }


    // other methods

    /**
     * Defines the current Human character behaviour as it tries to reach a destination on the game map.
     * @param map the active game map
     * @param intendedDestination the position on the game map expected to be reached by the current game character
     */
    public void actAlongThePath( GameMap map, MapPosition intendedDestination )
    {
        // obtain the position of each step on the map towards the proposed destination
        List<MapPosition> path = GameMap.buildPathToDestination( this.getPosition(), intendedDestination );
        System.out.println( TextGenerator.movingTowardsDestinationMsg( path.get( path.size() - 1 ) ) );

        // act at each step
        for( MapPosition step : path )
        {
            if( this.health <= 0 )
            {
                System.out.println( TextGenerator.deadHumanMsg() );

                Main.pauseToEvaluateGameProgress();

                Main.endGame( map );
            }

            this.position = step;
            System.out.println( TextGenerator.newStepTakenMsg( this.position ) );

            this.checkEnvironment( map );
        }
    }


    /**
     * Defines the way the current Human character checks and then reacts to the surrounding game map.
     * @param map the active game map
     */
    private void checkEnvironment( GameMap map )
    {
        if( map != null )
        {
            // only react to artifacts within the current character's range
            for( Artifact art : map.getArtifacts() )
            {
                if( this.containsInRange( art.getPosition() ) && !art.getIsAlreadyUsed() )
                {
                    this.reactToNewArtifact( art );

                    Main.pauseToEvaluateGameProgress();
                }
            }

            // only react to game characters within the current character's range
            for( GameCharacter ch : map.getGameCharacters() )
            {
                if( this.containsInRange( ch.getPosition() ) )
                {
                    if( ch.health > 0 )
                    {
                        if( (ch instanceof Animal) && !ch.isAlreadyDiscovered )   // only react to an Animal once
                        {
                            this.reactToNewLiveAnimal( (Animal) ch );

                            Main.pauseToEvaluateGameProgress();
                        }
                        else if( ch instanceof Monster )    // react to a Monster at each step taken on the map
                        {
                            this.reactToNewLiveMonster( (Monster) ch );

                            Main.pauseToEvaluateGameProgress();
                        }
                    }
                    else if( !ch.isAlreadyDiscoveredDead )
                    {
                        TextGenerator.foundNewDeadGameCharacterMsg( ch.getClass().getSimpleName() );
                        ch.setIsAlreadyDiscoveredDead( true );

                        Main.pauseToEvaluateGameProgress();
                    }
                }
            }
        }
    }


    private void reactToNewArtifact( Artifact artifact )
    {
        if( artifact != null )
        {
            System.out.println( TextGenerator.foundNewUnusedArtifactMsg( artifact.getName() ) );

            this.useArtifact( artifact );
            artifact.setIsAlreadyUsed( true );

            System.out.println( "     +" + artifact.getStrength()
                                + " " + artifact.getType()
                                + " (" + this.name
                                + ": health " + this.getHealth()
                                + ", energy " + this.getEnergy() + ")" );
        }
    }


    private void reactToNewLiveAnimal( Animal animal )
    {
        System.out.println( TextGenerator.foundNewLiveAnimalMsg( animal.name ) );
        animal.setIsAlreadyDiscovered( true );

        // an Animal is attacked once
        if( this.energy >= Human.attackEnergyCost )
        {
            this.setEnergy( this.energy - Human.attackEnergyCost );
            animal.receiveAttack( this, Human.attackHealthDamage );
        }
        else
        {
            System.out.println( TextGenerator.lackOfEnergyNoAttackMsg() );
        }
    }


    private void reactToNewLiveMonster( Monster monster )
    {
        System.out.println( TextGenerator.foundNewLiveMonsterMsg( monster.name ) );
        monster.setIsAlreadyDiscovered( true );

        // a Monster is attacked once
        if( this.energy >= Human.attackEnergyCost )
        {
            this.setEnergy( this.energy - Human.attackEnergyCost );
            monster.receiveAttack( this, Human.attackHealthDamage );
        }
        else
        {
            System.out.println( TextGenerator.lackOfEnergyNoAttackMsg() );
        }
    }


    private boolean containsInRange(MapPosition position )
    {
        int[] rangeLimits = this.getRangeLimits();

        return position.getXCoordinate() >= rangeLimits[0] && position.getXCoordinate() <= rangeLimits[1]
               && position.getYCoordinate() >= rangeLimits[2] && position.getYCoordinate() <= rangeLimits[3];
    }


    /**
     * This method modifies the value of a field from the current object's state
     * automatically identified based on the type of Artifact used as argument.
     * Only non-final and non-static fields can be affected by an Artifact.
     * @param artifact the Artifact to be used
     */
    public void useArtifact( Artifact artifact )
    {
        if( artifact != null && !artifact.getIsAlreadyUsed() )
        {
            Field[] fieldsOfClass = this.getClass().getDeclaredFields();
            Field[] fieldsOfSuperclass = this.getClass().getSuperclass().getDeclaredFields();
            Field[] fieldsAll = Arrays.copyOf( fieldsOfClass,
                                     fieldsOfClass.length + fieldsOfSuperclass.length );
            System.arraycopy( fieldsOfSuperclass, 0,
                              fieldsAll,
                              fieldsOfClass.length,
                              fieldsOfSuperclass.length );

            for( Field f : fieldsAll )
            {
                if( Objects.equals( artifact.getType(), f.getName() ) )
                {
                    if( !Modifier.isFinal( f.getModifiers() )
                        && !Modifier.isStatic( f.getModifiers() ) )
                    {
                        try
                        {
                            f.set( this, ( f.getInt( this ) + artifact.getStrength() ) );
                        }
                        catch( IllegalAccessException iaex )
                        {
                            iaex.printStackTrace();
                            return;
                        }

                        artifact.setIsAlreadyUsed( true );
                    }
                }
            }
        }
    }


    public void receiveAttack( GameCharacter source, int damage )
    {
        this.strategy.receiveAttack( source, damage, this );
    }
}
