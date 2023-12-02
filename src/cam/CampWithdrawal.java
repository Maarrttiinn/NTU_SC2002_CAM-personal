package cam;


import java.util.ArrayList;
import java.io.*;

/**
  Class for retrieving and storing data of Student withdrawal from camp.
  @author Martin Ng
  @version 1.0
  @since 25-11-2023
 */
public class CampWithdrawal 
{
    /**
     * The UserID of StudentAcc. 
     */
    private String UserID;

    /**
     * ArrayList<Camp> of withdrawn camp by Student of UserID.
     */
    private ArrayList<Camp> withdrawals;

    /**
     * Constructor to create a new CampWithdrawal object.
     * @param UserID Student's UserID.
     * @param withdrawals ArrayList of camps withdrawn by Student.
     */
    public CampWithdrawal(String UserID, ArrayList<Camp> withdrawals)
    {
        this.UserID = UserID;
        this.withdrawals = withdrawals;
    }

    /**
     * Gets the Student's UserID.
     * @return UserID.
     */
    public String getUserID()
    {
        return UserID;
    }

    /**
     * Gets ArrayList of camps withdrawn by Student.
     * @return ArrayList of camps.
     */
    public ArrayList<Camp> getWithdrawals()
    {
        return withdrawals;
    }

    /**
     * Write Student's UserID and campID of all camps withdrawn by Student to txt file.
     * Each Student's information is writte to a new line in txt file.
     * @param allWithdrawals ArrayList of CampWithdrawal for all Students.
     */
    public static void WriteWithdrawal(ArrayList<CampWithdrawal> allWithdrawals)
    {
        try
        {
            FileWriter fw = new FileWriter("withdrawals.txt");
            for(CampWithdrawal cw : allWithdrawals)
            {
                fw.write(cw.getUserID()+",");
                ArrayList<Camp> UserWithdrawals = cw.getWithdrawals();
                if(UserWithdrawals.size()==0)
                {
                    fw.write("\n");
                }
                else
                {
                    for(Camp c : UserWithdrawals)
                    {
                        fw.write(c.getCampInfo().getCampID()+",");
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
     * Read from withdrawals txt file to initialise CampWithdrawal object for all students.
     * @return ArrayList of CampWithdrawal for all students.
     */
    public static ArrayList<CampWithdrawal> ReadWithdrawal()
    {
        ArrayList<CampWithdrawal> allWithdrawals = new ArrayList<CampWithdrawal>();
        try
        {
            BufferedReader bf = new BufferedReader(new FileReader("withdrawals.txt"));
            String line=bf.readLine();
            while(line!=null)
            {
                ArrayList<Camp> UserWithdrawals = new ArrayList<Camp>();
                String[] line_arr = line.split(",");
                for(int i=1;i<line_arr.length;i++)
                {
                    if(!line_arr[i].equals("\n"))
                    {
                        String campID = line_arr[i];
                        Camp camp = Camp.getCampByCampID(campID);
                        UserWithdrawals.add(camp);
                    }
                }
                CampWithdrawal withdrawals = new CampWithdrawal(line_arr[0],UserWithdrawals);
                allWithdrawals.add(withdrawals);
                line=bf.readLine();
            }
            bf.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return allWithdrawals;
    }

    /**
     * Gets all camp withdrawal of a particular Student.
     * Used in App.java to initialise Student's camp withdrawals.
     * @param allWithdrawals ArrayList of CampWithdrawals for all students.
     * @param UserID UserID of student in interest.
     * @return ArrayList of camps withdrawn by Student.
     */
    public static ArrayList<Camp> getStudentWithdrawals(ArrayList<CampWithdrawal> allWithdrawals,String UserID)
    {
        ArrayList<Camp> UserWithdrawals = new ArrayList<Camp>();
        for(CampWithdrawal cw : allWithdrawals)
        {
            if(cw.getUserID().equals(UserID))
            {
                UserWithdrawals = cw.getWithdrawals();
                break;
            }
        }
        return UserWithdrawals;
    }
}
