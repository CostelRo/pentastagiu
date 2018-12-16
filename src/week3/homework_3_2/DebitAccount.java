package week3.homework_3_2;


public class DebitAccount
{
    // state
    private String accountOwner;
    private double accountBalance;


    // constructors
    public DebitAccount( String owner, double balance )
    {
        if( (owner != null) && (owner.length() > 0) && (balance > 0) )
        {
            this.accountOwner = owner;
            this.accountBalance = balance;
        }
    }


    // getters & setters
    public String getAccountOwner()
    {
        return this.accountOwner;
    }

    /*
     * Since the owner of a bank account cannot, as a rule, be changed freely,
     * this method should probably only be used when the owner changes their name.
     */
    public void setAccountOwner( String newOwner )
    {
        if( (newOwner != null) && (newOwner.length() > 0) )
        {
            this.accountOwner = newOwner;
        }
    }


    public double getAccountBalance()
    {
        return this.accountBalance;
    }

    public void setAccountBalance( double newBalance )
    {
        if( newBalance >= 0 )
        {
            this.accountBalance = newBalance;
        }
    }


    // other methods
    /*
     * This is the recommended method for increasing the account's balance,
     * since more checks (not shown below) might be needed before accepting to deposit money into a bank account.
     */
    public String depositIntoAccount( double amount )
    {
        String result;

        if( amount <= 0 )
        {
            result = "Illegal depositing attempt (amount <= 0).";
        }
        else
        {
            this.setAccountBalance( this.accountBalance + amount );
            result = "Money deposited.";
        }

        return result;
    }


    /*
     * This is the recommended method for reducing the account's balance,
     * since more checks (not shown below) might be needed before accepting to withdraw money from a bank account.
     */
    public String withdrawFromAccount( double amount )
    {
        String result;

        if( amount <= 0)
        {
            result = "Illegal withdrawal attempt (amount <= 0).";
        }
        else if( (amount > 0) && (amount <= this.accountBalance) )
        {
            this.accountBalance -= amount;
            result = "Money withdrawn.";
        }
        else
        {
            result = "Insufficient funds.";
        }

        return result;
    }


    @Override
    public String toString()
    {
        return this.accountOwner + " - balance = " + this.getAccountBalance();
    }
}
