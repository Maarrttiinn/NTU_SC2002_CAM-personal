package cam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
  Represents the Camp Information of a Camp.
  @author Sruthi (Version 1.0)
  @author Low Kar Choon (Version 1.1)
  @version 1.1
  @since 25-11-2023
 */
public class CampInfo {
	/**
	 * The ID of a Camp.
	 */
    private String campID;
    
    /**
     * The Name of a Camp.
     */
    private String campName;
    
    /**
     * The Location of a Camp.
     */
    private String campLocation;
    
    /**
     * The Start Date of a Camp.
     */
    private LocalDate campStartDate;
    
    /**
     * The End Date of a Camp.
     */
    private LocalDate campEndDate;
    
    /**
     * The Registration Closing Date of a Camp.
     */
    private LocalDate campRegistrationClosingDate;
    
    /**
     * The maximum Attendee slots of a Camp.
     */
    private int campMaxAttending;    
    
    /**
     * The maximum Committee slots of a Camp.
     */
    private int campMaxCommittee;
    
    /**
     * The Description of a Camp.
     */
    private String campDescription;
    
    /**
     * The Staff that created the Camp.
     */
    private String campStaff;
    
    /**
     * The Faculty that the Camp is for.
     */
    private Faculty campUserGroup;
    
    //Empty Constructor
    public CampInfo(){}

    /**
     * Creates a CampInfo with all the attributes less Camp ID because Camp ID = Camp Name.
     * @param campName The Name of the Camp.
     * @param campLocation The Location of the Camp.
     * @param campStartDate The Start Date of the Camp.
     * @param campEndDate The End Date of the Camp.
     * @param campRegistrationClosingDate The Registration Closing Date of the Camp.
     * @param campMaxAttending The maximum Attendee slots of the Camp.
     * @param campMaxCommittee The maximum Committee slots of the Camp.
     * @param campDescription The Description of the Camp.
     * @param campStaff The Staff creating the Camp.
     * @param campUserGroup The Faculty that the Camp is for.
     */
    public CampInfo(String campName, String campLocation, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationClosingDate, int campMaxAttending, int campMaxCommittee, String campDescription, String campStaff, Faculty campUserGroup){
        this.campID = campName;
        this.campName = campName;
        this.campLocation = campLocation;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationClosingDate = campRegistrationClosingDate;
        this.campMaxAttending = campMaxAttending;
        this.campMaxCommittee = campMaxCommittee;
        this.campDescription = campDescription;
        this.campStaff = campStaff;
        this.campUserGroup = campUserGroup;

    }

    /**
     * Gets the Camp ID of this Camp.
     * @return The Camp ID of this Camp.
     */
    public String getCampID() {
        return campID;
    }
    
    /**
     * Sets the Camp ID of this Camp.
     * @param campID The new Camp ID value.
     */
    public void setCampID(String campID) {
        this.campID = campID;
    }

    /**
     * Gets the Name of this Camp.
     * @return The Name of this Camp.
     */
    public String getCampName() {
        return campName;
    }
    
    /**
     * Sets the Name of this Camp.
     * @param campName The new Name of this Camp.
     * @return If the Name was set successfully.
     */
    public boolean setCampName(String campName) {
    	if (campName.isBlank()) {
    		System.out.println("Camp Name cannot be empty. Please try again.");
    		return false;
    	}
    	else if (!Camp.checkDuplicateCampName(campName)) {
    		this.campName = campName;
    		this.campID = campName;
    		System.out.println("Camp Name has been updated to '" + this.campName + "'");
    		return true;
    	}
    	else {
    		System.out.println("Duplicate Camp Name found. Please try another name.");
    		return false;
    	}
    }

    /**
     * Gets the Location of this Camp.
     * @return The Location of this Camp.
     */
    public String getCampLocation() {
        return campLocation;
    }
    
    /**
     * Sets the Location of this Camp.
     * @param campLocation The new Location of this Camp.
     * @return If the Location was set successfully.
     */
    public boolean setCampLocation(String campLocation) {
    	if (campLocation.isBlank()) {
    		System.out.println("Camp Location cannot be empty. Please try again.");
    		return false;
    	}
    	else {
    		this.campLocation = campLocation;
    		System.out.println("Camp Location has been updated to '" + this.campLocation + "'");
    		return true;
    	}
    }

    /**
     * Gets the Start Date of this Camp.
     * @return The Start Date of this Camp.
     */
    public LocalDate getCampStartDate() {
        return campStartDate;
    }
    
    /**
     * Sets the Start Date of this Camp based on the following conditions.
     * 1) New Start Date is later than today's date.
     * 2) New Start Date is not before Registration Closing Date.
     * @return If the Start Date was set successfully.
     */
    public boolean setCampStartDate(String campStartDateInput) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate newCampStartDate = LocalDate.now();
    	if (campStartDateInput.isBlank()) {
    		System.out.println("Camp Start Date cannot be empty. Please try again.");
    		return false;
    	}
    	else {
    		try {
    			newCampStartDate = LocalDate.parse(campStartDateInput, formatter);
    			// if startdate before today's date
    			if (newCampStartDate.isBefore(LocalDate.now())) {
    				System.out.println("Camp Start Date cannot be earlier than today's date. Please try again.");
    				return false;
    			}
    			// if startdate is before reg closing date
    			else if (newCampStartDate.isBefore(campRegistrationClosingDate)) {
    				System.out.println("Camp Start Date cannot be earlier than Camp Registration Closing Date '" + campRegistrationClosingDate.format(formatter) + "'. Please try again.");
    				return false;
    			}
    			else {
    				this.campStartDate = newCampStartDate;
    				System.out.println("Camp Start Date has been updated to '" + this.campStartDate.format(formatter) + "'" );
    				return true;
    			}
    		}
    		catch (Exception ex) {
    			System.out.println("Invalid Camp Start Date. Please try again.");
    			return false;
    		}
    	}
    }
    
   /**
    * Gets the End Date of this Camp.
    * @return The End Date of this Camp.
    */
    public LocalDate getCampEndDate() {
        return campEndDate;
    }
    
    /**
     * Sets the End Date of this Camp based on the following conditions.
     * 1) New End Date is not empty.
     * 2) New End Date is not before Start Date.
     * 3) New End Date is not before Registration Closing Date.
     * @param campEndDateInput The new End Date of this Camp.
     * @return If the End Date was set successfully.
     */
    public boolean setCampEndDate(String campEndDateInput) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate newCampEndDate = LocalDate.now();
    	
    	if (campEndDateInput.isBlank()) {
    		System.out.println("Camp End Date cannot be empty. Please try again.");
    		return false;
    	}
    	
    	else {    	
    		try {
    			newCampEndDate = LocalDate.parse(campEndDateInput, formatter);
    			// if enddate before startdate
    			if (newCampEndDate.isBefore(campStartDate)) {
    				System.out.println("Camp End Date cannot be earlier than Camp Start Date '" + campStartDate.format(formatter) + "'. Please try again.");
    				return false;
    			}

    			// if enddate is before reg closing date
    			else if (newCampEndDate.isBefore(campRegistrationClosingDate)) {
    				System.out.println("Camp End Date cannot be earlier than Camp Registration Closing Date '" + campRegistrationClosingDate.format(formatter) + "'. Please try again.");
    				return false;
    			}
    			else {
    				this.campEndDate = newCampEndDate;
    				System.out.println("Camp End Date has been updated to '" + this.campEndDate.format(formatter) + "'" );
    				return true;
    			}
    		}
    		catch(Exception ex) {
    			System.out.println("Invalid Camp End Date. Please try again.");
    			return false;
    		}
    	}
    }

    /**
     * Gets the Registration Closing Date of this Camp.
     * @return The Registration Closing Date of this Camp.
     */
    public LocalDate getCampRegistrationClosingDate() {
        return campRegistrationClosingDate;
    }
    
    /**
     * Sets the Registration Closing Date of this Camp based on the following conditions.
     * 1) New Registration Closing Date is not empty.
     * 2) New Registration Closing Date is not after Start Date.
     * 3) New Registration Closing Date is not after End Date.
     * 4) New Registration Closing Date is not before today's date.
     * @param campRegClosingDateInput The new Registration Closing Date for this Camp.
     * @return If the Registration Closing Date was set successfully.
     */
    public boolean setCampRegistrationClosingDate(String campRegClosingDateInput) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate newCampRegClosingDate = LocalDate.now();

    	if (campRegClosingDateInput.isBlank()) {
    		System.out.println("Camp Registration Closing Date cannot be empty. Please try again.");
    		return false;
    	}
    	else {    	
    		try {
    			newCampRegClosingDate = LocalDate.parse(campRegClosingDateInput, formatter);
    			// if closing date before start date
    			if (newCampRegClosingDate.isAfter(campStartDate))
    			{
    				System.out.println("Camp Registration Closing Date cannot be later than the Camp Start Date '" + campStartDate.format(formatter) + "'. Please try again.");
    				return false;
    			}
    			else if (newCampRegClosingDate.isAfter(campEndDate)) {
					System.out.println("Camp Registration Closing Date cannot be later than the Camp End Date'" + campEndDate.format(formatter) + "'. Please try again.");
					return false;
    			}
    			// if closing date before today's date
    			else if (newCampRegClosingDate.isBefore(LocalDate.now())) {
    				System.out.println("Camp Registration Closing Date cannot be earlier than today's date. Please try again.");
    				return false;
    			}
    			else {
    				this.campRegistrationClosingDate = newCampRegClosingDate;
    				System.out.println("Camp Registration Closing Date has been updated to '" + this.campRegistrationClosingDate.format(formatter) + "'" );
    				return true;
    			}
    		}
    		catch (Exception ex) {
    			System.out.println("Invalid Camp Registration Closing Date. Please try again.");
    			return false;
    		}
    	}}

    /**
     * Gets the maximum Attendee slots for this Camp.
     * @return the maximum Attendee slots for this Camp.
     */
    public int getCampMaxAttending() {
        return campMaxAttending;
    }
    
    /**
     * Sets the maximum Attendee slots for this Camp based on the following conditions.
     * 1) New Max Attendee slots is not empty.
     * 2) Current number of Attendees is not more than New Max Attendee slots.
     * @param campMaxAttendeeInput New Maximum Attendee slots for this Camp.
     * @param currentNumberOfAttendees Current number of Attendees for this Camp.
     * @return If the maximum Attendee slots for this camp was set successfully.
     */
    public boolean setCampMaxAttending(String campMaxAttendeeInput, int currentNumberOfAttendees) {    	
    	if (campMaxAttendeeInput.isBlank()) {
    		System.out.println("Camp Max Attendees cannot be empty. Please try again.");
    		return false;
    	}
    	else {
    		try {
    			if (currentNumberOfAttendees <= Integer.parseInt(campMaxAttendeeInput)) {
    				this.campMaxAttending = Integer.parseInt(campMaxAttendeeInput);
    				System.out.println("New Max Attendees Capacity has been updated to '" + this.campMaxAttending + "'");
    				return true;
    			}
    			else {
    				System.out.println("New Max Attendees Capacity cannot be less than current number of attendees (" + currentNumberOfAttendees +"). Please try again.");
    				return false;
    			}
    		}
    		catch (Exception ex) {
    			System.out.println("Invalid Camp Max number of Attendees. Please try again.");
    			return false;
    		}
    	}
    }

    /**
     * Gets the maximum Committee slots for this Camp.
     * @return The maximum Committee slots for this Camp.
     */
    public int getCampMaxCommittee() {
        return campMaxCommittee;
    }
    
    /**
     * Sets the maximum Committee slots for this Camp based on the following conditions.
     * 1) New Max Committee slots is not empty.
     * 2) Current number of Committees is not more than New Max Committee slots.
     * @param campMaxCommitteeInput New Maximum Committee slots for this Camp.
     * @param currentNumberOfCommittees Current number of Committees for this Camp.
     * @return If the maximum Committee slots for this camp was set successfully.
     */
    public boolean setCampMaxCommittee(String campMaxCommitteeInput, int currentNumberOfCommittees) {
    	if (campMaxCommitteeInput.isBlank()) {
    		System.out.println("Camp Max Committees cannot be empty. Please try again.");
    		return false;
    	}
    	else {
    		try {
    			if (currentNumberOfCommittees <= Integer.parseInt(campMaxCommitteeInput)) {
    				this.campMaxCommittee = Integer.parseInt(campMaxCommitteeInput);
    				System.out.println("New Max Committees Capacity has been updated to '" + this.campMaxCommittee + "'");
    				return true;
    			}
    			else {
    				System.out.println("New Max Committees Capacity cannot be less than current number of committees (" + currentNumberOfCommittees +"). Please try again.");
    				return false;
    			}
    		}
    		catch (Exception ex) {
    			System.out.println("Invalid Camp Max number of Committees. Please try again.");
    			return false;
    		}
    	}
    }

    /**
     * Gets the Description of this Camp.
     * @return The Description of this Camp.
     */
    public String getCampDescription() {
        return campDescription;
    }
    
    /**
     * Sets the Description of this Camp.
     * @param campDescription The new Description for this Camp.
     * @return If the new Description was set successfully.
     */
    public boolean setCampDescription(String campDescription) {
    	if (campDescription.isBlank()) {
    		System.out.println("Camp Description cannot be empty. Please try again.");
    		return false;
    	}
    	else {
    		this.campDescription = campDescription;
    		System.out.println("Camp Description has been updated to '" + this.campDescription + "'");
    		return true;
    	}
    }

    /**
     * Gets the Staff ID who created this Camp.
     * @return The Staff ID who created this Camp.
     */
    public String getCampStaff() {
        return campStaff;
    }
    
    /**
     * Sets the Staff ID
     * @param campStaff The new Staff ID
     */
    public void setCampStaff(String campStaff) {
        this.campStaff = campStaff;
    }

    /**
     * Gets the Faculty that this Camp is available to.
     * @return The Faculty that this Camp is available to.
     */
    public Faculty getCampUserGroup() {
        return campUserGroup;
    }
    
    /**
     * Sets the Faculty that this Camp is available to.
     * @param campUserGroup The new Faculty that this Camp is available to.
     */
    public void setCampUserGroup(Faculty campUserGroup) {
        this.campUserGroup = campUserGroup;
    }
}
