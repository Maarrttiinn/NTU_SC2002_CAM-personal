package cam;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
  Represents a Staff, derived from UserAcc.java
  @author Martin Ng (Version 1.0)
  @author Low Kar Choon (Version 1.1, 1.3)
  @author Tan Shirley (Version 1.2, 1.4)
  @version 1.4
  @since 25-11-2023
*/
public class StaffAcc extends UserAcc {
	/**
	 * List of Camps created by this Staff
	 */
	private ArrayList<Camp> campsCreated;
	
	/**
	 * List of replies made by this Staff
	 */
	private ArrayList<Reply> replies;
	
	/**
	 * Date time formatter for display and storage
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	/**
	 * Creates a new Staff
	 * @param user The user ID of this Staff
	 */
	public StaffAcc(String user) {
		super(user);
		setAccType("staff");
		information = this.getInformation(); // obtain user information
		setInformation(information);
		campsCreated = new ArrayList<Camp>();
	}

	/**
	 * Creates a new Staff with the camps and replies made by this Staff
	 * @param user The user ID of this Staff
	 * @param campsCreated The list of Camps created by this Staff
	 * @param replies The list of replies made by this Staff
	 */
	public StaffAcc(String user, ArrayList<Camp> campsCreated,  ArrayList<Reply> replies) {
		super(user);
		setAccType("staff");
		information = this.getInformation(); // obtain user information
		setInformation(information);
		this.campsCreated = campsCreated;
		this.replies = replies;
	}

	/**
	 * Prints the actions the Staff can do
	 * @return The total number of options
	 */
	public int displayOptions() // displays available options
	{
		int numberOfOptions = 8;
		System.out.println("\nList of available options: ");
		System.out.println("1. View all camps");
		System.out.println("2. Create new camp");
		// in view camps created by me, staff can edit camp if needed
		System.out.println("3. View camps created by me");
		System.out.println("4. View/Reply enquiries");
		System.out.println("5. View suggestions");
		System.out.println("6. Generate student list");
		System.out.println("7. Generate performance report");
		System.out.println("8. Back");
		System.out.println("Enter your choice:");
		return numberOfOptions;
	}

	/**
	 * Performs the actions based on the Staff's choice
	 * @param choice The choice that the Staff has selected
	 * @param sc The scanner object created initially in App.java
	 */
	public void doChoice(int choice, Scanner sc) {
		String campIDInput = "";
		int rID;
		int enqID;
		int sugID;
		String campID;
		
		switch (choice) {
		// 1. View all camps
		case 1:
			Camp.displayAllCamps();
			break;
			// 2. Create new camp
		case 2:
			createCamp(sc);
			break;
			// 3. View camps created by me
		case 3:
//			int campNoInput = -2;
			if (campsCreated.size() != 0) {
				while (!campIDInput.equalsIgnoreCase("exit")) {
					Camp.displayAllCamps(this);
					// Use Camp Number, not Camp ID, see viewAllCampsCreated() to see the Camp
					// Number displayed at the top
					System.out.println("Enter the Camp ID of a Camp to edit (Enter 'exit' to exit): ");
					campIDInput = sc.nextLine().strip();
					if (campIDInput.equalsIgnoreCase("exit"))
						return;

					int editCampDetailsInput = -99;

					editCampOptions(editCampDetailsInput, campIDInput, sc);
					
					// after return from editCampOptions, need to check again if there are any camps created by this staff left
					if (campsCreated.size() == 0)
						campIDInput = "exit";
				}
			}
			else
				Camp.displayAllCamps(this);
			break;
			// 4. View/Reply enquiries; view reply
		case 4:
			System.out.println("1) View camp enquiries \n");
			System.out.println("2) Reply camp enquiries \n");
			System.out.println("3) View Reply to enquiries \n");
			int selectiion = sc.nextInt();
			campID = sc.nextLine();	
			if (selectiion == 1)
			{
				//insert view camps
				Camp.displayAllCamps(this);
				System.out.println("All Pending Camp Enquiries:");
				do{
					System.out.println("Enter Camp ID: ");
					campID = sc.nextLine();	

					Camp camp = Camp.getCampByCampID(campID);
					camp.getCampInfo().getCampStaff();
					if(!camp.getCampInfo().getCampStaff().equals(super.getUserID()))
					{
						System.out.println("Invalid Camp ID. Please try again.");
					}

				}while(!Camp.getCampByCampID(campID).getCampInfo().getCampStaff().equals(super.getUserID()));
				if (Enquiry.viewCampEnquiry(campID,"pending") == 0)
					break;
			}
			else if(selectiion == 2)
			{
				//insert view camps
				Camp.displayAllCamps(this);
				//view camp enquiry
				do{
					System.out.println("Enter Camp ID: ");
					campID = sc.nextLine();	
					Camp camp = Camp.getCampByCampID(campID);
					camp.getCampInfo().getCampStaff();
					if(!camp.getCampInfo().getCampStaff().equals(super.getUserID()))
					{
						System.out.println("Invalid Camp ID. Please try again.");
					}
				}while(!Camp.getCampByCampID(campID).getCampInfo().getCampStaff().equals(super.getUserID()));
				if (Enquiry.viewCampEnquiry(campID,"pending") == 0)
					break;


				System.out.println("Enquiry Reply System:");
				boolean found = false;
				do{
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
					try {
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
					} catch (IOException e1) {
						e1.printStackTrace();
					}					
				}

			}
			else if (selectiion ==3)
			{
				//insert view camps
				Camp.displayAllCamps(this);

				//view camp enquiry
				do{
					System.out.println("Enter Camp ID: ");
					campID = sc.nextLine();	
					Camp camp = Camp.getCampByCampID(campID);
					camp.getCampInfo().getCampStaff();
					if(!camp.getCampInfo().getCampStaff().equals(super.getUserID()))
					{
						System.out.println("Invalid Camp ID. Please try again.");
					}
				}while(!Camp.getCampByCampID(campID).getCampInfo().getCampStaff().equals(super.getUserID()));
				if (Enquiry.viewCampEnquiry(campID,"pending") == 0)
					break;
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
			else
			{
				System.out.println("Invalid option.");
			}	
			break;
			// 5. View suggestions ; Aprrove/Reject suggestion 
		case 5:
			System.out.println("1) View camp suggestions \n");
			System.out.println("2) Accept or reject camp suggestions \n");
			int value = sc.nextInt();
			sc.nextLine();
			if (value == 1)
			{
				//insert display list of camp
				Camp.displayAllCamps(this);
				System.out.println("All Pending Camp Suggestions:");
				do{
					System.out.println("Enter Camp ID: ");
					campID = sc.nextLine();	

					Camp camp = Camp.getCampByCampID(campID);
					camp.getCampInfo().getCampStaff();
					if(!camp.getCampInfo().getCampStaff().equals(super.getUserID()))
					{
						System.out.println("Invalid Camp ID. Please try again.");
					}

				}while(!Camp.getCampByCampID(campID).getCampInfo().getCampStaff().equals(super.getUserID()));
				if (Suggestion.viewCampSuggestion(campID,"pending") == 0)
					break;
			}
			else if (value == 2)
			{
				//insert display list of camp
				Camp.displayAllCamps(this);
				System.out.println("Accept/Reject Camp Suggestion:");
				System.out.println("All Pending Camp Suggestions:");
				do{
					System.out.println("Enter Camp ID: ");
					campID = sc.nextLine();	

					Camp camp = Camp.getCampByCampID(campID);
					camp.getCampInfo().getCampStaff();
					if(!camp.getCampInfo().getCampStaff().equals(super.getUserID()))
					{
						System.out.println("Invalid Camp ID. Please try again.");
					}

				}while(!Camp.getCampByCampID(campID).getCampInfo().getCampStaff().equals(super.getUserID()));
				if (Suggestion.viewCampSuggestion(campID,"pending") == 0)
					break;
			
				boolean found = false;
				do
				{
					System.out.println("Enter Suggestion ID: ");
					sugID = sc.nextInt();
					sc.nextLine();

					if(!Suggestion.getSuggestionBySugID(sugID).getStatus().equals("pending"))
					{
						System.out.println("Suggestion has been "+ Suggestion.getSuggestionBySugID(sugID).getStatus() + "." );
						break;
					}

					if(findSuggestion(sugID, campID, "pending") == null)
					{
						System.out.println("Invalid Suggestion ID. Please try again.");
					}
					else
					{
						found = true;
					}
				}while (findSuggestion(sugID, campID, "pending") == null);

				if (found == true)
				{
					Suggestion sug = Suggestion.getSuggestionBySugID(sugID);
					System.out.println("1)Accept suggestions \n");
					System.out.println("2)Reject suggestions \n");
					int acceptSuggestion = sc.nextInt();
					if(acceptSuggestion == 1)
					{
						sug.setStatus("approved");
						Suggestion.updateSuggestion(sug);
					}
					else if (acceptSuggestion ==2 )
					{
						sug.setStatus("rejected");
						Suggestion.updateSuggestion(sug);
					}
					else
					{System.out.println("Invalid option.");}
				}
			}
			else
			{
				System.out.println("Invalid option.");
			}
			break;
				
			// 6. Generate student list
		case 6:
			System.out.println("Enter the filter(Attendee/Committee/none): ");
			String filter = sc.nextLine();
			GenerateReport.generateReport(this,filter,"Staff");
			break;
			// 7. Generate performance report
		case 7:
			GenerateReport.generatePerformanceReport(this);
			break;
			// logout
		case 8:
			break;
		}
	}

	/**
	 * Prints the actions the Staff can do if the Staff wants to edit a camp detail.
	 * @param editCampDetailsInput The choice that the Staff has selected.
	 * @param campIDInput The Camp ID of the Camp to be edited.
	 * @param sc The scanner object created initially in App.java.
	 */
	public void editCampOptions(int editCampDetailsInput, String campIDInput, Scanner sc) {
		String newCampNameInput;
		String newCampLocationInput;
		String newCampDescriptionInput;
		
		// to hold input temporarily before parsing
		String temp = "";
		
		// Choice to edit any camps staff created
		do {
			System.out.println("\nList of details to edit: ");
			System.out.println("1. Camp Name");
			System.out.println("2. Camp Location");
			System.out.println("3. Start Date");
			System.out.println("4. End Date");
			System.out.println("5. Registration Closing Date");
			System.out.println("6. Max Attendees");
			System.out.println("7. Max Committees");
			System.out.println("8. Camp Description");
			System.out.println("9. Camp Visibility");
			System.out.println("10. Remove Camp");
			System.out.println("11: Back");
			System.out.println("Enter your choice: ");
			editCampDetailsInput = sc.nextInt();

			// consume \n from enter key because nextInt() doesn't do it
			sc.nextLine();

			switch (editCampDetailsInput) {
			// edit camp name
			case 1:
				do {
					System.out.println("Current Camp Name is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampName() + "'");
					System.out.println("Enter New Camp Name: ");
					newCampNameInput = sc.nextLine().strip();
				} while(!Camp.getCampByCampID(campIDInput).getCampInfo().setCampName(newCampNameInput));
				campIDInput = newCampNameInput;
				break;
				
			// edit camp location
			case 2:
				do {
					System.out.println("Current Camp Location is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampLocation() + "'");
					System.out.println("Enter New Camp Location: ");
					newCampLocationInput = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampLocation(newCampLocationInput));						
				break;
				
			// edit start date
			case 3:
				do {
				System.out.println("Current Camp Start Date is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampStartDate().format(formatter) + "'");
				System.out.println("Enter New Camp Start Date (e.g. " + LocalDate.now().format(formatter) + "): ");
				temp = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampStartDate(temp));
				break;
				
			// edit end date
			case 4:
				do {
					System.out.println("Current Camp End Date is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampEndDate().format(formatter) + "'");
					System.out.println("Enter New Camp End Date (e.g. " + LocalDate.now().format(formatter) + "): ");
					temp = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampEndDate(temp));
				break;
				
			// edit reg closing date
			case 5:
				do {
				System.out.println("Current Camp Registration Closing Date is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampRegistrationClosingDate().format(formatter) + "'");
				System.out.println("Enter New Camp Registration Closing Date (e.g. " + LocalDate.now().format(formatter) + "): ");
				temp = sc.nextLine().strip();
				
				} while(!Camp.getCampByCampID(campIDInput).getCampInfo().setCampRegistrationClosingDate(temp));
				break;
			
			// edit max attendees
			case 6:
				do {
				System.out.println("Current Camp Max Attendees Capacity is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampMaxAttending() + "'");
				System.out.println("Enter New Max Attendees Capacity: ");
				temp = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampMaxAttending(temp, Camp.getCampByCampID(campIDInput).getCampAttendees().size()));
				break;
				
			// edit max committees
			case 7:
				do {
					System.out.println("Current Camp Max Committees Capacity is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampMaxCommittee()+ "'");
					System.out.println("Enter New Max Commmittees Capacity: ");
					temp = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampMaxCommittee(temp, Camp.getCampByCampID(campIDInput).getCampCommittees().size()));
				break;
				
			// edit camp desc
			case 8:
				do {
					System.out.println("Current Camp Description is '" + Camp.getCampByCampID(campIDInput).getCampInfo().getCampDescription() + "'");
					System.out.println("Enter New Camp Description: ");
					newCampDescriptionInput = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).getCampInfo().setCampDescription(newCampDescriptionInput));						
				break;
				
			// edit camp visibility
			case 9:
				do {
				System.out.println("Enter New Camp Visibility (True/False): ");
				temp = sc.nextLine().strip();
				} while (!Camp.getCampByCampID(campIDInput).setVisibility(temp, this));
				break;
				
			// remove camp
			case 10:
				removeCamp(Camp.getCampByCampID(campIDInput));
				editCampDetailsInput = 11;
				break;
				
			// cancel
			case 11:
				campIDInput = "exit";
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				System.out.println("Enter your choice ");
				editCampDetailsInput = sc.nextInt();

				// consume \n from enter key because nextInt() doesn't do it
				sc.nextLine();
			}
		} while (editCampDetailsInput != 11);
	}

      /** 
     	* Get a Enquiry based on Enquiry ID
     	* @param enqID Enquiry ID of a enquiry
     	* @param campID The camp ID of the enquiry
     	* @param status The status of enquiry
     	* @return Enquiry The enquiry of the camp the Staff has created.
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
 	     * Get a Suggestion based on Suggestion ID
	     * @param sugID Suggestion ID of a suggestion
     	     * @param campID The camp ID of the suggestion
	     * @param status Status of suggestion
	     * @return Suggestion The suggestion of the camp the Staff has created.
	*/
    	public Suggestion findSuggestion(int sugID, String campID, String status)
	{
		for (Suggestion s: Suggestion.getAllSuggestion())
		{
			if(s.getID() == sugID && s.getCampID().equals(campID) && s.getStatus().equals(status))
			{
				return s;
			}
		}
		return null;
	}

	/* Camp Related Methods */

	/**
	 * Gets all the camps created by this Staff.
	 * @return List of all the camps created by this Staff.
	 */
	public ArrayList<Camp> getAllCampsCreated() {
		return this.campsCreated;
	}

	/**
	 * Removes all the camps created by this Staff.
	 */
	private void removeAllCampsCreated() {
		this.campsCreated.clear();
	}

	/**
	 * Creates a camp under this Staff
	 * @param sc Scanner object initialised in App.java
	 */
	private void createCamp(Scanner sc) {
		String campNameInput;
		String campLocationInput;
		LocalDate campStartDateInput = LocalDate.now();
		LocalDate campEndDateInput = LocalDate.now();
		LocalDate campRegClosingDateInput = LocalDate.now();
		int campMaxAttendeeInput = 0;
		int campMaxCommitteeInput = 0;
		String campDescriptionInput;
		boolean campVisibilityInput = false;
		int campFacultyInput = 0;
		Faculty fac = this.getFaculty();
		// to hold input temporarily before parsing
		String temp = "";
		boolean invalidInput = false;
		
		// camp name input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Name:");
			campNameInput = sc.nextLine().strip();
			if (Camp.checkDuplicateCampName(campNameInput))
				System.out.println("Duplicate Camp Name found. Please try another name.");
			else if(campNameInput.isBlank())
				System.out.println("Camp Name cannot be empty. Please try again.");
			else
				invalidInput = false;
		} while (invalidInput);

		// camp location input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Location:");
			campLocationInput = sc.nextLine().strip();
			if (campLocationInput.isBlank())
				System.out.println("Camp Location cannot be empty. Please try again.");
			else
				invalidInput = false;
		} while(invalidInput);

		// camp start date input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Start Date (e.g. " + LocalDate.now().format(formatter) + "): ");
			temp = sc.nextLine().strip();
			if (temp.isBlank()) {
				System.out.println("Camp Start Date cannot be empty. Please try again.");
			}
			else {
				try {
				campStartDateInput = LocalDate.parse(temp, formatter);
				if (campStartDateInput.isBefore(LocalDate.now()))
					System.out.println("Camp Start Date cannot be earlier than today's date. Please try again.");
				else
					invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp Start Date. Please try again.");
				}
			}
		} while (invalidInput);

		// camp end date input
		do {
			invalidInput = true;
			System.out.println("Enter Camp End Date (e.g. " + LocalDate.now().format(formatter) + "): ");
			temp = sc.nextLine().strip();
			if (temp.isBlank()) {
				System.out.println("Camp End Date cannot be empty. Please try again.");
			}
			else {
				try {
					campEndDateInput = LocalDate.parse(temp, formatter);
					if (campEndDateInput.isBefore(campStartDateInput))
						System.out.println("Camp End Date cannot be earlier than the Camp Start Date. Please try again.");
					else
						invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp End Date. Please try again.");
				}
			}
		} while (invalidInput);

		// camp reg closing date input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Registration Closing Date (e.g. " + LocalDate.now().format(formatter) + "): ");
			temp = sc.nextLine().strip();
			if (temp.isBlank()) {
				System.out.println("Camp Start Date cannot be empty. Please try again.");
			}
			else {
				try {
					campRegClosingDateInput = LocalDate.parse(temp, formatter);
					if (campRegClosingDateInput.isAfter(campStartDateInput))
						System.out.println("Camp Registration Closing Date cannot be later than the Camp Start Date. Please try again.");
					else if (campRegClosingDateInput.isAfter(campEndDateInput))
						System.out.println("Camp Registration Closing Date cannot be later than the Camp End Date. Please try again.");
					else if (campRegClosingDateInput.isBefore(LocalDate.now())) {
	    				System.out.println("Camp Registration Closing Date cannot be earlier than today's date. Please try again.");
	    			}
					else
						invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp Registration Closing Date. Please try again.");
				}
			}
		} while (invalidInput);

		// camp max attendee input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Max Attendees: ");
			temp = sc.nextLine().strip();
			if (temp.isBlank()) {
				System.out.println("Camp Max Attendees cannot be empty. Please try again.");
			}
			else {
				try {
					campMaxAttendeeInput = Integer.parseInt(temp);
					if (campMaxAttendeeInput < 0)
						System.out.println("Camp Max Attendees cannot be less than 0. Please try again.");
					else
						invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp Max number of Attendees. Please try again.");
				}
			}
		} while (invalidInput);

		// camp max committee input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Max Committees: ");
			temp = sc.nextLine().strip();
			if (temp.isBlank()) {
				System.out.println("Camp Max Committees cannot be empty. Please try again.");
			}
			else {
				try {
					campMaxCommitteeInput = Integer.parseInt(temp);
					if (campMaxCommitteeInput < 0)
						System.out.println("Camp Max Committees cannot be less than 0. Please try again.");
					else
						invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp Max number of Committees. Please try again.");
				}
			}
		} while (invalidInput);

		// camp description input
		do {
			invalidInput = true;
			System.out.println("Enter Camp Description:");
			campDescriptionInput = sc.nextLine().strip();
			if (campDescriptionInput.isBlank())
				System.out.println("Camp Description cannot be empty. Please try again.");
			else
				invalidInput = false;
		} while(invalidInput);

		// camp visibility input
		do {
			invalidInput = true;
			System.out.println("Set Camp Visibility (True/False) : ");
			temp = sc.nextLine();
			if (temp.isBlank())
				System.out.println("Camp Visibility cannot be empty. Please try again.");
			else {
				if (!temp.equalsIgnoreCase("true") && !temp.equalsIgnoreCase("false"))
					System.out.println("Invalid Camp Visibility. Please try again.");
				else {
					campVisibilityInput = Boolean.parseBoolean(temp);
					invalidInput = false;
				}
			}
		} while (invalidInput);

		// camp faculty input
		// either create under the same faculty as staff or under NTU where every student can see
		do {
			invalidInput = true;
			System.out.println("Enter Camp Faculty (1) " + this.getFaculty().getFacultyType() + " or (2) NTU :");
			temp = sc.nextLine();
			if (temp.isBlank())
				System.out.println("Camp Faculty cannot be empty. Please try again.");
			else {
				try {
					campFacultyInput = Integer.parseInt(temp);
					if (campFacultyInput != 1 && campFacultyInput != 2)
						System.out.println("Invalid Camp Faculty. Please try again.");
					else
						invalidInput = false;
				}
				catch (Exception ex) {
					System.out.println("Invalid Camp Faculty. Please try again.");
				}
			}
		} while (invalidInput);

		if (campFacultyInput == 2)
			fac = Faculty.DEPARTMENT6;

		try {
			// create Camp Info
			CampInfo campInfo = new CampInfo(campNameInput, campLocationInput, campStartDateInput, campEndDateInput,
					campRegClosingDateInput, campMaxAttendeeInput, campMaxCommitteeInput, campDescriptionInput,
					this.getUserID(), fac);

			// create Camp
			Camp camp = new Camp(campInfo, campVisibilityInput);

			// add the camp under this staff
			this.campsCreated.add(camp);

			// add to static var allCamps
			Camp.getAllCamps().add(camp);


			CampRW.AddCampToFile();
			System.out.println("Successfully created Camp '" + camp.getCampInfo().getCampName() + "'.");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error creating camp. Please try again.");
		}
	}

	/* // create camp method overloaded with 2 extra params: campCommittees, campAttendees
	private boolean createCamp(String campName, String campLocation, LocalDate campStartDate, LocalDate campEndDate,
			LocalDate campRegistrationClosingDate, int campMaxAttending, int campMaxCommittee, String campDescription,
			String campStaff, Faculty campUserGroup, Boolean isVisible, ArrayList<StudentAcc> campCommittees,
			ArrayList<StudentAcc> campAttendees) {
		// check duplicate
		if (Camp.checkDuplicateCampName(campName)) {
			System.out.println("Camp Name already exists. Please try again.");
			return false;
		}
		try {
			// create CampInfo
			CampInfo campInfo = new CampInfo(campName, campLocation, campStartDate, campEndDate, campRegistrationClosingDate,
					campMaxAttending, campMaxCommittee, campDescription, campStaff, campUserGroup);

			// create Camp
			Camp camp = new Camp(campInfo, isVisible, campCommittees, campAttendees);

			// add the camp under this staff
			this.campsCreated.add(camp);

			// add to static var allCamps
			Camp.getAllCamps().add(camp);

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	} */
	
	/**
	 * Removes a camp created by this Staff.
	 * @param camp The camp to be removed.
	 */
	private void removeCamp(Camp camp) {
		String campName = camp.getCampInfo().getCampName();
		if (campsCreated.contains(camp)) {
			if (camp.getCampAttendees().size() > 0 || camp.getCampCommittees().size() > 0) {
				System.out.println("Cannot remove Camp '" + camp.getCampInfo().getCampName() + "' as it has " + camp.getCampAttendees().size() + " attendees and " + camp.getCampCommittees().size() + " committees.");
			}
			else {
				// remove from this staff
				if (campsCreated.contains(camp))
					campsCreated.remove(camp);
				if (Camp.getAllCamps().contains(camp))
					Camp.getAllCamps().remove(camp);

				CampRW.DeleteCampFromFile();
				System.out.println("Camp '" + campName + "' removed successfully.");
			}
		}
	}
}
