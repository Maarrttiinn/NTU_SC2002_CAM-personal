package cam;

import java.io.*;

/**
  Class to verify, store and retrieve data from txt files relating to UserAcc.
  Uses staff.txt, staff_temp.txt, student.txt and student_temp.txt to facilitate writing.
  @author Martin Ng
  @version 1.0
  @since 25-11-2023 
 */
public class FileRW 
{   
    /**
     * Number of information categories in staff and student account files. 
     */
    private static final int NUM_OF_INFO = 6; 

    /**
     * Method to verify account userID and password.
     * @param user UserID of user.
     * @param password password of user.
     * @param type Account type, Student or Staff.
     * @return true if UserID and password exists in user account files, else false.
     */
    public static boolean verify(String user, String password,String type)
    {
        String line="";
        try
        {
            String[] content;
            final int USER_INDEX =3;
            BufferedReader reader = new BufferedReader(new FileReader(type.toLowerCase()+".txt"));
            line = reader.readLine();
            while(line!=null)
            {
                content = line.split(",");
                if(content[USER_INDEX].equals(user+":"+password))
                {
                    boolean verified = true;
                    reader.close();
                    return verified;
                }
                line=reader.readLine();
            }
            reader.close();
        }
        catch(IOException e)
        {
            e.getMessage();
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to change user account password and update txt file.
     * Utilise temp file(eg. staff_temp.txt) to facilitate writing.
     * @param acc UserAcc which wants to change password.
     * @param new_password password to change to.
     */
    public static void changePassword(UserAcc acc,String new_password) 
    {
        String type = acc.getAccType();
        String fileName = type+".txt";
        String temp_file =type+"_temp.txt";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(temp_file));
            FileWriter writer = new FileWriter(fileName);
            String line= reader.readLine();
            while(line!=null)
            {
                if(line.contains(acc.getUserID()))
                {
                    line = partition(line);
                    line =line+new_password+",false,";
                    writer.write(line+"\n");
                }
                else if(!line.equals("\n")) 
                {
                    writer.write(line+"\n");
                }
                line = reader.readLine();
            }
            reader.close();
            writer.close();

        }
        catch(IOException e)
        {
            e.getMessage();
            e.printStackTrace();
        }
        updateTempFile(fileName,temp_file); 
    }

    /**
     * Method to be used in changePassword() by partitioning string to exclude old password.
     * @param line String in txt file which contains user account data.
     * @return String without old password.
     */
    public static String partition(String line) 
    {                                           
        int length = line.length();             
        String new_str ="";
        for(int i=0;i<length;i++)
        {
            new_str+=line.charAt(i);
            if(line.charAt(i)==':')
            {
                return new_str;
            }
        }
        return new_str;
    }

    /**
     * Read txt file to retrieve user account information.
     * @param acc UserAcc of interest.
     * @return Array of String containing information.
     */
    public static String[] getInformation(UserAcc acc)
    {
        String[] information = new String[NUM_OF_INFO];
        String type = acc.getAccType();
        String fileName = type+".txt";
        int counter =0; 
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String str_to_append ="";
            while(line!=null)
            {
                if(line.contains(acc.getUserID()))
                {
                    for(int i=0;i<line.length();i++)
                    {
                        if(line.charAt(i)==','||line.charAt(i)==':')
                        {
                            if(str_to_append.length()>0) 
                            {
                                information[counter] = str_to_append;
                                str_to_append ="";
                                counter++;
                            }
                            if(counter>=NUM_OF_INFO) 
                            {
                                break;
                            }
                            continue;
                        }
                        else
                        {
                            str_to_append+=line.charAt(i); 
                        }
                    }
                    break;
                }
                else
                {
                    line =reader.readLine();
                }
            }
            reader.close();
            return information;
        }
        catch(IOException e)
        {
            e.getMessage();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to update temp txt file(eg.staff_temp.txt) after writing to original file to maintain consistency.
     * @param original name of original file without file extension(eg. staff).
     * @param temp name of temp file without file extension(eg. staff_temp).
     */
    public static void updateTempFile(String original, String temp) 
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(original));
            FileWriter writer = new FileWriter(temp);
            String line = reader.readLine();
            while(line!=null && !line.equals("\n")) 
            {
                writer.write(line+"\n");
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        }
        catch(IOException e)
        {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
