/*
 * Very simple authentication management system, without secure password management.
 * May be usable if the application is needed to run on a single computer and with no need for security.
 */


package week3.homework_3_4;


import java.util.HashMap;


public final class AuthenticationManager
{
    // state
    private static  AuthenticationManager singleton = null;
    private         HashMap<String, String> loginLiveData;
    private         User activeUser;


    // constructors
    private AuthenticationManager()
    {
        this.loginLiveData = new HashMap<>();
        this.activeUser = null;
    }


    // getters & setters
    public static AuthenticationManager getSingleton()
    {
        if( AuthenticationManager.singleton != null )
        {
            return AuthenticationManager.singleton;
        }

        AuthenticationManager.singleton = new AuthenticationManager();

        return AuthenticationManager.singleton;
    }


    public HashMap<String, String> getLoginLiveData()
    {
        return this.loginLiveData;
    }


    public User getActiveUser()
    {
        return this.activeUser;
    }


    // other methods
    public User signUp( String username, String email )
    {
        User result = null;

        if( User.isValidEmailFormat( username ) && User.isValidEmailFormat( email )
            && this.isUserNameAvailable( username ) )
        {
            result = new User( username, email );
            this.loginLiveData.put( username, email );
            this.activeUser = result;
        }

        return result;
    }


    private boolean isUserNameAvailable( String username )
    {
        return User.isValidNameFormat( username ) && !this.loginLiveData.containsKey( username );
    }


    public User login( String username, String email )
    {
        User result = null;

        if( (User.isValidNameFormat( username ) || User.isValidEmailFormat( email ))
            && this.loginLiveData.containsKey( username ) && this.loginLiveData.get( username ).equals( email ) )
        {
            this.activeUser = new User( username, email );
            result = this.activeUser;
        }

        return result;
    }


    public void logout()
    {
        this.activeUser = null;
    }
}
