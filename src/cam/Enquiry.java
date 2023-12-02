package cam;

import java.io.IOException;
import java.util.ArrayList;

/**
  Represents a Enquiry created by one Student.
  A Student can make enquiry for available camps.
  @author Tan Shirley
  @version 1.0
  @since 25-11-2023
 */

public class Enquiry extends Feedback {

    /**
    * The enquiry index
    */
    public static int COUNT_ENQUIRY;

    /**
    * The status of this Enquiry
    */
    private String status;

    /**
    * The camp ID of this Enquiry
    */
    private String campID;
	
    /**
    * The list of all enquires.
    */
    private static ArrayList<Enquiry> allEnquiry = new ArrayList<Enquiry>();

	/**
		* Creates a new Enquiry with campID, student userID and enquiry text.
		* @param campID The camp ID of the enquiry
		* @param userName The student userID who create this Enquiry
		* @param text The enquiry text
	*/	
    public Enquiry(String campID, String userName, String text ) {
        super(COUNT_ENQUIRY,"enquiry", text, userName);
        setID(COUNT_ENQUIRY);
        this.status = "pending";
        this.campID = campID;
        COUNT_ENQUIRY += 1;
    }

	/**
		* Creates a new Enquiry for each Enquiry read from enquiry.txt
		* @param id The enquiry ID
		* @param campID The camp ID of the enquiry
		* @param userName The student userID who create this Enquiry
		* @param text The enquiry text
		* @param status The status of the enquiry
	*/
    public Enquiry(int id, String campID, String userName, String text , String status) {
        super(id,"enquiry", text, userName);
        setID(id);
        this.status = status;
        this.campID = campID;     
    }

   /** 
     * Retrieve camp ID of  this Enquiry
     * @return String campID
     */
    public String getCampID()
    {
        return campID;
    }

    /** 
     * Retrieve status of this Enquiry
     * @return String status 
     */
    public String getStatus() {
        return status;
    }
	
    /** 
     * Set status of this Enquiry
     * @param status status of enquiry
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 
     * Set enquiry index for enquiry ID.
     * @param eLastIndex enquiry index for id.
     */
    public static void setCounter(int eLastIndex) {
        COUNT_ENQUIRY = eLastIndex+1;
    }

     /** 
     * Retrieve all enquiries created.
     * @return ArrayList<Enquiry> a list of all enquiries.
     */
    public static ArrayList<Enquiry> getAllEnquiry() {
		return allEnquiry;
	}
	
    /** 
     * Retrieve a Enquiry by enquiry ID.
     * @param enqID The id of enquiry
     * @return Enquiry object.
     */
    public static Enquiry getEnquiryByEnqID(int enqID) {
	for (Enquiry enquiry : allEnquiry) {
		if (enquiry.getID() == enqID)
			return enquiry;
	}
	return null;
    }   

    /** 
     * Pass new enquiry to feedbackRW to write into enquiry text file
     * @param enquiry The new enquiry.
     */
    public static void addEnquiry(Enquiry enquiry) {
        try {
            feedbackRW.writeEnquiry(enquiry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** 
     * Retrieve all Student enquiries based on student userID and status of enquiry
     * @param studentName The student userID of this Enquiry
     * @param status The status of enquiry
     * @return number of enquiries based on status
     */
	public static int viewStudentEnquiryByStatus(String studentName, String status) {
		int count = 0;
        if (allEnquiry.size() == 0) {
			System.out.println("No enquiry available.");
		} 
		else 
        {
            for (Enquiry enquiry : Enquiry.allEnquiry) 
            {
            	count++;
                if(enquiry.getName().equals(studentName) && enquiry.getStatus().equals(status))
                {
                    System.out.println("======= List of "+ studentName+"'s Enquiry =======");
                    System.out.println("------------------------------------");
                    System.out.println("Enquiry ID: " + enquiry.getID());
                    System.out.println("Status: " + enquiry.getStatus());
                    System.out.println("Student Name: " + enquiry.getName());
                    System.out.println("Camp ID: " + enquiry.getCampID());
                    System.out.println("Enquiry: " + enquiry.getText());
                    System.out.println("------------------------------------\n");
                }
            }
            if (count > 0)
            	System.out.println("======= End of List of All Enquiry =======\n");
            else
            	System.out.println("No enquiry available.");
        }
        return count;
    }

    /** 
     * Pass modified enquiry to feedbackRW to override in enquiry text file
     * @param enquiry The modified enquiry
     */
    public static void modifyEnquiry(Enquiry enquiry) {
		feedbackRW.updateEnquiry(enquiry);
    }

    /** 
     * Pass deleted enquiry to feedbackRW to remove in enquiry text file
     * @param enquiry The deleted enquiry
     */
    public static void deleteEnquiry(Enquiry enquiry) {
        feedbackRW.deleteEnquiry(enquiry);
    }

    /** 
     * Retrieve all camp enquiries based on campID and status
     * @param campID The camp ID of enquiry
     * @param status The status of enquiry
     * @return The number of pending enquiries of the camp 
     */
	public static int viewCampEnquiry(String campID,  String status) {
	int count = 0;
        if (allEnquiry.size() == 0) {
			System.out.println("No enquiry available.");
		} 
		else 
        {
            for (Enquiry enquiry : Enquiry.allEnquiry) 
            {
                if(enquiry.getCampID().equals(campID) && enquiry.getStatus().equals(status))
                {
                	count++;
                    System.out.println("======= List of "+ enquiry.getCampID()+"'s Enquiry =======");
                    System.out.println("------------------------------------");
                    System.out.println("Enquiry ID: " + enquiry.getID());
                    System.out.println("Status: " + enquiry.getStatus());
                    System.out.println("Student Name: " + enquiry.getName());
                    System.out.println("Camp ID: " + enquiry.getCampID());
                    System.out.println("Enquiry: " + enquiry.getText());
                    System.out.println("------------------------------------\n");
                }
            }
            if (count > 0)
            	System.out.println("======= End of List of All Enquiry =======\n");
            else
            	System.out.println("No enquiry available.");
        }
        return count;
    }





}
