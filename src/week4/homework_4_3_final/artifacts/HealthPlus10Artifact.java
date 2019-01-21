package week4.homework_4_3_final.artifacts;


import week4.homework_4_3_final.map.MapPosition;

public class HealthPlus10Artifact extends Artifact
{
    // constructors

    public HealthPlus10Artifact( MapPosition position )
    {
        super( "health", "Health+10", 10, position );
    }
}
