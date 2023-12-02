package cam;

import java.util.Scanner;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        UserAcc acc;
        String userID,password,type;
        final int MAX_LOGIN_ATTEMPTS = 5;
        // first level choice
        int choice;
        // second level choice, used for doChoice() depending on acc type
        int accChoice = 0;
        // store the choice to logout depending on the number of the last option in displayOptions depending on acc type
        int choiceToLogOut;
        boolean quit = false;
        // number of failed log in attempts
        int logInAttempts = MAX_LOGIN_ATTEMPTS;
        boolean logOut = true;
        
        feedbackRW.createNewFile("enquiry"); //create when program run?
        feedbackRW.createNewFile("suggestion");
        feedbackRW.createNewFile("reply");

		//get all camps and assign to Camp static variable
		CampRW.getAllCamps();
		ArrayList<CampWithdrawal> allWithdrawals = CampWithdrawal.ReadWithdrawal();
	    
	feedbackRW.getAllEnquiry();
    	feedbackRW.getAllReply();
	        	feedbackRW.getAllSuggestion();
		

        do {
        	System.out.println("Log In attempts left: " + logInAttempts);
        	
        	do {
        		System.out.println("Enter account type(student/staff) or 'quit' to exit: ");
        		type = sc.nextLine();
        		if (!type.equalsIgnoreCase("student") && !type.equalsIgnoreCase("staff") && !type.equalsIgnoreCase("quit"))
        			System.out.println("Invalid account type. Please try again.");
        	} while(!type.equalsIgnoreCase("student") && !type.equalsIgnoreCase("staff") && !type.equalsIgnoreCase("quit"));
        	
        	if (type.equalsIgnoreCase("quit")) {
        		quit = true;
        		continue;
        	}
        	System.out.println("Enter username: ");
        	userID = sc.nextLine().strip();
        	System.out.println("Enter password: ");
        	password = sc.nextLine().strip();

        	if(FileRW.verify(userID,password,type))
        	{        		
        		logOut = false;
        		logInAttempts = MAX_LOGIN_ATTEMPTS;
        		if(type.equalsIgnoreCase("student"))
        		{
					ArrayList<Camp> UserWithdrawals = CampWithdrawal.getStudentWithdrawals(allWithdrawals,userID);
					ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
					int userPoints =0;
				
					if(!Camp.getAllCamps().isEmpty())
					{
						ArrayList<Camp> regCamps = new ArrayList<Camp>();
						Camp CommitteeOf = null;
						int com_counter=0;
						for(Camp camp : Camp.getAllCamps())
						{
							int atd_count =0;
							
							for(StudentAcc stu : camp.getCampAttendees())
							{
								if(stu.getUserID().equals(userID))
								{
									regCamps.add(camp);
									atd_count++;
									break;
								}
							}
							if(atd_count==0 && com_counter==0)
							{
								for(StudentAcc com : camp.getCampCommittees())
								{
									if(com.getUserID().equals(userID))
									{
										CommitteeOf = camp;
										userPoints = com.getPoints();
										com_counter++;
										break;
									}
								}
							}

						}
						if(!Enquiry.getAllEnquiry().isEmpty())
						{
							int eLastIndex = 0;
							for(Enquiry e : Enquiry.getAllEnquiry())
							{
								eLastIndex = e.getID();
								if(e.getName().equals(userID))
								{
									enquiries.add(e);									
								}
							}
//							feedbackRW.storeLastEnquiryIndex(eLastIndex);
							Enquiry.setCounter(eLastIndex);
						}	
						
						acc = new StudentAcc(userID,regCamps,CommitteeOf,UserWithdrawals,enquiries,userPoints);
					}
					else
					{
        				acc = new StudentAcc(userID,UserWithdrawals,enquiries);
					}
        		}
        		else
        		{
					if(!Camp.getAllCamps().isEmpty())
					{
						ArrayList<Camp> campsCreated = new ArrayList<Camp>();
						ArrayList<Reply> replies = new ArrayList<Reply>();
						for(Camp camp : Camp.getAllCamps())
						{
							if(camp.getCampInfo().getCampStaff().equals(userID))
							{
								campsCreated.add(camp);
								
							}
						}
						if(!Reply.getAllReply().isEmpty())
						{
							int rLastIndex = 0;
							for(Reply r: Reply.getAllReply())
							{
								rLastIndex = r.getID();
								if(r.getName().equals(userID))
								{
									replies.add(r);									
								}
							}
							Reply.setCounter(rLastIndex);
						}	

						
						acc = new StaffAcc(userID,campsCreated, replies);
						
					}
					else
					{
        				acc = new StaffAcc(userID);
					}
        		}
        		while(!logOut)
        		{
        			if (acc.getIsFirstTimeLogIn()) {
        				do {
        				System.out.println("Please change your password as it is your first log in.");
        				System.out.println("New Password:");
        				password = sc.nextLine().strip();
        				if (password.isBlank())
        					System.out.println("New Password cannot be empty. Please try again.");
        				else if (password.length() <= 8)
        					System.out.println("New Password length must contain 8 or more characters. Please try again.");
        				} while (password.isBlank() || password.length() < 8);
        				acc.changePassword(password);        				
        			}
        			
        			System.out.println("======= Welcome to CAMS =======");
        			System.out.println("Logged In as " + acc.getAccType() + ", " + acc.getName());
        			System.out.println("1. Display Options");
        			System.out.println("2. Change Password");
        			System.out.println("3. Log Out");
        			System.out.println("Enter your choice: ");
        			choice = sc.nextInt();
        			sc.nextLine(); //clear input stream of '\n'
        			if (choice == 1) {
        				do {
        				choiceToLogOut = acc.displayOptions();
        				accChoice = sc.nextInt();
        				sc.nextLine(); //clear input stream of '\n'
        				// keep this line if not there will be error after logging out
        				if (accChoice == choiceToLogOut)
        					continue;
        				acc.doChoice(accChoice, sc);
        				} while (accChoice != choiceToLogOut);
        			}
        			else if (choice == 2) {
        				do {
        					System.out.println("Enter new password:");
        					password = sc.nextLine().strip();
        					if (password.isBlank())
        						System.out.println("New Password cannot be empty. Please try again.");
        					else if (password.length() <= 8)
        						System.out.println("New Password length must contain 8 or more characters. Please try again.");
        				} while (password.isBlank() || password.length() < 8);
        				acc.changePassword(password);  
        			}
        			else {
        				logOut = true;
        				if(acc instanceof StudentAcc)
        				{
        					CampWithdrawal.WriteWithdrawal(allWithdrawals);
        				}
        				System.out.println("You have logged out successfully.");
        			}
        		}
        	}
        	else {
        		logInAttempts--;
        		System.out.println("Invalid credentials. Please try again.");
        	}
        } while (!quit && logInAttempts > 0);
        if (logInAttempts == 0)
        	System.out.println("Log In attempts exceeded.");
        System.out.println("Program terminating...");
    	sc.close();
    }
}
