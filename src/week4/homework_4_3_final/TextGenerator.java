package week4.homework_4_3_final;


import week4.homework_4_3_final.map.GameMap;
import week4.homework_4_3_final.map.MapPosition;

import java.util.Formatter;
import java.util.Random;


public class TextGenerator
{
    // state

    private static Random rand = new Random();


    // other methods

    public static String movingTowardsDestinationMsg( MapPosition destination )
    {
        return "\u21D2  Onwards to " + destination + "!";
    }

    public static String newStepTakenMsg( MapPosition newStep )
    {
        Formatter result = new Formatter();
        result.format( "%s to %s...",
                       GameMap.getStepOnMapSymbol(),
                       newStep );

        return result.toString();
    }


    public static String foundNewUnusedArtifactMsg( String artifactName )
    {
        String[] startMsg = { "Great, a",
                              "Lucky day, a",
                              "Yuhuu, a",
                              "Excelent, a",
                              "Perfect, a" };

        return "   " + GameMap.getArtifactMapElement() + " "
               + startMsg[ TextGenerator.rand.nextInt( startMsg.length ) ] + " "
               + artifactName + "!";
    }


    public static String foundNewLiveAnimalMsg( String animalName )
    {
       String[] startMsg = { "Oh, it's an",
                             "Amazing, this",
                             "Great, an",
                             "Look at this",
                             "Wow, another" };

       return "   " + GameMap.getAnimalMapElement()
              + " " + startMsg[ TextGenerator.rand.nextInt( startMsg.length ) ]
              + " " + animalName + "!"
              + " Let's strike it once!";
    }


    public static String foundNewLiveMonsterMsg( String monsterName )
    {
        String[] startMsg = { "Ugh, it's a",
                              "Horrible",
                              "Die,",
                              "Grr, you die now,",
                              "Disappear, you" };

        return "   " + GameMap.getMonsterMapElement()
                + " " + startMsg[ TextGenerator.rand.nextInt( startMsg.length ) ]
                + " " + monsterName + "!";
    }


    public static String foundNewDeadGameCharacterMsg( String characterClass )
    {
        return "Hm... a dead " + characterClass + ". Moving On.";
    }


    public static String lackOfEnergyNoAttackMsg()
    {
        return "   ! No energy... no attack! \u26A0";
    }


    public static String monsterAttacksHumanMsg()
    {
        String[] options = { "Argh! An attack...!",
                             "Ugh! It strikes back!",
                             "Grrr! It dares to hit me!" };

        return "   \uD83D\uDC3E "
                + options[ TextGenerator.rand.nextInt( options.length ) ];
    }


    public static String deadHumanMsg()
    {
        String[] startMsg = { "...Oh. Hero dead.",
                              "...Shocking, hero is dead!",
                              "...Nooo!... Dead hero...",
                              "...Death. End of story."};

        return GameMap.getHeroDeadMapElement() + " "
               + startMsg[ TextGenerator.rand.nextInt(startMsg.length) ];
    }
}
