import java.io.*;
import java.util.*;
/**
 * The class Main() reads the data from CSV file. After reading the data it 
 * classifies the data based on the categories and then it calculates that 
 * how much amount that the user could have saved and that can save him 
 * from getting divorce. 
 *  
 * @ author	Niraj Dedhia
 */
public class Main 
{
	public static HashMap<String, Categories> hm = new HashMap();
	public static Scanner s = new Scanner(System.in);
	
	/**
	 * main() : It calls the method to read the CSV file.
	 *
	 * @param  String arrays from command line
	 * 
	 * @return avoided
	 */
	public static void main(String args[])
	{
		readCSVFile();
	}
	
	/**
	 * readCSVFile() : It asks the user to enter the file number of user.
	 * Post reading, it reads the particular file in line by line.
	 * After reading each line it sends that line for classification.
	 *
	 * @param  avoided
	 * 
	 * @return avoided
	 */
	public static void readCSVFile()
	{
		System.out.println("Enter the File Number of the User in between 0 to 99 : ");
		System.out.println("(Example : for file 'user-85.csv' enter '85')");
		String fileName = "user-"+Integer.parseInt(s.next())+".csv";
		System.out.println("************* Displaying the results for '"+fileName+"' *************");
		System.out.println("");
		File file = new File(fileName);
		try
		{
			Scanner line = new Scanner(file);
			int i = 1;
			while(line.hasNext())
			{
				String lineData = line.nextLine();
				if(i>1)
				{
					dataSegmentation(lineData);
				}
				i++;
			}
			
			// Savings
			savings();	
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * dataSegmentation() : It takes a string and splits it by ','.
	 * After which it classifies the content whether that belongs to same
	 * month or not. On the basis of that it put the content in dictionary. 
	 * Also classifying the content in to categories.
	 * I have defined few categories which are : Paycheck, Rent, loan, 
	 * transportation, groceries, entertainments and other 
	 * 
	 * Paycheck : Content is related to paycheck(salary).
	 * Rent : Rent of house, electricity, water, cable, netflix, gas etc...
	 * Loan : student loan or housing or other loan.
	 * Transportation : Uber, Car, Fuel, Lyft, Bus, Train etc...
	 * Groceries : Groceries, mart, market etc...
	 * Entertainments : movies, bar, pub, restaurants etc...   
	 * Other : Probably it contains the hobbies.
	 * 
	 * @param  data which is a line from statement
	 * 
	 * @return avoided
	 */
	public static void dataSegmentation(String data)
	{
		Categories cat = new Categories();
		String[] str = data.split(",");
		String mmyyyy = ( (str[1].split("/"))[0] +"/"+ (str[1].split("/"))[2] );
		if(hm.containsKey(mmyyyy))
		{
			cat = (Categories)hm.get(mmyyyy);
		}
		
		// Calculation for Paycheck
		if(str[2].toLowerCase().contains("paycheck"))
		{
			cat.paycheck = (double)cat.paycheck + Double.parseDouble(str[3]);
		}
		// Calculation for House rent, Wifi, Electricity, Water, TV bills
		else if(str[2].toLowerCase().contains("rent") || str[2].toLowerCase().contains("cable") || str[2].toLowerCase().contains("water") || str[2].toLowerCase().contains("electric") || str[2].toLowerCase().contains("gas") || str[2].toLowerCase().contains("netflix") || str[2].toLowerCase().contains("credit card"))
		{
			//System.out.println(data);
			cat.rent_bill = (double)cat.rent_bill + Double.parseDouble(str[3]) * -1.0;
		}
		// Calculation for loan
		else if(str[2].toLowerCase().contains("loan") )
		{
			cat.loan = (double)cat.loan + Double.parseDouble(str[3]) * -1.0;
		}
		// Calculation for Tuition Fees
		else if(str[2].toLowerCase().contains("fees") || str[2].toLowerCase().contains("tuition") || str[2].toLowerCase().contains("course"))
		{
			cat.study_fees = (double)cat.study_fees + Double.parseDouble(str[3]) * -1.0;
		}
		// Calculation for Transportation
		else if(str[2].toLowerCase().contains("transportation") || str[2].toLowerCase().contains("lyft") || str[2].toLowerCase().contains("uber") || str[2].toLowerCase().contains("taxi") || str[2].toLowerCase().contains("train") || str[2].toLowerCase().contains("bus") )
		{
			//System.out.println(data);
			cat.transportation = (double)cat.transportation + Double.parseDouble(str[3]) * -1.0;
		}	
		// Calculation for Groceries
		else if(str[2].toLowerCase().contains("market") || str[2].toLowerCase().contains("grocer") || str[2].toLowerCase().contains("walmart") )
		{
			cat.groceries = (double)cat.groceries + Double.parseDouble(str[3]) * -1.0;
		}
		// Calculation for Online shopping
		else if(str[2].toLowerCase().contains(".com") || str[2].toLowerCase().contains("amazon") )
		{
			cat.eShop = (double)cat.eShop + Double.parseDouble(str[3]) * -1.0;
		}
		// Restaurants and Restaurant and pubs and clubs
		else if(str[2].toLowerCase().contains("restaurant") || str[2].toLowerCase().contains("club") || str[2].toLowerCase().contains("pub") || str[2].toLowerCase().contains("coffee") || str[2].toLowerCase().contains("food") || str[2].toLowerCase().contains("bar") || str[2].toLowerCase().contains("brewery") || str[2].toLowerCase().contains("wine") || str[2].toLowerCase().contains("beer") || str[2].toLowerCase().contains("movies") )
		{
			cat.entertainments = (double)cat.entertainments + Double.parseDouble(str[3]) * -1.0;
			//System.out.println(str[2]);
		}
		// Restaurants and other stuffs
		else
		{
			cat.other = (double)cat.other + Double.parseDouble(str[3]) * -1.0;
			//System.out.println(str[2]);
		}
		hm.put(mmyyyy, cat); // Putting back or adding the contents to dictionary having key as a month
	}
	
	/**
	 * savings() : Calculating the total amounts that he has spent on each items 
	 * monthly. 
	 * Taking the average of lowest and highest spending and basis of that suggesting
	 * that how much amount user could have saved from spending. 
	 * 
	 * @param  avoided
	 * 
	 * @return avoided
	 */
	public static void savings()
	{
		//System.out.println("In Saving");
		double min_other = 99999;
		double min_groceries = 99999;
		double min_entertainments = 99999;
		double min_eShop = 99999;
		double min_study_fees = 99999;
		double total_other = 0;
		double total_groceries = 0;
		double total_entertainments = 0;
		double total_eShop = 0;
		double total_study_fees = 0;
		double average = 0;
		double median = 0;
		double savings = 0;
		String detail = "";
		String total_savings = "";
		
		for(String key: hm.keySet())
		{
			Categories cat = (Categories)hm.get(key);
			// Calculating minimum and average spending on Other stuffs
			total_other += (double)cat.other;
			if((double)cat.other < min_other)
				min_other = (double)cat.other; 		
			
			// Calculating minimum and average spending on Online Shopping
			total_eShop += (double)cat.eShop;
			if((double)cat.eShop < min_eShop)
				min_eShop = (double)cat.eShop; 		
			
			// Calculating minimum and average spending on Groceries
			total_groceries += (double)cat.groceries;
			if((double)cat.groceries < min_groceries)
				min_groceries = (double)cat.groceries; 		
			
			// Calculating minimum and average spending on Entertainments
			total_entertainments += (double)cat.entertainments;
			if((double)cat.entertainments < min_entertainments)
				min_entertainments = (double)cat.entertainments; 
			
			// Calculating minimum and average spending on Entertainments
			total_study_fees += (double)cat.study_fees;
			if((double)cat.study_fees < min_study_fees)
				min_study_fees = (double)cat.study_fees; 
		}
		
		// Calculating the average of amount user spent per month.
		average = total_eShop/24.0;
		// Calculating the average amount user could have spent per month.
		median = ( ( min_eShop + average )/2 * 1.1 ); // average of minimum and actual spending
		
		if(median > 0.0 || average > 0.0)
		{
			detail += "--------------------Online Shopping-------------------------" + "\n";
			detail += "Average amount spent by user per month : $" + average + "\n";
			detail += "Amount user could have spent per month : $" + median + "\n";
			detail += "Amount user could have saved in 2 years : $" +( total_eShop - (median*24) ) +"\n";
			// Calculating and Total amount user could have saved.
			savings += ( total_eShop - (median*24) );
		}
			
		// Calculating the average of amount user spent per month.
		average = total_other/24.0;
		// Calculating the average amount user could have spent per month.
		median = ( ( min_other + average )/2 * 1.1 ); // average of minimum and actual spending and adding 10% to it
		
		if(median > 0.0 || average > 0.0)
		{
			detail += "-------------------------OTHER-------------------------------" + "\n";
			detail += "Average amount spent by user per month : $" + average + "\n";
			detail += "Amount user could have spent per month : $" + median + "\n";
			detail += "Amount user could have saved in 2 years : $" +( total_other - (median*24) ) +"\n";
			// Calculating and Total amount user could have saved.
			savings += ( total_other - (median*24) );
		}
		
		
		// Calculating the average of amount user spent per month.
		average = total_groceries/24.0;
		// Calculating the average amount user could have spent per month.
		median = ( ( min_groceries + average )/2 * 1.1 ); // average of minimum and actual spending and adding 10% to it	
		
		if(median > 0.0 || average > 0.0)
		{
			detail += "------------------------Groceries----------------------------" + "\n";
			detail += "Average amount spent by user per month : $" + average + "\n";
			detail += "Amount user could have spent per month : $" + median + "\n";
			detail += "Amount user could have saved in 2 years : $" +( total_groceries - (median*24) ) +"\n";
			// Calculating and Total amount user could have saved.
			savings += ( total_groceries - (median*24) );
		}
		
		
		// Calculating the average of amount user spent per month.
		average = total_entertainments/24.0;
		// Calculating the average amount user could have spent per month.
		median = ( ( min_entertainments + average )/2); // average of minimum and actual spending and adding 10% to it
		
		if(median > 0.0 || average > 0.0)
		{
			detail += "------------------------Entertainments------------------------" + "\n";
			detail += "Average amount spent by user per month : $" + average + "\n";
			detail += "Amount user could have spent per month : $" + median + "\n";
			detail += "Amount user could have saved in 2 years : $" +( total_entertainments - (median*24) ) +"\n";
			// Calculating and Total amount user could have saved.
			savings += ( total_entertainments - (median*24) );
		}
		
		total_savings += "====================================================================" + "\n";
		total_savings += " Total amount user could have saved in 2 years : $" + savings + "\n";
		total_savings += "====================================================================" + "\n";
		
		display(detail, total_savings,total_study_fees);
	}
	
	/**
	 * display() : Displaying the total amount user has spent and total 
	 * saving he could have done on the basis of above logic.
	 * 
	 * @param  avoided
	 * 
	 * @return avoided
	 */
	public static void display(String detail, String total_savings, double total_study_fees)
	{
		System.out.println(total_savings);
		System.out.println("********Details of the savings********");
		System.out.println(detail);
		if(total_study_fees > 0.0)
			System.out.println("User is a Student");
		else
			System.out.println("User is not a Student");
	}
	
}
