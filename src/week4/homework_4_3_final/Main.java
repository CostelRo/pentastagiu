package week4.homework_4_3_final;


import week4.homework_4_3_final.artifacts.EnergyPlus10Artifact;
import week4.homework_4_3_final.artifacts.HealthPlus10Artifact;
import week4.homework_4_3_final.character.Animal;
import week4.homework_4_3_final.character.Human;
import week4.homework_4_3_final.character.Monster;
import week4.homework_4_3_final.map.GameMap;
import week4.homework_4_3_final.map.MapPosition;

import java.util.Arrays;
import java.util.List;


public class Main
{
    public static void main( String[] args )
    {
        GameMap currentMap = Main.startGame();

        Main.endGame( currentMap );
    }


    private static GameMap startGame()
    {
        // initialize a new, empty, game map
        GameMap map = new GameMap();


        // TEST 1.1: manually populate the game map & make the Human use all artifact irrespective of position

        HealthPlus10Artifact healthArtifact_1 = new HealthPlus10Artifact( new MapPosition(3, 4) );
        EnergyPlus10Artifact energyArtifact_2 = new EnergyPlus10Artifact( new MapPosition(1, 2) );
        Animal animal_1 = new Animal( "Animalus~1", new MapPosition(5, 3) );
        Monster monster_2 = new Monster( "Monstruus~2", new MapPosition(6, 6) );
        Human human_1 = new Human( "Lancelot", new MapPosition(6, 0) );

        List<Object> entities = Arrays.asList( healthArtifact_1,
                                               energyArtifact_2,
                                               animal_1,
                                               monster_2,
                                               human_1 );
        map.populateMap( entities );

        map.displayGameStatus();

        // use all artifact irrespective of position
        System.out.println( human_1 );
        human_1.useArtifact( healthArtifact_1 );
        System.out.println( human_1 );
        human_1.useArtifact( energyArtifact_2 );
        System.out.println( human_1 );


/*
        // TEST 1.2: manually populate the map as above, then move Human & let him interact with entities automatically

        human_1.actAlongThePath( map, new MapPosition( -5, 11 ) );
*/


/*
        // TEST 2: randomly populate the game map & randomly generate Human movements on the map

        map.populateMapRandomly();

        map.displayGameStatus();

        Human human = map.getTheHumanHero();
        if( human != null )
        {
            human.actAlongThePath( map, new MapPosition( GameMap.getSize() / 2,
                                                         GameMap.getSize() / 2 ) );
        }
*/


        // finally, if the Human hero is still alive, then continue towards end game
        return map;
    }


    /**
     * Pauses the application so the user has more time to read the console and follow the game progress.
     */
    public static void pauseToEvaluateGameProgress()
    {
        try
        {
            Thread.sleep(400);
        }
        catch( InterruptedException iex )
        {
            iex.printStackTrace();
        }
    }


    /**
     * Defines the procedure to be run at the end of each game.
     */
    public static void endGame( GameMap map )
    {
        map.displayGameStatus();

        Main.pauseToEvaluateGameProgress();

        System.exit( 0 );
    }
}
