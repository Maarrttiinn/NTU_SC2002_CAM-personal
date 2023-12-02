package cam;

import java.io.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

/**
  Class for Staff and Committee to generate camp report.
  @author Martin Ng
  @version 1.0
  @since 25-11-2023
 */
public class GenerateReport
{
    /**
     * LocalDate formatter to convert String to LocalDate, vice versa.
     */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Generates a Camp report to a txt file.
     * Used by Staff or Committee.
     * @param user  Account of User.
     * @param filter    The filter to use, Attendee or Committee or None.
     * @param TypeOfReport staff or committee or performance report.
     */
    public static void generateReport(UserAcc user, String filter, String TypeOfReport) 
    {                                                                                 
        try
        {
            FileWriter fw = new FileWriter(TypeOfReport+"Report.txt");
            ArrayList<Camp> campsCreated = new ArrayList<>();
            boolean AttendeeFilter = false; 
            boolean CommitteeFilter = false; 
            if(user instanceof StaffAcc) 
            {
                StaffAcc staff = (StaffAcc) user;
                campsCreated = staff.getAllCampsCreated();
                fw.write(campsCreated.get(0).getCampInfo().getCampStaff() + "'s camp report\n\n");
            }
            else if(user instanceof StudentAcc)
            {
                StudentAcc committee = (StudentAcc) user;
                campsCreated.add(committee.getCommitteeOfCamp()); 
                fw.write(committee.getName() + "'s camp report\n\n"); 
            }
            
            if(filter.equalsIgnoreCase("Attendee")) 
            {
                AttendeeFilter = true;
            }
            else if(filter.equalsIgnoreCase("Committee"))
            {
                CommitteeFilter = true;
            }

            for(Camp camp : campsCreated) 
            {
                CampInfo campInfo = camp.getCampInfo();
                fw.write("CampName: "+campInfo.getCampName()+"\n");
                fw.write("CampID: "+campInfo.getCampID()+"\n");
                fw.write("CampLocation: "+campInfo.getCampLocation()+"\n");
                fw.write("Start Date: "+campInfo.getCampStartDate().format(formatter)+"\n");
                fw.write("Registration Closing Date: "+campInfo.getCampRegistrationClosingDate().format(formatter)+"\n");
                fw.write("Faculty: "+campInfo.getCampUserGroup().getFacultyType()+"\n");
                fw.write("Camp Description: "+campInfo.getCampDescription()+"\n");
                fw.write("Camp Visibility: "+ String.valueOf(camp.getVisibility()+"\n"));
                fw.write("Maximum Attendee: "+String.valueOf(campInfo.getCampMaxAttending())+"\n");
                fw.write("Maximum Committee: "+String.valueOf(campInfo.getCampMaxCommittee())+"\n");
                fw.write("Full Attendee: "+String.valueOf(camp.getIsFullAttendees())+"\n");
                fw.write("Full Committee: "+String.valueOf(camp.getIsFullCommittees())+"\n\n");
                if(!AttendeeFilter) 
                {
                    fw.write("Camp Committee Members-Score: \n");
                    for(StudentAcc c : camp.getCampCommittees())
                    {
                        fw.write(c.getName()+"-"+c.getPoints()+"\n"); //edit if getter function is implemented and add method to get score
                    }
                }
                if(!CommitteeFilter)
                {
                    fw.write("Camp Attendee Members: \n");
                    for(StudentAcc attendee : camp.getCampAttendees())
                    {
                        fw.write(attendee.getName()+"\n");
                    }
                }
                fw.write("\n\n");
            }
            fw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * Used by Staff to generate performance report on Committee.
     * Utilises generateReport() method with filter of Committee
     * @param staff Account of staff.
     */
    public static void generatePerformanceReport(StaffAcc staff)
    {
        generateReport(staff,"Committee","Performance");
    }    
}
