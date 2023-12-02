package cam;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
  Represents a Student, derived from UserAcc.java
  @author Martin Ng (Version 1.0, 1.7)
  @author Steph (Version 1.1)
  @author Tan Shirley (Version 1.2, 1.5, 1.9)
  @author Sruthi (Version 1.3)
  @author Low Kar Choon (Version 1.4, 1.6, 1.8)
  @version 1.9
  @since 25-11-2023
*/
public class StudentAcc extends UserAcc {
	
	/**
	 * The name of this Student
	 */
	private String studentName;
	
	/**
	 * The list of camps this Student is attending
	 */
	private ArrayList<Camp> registeredCamps;
	
	/**
	 * The list of camps this Student has withdrawn from
	 */
	private ArrayList<Camp> withdrawnCamps;
	
	/**
	 * The list of enquires made by this Student.
	 */
	private ArrayList<Enquiry> enquiries;
	
	/**
	 * The camp that this Student is a committee of (if any)
	 */
	private Camp committeeOfCamp;

	/**
	 * Points obtained by a Committee member.
	 */
	protected int Points;
	
	/**
	 * Date time formatter for display and storage
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	/**
	 * Creates a new Student
	 * @param user The User ID of this Student
	 */
	public StudentAcc(String user) {
		super(user);
		setAccType("student");
		this.registeredCamps = new ArrayList<>();
		this.enquiries = new ArrayList<Enquiry>();
		information = this.getInformation(); /* Obtain user information. */
		setInformation(information);
		this.studentName = getName();
		registeredCamps = new ArrayList<Camp>();
		withdrawnCamps = new ArrayList<Camp>();
		Points =0;
	}

	/**
	 * Creates a new Student with USerID and Points obtained.
	 * @param user UserID
	 * @param point Points obtained
	 */
	public StudentAcc(String user, int point){
		super(user);
		setAccType("student");
		this.registeredCamps = new ArrayList<>();
		this.enquiries = new ArrayList<Enquiry>();
		information = this.getInformation(); /* Obtain user information. */
		setInformation(information);
		this.studentName = getName();
		registeredCamps = new ArrayList<Camp>();
		withdrawnCamps = new ArrayList<Camp>();
		this.Points = point;
	}

	/**
	 * Creates a new Student with User ID, the list of camps withdrawn and the list of enquires made
	 * @param user The User ID of this Student
	 * @param UserWithdrawals The list of camps this Student has withdrawn
	 * @param enquiries The list of enquires made by this Student
	 */
	public StudentAcc(String user,ArrayList<Camp> UserWithdrawals,ArrayList<Enquiry> enquiries )
	{
		super(user);
		setAccType("student");
		this.registeredCamps = new ArrayList<>();
		this.enquiries = enquiries;
		information = this.getInformation();
		setInformation(information);
		this.studentName = getName();
		withdrawnCamps = UserWithdrawals;
		this.committeeOfCamp = null;
		Points=0;
	}

	/**
	 * Creates a new Student with User ID, the list of camps registered, the camp this student is a committee of, the list of camps withdrawn and the list of enquires made
	 * @param user The User ID of this Student
	 * @param regCamps The list of camps this Student is attending
	 * @param CommitteeOf The camp this Student is a committee of
	 * @param UserWithdrawals The list of camps this Student has withdrawn
	 * @param enquiries The list of enquiries made by this Student
	 * @param point Points obtained by Committee member
	 */
	public StudentAcc(String user,ArrayList<Camp> regCamps,Camp CommitteeOf,ArrayList<Camp> UserWithdrawals ,ArrayList<Enquiry> enquiries,int point)
	{
		super(user);
		setAccType("student");
		this.registeredCamps = regCamps;
		this.enquiries = new ArrayList<>();
		information = this.getInformation();
		setInformation(information);
		this.studentName = getName();
		withdrawnCamps = UserWithdrawals;
		this.committeeOfCamp = CommitteeOf;
		this.enquiries = enquiries;
		this.Points = point;
	}

	/**
	 * Prints the actions the Student can do
	 * @return The total number of options
	 */
	public int displayOptions()
	{
		int numberOfOptions = 12;
		System.out.println("\nList of available options: ");
		// student can register for a camp inside view camp list
		System.out.println("1. View camp list");
		// student can withdraw from a camp inside view registered camp
		System.out.println("2. View registered camp");
		System.out.println("3. Submit enquiries");
		System.out.println("4. View enquiries");
		System.out.println("5. Edit enquiries");
		System.out.println("6. Delete enquiries");
		System.out.println("7. View reply to enquiry");
		System.out.println("8. Reply enquiries");
		System.out.println("9. Add suggestion");
		System.out.println("10. View suggestion");
		System.out.println("11. Generate student list");
		System.out.println("12. Back");
		System.out.println("Enter your choice:");
		return numberOfOptions;
	}

	/**
	 * Performs the actions based on the Student's choice
	 * @param choice The choice the Student has selected
	 * @param sc The Scanner object initialised in App.java
	 */
	public void doChoice(int choice, Scanner sc) {
		String campID;
		int enqID;
		String campIDInput;

		
		/////////load suggestions//////////////////////
		int sLastIndex = 0;
		feedbackRW.getAllSuggestion();
		ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
		if(!Suggestion.getAllSuggestion().isEmpty())
		{
			
			for(Suggestion s : Suggestion.getAllSuggestion())
			{
				sLastIndex = s.getID();
				if(s.getName().equals(super.getUserID()))
				{
					suggestions.add(s);									
				}
			}
			Suggestion.setCounter(sLastIndex);
		}
		else
		{
			Suggestion.setCounter(sLastIndex);
		}	
		///////////////////////////////////////////////////
		/////////load replies/////////////////////
		int rLastIndex = 0;
		feedbackRW.getAllReply();
		ArrayList<Reply> replies = new ArrayList<Reply>();
		if(!Reply.getAllReply().isEmpty())
		{
			
			for(Reply r : Reply.getAllReply())
			{
				rLastIndex = r.getID();
				if(r.getName().equals(super.getUserID()))
				{
					replies.add(r);									
				}
			}
			Reply.setCounter(rLastIndex);
		}
		else
		{
			Reply.setCounter(rLastIndex);
		}	
		///////////////////////////////////////////////////
		
		switch (choice) {
		/* View all available camp */
		case 1:
			// if method returns 1, there is no camp available for this student
			int campSize = Camp.displayAllCamps(this);
			if (campSize == 1)
				return;			

			int registrationChoice;

				// camp number is the index of the camp in the arraylist
				System.out.println("Enter the Camp ID of a Camp to Register (Enter 'exit' to exit): ");
				campIDInput = sc.nextLine().strip();
			
			if (campIDInput.equalsIgnoreCase("exit"))
				return;

			// if slots still available for attendee or committee
			if (!Camp.getCampByCampID(campIDInput).getIsFullAttendees() && !Camp.getCampByCampID(campIDInput).getIsFullCommittees()) {
				System.out.println("Enter your choice: (1) Register as Attendee or (2) Register as Committee: ");
				registrationChoice = sc.nextInt();
				sc.nextLine();
				if (registrationChoice == 1) {
					// register for camp
					if(!registeredCamps.contains(Camp.getCampByCampID(campIDInput)))
					{
						Camp.getCampByCampID(campIDInput).addCampAttendee(this);
						registeredCamps.add(Camp.getCampByCampID(campIDInput));
					}
					else
					{
						System.out.println("You have already registered for this camp.");
						return;
					}
				}
				else if (registrationChoice == 2) {
					if (committeeOfCamp == null) {
						Camp.getCampByCampID(campIDInput).addCampCommittee(new Committee(this.getUserID(), campIDInput, suggestions,replies));
						committeeOfCamp = Camp.getCampByCampID(campIDInput);
					}
					else {
						System.out.println("You are already a committee of Camp '" + committeeOfCamp.getCampInfo().getCampName() + "'. You cannot be a committee of another camp.");
					}
				}
				else
					return;
			}

			// else if slots still available for attendee or committee
			else if (!Camp.getCampByCampID(campIDInput).getIsFullAttendees()) {
				System.out.println("You can only register as an Attendee, Enter (1) Register or (-1) Exit: ");
				registrationChoice = sc.nextInt();
				sc.nextLine();
				if (registrationChoice == 1) {
					// register for camp
					if(!registeredCamps.contains(Camp.getCampByCampID(campIDInput)))
					{
						Camp.getCampByCampID(campIDInput).addCampAttendee(this);
						registeredCamps.add(Camp.getCampByCampID(campIDInput));
					}
					else
					{
						System.out.println("You have already registered for this camp.");
						return;
					}
				}
				else
					return;
			}

			// else if slots still available for attendee or committee
			else if (!Camp.getCampByCampID(campIDInput).getIsFullCommittees()) {
				System.out.println("You can only register as a Committee, Enter (1) Register or (-1) Exit: ");
				registrationChoice = sc.nextInt();
				sc.nextLine();
				if (registrationChoice == 1) {
					if (committeeOfCamp == null) {
						Camp.getCampByCampID(campIDInput).addCampCommittee(new Committee(this.getUserID(),suggestions,replies));
						committeeOfCamp = Camp.getCampByCampID(campIDInput);
					}
					else {
						System.out.println("You are already a committee of Camp '" + committeeOfCamp.getCampInfo().getCampName() + "'. You cannot be a committee of another camp.");
					}
				}
				else
					return;
			}
			break;


		/* View registered camps: */
		case 2:
			viewRegisteredCamps();
			if (registeredCamps.size() > 0) {
				System.out.println("Enter the Camp ID of a Camp to Withdraw (Enter 'exit' to exit): ");
				campIDInput = sc.nextLine().strip();
				
				if(campIDInput.equalsIgnoreCase("exit"))
					return;
				/* camp committee cannot withdraw from a camp from faq
				 * // camp number of committee camp always = registered camp + 1
				if (campNoInput == (registeredCamps.size() + 1)) {
					 committeeOfCamp.removeCampCommittee(this);
					 withdrawnCamps.add(committeeOfCamp);
					 committeeOfCamp = null;
				}*/
				
				// to remove attendee from camp
				Camp.getCampByCampID(campIDInput).removeCampAttendee(this);
				
				if (!withdrawnCamps.contains(Camp.getCampByCampID(campIDInput)))
					withdrawnCamps.add(Camp.getCampByCampID(campIDInput));
				
				if (registeredCamps.contains(Camp.getCampByCampID(campIDInput)))
					registeredCamps.remove(Camp.getCampByCampID(campIDInput));
				
				return;
			}
			break;

		/* Submit an enquiry: 
		 * Ask the student for the camp ID he wishes to submit an enquiry to.
		 * Ask the student what he wants to enquire.
		 * Use the camp ID, his name, and his enquiry as arguments to add the enquiry to the camp. */
		case 3:
			//show all available camp
			int cSize = Camp.displayAllCamps(this);
			if (cSize == 1) {
				System.out.println("There is no camps available to make enquiry. ");		
				break;
			}

			//show all available camp
			System.out.println("Enquiry Submission System");
			do
			{
				System.out.println("Enter Camp ID: ");
				campID = sc.nextLine();
				if (Camp.getCampByCampID(campID) == null)
					System.out.println("Camp doesn't exist. Please try again.");

			}while (Camp.getCampByCampID(campID) == null);  //campid not in available camp
	

			System.out.println("Enter Enquiry: ");
			String studentEnquiry = sc.nextLine();
			Enquiry enquiry = new Enquiry(campID, super.getUserID(), studentEnquiry);
			Enquiry.addEnquiry(enquiry);
			this.enquiries.add(enquiry);
			Enquiry.getAllEnquiry().add(enquiry);
			System.out.println("Successfully added your enquiry.");
			break;               

		/* View own enquiries: */
		case 4:
			System.out.println("Pending Enquiries made by "+ studentName +":");
			if (this.enquiries.size() == 0)
			{
				System.out.println("There is no enquiry.");
			}
			else
			{
				Enquiry.viewStudentEnquiryByStatus(super.getUserID(), "pending");
			}
			break;

		/* Edit own enquiry: 
		 * Ask the student for the camp ID he wishes to edit his enquiry for.
		 * Use the camp ID, his name, and his modified enquiry as arguments to modify his enquiry to the camp. */
		case 5:
			if(Enquiry.viewStudentEnquiryByStatus(super.getUserID(),"pending") == 0)
				break;
			System.out.println("Enquiry Modification System:");

			do 
			{
				System.out.println("Enter Enquiry ID: ");
				enqID = sc.nextInt();
				sc.nextLine();
				if(findEnquiry(enqID, super.getUserID(), "pending")== null)
				{
					System.out.println("Invalid Enquiry ID. Please try again.");
				}
				

			}while(findEnquiry(enqID, super.getUserID(), "pending")== null);
			
			Enquiry e = findEnquiry(enqID,super.getUserID(), "pending");
			if(e.getStatus().equals("pending"))
			{
				
				System.out.println("Modify Enquiry: ");
				String modifiedEnquiry = sc.nextLine();
				e.setText(modifiedEnquiry);
				Enquiry.modifyEnquiry(e);
			}
			break;

		/* Remove own enquiry: 
		 * Ask the student for the camp ID he wishes to delete his enquiry for.
		 * Goes through the list of enquiries to check for the student's name.
		 * If an enquiry matches, remove it from the list. */
		case 6:
			if (Enquiry.viewStudentEnquiryByStatus(super.getUserID(),"pending") == 0)
				break;
			System.out.println("Enquiry Deletion System:");
			do
			{
				System.out.println("Enter Enquiry ID: ");
				enqID = sc.nextInt();
				sc.nextLine();
				if(findEnquiry(enqID, super.getUserID(), "pending")== null)
				{
					System.out.println("Invalid Enquiry ID. Please try again.");
				}

			}while(findEnquiry(enqID, super.getUserID(), "pending")== null);
			Enquiry en = Enquiry.getEnquiryByEnqID(enqID);
			if(en.getStatus().equals("pending"))
			{

				Enquiry.deleteEnquiry(en);
			}
			break;
				
		//View reply to enquiry: 
		case 7:
			if (Enquiry.viewStudentEnquiryByStatus(super.getUserID(),"replied") == 0)
				break;
			System.out.println("View Reply System:");
			do
			{
				System.out.println("Enter Enquiry ID to view reply: ");
				enqID = sc.nextInt();
				sc.nextLine();
				if(findEnquiry(enqID, super.getUserID(),"replied")== null)
				{
					System.out.println("Invalid Enquiry ID. Please try again.");
				}

			} while (findEnquiry(enqID, super.getUserID(),"replied")== null);

			Reply.viewReply(enqID);
			break;

			/* Reply to enquiries */
		case 8:
			if (committeeOfCamp != null) {
				Committee committee = new Committee(this.getUserID(), committeeOfCamp.getCampInfo().getCampID(), suggestions,replies);
				try {
					committee.replyEnquiry(sc);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.out.println("Unable to add your reply. Please try again later.");
				}
			}
			else {
				System.out.println("You need to be a Committee of a Camp to use this function.");
			}
			break;
			
		case 9:
			if (committeeOfCamp != null) {
				Committee committee = new Committee(this.getUserID(), committeeOfCamp.getCampInfo().getCampID(), suggestions,replies);
				try {
					committee.submitSuggestion(sc);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				System.out.println("You need to be a Committee of a Camp to use this function.");
			}
			break;
		case 10:
			if (committeeOfCamp != null) {
				Committee committee = new Committee(this.getUserID(), committeeOfCamp.getCampInfo().getCampID(), suggestions,replies);
				committee.viewSuggestions();
			}
			else {
				System.out.println("You need to be a Committee of a Camp to use this function.");
			}
			break;
			/* Generate student list. */
		case 11:
			if (committeeOfCamp != null) {
				System.out.println("Enter the filter (Attendee/Committee/none): ");
				String filter = sc.nextLine();
				GenerateReport.generateReport(this,filter,"Committee");
			}
			else {
				System.out.println("You need to be a Committee of a Camp to use this function.");
			}
			break;

		/* Log out of the application. */
		case 12:
			break;
		} 
	}

	/**
	 * Gets the Enquiry based on the Enquiry ID and User ID
	 * @param enqID The Enquiry ID
	 * @param userID The User ID of this Student
 	 * @param status The status of the enquiry
	 * @return The Enquiry object if found
	 */
	public Enquiry findEnquiry(int enqID, String userID, String status)
	{
		for (Enquiry e: enquiries)
		{
			if(e.getID() == enqID && e.getName().equals(userID) && e.getStatus().equals(status))
			{
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Gets all the camps this Student is attending
	 * @return The list of camps this Student is attending
	 */
	public ArrayList<Camp> getRegisteredCamps() {
		return registeredCamps;
	}

	/**
	 * Prints all the camps this Student is attending
	 */
	private void viewRegisteredCamps() {
		if (registeredCamps.size() == 0 && committeeOfCamp == null) {
			System.out.println("You have not registered for any camps.");
			return;
		}
		else {
			if (registeredCamps.size() > 0) {
				System.out.println("======= List of Camps Registered =======");
				for (Camp camp : registeredCamps) {
					System.out.println("------------------------------------");
					System.out.println("Camp ID: " + camp.getCampInfo().getCampID());
					System.out.println("Camp Name: " + camp.getCampInfo().getCampName());
					System.out.println("Camp Location: " + camp.getCampInfo().getCampLocation());
					System.out.println("Start Date: " + camp.getCampInfo().getCampStartDate());
					System.out.println("End Date: " + camp.getCampInfo().getCampEndDate().format(formatter));
					System.out.println("Registration Closing Date: " + camp.getCampInfo().getCampRegistrationClosingDate());
					System.out.println("Attendees Capacity: " + camp.getCampAttendees().size() + "/" + camp.getCampInfo().getCampMaxAttending());
					System.out.println("Committees Capacity: " + camp.getCampCommittees().size() + "/" + camp.getCampInfo().getCampMaxCommittee());
					System.out.println("Description: " + camp.getCampInfo().getCampDescription());
					System.out.println("Faculty: " + camp.getCampInfo().getCampUserGroup().getFacultyType());
					System.out.println("------------------------------------\n");
				}
				System.out.println("======= End of List of Camps Registered =======");
			}

			if (committeeOfCamp != null) {
				System.out.println("======= Details of Camp that you are a committee of =======");
				System.out.println("------------------------------------");
				System.out.println("Camp ID: " + committeeOfCamp.getCampInfo().getCampID());
				System.out.println("Camp Name: " + committeeOfCamp.getCampInfo().getCampName());
				System.out.println("Camp Location: " + committeeOfCamp.getCampInfo().getCampLocation());
				System.out.println("Start Date: " + committeeOfCamp.getCampInfo().getCampStartDate());
				System.out.println("End Date: " + committeeOfCamp.getCampInfo().getCampEndDate().format(formatter));
				System.out.println("Registration Closing Date: " + committeeOfCamp.getCampInfo().getCampRegistrationClosingDate());
				System.out.println("Attendees Capacity: " + committeeOfCamp.getCampAttendees().size() + "/" + committeeOfCamp.getCampInfo().getCampMaxAttending());
				System.out.println("Committees Capacity: " + committeeOfCamp.getCampCommittees().size() + "/" + committeeOfCamp.getCampInfo().getCampMaxCommittee());
				System.out.println("Description: " + committeeOfCamp.getCampInfo().getCampDescription());
				System.out.println("Faculty: " + committeeOfCamp.getCampInfo().getCampUserGroup().getFacultyType());
				System.out.println("------------------------------------\n");
				System.out.println("======= End of Details of Camp that you are a committee of =======");
			}
		}
	}

	/**
	 * Gets the camp this Student is a committee of
	 * @return The Camp this Student is a committee of
	 */
	public Camp getCommitteeOfCamp() {
		return committeeOfCamp;
	}

	/**
	 * Gets all the camps this Student has withdrawn from
	 * @return The list of camps this Student has withdrawn from
	 */
	public ArrayList<Camp> getWithdrawnCamps() {
		return withdrawnCamps;
	}

	/**
	 * Get points earned by Committee.
	 * @return Points
	 */
	public int getPoints()
	{
		return Points;
	}
}
