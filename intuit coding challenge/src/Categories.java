/**
 * Categories() This class is used to store the values in different categories.
 * Declare and initialize the different categories. 
 *  
 * @ author	Niraj Dedhia
 */
public class Categories 
{
	double paycheck; // Store the value of Categories
	double rent_bill; // Store the value of Rents
	double loan; // Store the value of Loans
	double transportation; // Store the value of Transportations
	double groceries; // Store the value of Groceries
	double eShop; // Store the value of Online shopping
	double study_fees; // Store the value of Study fees
	double entertainments; // Store the value of Entertainments
	double other; // Store the value of Other (Hobbies)
	
	/**
	 * Categories() : Constructor to initialize the varoables.
	 * 
	 * @param  avoided
	 * 
	 * @return avoided
	 */
	public Categories()
	{
		this.paycheck=0;
		this.rent_bill=0;
		this.transportation = 0;
		this.loan=0;
		this.study_fees=0;
		this.groceries=0;
		this.entertainments=0;
		this.eShop=0;
		this.other=0;
	}
}
