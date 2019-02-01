package airportmanager;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


public class ReportCreator
{
    // state

    private static final String SEPARATOR1 = "****************************************";
    private static final String SEPARATOR2 = "----------------------------------------";
    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd, HH:mm:ss";
    private static final String DATE_FORMATTER      = "yyyy-MM-dd";


    // getters & setters

    public static String getDateTimeFormatter()
    {
        return ReportCreator.DATE_TIME_FORMATTER;
    }

    public static String getDateFormatter()
    {
        return ReportCreator.DATE_FORMATTER;
    }


    // other methods

    /**
     * Creates a formatted report using data provided.
     * @param sourceData the data to be formatted as a report
     * @param reportTime the date & time of the data in the report
     * @return a String containing the formatted report
     */
    public static String buildReport( List<Object>  sourceData,
                                      String        subject,
                                      LocalDateTime reportTime )
    {
        if( sourceData == null || sourceData.size() == 0 || subject == null || reportTime == null )
        {
            return "\nREPORT - Missing data!\n";
        }

        // create the report
        String formattedReportTime = reportTime.format( DateTimeFormatter.ofPattern( DATE_TIME_FORMATTER ) );
        String generalTitle = "      REPORT [" + formattedReportTime + "]";

        FlightsManager flightsManager       = FlightsManager.getSingleton( FlightValidator.getSingleton() );
        PassengersManager passengersManager = PassengersManager.getSingleton( PassengerValidator.getSingleton() );

        String localAirportInfo = "       == " + AirportManager.getSingleton( flightsManager, passengersManager )
                                                               .getLocalAirport().toString() + " ==";

        StringBuilder collector = new StringBuilder( ReportCreator.SEPARATOR1);
        collector.append( localAirportInfo );
        collector.append( "\n" );
        collector.append( generalTitle );
        collector.append( "\n" );
        collector.append( ReportCreator.SEPARATOR2);
        collector.append( "\n" );
        collector.append( subject );
        collector.append( "\n" );
        collector.append( ReportCreator.SEPARATOR2);
        collector.append( "\n" );

        sourceData.stream()
                  .filter( Objects::nonNull )
                  .sorted()
                  .forEach( element -> { collector.append( element.toString() );
                                         collector.append("\n"); } );

        collector.append( ReportCreator.SEPARATOR1);
        collector.append( "\n" );

        // deliver the report
        return collector.toString();
    }
}
