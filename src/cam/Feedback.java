package cam;

/**
  Represents an abstract Feedback base class.
  @author Tan Shirley
  @version 1.0
  @since 25-11-2023
 */

public abstract class Feedback {

    /**
	 * The feedback id
	 */
    protected int id;

    /**
	 * The feedback text
	 */
    protected String text;

    /**
	 * The feedback type
	 */
    private String fbType;

    /**
	 * The userID
	 */
    protected String userName;

    
    /**
	 * Creates a new Feedback with id, feedback type and feedback text and user ID
     * @param id The feedback ID
	 * @param fbType The type of feedback - enquiry, suggestion, reply
     * @param text The feedback text
	 * @param userName The  userID who create this Feedback
	 */
    public Feedback(int id, String fbType, String text, String userName) {
        this.id = id;
        this.text = text;
        this.fbType = fbType;
        this.userName = userName;
    }

    /** 
     * Set id of this Feedback
     * @param id id of feedback
     */
    public void setID(int id) {
        this.id = id;
    }

    /** 
     * Retrieve id of this Feedback
     * @return int id 
     */
    public int getID() {
        return id;
    }

    /** 
     * Retrieve user ID of this Feedback
     * @return String userName 
     */
    public String getName() {
        return userName;
    }

    /** 
     * Retrieve type of this Feedback
     * @return String type 
     */
    public String getType() {
        return fbType;
    }

    /** 
     * Retrieve text of this Feedback
     * @return String text  
     */
    public String getText() {
        return text;
    }

    /** 
     * Set text of this Feedback
     * @param text text of enquiry
     */
    public void setText(String text) {
        this.text = text;
    }

}
