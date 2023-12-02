/* 
package cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Student {
    public String studentName;
    public ArrayList<Camp> registeredCamps;
    public ArrayList<Enquiry> enquiries;

    // Constructor with parameters.
    public Student(String studentName) {
        this.studentName = studentName;
        this.registeredCamps = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    /* View all available camps:
     * If the camp is visible, print out the information. *
    public void viewAllCamps(List<Camp> allCamps) {
        for (Camp camp : allCamps) {
            if (camp.getVisibility()) {
                System.out.println("Camp ID: " + camp.getCampInfo().getCampID()); // Use getCampID() method
                System.out.println("Camp Name: " + camp.getCampInfo().getCampName());
                System.out.println("Camp Location: " + camp.getCampInfo().getCampLocation());
                System.out.println("Start Date: " + camp.getCampInfo().getCampStartDate());
                System.out.println("Registration Closing Date: " + camp.getCampInfo().getCampRegistrationClosingDate());
                System.out.println("Maximum Attendees: " + camp.getCampInfo().getCampMaxAttending()); // Use getCampMaxAttending() method
                System.out.println("Description: " + camp.getCampInfo().getCampDescription());
                System.out.println("User Group: " + camp.getCampInfo().getCampUserGroup());
                System.out.println(); // Add a separator between camps.
            }
        }
    }

    /* Register for a camp:
     * Ask the student for the camp ID he wishes to join.
     * Use the camp ID he selected and his name as arguments to add the student to the camp - addCampAttendee(). *
    public void registerCamp(Camp camp) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Camp Registration System");
        System.out.println("Enter Camp ID: ");
        String campID = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character
        camp.addCampAttendee(campID, new Attendee(this.studentName));
        System.out.println("Successfully registered for the camp.");

        scanner.close();

        this.registeredCamps.add(camp);
    }

    /* Submit an enquiry:
     * Ask the student for the camp ID he wishes to submit an enquiry to.
     * Ask the student what he wants to enquire.
     * Use the camp ID, his name, and his enquiry as arguments to add the enquiry to the camp - addEnquiry(). *
    public void submitEnquiry(Enquiry enquiry) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enquiry Submission System");
        System.out.println("Enter Camp ID: ");
        String campID = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Enter Enquiry: ");
        String studentEnquiry = scanner.nextLine();
        enquiry.addEnquiry(campID, studentName, studentEnquiry);
        System.out.println("Successfully added your enquiry.");

        scanner.close();
    }

    /* View own enquiries:
     * Goes through the list of enquiries to check for the student's name.
     * If his name is in the list of enquiries, print out the camp ID of his enquiry next to his enquiry. *
    public void viewEnquiries() {
        System.out.println("Your Enquiries:");

        for (Enquiry enquiry : enquiries) {
            if (enquiry.getName().equals(studentName)) { // Use getName() method
                System.out.println("Camp ID: " + enquiry.getCampID());
                System.out.println("Enquiry: " + enquiry.getText());
                System.out.println(); // Add a separator between enquiries.
            }
        }
    }

    /* Edit own enquiry:
     * Ask the student for the camp ID he wishes to edit his enquiry for.
     * Use the camp ID, his name, and his modified enquiry as arguments to modify his enquiry to the camp - modifyEnquiry(). *
    public void editEnquiry(Enquiry enquiry) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enquiry Modification System:");
        System.out.println("Enter Camp ID: ");
        String campID = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Modify Enquiry: ");
        String modifiedEnquiry = scanner.nextLine();
        if (enquiry.getStatus().equals("pending"))
        {
             enquiry.modifyEnquiry(campID, studentName, modifiedEnquiry);
        }
       
        System.out.println("Successfully modified your enquiry.");

        scanner.close();
    }

    /* Remove own enquiry:
     * Ask the student for the camp ID he wishes to delete his enquiry for.
     * Goes through the list of enquiries to check for the student's name.
     * If an enquiry matches, remove it from the list. *
    public void deleteEnquiry(Enquiry enquiry) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enquiry Deletion System:");
        System.out.println("Enter Camp ID: ");
        String campID = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        // Iterate through the list of enquiries and remove matching ones
        enquiries.removeIf(e -> Objects.equals(e.getCampID(), campID) && e.getName().equals(studentName));

        System.out.println("Successfully deleted your enquiry.");

        scanner.close();
    }

    // View student's registered camps:
    public List<Camp> viewRegisteredCamps() {
        return this.registeredCamps;
    }
}

*/
