package dsProject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import dsProject.DiffDate;

public class Node {
	String name;
	String date;
	double amount;
	int priority;
	Node next;
	static Node head=null;
	static void enqueue(String name,String date,double amount,int p)
	{
		Node newnode=new Node();
		newnode.name=name;
		newnode.date=date;
		newnode.amount=amount;
		newnode.priority=p;
		newnode.next=null;
		Node rear = head;
		if(head==null)
		{
			head = newnode;
			return;
		}
		if (head.priority > p)
	    {
	        newnode.next = head;
	        head = newnode;
	    }
	    else
	    {
	        while (rear.next != null && rear.next.priority < p)
	        {
	            rear = rear.next;
	        }
	        newnode.next = rear.next;
	        rear.next = newnode;
	    }
	}
	static void dequeue()
	{
	    head = head.next;
	}
	
	static void display()
	{
		Node temp=head;
		if(head == null)
		{
			System.out.println(">>>>>>>>>> No Notifications <<<<<<<<<<");
			return;
		}
		if(temp.priority<0)
			System.out.println(">>>>>>>>>> Over Due <<<<<<<<<<");
		
		System.out.println("Bill Details :");
		System.out.println(temp);
	}
	static boolean isEmpty()
	{
		return (head==null);
	}
	
	static int getPriority(String date)
	{
		String d= date;
	    int day = Integer.parseInt(d.substring(0,2));
	    int month = Integer.parseInt(d.substring(3,5));
	    int year = Integer.parseInt(d.substring(6,10));
	    
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();  
		String d1 = dtf.format(now);
	    int day1 = Integer.parseInt(d1.substring(8,10));
	    int month1 = Integer.parseInt(d1.substring(5,7));
	    int year1 = Integer.parseInt(d1.substring(0,4));
	    
	    
	    LocalDate present_date = LocalDate.of(year1, month1, day1);   
	    LocalDate upcoming_date = LocalDate.of(year, month, day);
	    return (DiffDate.find(present_date,upcoming_date));
	}
	static void totalDisplay(File df) throws Exception
	{
		Node temp=head;
		String s="";
		String done = "       ✔ ";
		System.out.println("_________________________________________________________________________\n");
		System.out.println("\tName of the Bill\tDate\t\tAmount\t\tStatus");
		System.out.println("_________________________________________________________________________\n");
		Scanner sf = new Scanner(df);
		while(sf.hasNext())
		{
			System.out.println("\t"+sf.nextLine()+"\t\t\t"+sf.nextLine()+"\t"+sf.nextLine()+"\t\t"+done);
		}
		sf.close();		
		while(temp!=null)
		{
			if(temp.priority<0)
			{
				s="[ Due ]";
			}
			else
			{
				s="";
			}
			System.out.println("\t"+temp.name + "\t\t\t" + temp.date + "\t" + temp.amount+"\t\t"+s);
			temp=temp.next;
		}
		System.out.println("_________________________________________________________________________");
	}
	
	static void update(File f)
	{
		Node temp=head;
		try {
		BufferedWriter bf=new BufferedWriter(new FileWriter(f));
		while(temp!=null)
		{
			bf.write(temp.name);
			bf.newLine();
			bf.write(temp.date);
			bf.newLine();
			bf.write(Double.toString(temp.amount));
			bf.newLine();
			temp=temp.next;
		}
		bf.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public static String message()
	{
		Node temp = head;
		String str = "";
		if (head == null)
			return "Empty";
		while(temp.priority<0 && temp.next!=null)
		{
			temp=temp.next;
		}
		if (temp.next == null && temp.priority < 0)
			return "Empty";
		if(temp.priority == 0)
			str = " [i.e today]";
		return "Your "+temp.name+" bill is due. Its last date is "+temp.date+str+". So, kindly pay Rs."+temp.amount+" without fail. Ignore if already paid. Thank You.";
	}
	
	static void settings(Scanner sc,File f,File p_f,File df,String password,String phone_no)
	{
		try {
		System.out.print("Do you want to manage your settings : ");
		String replay = (sc.next()).toUpperCase();
			if(replay.equals("YES"))
			{
			while(true)
			{
				System.out.println("⚙ Settings");
				System.out.println("1 -> 🔐 Change Password\n2 -> ☎ Change Phone Number\n3 -> 📖 Clear History\n4 -> 🗑 Delete Account\n5 -> 📤 Exit");
				System.out.print("Enter your choice 🖥 : "); 
				int c = sc.nextInt();
				switch(c)
				{
				case 1:	BufferedWriter bw = new BufferedWriter(new FileWriter(p_f));
						System.out.print("🔐 Enter new password : ");
						String new_password = sc.next();
						bw.write("Active");
						bw.newLine();
						bw.write(new_password);
						bw.newLine();
						bw.write(phone_no);
						bw.newLine();
						bw.close();
						break;
				case 2:	BufferedWriter bw1 = new BufferedWriter(new FileWriter(p_f));
						System.out.print("📠 Enter new phone number :");
						String new_phone_no = sc.next();
						phone_no = new_phone_no; 
						bw1.write("Active");
						bw1.newLine();
						bw1.write(password);
						bw1.newLine();
						bw1.write(new_phone_no);
						bw1.close();
						break;
				case 3:	System.out.println("💬 This will totally clear you history");
						System.out.print("Are you sure do you really want to clear your history : ");
						String r1 = (sc.next()).toUpperCase();
						if(r1.equals("YES"))
						{
							new FileWriter(f, false).close();
							new FileWriter(df, false).close();
							System.out.println(">>>>> Your History has been cleared sucessfully <<<<<");
							System.exit(0);
						}
						break;
				case 4: System.out.print("Are you sure do you really want to delete your account : ");
						String r = (sc.next()).toUpperCase();
						if(r.equals("YES"))
						{
							BufferedWriter bw2 = new BufferedWriter(new FileWriter(p_f));
							bw2.write("DeActive");
							bw2.newLine();
							bw2.write(password);
							bw2.newLine();
							bw2.write(phone_no);
							bw2.close();
							System.out.println(">>>>> Your account has been deleted sucessfully <<<<<");
							System.exit(0);
						}
						break;
				case 5:	return;
				}
			}
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	static void done(File df) throws IOException
	{
		Node temp = head;
		BufferedWriter bp=new BufferedWriter(new FileWriter(df,true));
		bp.write(temp.name);
		bp.newLine();
		bp.write(temp.date);
		bp.newLine();
		bp.write(Double.toString(temp.amount));
		bp.newLine();
		bp.close();
	}
	
	@Override
	public String toString() {
		return "[Name of the Bill =" + name + ", Date=" + date + ", Amount=" + amount+"]";
	}
	public static void main(String args[]) throws Exception
	{
		Scanner sc=new Scanner(System.in);
		String name;
		String date;
		double amount;
		int p;
		
		String status = null;
		String password = null;
		String phone_no = null;
		boolean re_activated = false;
		
		System.out.print("Enter User Id 💳 : ");
		String user_name = sc.next();
		File f=new File("D:\\Java Programs\\Data Structures\\src\\dsProject\\"+user_name+".txt");
		File pf=new File("D:\\Java Programs\\Data Structures\\src\\dsProject\\"+user_name+"_details.txt");
		File df=new File("D:\\Java Programs\\Data Structures\\src\\dsProject\\"+user_name+"_done.txt");
		boolean b = f.exists();
		boolean is_new = false;
		if(!b)
		{
			System.out.println(">>>>> New User Id Found <<<<<\n");
			System.out.print("Do you want to create an account : ");
			String st =sc.next();
			st = st.toUpperCase();
			switch(st)
			{
			case "YES":	f.createNewFile();
						pf.createNewFile();
						df.createNewFile();
						System.out.print("-----> Create a Password <-----\n🔐 Enter a password : ");
						password = sc.next();
						System.out.print("📠 Enter your Phone Number : ");
						phone_no = sc.next();
						BufferedWriter bp=new BufferedWriter(new FileWriter(pf,true));
						bp.write("Active");
						bp.newLine();
						bp.write(password);
						bp.newLine();
						bp.write(phone_no);
						bp.close();
						is_new = true;
						break;
			case "NO":	System.exit(0);
			}
		}
		Scanner sp=new Scanner(pf);
		if (!is_new)
		{
			status = sp.nextLine();
			if (status.equals("DeActive"))
			{
				System.out.print("Your account is deactive do you want to activate it : ");
				String r = sc.next().toUpperCase();
				if (r.equals("YES"))
				{
					System.out.print("Do you want to retrive your data 🗃 : "); 
					String r1 = (sc.next()).toUpperCase();
					if(r1.equals("NO"))
					{
						new FileWriter(f, false).close();
						new FileWriter(df, false).close();
					}
					System.out.print("-----> Create a Password <-----\n🔐 Enter a password : ");
					password = sc.next();
					System.out.print("📠 Enter your Phone Number : ");
					phone_no = sc.next();
					BufferedWriter ra=new BufferedWriter(new FileWriter(pf));
					ra.write("Active");
					ra.newLine();
					ra.write(password);
					ra.newLine();
					ra.write(phone_no);
					ra.close();
					re_activated = true;
				}
				else
					System.exit(0);
			}
			if(!re_activated)
			{
				System.out.print("Enter password 🔐 : ");
				password = sp.nextLine();
				if (!(sc.next().equals(password)))
				{
					System.out.println("Sorry!!! Wrong Password 🔐.");
					sp.close();
					System.exit(0);
				}
				phone_no = sp.nextLine();
			}
		}
		Scanner sf=new Scanner(f);
		int count=0;
		while(sf.hasNext())
		{
			count++;
			name=sf.nextLine();
			date=sf.nextLine();
			amount=Double.parseDouble(sf.nextLine());
			p=getPriority(date);
			enqueue(name,date,amount,p);
		}
		if(count == 0)
		{
			System.out.println("\nEnter:\n1 -> 📄 Enter a Bill\n2 -> ☣ Exit");
			System.out.print("Enter your choice 🖥 : "); 
			int c=sc.nextInt();
			switch(c)
			{
			case 1:	System.out.print("📄 Bill Name :");
					name = sc.next();
					System.out.print("🗓 Last Date (DD/MM/YYYY) :");
					date= sc.next();
					System.out.print("💰 Amount to be Paid :");
					amount = sc.nextDouble();
					p=getPriority(date);
					BufferedWriter bf=new BufferedWriter(new FileWriter(f,true));
					bf.write(name);
					bf.newLine();
					bf.write(date);
					bf.newLine();
					bf.write(Double.toString(amount));
					bf.newLine();
					bf.close();
					enqueue(name,date,amount,p);
					break;
			case 2: sc.close();
					sp.close();
					sf.close();
					System.exit(0);
			}
		}
		while(true) {
			System.out.println("\n>>>>>  Menu  <<<<<");
			System.out.println("Enter:\n1 -> 📄 New Bill\n2 -> 📝 Check\n3 -> 📈 All Notifications\n4 -> ⚙ Settings\n5 -> ☣ Exit"); 
			System.out.print("Enter your choice 🖥 : "); 
			int ch=sc.nextInt();
			switch(ch)
			{
			case 1:	System.out.print("📄 Bill Name :");
					name = sc.next();
					System.out.print("🗓 Last Date (DD/MM/YYYY) :");
					date= sc.next();
					System.out.print("💰 Amount to be Paid :");   
					amount = sc.nextDouble();
					p=getPriority(date);
					BufferedWriter bf=new BufferedWriter(new FileWriter(f,true));
					bf.write(name);
					bf.newLine();
					bf.write(date);
					bf.newLine();
					bf.write(Double.toString(amount));
					bf.newLine();
					bf.close();
					enqueue(name,date,amount,p);
					break;
			case 2:	display();
					if(!isEmpty())
					{
						System.out.println("1 -> If Done ✔\n2 -> If Not Done 🚫\n3 -> Delete ✖");
						int c=sc.nextInt();
						if(c == 1)
						{
							done(df);
							dequeue();
						}
						if(c== 3)
							dequeue();
					}
					break;
			case 3:	totalDisplay(df);
					break;
			case 4:	settings(sc, f, pf, df, password, phone_no);
					break;
			case 5:	String s = message();
					if(s!="Empty")
						SMS.sendSms(message(), phone_no);
					update(f);
					System.out.println(s);
					sc.close();
					sp.close();
					sf.close();
					System.exit(0);
			}
		}
	}
}
