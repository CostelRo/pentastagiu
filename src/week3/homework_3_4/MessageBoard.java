package week3.homework_3_4;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/*
 * IMPLEMENTATION NOTES:
 * This is a very simple implementation, designed as an exercise in basic OOP.
 * The application offers tools to create and keep the data in memory at runtime,
 * without long-term data storage in a database (which we would most likely choose in a real situation).
 */
public class MessageBoard
{
    // state
    private HashMap<User, Set<Integer>> liveUsersAndMessageIDs;    // pairs (User, set-of-user-messages-IDs)
    private HashMap<Integer, Message>   liveMessagesByID;          // pairs (message-ID, Message-object)


    // getters & setters
    public HashMap<User, Set<Integer>> getLiveUsersAndMessageIDs()
    {
        return this.liveUsersAndMessageIDs;
    }

    public HashMap<Integer, Message> getLiveMessagesByID()
    {
        return this.liveMessagesByID;
    }


    // constructors
    public MessageBoard()
    {
        this.liveUsersAndMessageIDs = new HashMap<>();
        this.liveMessagesByID = new HashMap<>();
    }


    // other methods
    public static void main( String[] args )
    {
        MessageBoard board1 = new MessageBoard();

        board1.startProgram();
    }


    private void startProgram()
    {
        UserInterfaceManager.buildStartPage();

        this.manageUserCommands();
    }


    private void manageUserCommands()
    {
        while( true )
        {
            Scanner userInput = new Scanner( System.in );

            switch( userInput.nextLine().toLowerCase() )
            {
                case "exit":
                    userInput.close();
                    System.exit( 0 );
                    break;

                case "login":
                case "log in":
                case "log-in":
                    UserInterfaceManager.buildLoginPage();
                    MessageBoard.processLoginInput( userInput );
                    User activeUser1 = AuthenticationManager.getSingleton().getActiveUser();
                    UserInterfaceManager.buildMessagesPage( activeUser1,
                                                            this.collectMessagesByUser( activeUser1 ),
                                                            true );
                    break;

                case "logout":
                case "log out":
                case "log-out":
                    AuthenticationManager.getSingleton().logout();
                    UserInterfaceManager.buildStartPage();
                    break;

                case "signup":
                case "sign up":
                case "sign-up":
                    UserInterfaceManager.buildSignUpPage();
                    this.processSignUpInput( userInput );
                    User activeUser2 = AuthenticationManager.getSingleton().getActiveUser();
                    UserInterfaceManager.buildMessagesPage( activeUser2,
                                                            this.collectMessagesByUser( activeUser2 ),
                                                            true );
                    break;

                case "messages":
                    User activeUser3 = AuthenticationManager.getSingleton().getActiveUser();
                    if( activeUser3 == null )
                    {
                        UserInterfaceManager.buildMessagesPage( activeUser3,
                                                                this.collectMessagesByUser( activeUser3),
                                                                false );
                    }
                    else
                    {
                        UserInterfaceManager.buildMessagesPage( activeUser3,
                                                                collectMessagesByUser( null ),
                                                                false );
                    }
                    break;

                case "user":
                    User activeUser4 = AuthenticationManager.getSingleton().getActiveUser();
                    UserInterfaceManager.buildMessagesPage( activeUser4,
                                                            this.collectMessagesByUser( activeUser4 ),
                                                            true );
                    break;

                case "add":
                    UserInterfaceManager.buildAddNewMessagePage( AuthenticationManager.getSingleton().getActiveUser() );
                    Message newMessage = MessageBoard.processNewMessageInput( userInput );
                    this.addNewMessage( newMessage );
                    System.out.println( "    Messaged registered.\n" );
                    break;

                case "cancel":
                    User activeUser5 = AuthenticationManager.getSingleton().getActiveUser();
                    UserInterfaceManager.buildMessagesPage( activeUser5,
                                                            this.collectMessagesByUser( activeUser5 ),
                                                            true );
                    break;

                default:
                    continue;
            }
        }
    }


    private void processSignUpInput( Scanner inputSource )
    {
        while( true )
        {
            System.out.print("  >> Username ('cancel' to stop): ");
            String username = inputSource.nextLine();
            if( "cancel". equals( username ) )
            {
                System.out.println();
                return;
            }
            System.out.print("  >> Email: ");
            String email = inputSource.nextLine();

            User inputUserSignUpAttempt = AuthenticationManager.getSingleton().signUp( username, email );
            if( inputUserSignUpAttempt == null )
            {
                System.out.println( "      Incorrect authentication data.\n" );
            }
            else
            {
                this.liveUsersAndMessageIDs.put( inputUserSignUpAttempt, new HashSet<>() );
                return;
            }
        }
    }


    private static void processLoginInput( Scanner inputSource )
    {
        while( true )
        {
            System.out.print("  >> Username ('cancel' to stop): ");
            String username = inputSource.nextLine();
            if( "cancel".equals( username ) )
            {
                System.out.println();
                return;
            }
            System.out.print("  >> Email: ");
            String email = inputSource.nextLine();

            User inputUserLoginAttempt = AuthenticationManager.getSingleton().login( username, email );
            if( inputUserLoginAttempt == null )
            {
                System.out.println( "      Incorrect authentication data.\n" );
            }
            else
            {
                return;
            }
        }
    }


    public void addNewMessage( Message newMessage )
    {
        if( newMessage != null )
        {
            this.liveMessagesByID.put( newMessage.getId(), newMessage );

            this.getLiveUsersAndMessageIDs().get( AuthenticationManager.getSingleton().getActiveUser() )
                               .add( newMessage.getId() );
        }
    }


    private static Message processNewMessageInput( Scanner inputSource )
    {
        System.out.print( "  >> Message text: " );
        String messageText = inputSource.nextLine();

        Message result = null;

        if( messageText.length() > 0 )
        {
            result = new Message( AuthenticationManager.getSingleton().getActiveUser(), messageText );
        }

        return result;
    }


    private List<Message> collectMessagesByUser( User user )
    {
        List<Message> result = new ArrayList<>();

        if( user != null )
        {
            for( int id : this.getLiveUsersAndMessageIDs().get( user ) )
            {
                result.add( this.liveMessagesByID.get( id ) );
            }
        }
        else
        {
            result.addAll( this.liveMessagesByID.values() );
        }

        result.sort( new MessagesByDateReversedComparator() );
        return result;
    }
}
