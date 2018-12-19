package week3.homework_3_4;


import java.util.Collection;


public class UserInterfaceManager
{
    // state
    private static String consolePageSeparator = "====================";


    // other methods
    public static void buildStartPage()
    {
        String pageTitle = "Message Board START";

        String intro = "Welcome!\n Read here messages published by all members.\n "
                       + "Plus, of course, publish your own messages!"
                       + "\n(Choose a menu option by writing the word.)";

        String menuOptions = "Messages  |  Login  |  Signup  |  Exit";

        String contents = "";

        UserInterfaceManager.displayConsolePage( pageTitle, intro, menuOptions, contents );
    }


    public static void buildSignUpPage()
    {
        String pageTitle = "Sign-Up";

        String intro = "";

        String menuOptions = "Login  |  Cancel";

        String contents = "";

        UserInterfaceManager.displayConsolePage( pageTitle, intro, menuOptions, contents );
    }


    public static void buildLoginPage()
    {
        String pageTitle = "Log-In";

        String intro = "";

        String menuOptions = "Signup  |  Cancel";

        String contents = "";

        UserInterfaceManager.displayConsolePage( pageTitle, intro, menuOptions, contents );
    }


    public static void buildMessagesPage( User activeUser, Collection<Message> source, boolean isUsernameInPageTitle )
    {
        String authorInfo = (activeUser != null)
                            ? " from " + activeUser.getName()
                            : "";
        int messagesCounter = (source != null)
                              ? source.size()
                              : 0;
        String usernameInTitle = (isUsernameInPageTitle)
                                  ? authorInfo
                                  : "";
        String pageTitle = "Messages" + usernameInTitle + " (" + messagesCounter + ")";

        String intro = "";

        String menuOptions = (activeUser != null)
                             ? "Messages  |  User  |  Add  |  Logout  |  Exit"
                             : "Messages  |  Login  |  Signup  |  Exit";

        StringBuilder msgData = new StringBuilder( "" );
        if( source != null && source.size() > 0 )
        {
            String separator = "\n----------\n";

            if( activeUser != null && isUsernameInPageTitle )
            {
                for( Message msg : source )
                {
                    if( activeUser.getName().equals( msg.getAuthor().getName() ) )
                    {
                        msgData.append( msg.toString() )
                               .append( separator );
                    }
                }
            }
            else
            {
                for( Message msg : source )
                {
                    msgData.append( msg.toString() )
                           .append( separator );
                }
            }
        }

        UserInterfaceManager.displayConsolePage( pageTitle, intro, menuOptions, msgData.toString() );
    }


    public static void buildAddNewMessagePage( User activeUser )
    {
        String authorInfo = (activeUser != null)
                            ? " (from " + activeUser.getName() + ")"
                            : "";
        String pageTitle = "Add New Message" + authorInfo;

        String intro = "";

        String menuOptions = "Add  |  Cancel |  Logout";

        String contents = "";

        UserInterfaceManager.displayConsolePage( pageTitle, intro, menuOptions, contents );
    }


    private static void displayConsolePage( String pageTitle, String intro, String menuOptions, String contents )
    {
        System.out.println( UserInterfaceManager.consolePageSeparator
                            + "\n" + pageTitle
                            + "\n\n" + intro
                            + "\n\n" + "MENU: " + menuOptions
                            + "\n\n" + contents + "\n" );
    }
}
