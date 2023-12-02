package cam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
  Represents a Camp created by one staff.
  A Student can join as Attendee or Committee.
  @author Steph (Version 1.0)
  @author Sruthi (Version 1.1)
  @author Tan Shirley (Version 1.2, 1.4)
  @author Martin Ng (Version 1.3)
  @author Low Kar Choon (Version 1.5)
  @version 1.5
  @since 25-11-2023
*/
public class Committee extends StudentAcc {
	/**
	 * List of suggestions made by this Committee
	 */
    private ArrayList<Suggestion> suggestions;
    
    /**
     * List of replies made by this Committee
     */
    private ArrayList<Reply> replies;
    
    /**
     * The Camp ID of the Camp this Committee is in
     */
    public String campID;
    
    /**
     * Temporary variable to store Suggestion ID
     */
    public int sugID;
    
    /**
     * Temporary variable to store Enquiry ID
     */
    public int enqID;

    /**
     * Temporary variable to store Point
     */
    public static int POINT;
	
    /**
     * Create a new Committee
     * @param committeeName The Name of this Committee
     * @param suggestions The list of Suggestions made by this Committee
     * @param replies The list of Replies made by this Committee
     */
    public Committee(String committeeName ,ArrayList<Suggestion> suggestions, ArrayList<Reply> replies) {
    	super(committeeName);
    	this.suggestions = suggestions;
    	this.replies = replies;
    }
    
    /**
     * Create a new Committee
     * @param committeeName The Name of this Committee
     * @param campID The campID of the camp this Committee is in
     * @param suggestions The list of Suggestions made by this Committee
     * @param replies The list of Replies made by this Committee
     */
    public Committee(String committeeName, String campID, ArrayList<Suggestion> suggestions, ArrayList<Reply> replies) {
    	super(committeeName);
    	this.campID = campID;
    	this.suggestions = suggestions;
    	this.replies = replies;
    }

     /** 
     * Retrieve Committee Point from text file.
     * @param currentPoint The 
     */
    public static void restorePoint(int currentPoint) {
        POINT = currentPoint;
    }

    /** 
     * Add point for Committee.
     */
    public static void addPoint() {
        POINT++;
    }
	
    /**
     * Prints all "pending" enquiries
     */
    public void viewAllEnquiries() {
        System.out.println("All Pending Enquiries:");
        Enquiry.viewCampEnquiry(campID,"pending");
    }

    /**
     * Responds to an Attendee's enquiry
     * @param sc The Scanner object initialised in App.java
     * @throws IOException When it fails to create the reply to the enquiry
     */
    public void replyEnquiry(Scanner sc) throws IOException {
        if (Enquiry.viewCampEnquiry(campID,"pending") == 0)
        	return;
        System.out.println("Enquiry Reply System:");
        boolean found = false;
        do
        {
            System.out.println("Enter Enquiry ID to reply: ");
            enqID = sc.nextInt();
            sc.nextLine();      
            if(findEnquiry(enqID, campID ,"replied")!= null)
            {
                System.out.println("Enquiry has been replied.");
                break;
            }     
            if(findEnquiry(enqID, campID ,"pending")== null)
            {
                System.out.println("Invalid Enquiry ID. Please try again.");
            }   
            else
            {
                found = true;
            }

        }while (findEnquiry(enqID, campID,"pending")== null);

        if (found == true)
        {
            Enquiry e = findEnquiry(enqID, campID,"pending");
            System.out.println("Enter your reply:");
            String text = sc.nextLine();
            //create reply
            Reply reply = new Reply(enqID, super.getUserID(), text);
            Reply.addReply(reply);
            this.replies.add(reply);
            Reply.getAllReply().add(reply);

            //update enquiry
            e.setStatus("replied");
            Enquiry.modifyEnquiry(e);
        }
    }

    /**
     * Adds a Suggestion to the Camp that this Committee is in
     * @param sc The Scanner object initialised in App.java
     * @throws IOException When it fails to add the Suggestion
     */
    public void submitSuggestion(Scanner sc) throws IOException {
    	System.out.println("Camp Suggestion System:");
    	System.out.println("Enter your suggestion: ");
    	String text = sc.nextLine();
    	Suggestion suggestion = new Suggestion(campID, super.getUserID(), text);
    	Suggestion.addSuggestion(suggestion);
    	this.suggestions.add(suggestion);
    	Suggestion.getAllSuggestion().add(suggestion);
    	System.out.println("Successfully added your suggestion.");
    }

    /**
     * Edit a Suggestion if it is still "pending"
     * @param The Scanner object initialised in App.java
     */
    public void editSuggestion(Scanner sc) {
        if (Suggestion.viewCommitteeSuggestion(super.getUserID(),"pending") == 0)
        	return;
        System.out.println("Suggestion Edit System:");
        do 
        {
            System.out.println("Enter Suggestion ID: ");
            sugID = sc.nextInt();
            sc.nextLine();
	    if(findSuggestion(enqID, super.getUserID(),"pending")== null)
            {
                System.out.println("Invalid Suggestion ID. Please try again.");
            }

        }while(findSuggestion(sugID,super.getUserID(), "pending") == null);
       
        Suggestion s = findSuggestion(sugID,super.getUserID(), "pending");
        if(s.getStatus().equals("pending"))
        {
            System.out.println("Modify Suggestion: ");
            String modifiedSuggest = sc.nextLine();
            s.setText(modifiedSuggest);
            Suggestion.modifySuggestion(s);
        }
    }

    /**
     * Prints Suggestions made by this Committee
     */
    public void viewSuggestions() {
        System.out.println("Pending Suggestions made by "+ super.getName() +":");
	if (this.suggestions.size() == 0)
	{
		System.out.println("There are no suggestions.");
	}
	else
	{
		if (Suggestion.viewCommitteeSuggestion(super.getUserID(), "pending") == 0)
			return;
	}
    }

    /**
     * Removes a Suggestion made by this Committee
     * @param sc The Scanner object initialised in App.java
     */
    public void deleteSuggestion(Scanner sc) {
        if (Suggestion.viewCommitteeSuggestion(super.getUserID(), "pending") == 0)
        	return;
        System.out.println("Suggestion Deletion System:");

        do
        {
            System.out.println("Enter Suggestion ID: ");
            int sugID = sc.nextInt();
            sc.nextLine();
	    if(findSuggestion(enqID, super.getUserID(),"pending")== null)
            {
                System.out.println("Invalid Suggestion ID. Please try again.");
            }

        }while(findSuggestion(sugID,super.getUserID(), "pending") == null);
    
//        Suggestion s = findSuggestion(sugID,super.getUserID());/////!!!!!
//        if(s.getStatus().equals("pending"))
//        {
//            Suggestion.deleteCommitteeSuggestion(s);
//        }
    }

    /**
     * Gets a Suggestion based on Suggestion ID
     * @param sugID The Suggestion ID of a Suggestion
     * @param userID The User ID of this Committee
     * @param status The status of suggestion
     * @return The Suggestion made by this Committee
     */
    public Suggestion findSuggestion(int sugID, String userID, String status)
    {
    	for (Suggestion s: suggestions)
    	{
//    		if(s.getID() == sugID && s.getName().equals(userID) && s.getStatus.equals(status)//!!!!!
    		{
    			return s;
    		}
    	}
    	return null;
    }

   /** 
     * Get a Enquiry based on Enquiry ID
     * @param enqID Enquiry ID of a enquiry
     * @param campID The camp ID of the enquiry
     * @param status The status of enquiry
     * @return Enquiry The enquiry of the camp the Committee belongs to
     */
    public Enquiry findEnquiry(int enqID, String campID, String status)
	{
		for (Enquiry e: Enquiry.getAllEnquiry())
		{
			if(e.getID() == enqID && e.getCampID().equals(campID) && e.getStatus().equals(status))
			{
				return e;
			}
		}
		return null;
	}


    /**
     * View the reply to an Enquiry
     * @param sc The Scanner object initialised in App.java
     */
    public void viewReply(Scanner sc) {
        Enquiry.viewCampEnquiry(campID,"replied");
        System.out.println("View Reply System:");
        do
        {
            System.out.println("Enter Enquiry ID to view reply: ");
            enqID = sc.nextInt();
            sc.nextLine();
            if(findEnquiry(enqID, campID ,"replied")== null)
            {
                System.out.println("Invalid Enquiry ID. Please try again.");
            }
        } while (findEnquiry(enqID, campID ,"replied")== null);
        Reply.viewReply(enqID);
    }

    /**
     * Generate Camp Report based on the Filer
     * @param sc The Scanner object initialised in App.java
     */
    public void generateCampReport(Scanner sc) {
        System.out.print("Enter Filter(Attendee/Committee/None): ");
        String filter = sc.nextLine();
        GenerateReport.generateReport(this,filter,"Committee");
    }
}
