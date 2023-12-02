package cam;

import java.util.Scanner;

/**
  Represents a Student, derived from UserAcc.java
  @author Martin Ng (Version 1.0)
  @author Low Kar Choon (Version 1.1)
  @version 1.1
  @since 25-11-2023
*/
public abstract class UserAcc 
{
	/**
	 * The index of the User's Name in the txt file
	 */
    public static final int INDEX_OF_NAME =0;
    
    /**
	 * The index of the User's Email in the txt file
	 */
    public static final int INDEX_OF_EMAIL =1;
    
    /**
	 * The index of the User's Faculty in the txt file
	 */
    public static final int INDEX_OF_FACULTY =2;
    
    /**
	 * The index of the User's First Time Login status in the txt file
	 */
    public static final int INDEX_OF_FIRSTTIMELOGIN =5;

    /**
     * Stores the list of information of this User retrieved from the txt file
     */
    protected String[] information;

    /**
     * The User ID of this User
     */
    private String userID;
    
    /** 
     * The Faculty of this User
     */
    private Faculty faculty;
    
    /**
     * The Account Type of this User (Student/Staff)
     */
    private String accType;
    
    /**
     * The Email of this User
     */
    private String email;
    
    /**
     * The Name of this User
     */
    private String name;
    
    /**
     * Stores if this is this User has logged in before
     */
    private boolean isFirstTimeLogIn;
    
    /**
     * Creates a User based on the User ID
     * @param user The User ID of this User
     */
    public UserAcc(String user)
    {
        this.userID = user.toUpperCase();
    }

    /**
     * Gets the User ID of this User
     * @return The User ID of this User
     */
    public String getUserID()
    {
        return userID;
    }

    /**
     * Gets the Account Type of this User
     * @return The Account Type of this User
     */
    public String getAccType()
    {
        return accType;
    }

    /**
     * Gets the Faculty of this User
     * @return The Faculty of this User
     */
    public Faculty getFaculty()
    {
        return faculty;
    }

    /**
     * Gets the Email of this User
     * @return The Email of this User
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Gets the Name of this User
     * @return The Name of this User
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the first time login status of this User
     * @return The first time login status of this User
     */
    public boolean getIsFirstTimeLogIn() {
    	return isFirstTimeLogIn;
    }

    /**
     * Sets the Account Type of this User
     * @param type The new Account Type of this User
     */
    public void setAccType(String type)
    {
        this.accType = type.toLowerCase();
    }

    /**
     * Sets the User's Name, Email, Faculty and First Time Login status
     * @param information The list of information retrieved from the txt file
     */
    public void setInformation(String[] information)
    {
        this.name = information[INDEX_OF_NAME];
        this.email = information[INDEX_OF_EMAIL];
        this.faculty = Faculty.getFacultyType(information[INDEX_OF_FACULTY]);
        this.isFirstTimeLogIn = Boolean.parseBoolean(information[INDEX_OF_FIRSTTIMELOGIN]);
    }

    /**
     * Method to change the User's password
     * @param new_password The new password of the User
     */
    public void changePassword(String new_password) //calls static method in FileRW to change password
    {
        FileRW.changePassword(this, new_password);
    }

    /**
     * Gets the User's information retrieved from the txt file
     * @return The list of User's information retrieved from the txt file
     */
    public String[] getInformation() //calls static method in FileRW to obtain user information
    {
        String[] information =FileRW.getInformation(this);
        return information;
    }

    /**
     * Prints the actions the User can perform
     * @return The total number of options
     */
    public abstract int displayOptions();
    
    /**
     * Performs the actions based on the User's choice
     * @param choice The choice selected by the User
     * @param sc The Scanner object initialised in App.java
     */
    public abstract void doChoice(int choice, Scanner sc);
}
