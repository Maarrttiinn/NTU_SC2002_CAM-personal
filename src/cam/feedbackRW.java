package cam;
import java.io.*;
import java.util.ArrayList;

/**
    Class to Retrieve and Store Feedback - Enquiry, Suggestion and Reply
    @author Tan Shirley
    @version 1.0
    @since 25-11-2023
*/

public class feedbackRW {

    /** 
     * Create new text file based on type of feedback - enquiry, suggestion, reply
     * @param type Type of feedback 
     */
    public static void createNewFile(String type)
    {
        File myObj = new File(type+".txt");  
    }
    
    /** 
     * Write new enquiry data to text file.
     * Order of writing is: id, status, student userID, campID and enquiry text in the same line
     * @param enquiry  Enquiry object created
     * @throws IOException if exception occured
     */
    public static void writeEnquiry(Enquiry enquiry) throws IOException
    {
        FileWriter fw =new FileWriter("enquiry.txt", true);
        fw.write(enquiry.id + "," + enquiry.getStatus() + "," + enquiry.userName + "," + enquiry.getCampID() + "," + enquiry.text );
        fw.write("\n");
        fw.close();
    }

    /** 
     * Update enquiry with pending status to text file.
     * Used by Student and Attendee.
     * @param e Enquiry object to be updated
     */
    public static void updateEnquiry(Enquiry e)
    {
        try {
            int enqID;
            String status, studentName, campID, text;
            String enquiry;
            boolean found = false;
 
            File file = new File("enquiry.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            while (raf.getFilePointer() < raf.length()) 
            {
                enquiry = raf.readLine();
                String[] lineSplit = enquiry.split(",");                
                enqID = Integer.parseInt(lineSplit[0]);
                status = lineSplit[1];
                studentName = lineSplit[2];
                campID = lineSplit[3];
                text = lineSplit[4];
                if (enqID == e.getID() && status.equals("pending")) {
                    found = true;
                    break;
                }
            }
            if (found == true) 
            { 
                File tmpFile = new File("temp.txt");
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
                raf.seek(0);
                while (raf.getFilePointer() < raf.length()) 
                {
                    enquiry = raf.readLine(); 
                    String[] lineSplit = enquiry.split(",");                
                    enqID = Integer.parseInt(lineSplit[0]);
                    status = lineSplit[1];
                    studentName = lineSplit[2];
                    campID = lineSplit[3];
                    if (enqID == e.getID() && status.equals("pending")) {

                        enquiry = e.getID() + "," + e.getStatus() + "," + e.getName() + "," + e.getCampID()+ "," + e.getText();
                    }
                    tmpraf.writeBytes(enquiry);
                    tmpraf.writeBytes(System.lineSeparator());
                }
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) 
                {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete(); 
                System.out.println(" Enquiry updated.\n");
            }
            else 
            {
                raf.close();
                System.out.println(" Enquiry do not exist. \n");
            }
        }
        catch (IOException ioe) 
        {
            System.out.println(ioe);
        }
        catch (NumberFormatException nef) 
        {
            System.out.println(nef);
        }
    
    }

    /** 
     * Delete enquiry with pending status from text file.
     * Used by Student and Attendee.
     * @param e Enquiry obejct to be deleted.
     */
    public static void deleteEnquiry(Enquiry e)
    {
        try { 
            int enqID;
            String status, studentName;
            String enquiry;
            File file = new File("enquiry.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false; 
            while (raf.getFilePointer() < raf.length()) 
            {
                enquiry = raf.readLine(); 
                String[] lineSplit = enquiry.split(","); 
                enqID = Integer.parseInt(lineSplit[0]);
                status = lineSplit[1];
                studentName = lineSplit[2];
                if (enqID == e.getID() && status.equals("pending")) {
                    found = true;
                    break;
                }
            } 
            if (found == true) {
                File tmpFile = new File("temp.txt"); 
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw"); 
                raf.seek(0);
                while (raf.getFilePointer()< raf.length()) 
                { 
                    enquiry = raf.readLine(); 
                    String[] lineSplit = enquiry.split(",");                
                    enqID = Integer.parseInt(lineSplit[0]);
                    status = lineSplit[1];
                    studentName = lineSplit[2]; 
                    if (enqID == e.getID()&& status.equals("pending")) {
                        continue;
                    }
                    tmpraf.writeBytes(enquiry);
                    tmpraf.writeBytes(System.lineSeparator());
                } 
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) 
                {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                } 
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete(); 
                System.out.println(" Enquiry deleted. ");
            } 
            else 
            {
                raf.close();
                System.out.println("Enquiry does not exists. ");
            }
        } 
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    /** 
     * Retrieve all enquiries from text file.
     * @return ArrayList<Enquiry> of all existing enquiries.
     */
    public static ArrayList<Enquiry> getAllEnquiry()
    {
        ArrayList<Enquiry> enquiries = Enquiry.getAllEnquiry();
        try
        {            
            BufferedReader bf = new BufferedReader(new FileReader("enquiry.txt"));
            String line = bf.readLine();
            while(line!=null)
            {
                String[] enqInfo = line.split(",");
                Enquiry enq = new Enquiry(Integer.parseInt(enqInfo[0]),enqInfo[3],enqInfo[2],enqInfo[4],enqInfo[1]);
                enquiries.add(enq);
                line=bf.readLine();
            }
            bf.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return enquiries;
    }

    /** 
     * Write new suggestion data to text file.
     * Order of writing is: id, status, committee userID, campID and suggestion text in the same line
     * @param suggestion Suggestion object created.
     * @throws IOException if exception occured
     */
    public static void writeSuggestion(Suggestion suggestion) throws IOException
    {
        FileWriter fw =new FileWriter("suggestion.txt", true);
        fw.write(suggestion.getID() + "," + suggestion.getStatus() + "," + suggestion.userName+ "," + suggestion.getCampID() + "," + suggestion.getText() );
        fw.write("\n");
        fw.close();
    }
    
    /** 
     * Update suggestion with pending status to text file.
     * Used by Committee.
     * @param s Suggestion obejct to be updated.
     */
    public static void updateSuggestion(Suggestion s)
    {
        try {
            int sugID;
            String status, studentName, campID, text;
            String suggestion;
            boolean found = false; 
            File file = new File("suggestion.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");            
            while (raf.getFilePointer() < raf.length()) 
            {
                suggestion = raf.readLine();
                String[] lineSplit = suggestion.split(",");                
                sugID = Integer.parseInt(lineSplit[0]);
                status = lineSplit[1];
                studentName = lineSplit[2];
                campID = lineSplit[3];
                text = lineSplit[4];
                if (sugID == s.getID()&& status.equals("pending")) {
                    found = true;
                    break;
                }
            }
            if (found == true) 
            { 
                File tmpFile = new File("temp.txt");
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
                raf.seek(0);
                while (raf.getFilePointer() < raf.length()) 
                {
                    suggestion = raf.readLine(); 
                    String[] lineSplit = suggestion.split(",");                
                    sugID = Integer.parseInt(lineSplit[0]);
                    status = lineSplit[1];
                    studentName = lineSplit[2];
                    campID = lineSplit[3];
                    if (sugID == s.getID() && status.equals("pending")) {
                        suggestion = s.getID() + "," + s.getStatus() + "," + s.getName() + "," + s.getCampID()+ "," + s.getText();
                    } 
                    tmpraf.writeBytes(suggestion);
                    tmpraf.writeBytes(System.lineSeparator());
                }      
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) 
                {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete(); 
                System.out.println(" Suggestion updated.\n");
            }
            else 
            {
                raf.close();
                System.out.println(" Suggestion does not exist. \n");
            }
        } 
        catch (IOException ioe) 
        {
            System.out.println(ioe);
        } 
        catch (NumberFormatException nef) 
        {
            System.out.println(nef);
        }
    
    }
    
    /** 
     * Delete suggestion with pending status from text file.
     * Used by Committee.
     * @param s Suggestion object to be deleted.
     */
    public static void deleteSuggestion(Suggestion s)
    {
        try { 
            int sugID;
            String status, studentName;
            String suggestion;
            File file = new File("suggestion.txt");
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false; 
            while (raf.getFilePointer() < raf.length()) 
            {
                suggestion = raf.readLine(); 
                String[] lineSplit = suggestion.split(","); 
                sugID = Integer.parseInt(lineSplit[0]);
                status = lineSplit[1];
                studentName = lineSplit[2];
                if (sugID == s.getID() && status.equals("pending"))  {
                    found = true;
                    break;
                }
            } 
            if (found == true) {
                File tmpFile = new File("temp.txt"); 
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw"); 
                raf.seek(0);
                while (raf.getFilePointer()< raf.length()) 
                { 
                    suggestion = raf.readLine(); 
                    String[] lineSplit = suggestion.split(",");                
                    sugID = Integer.parseInt(lineSplit[0]);
                    status = lineSplit[1];
                    studentName = lineSplit[2]; 
                    if (sugID == s.getID() && status.equals("pending"))  {
                        continue;
                    }
                    tmpraf.writeBytes(suggestion);
                    tmpraf.writeBytes(System.lineSeparator());
                } 
                raf.seek(0);
                tmpraf.seek(0);
                while (tmpraf.getFilePointer() < tmpraf.length()) 
                {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                } 
                raf.setLength(tmpraf.length());
                tmpraf.close();
                raf.close();
                tmpFile.delete(); 
                System.out.println(" Suggestion deleted. ");
            } 
            else 
            {
                raf.close();
                System.out.println("Suggestion does not exists. ");
            }
        } 
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    /** 
     * Retrieve all suggestions from text file.
     * @return ArrayList<Suggestion> of all existing suggestions.
     */
    public static ArrayList<Suggestion> getAllSuggestion()
    {
        ArrayList<Suggestion> suggestions = Suggestion.getAllSuggestion();
        try
        {            
            BufferedReader bf = new BufferedReader(new FileReader("suggestion.txt"));
            String line = bf.readLine();
            while(line!=null)
            {
                String[] sugInfo = line.split(",");               
                int id = Integer.parseInt(sugInfo[0]);
                String status = sugInfo[1];
                String userID = sugInfo[2];
                String campID = sugInfo[3];
                String text = sugInfo[4];
                Suggestion sug = new Suggestion(id,status,userID,campID,text);
                suggestions.add(sug);
                line=bf.readLine();
            }
            bf.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return suggestions;
    }


    /** 
     * Write new reply data to text file.
     * Order of writing is: id, enquiryID, userID and reply text in the same line
     * @param reply Reply object created.
     * @throws IOException if exception occured
     */
    public static void writeReply(Reply reply) throws IOException
    {
        FileWriter fw =new FileWriter("reply.txt", true);
        fw.write(reply.getID() + "," + reply.getEnquiryID() + "," + reply.getName() + "," + reply.getText() );
        fw.write("\n");
        fw.close();
    }

    /** 
     * Retrieve all replies from text file.
     * @return ArrayList<Reply> of all existing replies.
     */
    public static ArrayList<Reply> getAllReply()
    {
        ArrayList<Reply> replies = Reply.getAllReply();
        try
        {            
            BufferedReader bf = new BufferedReader(new FileReader("reply.txt"));
            String line = bf.readLine();
            while(line!=null)
            {
                String[] replyInfo = line.split(",");
               
                int id = Integer.parseInt(replyInfo[1]);
                String enqID = replyInfo[0];
                String userID = replyInfo[2];
                String text = replyInfo[3];
                Reply r = new Reply(id,enqID,userID,text);
                replies.add(r);
                line=bf.readLine();
            }
            bf.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return replies;
    }
   



}
