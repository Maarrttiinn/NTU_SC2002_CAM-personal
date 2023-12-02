package cam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
  Represents a Camp created by one staff.
  A Student can join as Attendee or Committee.
  @author Low Kar Choon
  @version 1.0
  @since 25-11-2023
 */
public class Camp {
	/**
	 * Stores all the camps that have been created.
	 */
	private static ArrayList<Camp> allCamps = new ArrayList<Camp>();

	/**
	 * The camp information of a camp. One camp has one camp information.
	 */
	private CampInfo campInfo;
	
	/**
	 * Stores the status if the camp has reached maximum number of committees.
	 */
	private boolean isFullCommittees;
	
	/**
	 * Stores the status if the camp has reached maximum number of attendees.
	 */
	private boolean isFullAttendees;
	
	/**
	 * The visibility of this Camp.
	 */
	private boolean isVisible;
	
	/**
	 * Stores all the committees of this Camp.
	 */
	private ArrayList<StudentAcc> campCommittees;
	
	/**
	 * Stores all the attendees attending this Camp.
	 */
	private ArrayList<StudentAcc> campAttendees;

	/**
	 * Creates a new Camp with campInfo and specified visibility.
	 * Assumes there are no attendees and committees at the time of creation.
	 * campInfo must be created first.
	 * @param campInfo The Camp Information of the Camp
	 * @param isVisible The Visibility of the Camp
	 */
	public Camp(CampInfo campInfo, Boolean isVisible) {
		this.campInfo = campInfo;
		this.isVisible = isVisible;
		this.campAttendees = new ArrayList<StudentAcc>();
		this.campCommittees = new ArrayList<StudentAcc>();
		this.isFullCommittees = (this.campInfo.getCampMaxCommittee() <= this.campCommittees.size());
		this.isFullAttendees = (this.campInfo.getCampMaxAttending() <= this.campAttendees.size());
	}

	/**
	 * Creates a new Camp with campInfo, specified visibility and existing list of committees and attendees.
	 * Assumes there is an existing list of committees and attendees at the time of creation.
	 * @param campInfo The Camp Information of the Camp
	 * @param isVisible The Visibility of the Camp
	 * @param campCommittees The Committees of the Camp
	 * @param campAttendees The Attendees of the Camp
	 */
	public Camp(CampInfo campInfo, Boolean isVisible, ArrayList<StudentAcc> campCommittees, ArrayList<StudentAcc> campAttendees) {
		this.campInfo = campInfo;
		this.isFullCommittees = (this.campInfo.getCampMaxCommittee() <= this.campCommittees.size());
		this.isFullAttendees = (this.campInfo.getCampMaxAttending() <= this.campAttendees.size());
		this.isVisible = isVisible;
		//if (!campCommittees.isEmpty())
			this.campCommittees = campCommittees;
		//if (!campAttendees.isEmpty())
			this.campAttendees = campAttendees;
	}

	/**
	 * Creates a new Camp for each Camp read from camp.txt
	 * @param campInfo The Camp Information of the Camp
	 * @param isFullCommittees Stores the status if the camp has reached maximum number of committees.
	 * @param isFullAttendees Stores the status if the camp has reached maximum number of attendees.
	 * @param isVisible The Visibility of the Camp
	 * @param campComp The Committees of the Camp
	 * @param campAttendees The Attendees of the Camp
	 */
	public Camp(CampInfo campInfo,Boolean isFullCommittees,Boolean isFullAttendees, Boolean isVisible, ArrayList<StudentAcc> campComp, ArrayList<StudentAcc> campAttendees)
	{
		this.campInfo = campInfo;
		this.isFullCommittees = isFullCommittees;
		this.isFullAttendees = isFullAttendees;
		this.isVisible = isVisible;
		this.campCommittees =campComp;
		this.campAttendees = campAttendees;
	}
	
	/**
	 * Method to get all the Camps created.
	 * @return private static ArrayList<Camp> allCamps.
	 */
	public static ArrayList<Camp> getAllCamps() {
		return allCamps;
	}
	
	/**
	 * Method to retrieve a Camp Object by it's Camp ID.
	 * @param campID ID of a Camp.
	 * @return Camp Object.
	 */
	public static Camp getCampByCampID(String campID) {
		for (Camp camp : allCamps) {
			if (camp.getCampInfo().getCampID().equals(campID))
				return camp;
		}
		return null;
	}

	/**
	 * Displays all the camps and their respective information that have been created with no filter.
	 * Used in StaffAcc.java.
	 */
	public static void displayAllCamps() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (allCamps.size() == 0) {
			System.out.println("No camps available.");
		} 
		else {
			System.out.println("======= List of All Camps =======");
			allCamps.forEach((camp) -> {
				System.out.println("------------------------------------");
				System.out.println("Camp ID: " + camp.getCampInfo().getCampID());
				System.out.println("Camp Name: " + camp.getCampInfo().getCampName());
				System.out.println("Camp Location: " + camp.getCampInfo().getCampLocation());
				System.out.println("Visibility: " + (camp.getVisibility() ? "Visible" : "Not Visible"));
				System.out.println("Start Date: " + camp.getCampInfo().getCampStartDate().format(formatter));
				System.out.println("End Date: " + camp.getCampInfo().getCampEndDate().format(formatter));
				System.out.println("Registration Closing Date: " + camp.getCampInfo().getCampRegistrationClosingDate().format(formatter));
				System.out.println("Attendees Capacity: " + camp.getCampAttendees().size() + "/" + camp.getCampInfo().getCampMaxAttending());
				System.out.println("Committees Capacity: " + camp.getCampCommittees().size() + "/" + camp.getCampInfo().getCampMaxCommittee());
				System.out.println("Description: " + camp.getCampInfo().getCampDescription());
				System.out.println("Faculty: " + camp.getCampInfo().getCampUserGroup().getFacultyType());
				System.out.println("------------------------------------\n");
			});
			System.out.println("======= End of List of All Camps =======\n");
		}
	}

	/**
	 * Displays all the camps and their information based on the following conditions.
	 * 1) Camp Visibility set to true.
	 * 2) Camp Registration Closing Date is not earlier than today's date.
	 * 3) Camp Faculty is same as the currently logged in student's faculty or Camp Faculty is set to 'NTU'.
	 * 4) The currently logged in student has not withdrawn from the Camp.
	 * 5) The currently logged in student has not registered for the Camp as Attendee or Committee.
	 * 6) There is vacancy for the currently logged in student to join as Attendee or Committee.
	 * @param student The student who is currently logged in
	 * @return the total number of camps that is displayed.
	 */
	public static int displayAllCamps(StudentAcc student) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		int campNo = 1;
		for (Camp camp : Camp.allCamps) {
			// if camp is visible, registration closing date is later than today, same
			// faculty as student or faculty = NTU, student is not a committee of this camp
			// if camp is visible
			if (camp.getVisibility()
					// if camp registration closing date is not earlier than today
					&& (camp.getCampInfo().getCampRegistrationClosingDate().isAfter(LocalDate.now().minusDays(1)))
					// if camp faculty == student's faculty
					&& (camp.getCampInfo().getCampUserGroup() == student.getFaculty()
					// if camp faculty == "NTU"
					|| camp.getCampInfo().getCampUserGroup() == Faculty.DEPARTMENT6)
					// if student has not withdrawn from this camp before
					&& (!student.getWithdrawnCamps().contains(camp))
					// if student has not registered for this camp
					&& (!student.getRegisteredCamps().contains(camp))
					// both committees and attendees capacity not reached
					&& (!camp.isFullAttendees || !camp.isFullCommittees)) {

				// if getCommitteeOfCamp is not null
				if (student.getCommitteeOfCamp() != null)
					// if student is a committee of this camp
					if (camp.getCampInfo().getCampID().equals(student.getCommitteeOfCamp().getCampInfo().getCampID()))
						// skip this iteration
						continue;

				if (campNo == 1)
					System.out.println("======= List of Camps Available =======");

				System.out.println("------------------------------------");
				System.out.println("Camp ID: " + camp.getCampInfo().getCampID());
				System.out.println("Camp Name: " + camp.getCampInfo().getCampName());
				System.out.println("Camp Location: " + camp.getCampInfo().getCampLocation());
				System.out.println("Start Date: " + camp.getCampInfo().getCampStartDate().format(formatter));
				System.out.println("End Date: " + camp.getCampInfo().getCampEndDate().format(formatter));
				System.out.println("Registration Closing Date: "
						+ camp.getCampInfo().getCampRegistrationClosingDate().format(formatter));
				System.out.println("Attendees Capacity: " + camp.getCampAttendees().size() + "/" + camp.getCampInfo().getCampMaxAttending());
				System.out.println("Committees Capacity: " + camp.getCampCommittees().size() + "/" + camp.getCampInfo().getCampMaxCommittee());
				System.out.println("Description: " + camp.getCampInfo().getCampDescription());
				System.out.println("Faculty: " + camp.getCampInfo().getCampUserGroup().getFacultyType());
				System.out.println("------------------------------------\n");
				campNo++;
			}
		}
		if (campNo == 1)
			System.out.println("No camps available.");
		else if (campNo != 1)
			System.out.println("======= End of List of Camps Available =======\n");
		return campNo;
	}

	/**
	 * Displays all the camps created by a Staff.
	 * @param staff The Staff requesting to display all the camps he has created.
	 */
	public static void displayAllCamps(StaffAcc staff) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (staff.getAllCampsCreated().size() == 0) {
			System.out.println("You have not created any camps.");
			return;
		} else {
			System.out.println("======= Camps you have created =======");
			// Camp Number is used for editing and removal of Camp, different from Camp ID
			for (Camp camp : staff.getAllCampsCreated()) {
				System.out.println("------------------------------------");
				System.out.println("Camp ID: " + camp.getCampInfo().getCampID());
				System.out.println("Camp Name: " + camp.getCampInfo().getCampName());
				System.out.println("Camp Location: " + camp.getCampInfo().getCampLocation());
				System.out.println("Visibility: " + (camp.getVisibility() ? "Visible" : "Not Visible"));
				System.out.println("Start Date: " + camp.getCampInfo().getCampStartDate().format(formatter));
				System.out.println("End Date: " + camp.getCampInfo().getCampEndDate().format(formatter));
				System.out.println("Registration Closing Date: " + camp.getCampInfo().getCampRegistrationClosingDate().format(formatter));
				System.out.println("Attendees Capacity: " + camp.getCampAttendees().size() + "/" + camp.getCampInfo().getCampMaxAttending());
				System.out.println("Committees Capacity: " + camp.getCampCommittees().size() + "/" + camp.getCampInfo().getCampMaxCommittee());
				System.out.println("Description: " + camp.getCampInfo().getCampDescription());
				System.out.println("Faculty: " + camp.getCampInfo().getCampUserGroup().getFacultyType());
				System.out.println("------------------------------------\n");
			}
			System.out.println("======= End of Camps you have created =======\n");
		}
	}

	/**
	 * Gets the Visibility of this Camp
	 * @return visibility of this Camp.
	 */
	public boolean getVisibility() {
		return isVisible;
	}

	/**
	 * Sets the Visibility of this Camp.
	 * Only allowed when this Camp has no Attendees and Committees.
	 * @param isVisibleInput The new Visibility value.
	 * @param staff The Staff requesting for the change.
	 * @return If the update is successfully.
	 */
	public boolean setVisibility(String isVisibleInput, StaffAcc staff) {
		if (isVisibleInput.isBlank()) {
			System.out.println("Camp Visibility cannot be empty. Please try again.");
			return false;
		}
		else {
			// if this staff is in charged of this camp, and no attendees and committees yet, source: qna pdf
			if (staff.getAllCampsCreated().contains(this) && campAttendees.size() == 0 && campCommittees.size() == 0) {
				if (!isVisibleInput.equalsIgnoreCase("true") && !isVisibleInput.equalsIgnoreCase("false")) {
					System.out.println("Invalid Camp Visibility. Please try again.");
					return false;
				}
				else {
					System.out.println("Camp " +campInfo.getCampName() + " visiblity updated to '" + this.isVisible + "'");
					return true;
				}
			} 
			else {
				System.out.println("Not allowed to change visibility of Camp " + campInfo.getCampName() + " as there are attendees and committees.");
				return true;
			}
		}
	}

	/**
	 * Gets the status if the camp has reached maximum number of attendees.
	 * @return The status if the camp has reached maximum number of attendees.
	 */
	public boolean getIsFullAttendees() {
		return isFullAttendees;
	}

	/**
	 * Gets the status if the camp has reached maximum number of committees.
	 * @return The status if the camp has reached maximum number of committees.
	 */
	public boolean getIsFullCommittees() {
		return isFullCommittees;
	}

	/**
	 * Gets all the Committees of this Camp.
	 * @return ArrayList of all Committees of this Camp.
	 */
	public ArrayList<StudentAcc> getCampCommittees() {
		return campCommittees;
	}

	/**
	 * Adds a Committee to this Camp.
	 * @param newCommittee The Committee to be added.
	 */
	public void addCampCommittee(StudentAcc newCommittee) {
		if (!isFullCommittees) {
			campCommittees.add(newCommittee);
			isFullCommittees = (campInfo.getCampMaxCommittee() == campCommittees.size());
			CampRW.UpdateCampFile(allCamps);
			System.out.println("Successfully registered '" + newCommittee.getName() + "' to Camp '" + campInfo.getCampName() + "' as Committee.");
		}
		else {
			System.out.println("No available Committee slots for Camp " + campInfo.getCampName() + ".");
		}
	}

	/**
	 * Removes a Committee from this Camp.
	 * @param committee The Committee to be removed.
	 */
	public void removeCampCommittee(StudentAcc committee) {
		//campCommittees.remove(committee);
		for(StudentAcc com:campCommittees)
		{
			if(com.getUserID().equals(committee.getUserID()))
			{
				campCommittees.remove(com);
				break;
			}
		}
		isFullCommittees = (campInfo.getCampMaxCommittee() == campCommittees.size());
		CampRW.UpdateCampFile(allCamps);
		System.out.println("Successfully removed Committee '" + committee.getName() + "' from Camp '" + campInfo.getCampName() + "'.");
	}

	/**
	 * Gets all the Attendees of this Camp.
	 * @return ArrayList of all Attendees of this Camp.
	 */
	public ArrayList<StudentAcc> getCampAttendees() {
		return campAttendees;
	}

	/**
	 * Adds a Attendee to this Camp.
	 * @param newAttendee The Attendee to be added.
	 */
	public void addCampAttendee(StudentAcc newAttendee) {
		if (!isFullAttendees) {
			campAttendees.add(newAttendee);
			isFullAttendees = (campInfo.getCampMaxAttending() == campAttendees.size());
			CampRW.UpdateCampFile(allCamps);
			System.out.println("Successfully registered '" + newAttendee.getName() + "' to Camp '" + campInfo.getCampName() + "' as Attendee.");
		}
		else {
			System.out.println("No available Attendee slots for Camp " + campInfo.getCampName() + ".");
		}
	}

	/**
	 * Removes an Attendee from this Camp.
	 * @param attendee The Attendee to be removed.
	 */
	public void removeCampAttendee(StudentAcc attendee) {
		//campAttendees.remove(attendee);
		for(StudentAcc student :campAttendees)
		{
			if(student.getUserID().equals(attendee.getUserID()))
			{
				campAttendees.remove(student);
				break;
			}
		}
		isFullAttendees = (campInfo.getCampMaxAttending() == campAttendees.size());
		CampRW.UpdateCampFile(allCamps);
		System.out.println("Successfully removed Attendee '" + attendee.getName() + "' from Camp '" + campInfo.getCampName() + "'.");
	}

	/**
	 * Gets the CampInfo object of this Camp.
	 * @return The CampInfo object of this Camp.
	 */
	public CampInfo getCampInfo() {
		return this.campInfo;
	}

	/**
	 * Checks if a Camp with the same name exists.
	 * @param campName The name of a Camp.
	 * @return if a Camp with the same name exists.
	 */
	public static boolean checkDuplicateCampName(String campName) {
		if (allCamps.size() > 0) {
			for (Camp camp : allCamps) {
				if (camp.campInfo.getCampName().equalsIgnoreCase(campName)) {
					return true;
				}
			}
		}
		return false;
	}
}