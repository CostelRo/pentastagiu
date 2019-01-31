package airportmanager.springjavaconfig;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan( basePackages = "airportmanager" )
public class AirportManagerConfig
{
//    @Bean( name = "localAirport" )
//    public Airport airport( String airportCode, String city, String country )
//    {
//        if( airportCode != null && city != null && country != null )
//        {
//            return new Airport( "IAS", "Iasi", "Romania");
//        }
//        else
//        {
//            return new Airport( );
//        }
//    }
}
