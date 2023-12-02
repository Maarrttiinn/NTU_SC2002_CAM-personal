package cam;
import java.io.*;
import java.util.ArrayList;

/**
  Represents a Reply created by Committee or Staff.
  A Committee or Staff can make reply to enquiries of the camp they belong / created.
  @author Tan Shirley
  @version 1.0
  @since 25-11-2023
 */

public class Reply extends Feedback {

    /**
    * The enquiry index
    */
    public static int COUNT_REPLY;

   /**
    * The enquiry ID of this Reply
    */
    private int enquiryID;
	
    /**
     * The list of all replies.
    */
    private static ArrayList<Reply> allReply = new ArrayList<Reply>();
	
     /**
     * Creates a new Reply with enquiryID, userID and reply text.
     * @param enquiryID The enquiry ID
     * @param userName The userID who create this Enquiry
     * @param text The enquiry text
     */
    public Reply(int enquiryID, String fbType, String userName, String text) {
        super(COUNT_REPLY, "reply", text, userName);
        setID(COUNT_REPLY);
        this.enquiryID = enquiryID;
        COUNT_REPLY += 1;
    }
	
    /**
    * Creates a new Reply with enquiryID, userID and reply text.
    * @param enquiryID The enquiry ID
    * @param userName The userID who create this Enquiry
    * @param text The enquiry text
    */
    public Reply(int enquiryID, String userName, String text) {
        super(COUNT_REPLY, "reply", text, userName);
        setID(COUNT_REPLY);
        this.enquiryID = enquiryID;
        COUNT_REPLY += 1;
    }
	
    /** 
    * Creates a new Reply for each Reply read from reply.txt
    * @param id The reply ID
    * @param enquiryID The enquiryID of this reply
    * @param userName The userID who create this Reply
    * @param text The reply text
    */
    public Reply(int id, int enquiryID, String userName, String text) {
        super(id, "reply", text, userName);
        setID(id);
        this.enquiryID = enquiryID;
    }

    /** 
     * Retrieve enquiry ID of  this Reply
     * @return String enquiryID
     */
    public int getEnquiryID()
    {
        return enquiryID;
    }

    /** 
     * Set reply index for reply ID.
     * @param rLastIndex reply index for id.
     */
    public static void setCounter(int rLastIndex) {
        COUNT_REPLY = rLastIndex+1;
    }

    /** 
     * Retrieve all replies created.
     * @return ArrayList<Reply> a list of all replies.
     */
    public static ArrayList<Reply> getAllReply() {
		return allReply;
	}
	
    /** 
     * Retrieve a Reply by enquiry ID.
     * @param id The enquiryID of the reply
     * @return Reply object.
     */
	public static Reply findReply(int id)
	{
		for (Reply reply : allReply)
		{
			if(reply.getEnquiryID() == id )
			{
				return reply;
			}
		}
		return null;
	}

    /** 
     * Pass new reply to feedbackRW to write into reply text file
     * @param reply The new reply.
     */
    public static void addReply(Reply reply) throws IOException {  
        feedbackRW.writeReply(reply);
    }


    /** 
     * Retrieve all enquiry Reply based on enquiryID
     * @param enquiryID The enquiryID 
     */
    public static void viewReply(int enquiryID){
        if (allReply.size() == 0) {
			System.out.println("No reply available.");
		} 
		else 
        {
            for (Reply reply : Reply.allReply) 
            {
                if(reply.getEnquiryID() == enquiryID)
                {
                    System.out.println("------------------------------------");
                    System.out.println("Reply ID: " + reply.getID());
                    System.out.println("Enquiry ID: " + reply.getEnquiryID());
                    System.out.println("Made by: " + reply.getName());
                    System.out.println("Reply: " + reply.getText());
                    System.out.println("------------------------------------\n");
                }
            }
        }
    }




}
