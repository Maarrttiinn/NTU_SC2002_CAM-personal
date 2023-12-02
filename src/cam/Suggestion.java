package cam;

import java.io.IOException;
import java.util.ArrayList;
/**
  Represents a Suggestion created by one Student.
  A Committee can make suggestion to camp one belongs to.
  @author Tan Shirley
  @version 1.0
  @since 25-11-2023
 */

public class Suggestion extends Feedback {

    /**
    * The suggestion index
    */
    public static int COUNT_SUGGESTION;
	
    /**
    * The status of this Suggestion
    */	
    private String status;
	
    /** 
    * The camp ID of this Enquiry
    */
    private String campID;

    /**
    * The list of all enquires.
    */
    private static ArrayList<Suggestion> allSuggestion = new ArrayList<Suggestion>();
	
    /**
    * Creates a new Suggestion with campID, committee userID and suggestion text.
    * @param campID The camp ID of the suggestion
    * @param userName The committee userID who create this Suggestion
    * @param text The suggestion text
    */
    public Suggestion(String campID, String userName, String text) {
        super(COUNT_SUGGESTION, "suggestion", text, userName);
        setID(COUNT_SUGGESTION);
        this.status = "pending";
        this.campID = campID;
        COUNT_SUGGESTION += 1;
    }
	
    /**
    * Creates a new Suggestion for each Suggestion read from suggestion.txt
    * @param id The suggestion ID
    * @param fbType The type of feedback - suggestion
    * @param userName The committee userID who create this Suggestion
    * @param text The suggestion text
    * @param status The status of the suggestion
    * @param campID The camp ID of the suggestion
    */
    public Suggestion(int id , String status , String userName, String campID, String text ) {
        super(id,"suggestion", text, userName);
        setID(id);
        this.status = status;
        this.campID = campID;     
    }	
	
    /** 
     * Retrieve camp ID of  this Suggestion
     * @return String campID
     */
    public String getCampID()
    {
        return campID;
    }
	
    /** 
     * Retrieve status of this Suggestion
     * @return String status 
     */
    public String getStatus() {
        return status;
    }
	
    /** 
     * Set status of this Suggestion
     * @param status status of suggestion
     */
    public void setStatus(String status) {
        this.status = status;
    }

     /** 
     * Set suggestion index for suggestion ID.
     * @param LastIndex suggestion index for id.
     */
    public static void setCounter(int LastIndex) {
        COUNT_SUGGESTION = LastIndex+1;
    }
	
     /** 
     * Retrieve all suggestions created.
     * @return ArrayList<Suggestion> a list of all suggestions.
     */
    public static ArrayList<Suggestion> getAllSuggestion() {
		return allSuggestion;
	}
    
     /** 
     * Retrieve a Suggestion by suggestion ID
     * @param sugID the id of suggestion
     * @return Suggestion object
     */
    public static Suggestion getSuggestionBySugID(int sugID) {
		for (Suggestion suggestion : allSuggestion) {
			if (suggestion.getID() == sugID)
				return suggestion;
		}
		return null;
	}

	
    /** 
     * Pass new suggestion to feedbackRW to write into suggestion text file
     * @param suggestion The new suggestion.
     */
    public static void addSuggestion(Suggestion suggestion)
    {
        try {
            feedbackRW.writeSuggestion(suggestion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /** 
     * Retrieve all Committee suggestion based on committee userID and status of suggestion
     * @param userName The student userID of this Suggestion
     * @param status The status of suggestion
     */
    public static int viewCommitteeSuggestion(String userName, String status) {
    	int count = 0;
        if (allSuggestion.size() == 0) {
			System.out.println("No suggestion available.");
		} 
		else 
        {
            for (Suggestion suggestion : Suggestion.allSuggestion) 
            {
            	count++;
                if(suggestion.getName().equals(userName) && suggestion.getStatus().equals(status))
                {
                    System.out.println("======= List of "+ userName+"'s Suggestion =======");
                    System.out.println("------------------------------------");
                    System.out.println("Suggestion ID: " + suggestion.getID());
                    System.out.println("Status: " + suggestion.getStatus());
                    System.out.println("Student Name: " + suggestion.getName());
                    System.out.println("Camp ID: " + suggestion.getCampID());
                    System.out.println("Suggestion: " + suggestion.getText());
                    System.out.println("------------------------------------\n");
                }
            }
			if (count > 0)
				System.out.println("======= End of List of All Suggestion =======\n");
			else
				System.out.println("No suggestion available.");
        }
        return count;
    }
    
    /** 
     * Pass modified suggestion to feedbackRW to override in suggestion text file
     * @param suggestion The modified suggestion
     */
    public static void modifySuggestion(Suggestion suggestion)
    {
        feedbackRW.updateSuggestion(suggestion);
    }
	
    /** 
     * Pass deleted suggestion to feedbackRW to remove in suggestion text file
     * @param suggestion The deleted suggestion
     */
    public static void deleteCommitteeSuggestion(Suggestion suggestion)
    {
        feedbackRW.deleteSuggestion(suggestion);
    }

    /** 
     * Retrieve all camp suggestion based on campID and status
     * @param campID The camp ID of suggestion
     * @param status The status of suggestion
     */
	public static int viewCampSuggestion(String campID,String status) {
		int count = 0;
        if (allSuggestion.size() == 0) {
			System.out.println("No suggestion available.");
		} 
		else 
        {
            for (Suggestion suggestion : allSuggestion) 
            {
            	System.out.println("GET CAMPID" + suggestion.getCampID());
            	System.out.println("GET STATUS" + suggestion.getStatus());
                if(suggestion.getCampID().equals(campID) && suggestion.getStatus().equals(status))
                {
                	count++;
                    System.out.println("======= List of "+ campID+"'s Suggestion =======");
                    System.out.println("------------------------------------");
                    System.out.println("Suggestion ID: " + suggestion.getID());
                    System.out.println("Status: " + suggestion.getStatus());
                    System.out.println("Student Name: " + suggestion.getName());
                    System.out.println("Camp ID: " + suggestion.getCampID());
                    System.out.println("Suggestion: " + suggestion.getText());
                    System.out.println("------------------------------------\n");
                }
            }
            if (count > 0)
            	System.out.println("======= End of List of All Suggestion =======\n");
            else
            	System.out.println("No suggestion available.");
        }
        return count;
        //feedbackRW.getCampSuggestion(campID);
	}

    /** 
     * Staff approve or reject suggestion.
     * Committee update suggestion before it is processed.
     * Pass modified suggestion to feedbackRW to update in suggestion text file
     * @param suggestion
     */
    public static void updateSuggestion(Suggestion suggestion)
    {
        feedbackRW.updateSuggestion(suggestion);
    }

	
}
