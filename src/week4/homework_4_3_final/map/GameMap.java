package week4.homework_4_3_final.map;


import week4.homework_4_3_final.artifacts.Artifact;
import week4.homework_4_3_final.artifacts.EnergyPlus10Artifact;
import week4.homework_4_3_final.artifacts.HealthPlus10Artifact;
import week4.homework_4_3_final.character.Animal;
import week4.homework_4_3_final.character.GameCharacter;
import week4.homework_4_3_final.character.Human;
import week4.homework_4_3_final.character.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public final class GameMap
{
    // state

    private static final String     emptyMapElement       = "\u23B5";
    private static final String     heroMapElement        = "\u263A";       // "\uD83D\uDEB9";
    private static final String     heroDeadMapElement    = "\u2639";       //"\uD83D\uDC94";
    private static final String     artifactMapElement    = "\u2736";
    private static final String     animalMapElement      = "\u270C";       // "\uD83D\uDC18";
    private static final String     monsterMapElement     = "\u2694";       // "\uD83D\uDC7E";
    private static final String     monsterDeadMapElement = "\u2573";       // "\uD83D\uDE0A";
    private static final String     stepOnMapSymbol       = "\u2193";       // "\uD83D\uDC63";

    private static final int        minSize = 10;
    private static final int        maxSize = 20;
    private static       int        size = 0;                   // only 1 map ca be used at runtime
    private              List<Artifact>      artifacts;
    private              List<GameCharacter> gameCharacters;


    // constructors

//    static
    {
        GameMap.getMapSizeFromUserInput();
    }

    public GameMap()
    {
        this.artifacts = new ArrayList<>();
        this.gameCharacters = new ArrayList<>();
    }


    // getters & setters

    public static String getEmptyMapElement()
    {
        return emptyMapElement;
    }

    public static String getHeroMapElement()
    {
        return heroMapElement;
    }

    public static String getHeroDeadMapElement()
    {
        return heroDeadMapElement;
    }

    public static String getArtifactMapElement()
    {
        return artifactMapElement;
    }

    public static String getAnimalMapElement()
    {
        return animalMapElement;
    }

    public static String getMonsterMapElement()
    {
        return monsterMapElement;
    }

    public static String getMonsterDeadMapElement()
    {
        return monsterDeadMapElement;
    }

    public static String getStepOnMapSymbol() { return stepOnMapSymbol; }


    public static int getMinSize()
    {
        return GameMap.minSize;
    }


    public static int getMaxSize()
    {
        return GameMap.maxSize;
    }


    public static int getSize()
    {
        return GameMap.size;
    }


    public List<Artifact> getArtifacts()
    {
        return this.artifacts;
    }


    public List<GameCharacter> getGameCharacters()
    {
        return this.gameCharacters;
    }


    // other methods

    /**
     * Sets the new game map size from user input.
     */
    public static void getMapSizeFromUserInput()
    {
        String messageToUser = "Choose map size ("
                               + GameMap.minSize + " - " + GameMap.maxSize + "): ";

        try( Scanner sc = new Scanner(System.in) )
        {

            while( true )
            {
                System.out.print( messageToUser );

                while( !sc.hasNextInt() )
                {
                    System.out.print( messageToUser );
                    sc.next();
                }
                int userInput = sc.nextInt();

                if( (userInput >= GameMap.minSize) && (userInput <= GameMap.maxSize) )
                {
                    GameMap.size = userInput;
                    break;
                }
            }
        }
        catch( IllegalStateException isex )
        {
            System.out.println( "Error while getting user input on game map size.\n "
                                + "Application must stop now." );
            System.exit( -5 );
        }
    }


    /**
     * Creates a path between 2 MapPosition objects, using the most direct path
     * (moving in a straight or stair-stepped line as needed).
     * @param start the proposed start position
     * @param finish the proposed finish position
     * @return a list of MapPosition objects (with their coordinates within the current game map limits)
     */
    public static List<MapPosition> buildPathToDestination( final MapPosition start,
                                                            final MapPosition finish )
    {
        List<MapPosition> result = new ArrayList<>();

        // make sure to only create a path between two reachable positions on the game map
        MapPosition legalStart = GameMap.getLegalMapPosition( start );
        MapPosition legalFinish = GameMap.getLegalMapPosition( finish );

        // determine stepping direction on X and Y axis
        int xStep = legalStart.getXCoordinate() < legalFinish.getXCoordinate()
                    ? 1
                    : legalStart.getXCoordinate() > legalFinish.getXCoordinate()
                      ? -1
                      : 0;
        int yStep = legalStart.getYCoordinate() < legalFinish.getYCoordinate()
                    ? 1
                    : legalStart.getYCoordinate() > legalFinish.getYCoordinate()
                      ? -1
                      : 0;

        // identify each step of the path & add it to the result, including the starting position
        MapPosition currentPos = new MapPosition( legalStart.getXCoordinate(),
                                                  legalStart.getYCoordinate() );
        result.add( new MapPosition( currentPos.getXCoordinate(), currentPos.getYCoordinate() ) );

        int steps = Math.abs( legalStart.getXCoordinate() - legalFinish.getXCoordinate() )
                    + Math.abs( legalStart.getYCoordinate() - legalFinish.getYCoordinate() );

        while( steps > 0 )
        {
            if( currentPos.getXCoordinate() != legalFinish.getXCoordinate() )
            {
                currentPos.setXCoordinate( currentPos.getXCoordinate() + xStep );
                result.add( new MapPosition( currentPos.getXCoordinate(), currentPos.getYCoordinate() ) );
                steps--;
            }

            if( currentPos.getYCoordinate() != legalFinish.getYCoordinate() )
            {
                currentPos.setYCoordinate( currentPos.getYCoordinate() + yStep );
                result.add( new MapPosition( currentPos.getXCoordinate(), currentPos.getYCoordinate() ) );
                steps--;
            }
        }

        return result;
    }


    /**
     * Makes sure the coordinates of a position are inside the game map limits.
     * @param proposedPosition the proposed Position object
     * @return the original MapPosition object, if it is within the map limits,
     *         or a new MapPosition object on the edge of the map (in the direction indicated by the original).
     */
    private static MapPosition getLegalMapPosition( MapPosition proposedPosition )
    {
        MapPosition result = new MapPosition( proposedPosition.getXCoordinate(),
                                              proposedPosition.getYCoordinate() );

        if( result.getXCoordinate() < 0 )
        {
            result.setXCoordinate( 0 );
        }
        else if( result.getXCoordinate() >= GameMap.size )
        {
            result.setXCoordinate( GameMap.size - 1 );
        }

        if( result.getYCoordinate() < 0 )
        {
            result.setYCoordinate( 0 );
        }
        else if( result.getYCoordinate() >= GameMap.size )
        {
            result.setYCoordinate( GameMap.size - 1 );
        }

        return result;
    }


    public void populateMap( List<Object> entities )
    {
        if( entities != null )
        {
            for( Object item : entities )
            {
                if( item != null && (item instanceof Artifact) )
                {
                    this.addArtifact( (Artifact) item );
                }
                if( item != null && (item instanceof GameCharacter) )
                {
                    this.addCharacter( (GameCharacter) item );
                }
            }
        }
    }


    public void populateMapRandomly()
    {
        int limiter = 3;                              // arbitrarily chosen by the game designer
        int entitiesCount = GameMap.size / limiter;   // number of artifacts and also number of non-human characters

        this.populateWithArtifactsRandomly( entitiesCount );

        this.populateWithGameCharactersRandomly( entitiesCount );

        this.populateWithOneHumanRandomly();
    }


    private void populateWithGameCharactersRandomly( int entitiesCount )
    {
        MapPosition[] coordsCharacters = GameMap.getLegalRandomPositions( entitiesCount );
        for( int i = 0; i < coordsCharacters.length; i++ )
        {
            GameCharacter newChar;

            if( i % 2 == 0 )
            {
                newChar = new Animal( "Animalus~" + (i+1), coordsCharacters[ i ] );
            }
            else
            {
                newChar = new Monster( "Monstruus~" + (i+1), coordsCharacters[ i ] );
            }

            this.addCharacter( newChar );
            System.out.printf( "   + %s %s %s...%n",
                                newChar.getName(),
                                "at",
                                newChar.getPosition() );
        }
    }


    private void populateWithArtifactsRandomly( int entitiesCount )
    {
        MapPosition[] coordsArtifacts = GameMap.getLegalRandomPositions( entitiesCount );

        for( int i = 0; i < coordsArtifacts.length; i++ )
        {
            Artifact newArt;

            if( i % 2 == 0 )
            {
                newArt = new HealthPlus10Artifact( coordsArtifacts[ i ] );
            }
            else
            {
                newArt = new EnergyPlus10Artifact( coordsArtifacts[ i ] );
            }

            this.addArtifact( newArt );
            System.out.printf( "   + %s %s %s...%n",
                               newArt.getName(),
                               "at",
                               newArt.getPosition() );
        }
    }


    private void populateWithOneHumanRandomly()
    {
        MapPosition heroCoordinates = GameMap.getLegalRandomPositions( 1 )[0];

        String heroName = "Lancelot";                           // arbitrarily chosen by the game designer
        Human human = new Human( heroName, heroCoordinates );

        this.addCharacter( human );
        System.out.printf( "   + %s %s %s.%n%n",
                            human.getName(),
                            "starts at",
                            human.getPosition() );
    }


    private static MapPosition[] getLegalRandomPositions( int entitiesCount )
    {
        MapPosition[] result = new MapPosition[ entitiesCount ];

        Random rand = new Random();
        for( int i = 0; i < entitiesCount; i++ )
        {
            int xCoord = rand.nextInt( GameMap.size );
            int yCoord = rand.nextInt( GameMap.size );
            result[ i ] = new MapPosition( xCoord, yCoord );
        }

        return result;
    }


    public void addArtifact( Artifact newArtifact )
    {
        if( newArtifact != null )
        {
            this.artifacts.add( newArtifact );
        }
    }


    public void addCharacter( GameCharacter newGameCharacter )
    {
        if( newGameCharacter != null )
        {
            this.gameCharacters.add( newGameCharacter );
        }
    }


    public Human getTheHumanHero()
    {
        for( GameCharacter ch : this.gameCharacters )
        {
            if( ch instanceof Human )
            {
                return (Human) ch;
            }
        }

        return null;
    }


    public void displayGameStatus()
    {
        this.displayCharactersStatusReport();

        this.displayMap();
    }


    private void displayCharactersStatusReport()
    {
        String separator1 = "****************************************";
        String separator2 = "----------------------------------------";

        String title = "GAME STATUS";

        StringBuilder statusOfCharacters = new StringBuilder();
        for( GameCharacter ch : this.gameCharacters )
        {
            String statusOfChar = ch.getName()
                                  + " (health: " + ch.getHealth() + ", energy: " + ch.getEnergy() + ")"
                                  + "\n";

            if( ch instanceof Human )
            {
                statusOfCharacters.insert( 0, statusOfChar );
            }
            else
            {
                statusOfCharacters.append( statusOfChar );
            }
        }

        System.out.format( "%n%n%s%n %s%n%s%n%s%s%n",
                           separator1,
                           title,
                           separator2,
                           statusOfCharacters.toString(),
                           separator1 );
    }


    private void displayMap()
    {
        StringBuilder mapAsText = new StringBuilder();

        StringBuilder row = new StringBuilder();
        for( int i = 0; i < GameMap.size; i++ )
        {
            row.append( GameMap.emptyMapElement );
        }
        for( int i = 0; i < GameMap.size; i++ )
        {
            mapAsText.append( row )
                        .append( "\t" )
                        .append( i )
                        .append( "\n" );
        }

        for( Artifact art : this.artifacts )
        {
            if( art.getIsAlreadyUsed() )
            {
                continue;
            }

            int place = art.getPosition().getXCoordinate()
                        + (art.getPosition().getYCoordinate() * GameMap.size)
                        + (art.getPosition().getYCoordinate() * 3);   // this accounts for added chars at each row's end
            if( '\n' == mapAsText.charAt( place ) )
            {
                place++;
            }
            mapAsText.replace( place, place+1, GameMap.artifactMapElement );
        }

        for( GameCharacter ch : this.gameCharacters)
        {
            int place = ch.getPosition().getXCoordinate()
                        + (ch.getPosition().getYCoordinate() * GameMap.size )
                        + (ch.getPosition().getYCoordinate() * 3);   // this accounts for added chars at each row's end
            if( '\n' == mapAsText.charAt( place ) )
            {
                place++;
            }

            String replacement = "";
            if( ch instanceof Human )
            {
                replacement = ( ch.getHealth() <= 0 )
                              ? GameMap.heroDeadMapElement
                              : GameMap.heroMapElement;
            }
            else if( ch instanceof Monster )
            {
                replacement = ( ch.getHealth() <= 0 )
                              ? GameMap.monsterDeadMapElement
                              : GameMap.monsterMapElement;
            }
            else if( ch instanceof Animal )
            {
                replacement = GameMap.animalMapElement;
            }

            mapAsText.replace( place, place+1, replacement );
        }

        System.out.println( "\n" + mapAsText.toString() + "\n" );
    }
}
