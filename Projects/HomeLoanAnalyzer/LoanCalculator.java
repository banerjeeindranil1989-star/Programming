import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import org.jfree.ui.RefineryUtilities;

public class LoanCalculator {
	
	/*
	 * Variables for the graph plot
	 */
	static Map<Integer,Double> amountOutstandingDataPoints = new LinkedHashMap<Integer,Double>();
	static Map<Integer,Double> principalPartDataPoints = new LinkedHashMap<Integer,Double>();
	static Map<Integer,Double> interestPartDataPoints = new LinkedHashMap<Integer,Double>();
	static Map<Integer,Double> amountOutstandingWoTopUpDataPoints = new LinkedHashMap<Integer,Double>();
	static Map<Integer,Double> savingsDataPoints = new LinkedHashMap<Integer,Double>();
	static Map<Integer,Double> paymentsDataPoints = new LinkedHashMap<Integer,Double>();
	static int currentMonthNumber = 0;
	
	public static Map<Integer,Double> getAmountOutstandingDataPoints(){
		return(amountOutstandingDataPoints);
	}
	public static Map<Integer,Double> getPrincipalPartDataPoints(){
		return(principalPartDataPoints);
	}
	public static Map<Integer,Double> getInterestPartDataPoints(){
		return(interestPartDataPoints);
	}
	public static Map<Integer,Double> getAmountOutstandingWoTopUpDataPoints(){
		return(amountOutstandingWoTopUpDataPoints);
	}
	public static int getCurrentMonthNumber(){
		return(currentMonthNumber);
	}
	public static Map<Integer,Double> getSavingsDataPoints(){
		return(savingsDataPoints);
	}
	public static Map<Integer,Double> getPaymentsDataPoints(){
		return(paymentsDataPoints);
	}

	private static Map<String,Double> getActualInterestAmounts() {
		Map<String,Double> actualInterestAmounts   = new HashMap<>();

		//Manual entry of the actual Interest
		actualInterestAmounts.put("FEBRUARY 2021", 15763.00 );
		actualInterestAmounts.put("MARCH 2021", 29168.00 );
		actualInterestAmounts.put("APRIL 2021", 28114.00 );
		actualInterestAmounts.put("MAY 2021", 28925.00 );
		actualInterestAmounts.put("JUNE 2021", 27877.00 );
		actualInterestAmounts.put("JULY 2021", 28628.00 );
		actualInterestAmounts.put("AUGUST 2021", 28451.00 );
		actualInterestAmounts.put("SEPTEMBER 2021", 26803.00 );
		actualInterestAmounts.put("OCTOBER 2021", 27433.00 );
		actualInterestAmounts.put("NOVEMBER 2021", 26425.00 );
		actualInterestAmounts.put("DECEMBER 2021", 27169.00 );

		actualInterestAmounts.put("JANUARY 2022", 27049.00 );
		actualInterestAmounts.put("FEBRUARY 2022", 23583.00 );
		actualInterestAmounts.put("MARCH 2022", 25269.00 );
		actualInterestAmounts.put("APRIL 2022", 24308.00 );
		actualInterestAmounts.put("MAY 2022", 24970.00 );
		actualInterestAmounts.put("JUNE 2022", 26308.00 );
		actualInterestAmounts.put("JULY 2022", 27843.00 );
		actualInterestAmounts.put("AUGUST 2022", 28655.00 );
		actualInterestAmounts.put("SEPTEMBER 2022", 28354.00 );
		actualInterestAmounts.put("OCTOBER 2022", 30875.00 );
		actualInterestAmounts.put("NOVEMBER 2022", 29747.00 );
		actualInterestAmounts.put("DECEMBER 2022", 31246.00 );

		actualInterestAmounts.put("JANUARY 2023", 31685.00 );
		actualInterestAmounts.put("FEBRUARY 2023", 28849.00 );
		actualInterestAmounts.put("MARCH 2023", 32196.00 );
		actualInterestAmounts.put("APRIL 2023", 31028.00 );
		actualInterestAmounts.put("MAY 2023", 31915.00 );
		actualInterestAmounts.put("JUNE 2023", 30766.00 );
		actualInterestAmounts.put("JULY 2023", 31629.00 );
		actualInterestAmounts.put("AUGUST 2023", 29089.00 );
		actualInterestAmounts.put("SEPTEMBER 2023", 27506.00 );
		actualInterestAmounts.put("OCTOBER 2023", 28235.00 );
		actualInterestAmounts.put("NOVEMBER 2023", 27176.00 );
		actualInterestAmounts.put("DECEMBER 2023", 27890.00 );		
		actualInterestAmounts.put("JANUARY 2024", 27719.00 );		

		actualInterestAmounts.put("FEBRUARY 2024", 24086.00 );		
		actualInterestAmounts.put("MARCH 2024", 24037.00 );
		actualInterestAmounts.put("APRIL 2024", 21104.00 );
		actualInterestAmounts.put("MAY 2024", 21608.00 );
		actualInterestAmounts.put("JUNE 2024", 20630.00 );
		actualInterestAmounts.put("JULY 2024", 20821.00 );
		actualInterestAmounts.put("AUGUST 2024", 19377.00 );
		actualInterestAmounts.put("SEPTEMBER 2024", 18438.00 );
		actualInterestAmounts.put("OCTOBER 2024", 18282.00 );
		actualInterestAmounts.put("NOVEMBER 2024", 17326.00 );
		actualInterestAmounts.put("DECEMBER 2024", 17676.00 );
		
		actualInterestAmounts.put("JANUARY 2025", 16625.00 );
		actualInterestAmounts.put("FEBRUARY 2025", 12496.00 );
		actualInterestAmounts.put("MARCH 2025", 13120.00 );
		actualInterestAmounts.put("APRIL 2025", 9475.00 );
		actualInterestAmounts.put("MAY 2025", 9040.00 );
		actualInterestAmounts.put("JUNE 2025", 6752.00 );		
		actualInterestAmounts.put("JULY 2025", 6156.00 );
		actualInterestAmounts.put("AUGUST 2025", 5662.00 );
		actualInterestAmounts.put("SEPTEMBER 2025", 4538.00 );
		actualInterestAmounts.put("OCTOBER 2025", 3902.00 );
		actualInterestAmounts.put("NOVEMBER 2025", 3015.00 );
		

		return(actualInterestAmounts );
	}

	private static Map<LocalDate,Double> getInterestRateChanges() {
		Map<LocalDate,Double> interestRateChanges   = new HashMap<>();

		/* Only put the Interest Rate if changed, no need to put interest
		 *  rate for every month
		 */

		interestRateChanges.put(LocalDate.of(2021,2,12), 7.05);// Continued as 7.05 till May 22
		interestRateChanges.put(LocalDate.of(2022,6,7), 7.45);
		interestRateChanges.put(LocalDate.of(2022,6,10), 7.95);
		interestRateChanges.put(LocalDate.of(2022,8,15), 8.45);
		interestRateChanges.put(LocalDate.of(2022,10,1), 8.95);
		interestRateChanges.put(LocalDate.of(2022,12,15), 9.3);
		interestRateChanges.put(LocalDate.of(2023,1,21), 9.33);
		interestRateChanges.put(LocalDate.of(2023,2,17), 9.55);
		interestRateChanges.put(LocalDate.of(2024,3,23), 8.4);
		interestRateChanges.put(LocalDate.of(2025,2,15), 8.15);
		interestRateChanges.put(LocalDate.of(2025,4,15), 7.9);
		interestRateChanges.put(LocalDate.of(2025,6,15), 7.4);
		interestRateChanges.put(LocalDate.of(2025,8,15), 7.15);

		return( interestRateChanges );
	}

	private static Map<LocalDate,Double> getTransactions() {
		Map<LocalDate,Double> transactions      = new HashMap<>();

		/*
		 * PLEASE READ
		 * transaction map does not include interest & EMI's paid/payable
		 * These are automatically adjusted.
		 * YYYY,MM,DD
		 */

		transactions.put(LocalDate.of(2021,2,12), 4560000.00);
		transactions.put(LocalDate.of(2021,2,17), 328000.00 );
		transactions.put(LocalDate.of(2021,2,20), 20102.00  ); // 10000 + 102 + 10000
		transactions.put(LocalDate.of(2021,2,25), 118.00    );
		transactions.put(LocalDate.of(2021,2,26), -10000.00 );
		transactions.put(LocalDate.of(2021,7,06), -10220.00 );
		transactions.put(LocalDate.of(2021,8,03), -15000.00 );
		transactions.put(LocalDate.of(2021,8,10), -50000.00 );
		transactions.put(LocalDate.of(2021,8,18), 100000.00 );// 328000 - 228000 (Same day transactions )
		transactions.put(LocalDate.of(2021,8,31), -100000.00 );
		transactions.put(LocalDate.of(2021,9,10), -50000.00 );
		transactions.put(LocalDate.of(2021,9,11), -20000.00 );
		transactions.put(LocalDate.of(2022,2,14), -250000.00 );
		transactions.put(LocalDate.of(2023,8,5), -110000.00 );
		transactions.put(LocalDate.of(2023,8,7), -250000.00 );
		transactions.put(LocalDate.of(2024,2,5), -190000.00 );	
		transactions.put(LocalDate.of(2024,2,12), -50000.00 );
		transactions.put(LocalDate.of(2024,2,14), -50000.00 );
		transactions.put(LocalDate.of(2024,6,22), -45000.00 );
		transactions.put(LocalDate.of(2024,7,29), -100000.00 );
		transactions.put(LocalDate.of(2024,8,6), -100000.00 );
		transactions.put(LocalDate.of(2024,10,8), -100000.00 );
		transactions.put(LocalDate.of(2025,1,21), -100000.00 );
		transactions.put(LocalDate.of(2025,1,22), -250000.00 );
		transactions.put(LocalDate.of(2025,2,1), -50000.00 );
		transactions.put(LocalDate.of(2025,2,3), -50000.00 );		
		transactions.put(LocalDate.of(2025,3,25), -100000.00 );
		transactions.put(LocalDate.of(2025,3,28), -75000.00 );
		transactions.put(LocalDate.of(2025,4,1), -25000.00 );
		transactions.put(LocalDate.of(2025,4,3), -180000.00 );
		transactions.put(LocalDate.of(2025,4,6), -100000.00 );
		transactions.put(LocalDate.of(2025,4,27), -25000.00 );
		transactions.put(LocalDate.of(2025,6,6), -280000.00 );		
		transactions.put(LocalDate.of(2025,7,26), -30000.00 );
		transactions.put(LocalDate.of(2025,8,26), -60000.00 );
		transactions.put(LocalDate.of(2025,9,13), -100000.00 );
		transactions.put(LocalDate.of(2025,9,27), -25000.00 );
		transactions.put(LocalDate.of(2025,10,26), -98000.00 );
		transactions.put(LocalDate.of(2025,11,29), -20000.00 );
		transactions.put(LocalDate.of(2025,12,3), -250000.00 );
		transactions.put(LocalDate.of(2025,12,24), -174580.00 );
		
		
		// All manual adjustments reason unknown is listed here
		// None		
		
		//Planned ones
		
		
		return( transactions );
	}
	

	private static Map<LocalDate,Double> getPlusTransactions() {
		Map<LocalDate,Double> plusTransactions      = new HashMap<>();

		/*
		 * PLEASE READ
		 * transaction map does not include interest & EMI's paid/payable & Top Ups
		 * These are automatically adjusted.
		 */

		plusTransactions.put(LocalDate.of(2021,2,12), 4560000.00);
		plusTransactions.put(LocalDate.of(2021,2,17), 328000.00 );
		plusTransactions.put(LocalDate.of(2021,2,20), 20102.00  ); // 10000 + 102 + 10000
		plusTransactions.put(LocalDate.of(2021,2,25), 118.00    );
		
		return( plusTransactions );
	}

	public static void main(String[] args) {

		System.out.println("Home Loan Calculator !!");

		int tenure_in_years    = 14;

		double amountOutstanding    = 0;
		double amountOutstandingWoTopUp    = 0;
		double emi    = 48935.00;
		double monthlyInterestTotal = 0;
		double monthlyInterestWoTopUpTotal = 0;
		double actualInterestAmountsForGraph = 0;
		double payments = 0;
		int monthNumber = 1;
		LocalDate estimatedLoanClearDate = null;
		
		Map<LocalDate,Double> transactions      = getTransactions();
		Map<LocalDate,Double> interestRateChanges   = getInterestRateChanges();
		Map<String,Double> actualInterestAmounts   = getActualInterestAmounts();
		Map<LocalDate,Double> interestRates   = new HashMap<>();
		Map<LocalDate,Double> plusTransactions      = getPlusTransactions();


		LocalDate homeLoanStartDate = LocalDate.of(2021,2,12);
		LocalDate homeLoanEndDate = homeLoanStartDate.plusYears(tenure_in_years);
		//LocalDate homeLoanEndDate = LocalDate.of(2025, 4, 30);

		LocalDate currentDate = homeLoanStartDate;

		// Here the interest Rate Map is getting created/calculated
		Double tempInterestRate= 0.0;
		while(currentDate.isBefore(homeLoanEndDate.plusMonths(1)))
		{
			if(interestRateChanges.containsKey(currentDate))
			{
				tempInterestRate = interestRateChanges.get(currentDate);

				interestRates.put(currentDate, interestRateChanges.get(currentDate));

			}
			else
				interestRates.put(currentDate,tempInterestRate);

			currentDate = currentDate.plusDays(1);
		}

		// Here is the main program where the magic happens

		currentDate = homeLoanStartDate;

		while(currentDate.isBefore(homeLoanEndDate.plusDays(1)))
		{
			int noOfDaysInCurrentYear = (currentDate.isLeapYear())?366:365;

			if(monthlyInterestTotal == 0)
				System.out.println("Date -> Amount Outstanding -> Amount Outstanding Wo TopUp -> Interest -> Interest Wo TopUp -> Rate");

			System.out.print(currentDate+" -> ");

			for(Map.Entry<LocalDate, Double> transaction : transactions.entrySet())
			{
				if(transaction.getKey().isEqual(currentDate))
					amountOutstanding = amountOutstanding+transaction.getValue();
			}
			
			for(Map.Entry<LocalDate, Double> plusTransaction : plusTransactions.entrySet())
			{
				if(plusTransaction.getKey().isEqual(currentDate))
					amountOutstandingWoTopUp = amountOutstandingWoTopUp+plusTransaction.getValue();
			}

			if(currentDate.getDayOfMonth() == 5) {
				amountOutstanding = amountOutstanding - emi;
				amountOutstandingWoTopUp = amountOutstandingWoTopUp - emi;
			}

			System.out.print(String.format("%.2f",amountOutstanding) +" -> ");
			System.out.print(String.format("%.2f",amountOutstandingWoTopUp) +" -> ");

			double dailyInterestRate = interestRates.get(currentDate );
			double dailyInterest = amountOutstanding*dailyInterestRate/(100*noOfDaysInCurrentYear);
			double dailyInterestWoTopUp = amountOutstandingWoTopUp*dailyInterestRate/(100*noOfDaysInCurrentYear);

			System.out.print(String.format("%.2f", dailyInterest) +" -> ");
			System.out.println(String.format("%.2f", dailyInterestWoTopUp) +" -> " + dailyInterestRate);
			LocalDate previousDate = currentDate;

			currentDate = currentDate.plusDays(1);
			monthlyInterestTotal = monthlyInterestTotal + dailyInterest;
			monthlyInterestWoTopUpTotal = monthlyInterestWoTopUpTotal + dailyInterestWoTopUp;

			if(previousDate.getMonthValue() != currentDate.getMonthValue())
			{
				String monthYear =  previousDate.getMonth().getDisplayName(TextStyle.FULL,Locale.US)+" "+previousDate.getYear();
				payments = payments + emi;
				System.out.println("============================================================================");
				System.out.println("Amount Outstanding for the month of "+ monthYear +" = " + Math.round(amountOutstanding));
				System.out.println("Amount Outstanding Without Top Up for "+ monthYear +" = " + Math.round(amountOutstandingWoTopUp));
				System.out.println("Interest for the month of "+ monthYear +" = " + Math.round(monthlyInterestTotal));
				System.out.println("Interest Without Top Up for "+ monthYear +" = " + Math.round(monthlyInterestWoTopUpTotal));
				System.out.println("Interest Rate : " + dailyInterestRate + "%");
				System.out.println("Actual Interest: "+ actualInterestAmounts.get(monthYear.toUpperCase()));
				System.out.println("============================================================================");
				System.out.println();				
				if( actualInterestAmounts.get(monthYear.toUpperCase()) == null )
					actualInterestAmountsForGraph = monthlyInterestTotal;
				else
					actualInterestAmountsForGraph = actualInterestAmounts.get(monthYear.toUpperCase());
				amountOutstandingDataPoints.put(monthNumber, amountOutstanding/100);
				interestPartDataPoints.put( monthNumber, Math.floor(actualInterestAmountsForGraph));
				principalPartDataPoints.put(monthNumber, emi-actualInterestAmountsForGraph);
				amountOutstandingWoTopUpDataPoints.put(monthNumber, amountOutstandingWoTopUp/100);
				savingsDataPoints.put(monthNumber, (amountOutstandingWoTopUp-amountOutstanding)/100);
				paymentsDataPoints.put(monthNumber, payments/100);
				monthNumber++;
				
				
				String currentMonthYear = java.time.LocalDate.now().getMonth().getDisplayName(TextStyle.FULL,Locale.US)+" "+java.time.LocalDate.now().getYear();
				if( currentMonthYear.equals(monthYear))
					currentMonthNumber = monthNumber;
				else
					currentMonthNumber = monthNumber-1;
				
				amountOutstanding = Math.round(amountOutstanding+monthlyInterestTotal);
				amountOutstandingWoTopUp = Math.round(amountOutstandingWoTopUp+monthlyInterestTotal);
				monthlyInterestTotal = 0;
				monthlyInterestWoTopUpTotal = 0;
			}
			

			if( amountOutstanding < 0)
			{	
				estimatedLoanClearDate = currentDate;
				break;
			};
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String chartHeading = "Amount vs Month No.\n" + 
							  "Saved till Month No. (" + currentMonthNumber + "):\n" +
							  amountOutstandingWoTopUpDataPoints.get(currentMonthNumber)*100 + "-" + amountOutstandingDataPoints.get(currentMonthNumber)*100 + "=" + savingsDataPoints.get(currentMonthNumber)*100+ 
							  "\n Estimated Loan Clear Date:" +  estimatedLoanClearDate.format(formatter);
		
		HomeLoanTrend chart = new HomeLoanTrend(
		         "Home Loan Trend" ,
		         chartHeading);

		      chart.pack( );
		      RefineryUtilities.centerFrameOnScreen( chart );
		      chart.setVisible( true );
	}

}