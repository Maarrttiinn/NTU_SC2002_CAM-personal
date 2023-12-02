package cam;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// store in this order CampID,campName,campLocation,campStartDate,campEndDate,campRegistrationClosingDate,campMaxAttending,campMaxCommittee,CampDescription,CampStaff,campUserGroup
// isFullcommittee,isFullAttendee,isVisible in same line
// store all campcommittees name in next line
//store all campAttendees name in next line

/**
  Class to Retrieve and Store data related to Camp.
  @author Martin Ng
  @version 1.0
  @since 25-11-2023
 */
public class CampRW 
{
    /**
     * LocalDate formatter to convert String to LocalDate, vice versa.
     * LocalDate and String in the form of dd-MM-yyyy.
     */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Writes all camps data to txt file.
     * Order of writing is: CampID,campName,campLocation,campStartDate,
     * campEndDate,campRegistrationClosingDate,campMaxAttending,campMaxCommittee,
     * CampDescription,CampStaff,campUserGroup in the same line.
     * All CampCommittees UserID in the next line.
     * All CampAttendees UserID in the next line.
     * @param camps ArrayList of all existing camps
     */
    private static void WriteToFile(ArrayList<Camp> camps)
    {
        try
        {
            System.out.println("WriteToFile size: "+camps.size());
            FileWriter fw = new FileWriter("camp.txt",false);
            for(Camp c : camps)
            {
                CampInfo campInfo = c.getCampInfo();
                fw.write(campInfo.getCampID()+",");
                fw.write(campInfo.getCampName()+",");
                fw.write(campInfo.getCampLocation()+",");
                fw.write(campInfo.getCampStartDate().format(formatter)+",");
                fw.write(campInfo.getCampEndDate().format(formatter)+",");
                fw.write(campInfo.getCampRegistrationClosingDate().format(formatter)+",");
                fw.write(String.valueOf(campInfo.getCampMaxAttending())+",");
                fw.write(String.valueOf(campInfo.getCampMaxCommittee())+",");
                fw.write(campInfo.getCampDescription()+",");
                fw.write(campInfo.getCampStaff()+",");
                fw.write(campInfo.getCampUserGroup().getFacultyType()+",");
                fw.write(String.valueOf(c.getIsFullCommittees())+",");
                fw.write(String.valueOf(c.getIsFullAttendees())+",");
                fw.write(String.valueOf(c.getVisibility())+",");
                fw.write("\n");

                ArrayList<StudentAcc> committee = c.getCampCommittees(); 
                if(committee.isEmpty())
                {
                    fw.write("\n");
                }
                else
                {
                    for(StudentAcc com : committee)
                    {
                        fw.write(com.getUserID()+"-"+String.valueOf(com.getPoints())+",");
                    }
                    fw.write("\n");
                }
                ArrayList<StudentAcc> attendee = c.getCampAttendees();
                if(attendee.isEmpty())
                {
                    fw.write("\n");
                }
                else
                {
                    for(StudentAcc atd : attendee)
                    {
                        fw.write(atd.getUserID()+",");
                    }
                    fw.write("\n");
                }
            }
            fw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Updates camp txt file whenever Staff creates a new camp.
     * Used by Staff.
     * Uses sort() method.
     * Uses WriteToFile() method.
     */
    public static void AddCampToFile()
    {
        ArrayList<Camp> camps = Camp.getAllCamps();
        sort(camps);
        WriteToFile(camps);
        
    }

    /**
     * Updates camp txt file whenever Staff deletes a camp.
     * Used by Staff. 
     * Uses WriteToFile() method.
     */
    public static void DeleteCampFromFile()
    {
        ArrayList<Camp> camps = Camp.getAllCamps();
        WriteToFile(camps);
    }

    /**
     * Retrieve data of all camps from txt file.
     * @return An ArrayList<Camp> of all existing camps.
      */
    public static ArrayList<Camp> getAllCamps()
    {
        
        ArrayList<Camp> camps = Camp.getAllCamps();
        try
        {
            
            BufferedReader bf = new BufferedReader(new FileReader("camp.txt"));
            String line = bf.readLine();
            while(line!=null)
            {
                ArrayList<StudentAcc> committee = new ArrayList<StudentAcc>();
                ArrayList<StudentAcc> attendee = new ArrayList<StudentAcc>();
                String[] campInfo = line.split(",");
                CampInfo ci = new CampInfo(campInfo[0],campInfo[2],LocalDate.parse(campInfo[3],formatter),LocalDate.parse(campInfo[4],formatter),LocalDate.parse(campInfo[5],formatter),Integer.parseInt(campInfo[6]),Integer.parseInt(campInfo[7]),campInfo[8],campInfo[9],Faculty.getFacultyType(campInfo[10]));
                line = bf.readLine();
                if(line.contains(","))
                {
                    String[] com = line.split(",");
                    
                    for(String s : com)
                    {
                        if(!s.equals("\n") &&line!=null )
                        {
                            String[] NameAndPoint = s.split("-");
                            String User = NameAndPoint[0].trim();
                            String Points = NameAndPoint[1].trim(); 
                            committee.add(new StudentAcc(User,Integer.parseInt(Points)));
                        }
                    }
                }
                line = bf.readLine();
                if(line.contains(","))
                {
                    String[] atd = line.split(",");
                   
                    for(String s : atd)
                    {
                        if(!s.equals("\n") &&line!=null)
                        {
                            attendee.add(new StudentAcc(s));
                        }
                    }
                }
                camps.add(new Camp(ci,Boolean.parseBoolean(campInfo[11]),Boolean.parseBoolean(campInfo[12]),Boolean.parseBoolean(campInfo[13]),committee,attendee));
                line=bf.readLine();
            }
            bf.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        return camps;
    }

    /**
     * Updates camp txt file whenever a Student register or withdraw from a camp.
     * Used by Student.
     * Uses WriteToFile() method.
     * @param camps An ArrayList<Camp> of all camps, with updated information.
      */
    public static void UpdateCampFile(ArrayList<Camp> camps)
    {
        WriteToFile(camps);
    }

    /**
     * Insertion Sort to sort camps according to CampName.
     * Sorts in ascending order.
     * @param camps An ArrayList<Camp> of all existing camps.
     */
    private static void sort(ArrayList<Camp> camps)
    {
        for(int i=1;i<camps.size();i++)
        {
            int j =i;
            while(j>0)
            {
                if(camps.get(j).getCampInfo().getCampID().compareTo(camps.get(j-1).getCampInfo().getCampID())>=0)
                {
                    break;
                }
                else
                {
                    Camp temp_camp = camps.get(j);
                    camps.remove(j);
                    camps.add(j-1,temp_camp);
                    j--;
                }
            }
        }
    }
}
