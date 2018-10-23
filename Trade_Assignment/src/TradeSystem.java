import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import enums.Currency;
import enums.Entity;
import enums.TradeAction;
import enums.WWProfile;

/**
 * Main class for the trade application. This class contains
 * the entry point to the application 'Main'.
 * 
 * @author Scott Smith
 * @version 1.0
 */
public class TradeSystem
{
	/* List to hold the sample trade instruction data. */
	private static List<TradeInstruction> trades = new ArrayList<TradeInstruction>();
	/* Lists to hold all the transactions that take place.*/
	private static List<Transaction> incomingTransactions = new ArrayList<Transaction>();
	private static List<Transaction> outgoingTransactions = new ArrayList<Transaction>();
	/* Specify the date pattern we want based on sample data provided. */
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	/* Variables to hold the total incoming and outgoing totals. */
	private static float totalOutgoing = 0.0f;
	private static float totalIncoming = 0.0f;
	
	/**
	 * Entry point of the application.
	 * 
	 * @param No parameters are required to be passed to this application.
	 */
	public static void main(String[] args) {	
		setupTrades();
		if(!trades.isEmpty()) {
			
			trades.forEach(trade -> assignWorkingWeekProfile(trade));
			trades.forEach(trade -> adjustSettlementDate(trade));
			trades.forEach(trade -> performTrade(trade));
			
			generateReportData();
		}
		else {
			System.out.println("Error - You have not populated the system with any Trade Instructions.");
		}
	}
	
	/**
	 * Populates the 'trades' list with some sample trade instructions.
	 */
	private static void setupTrades() {
		
		try {
			/* Populating the trades list with some test Trade Instructions.
			 * To add more trades, create a duplicate, then adjust the parameters
			 * given for the construction of the TradeInstruction
			 * 
			 * all numeric values should be positive. Zero, or negative numbers
			 * will throw an exception.
			 * 
			 * Dates should be formed using the UK format.
			 * example: 1 October 2018 should be "01-10-2018".
			 * Malformed dates will throw an exception.
			 * */
			trades.add(new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.SGP,
				(LocalDate.parse("01-01-2016", formatter)), (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f ));
			trades.add(new TradeInstruction(Entity.BAR, TradeAction.BUY, 0.50f, Currency.AED,
				(LocalDate.parse("05-01-2016", formatter)), (LocalDate.parse("07-01-2016", formatter)), 400, 90.40f ));
			trades.add(new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.EUR,
				(LocalDate.parse("01-01-2016", formatter)), (LocalDate.parse("09-01-2016", formatter)), 300, 100.25f ));
			trades.add(new TradeInstruction(Entity.BAR, TradeAction.SELL, 0.22f, Currency.AED,
				(LocalDate.parse("05-01-2016", formatter)), (LocalDate.parse("07-01-2016", formatter)), 100, 90.50f ));
			trades.add(new TradeInstruction(Entity.FOO, TradeAction.SELL, 0.52f, Currency.SAR,
				(LocalDate.parse("06-01-2016", formatter)), (LocalDate.parse("10-01-2016", formatter)), 400, 100.25f ));
			trades.add(new TradeInstruction(Entity.BAZ, TradeAction.SELL, 0.80f, Currency.GBP,
				(LocalDate.parse("07-01-2016", formatter)), (LocalDate.parse("14-01-2016", formatter)), 600, 86.20f )); 
		}
		catch (IllegalArgumentException _exception) {
			System.out.println("An exception was raised: " + _exception.toString());
			/* We shouldn't continue, quitting application. */
			System.out.println("Developer forced the application to quit.");
			System.exit(1);
		}
		catch (java.time.format.DateTimeParseException _exception) {
			System.out.println("An exception was raised: " + _exception.toString());
			/* We shouldn't continue, quitting application. */
			System.out.println("Developer forced the application to quit.");
			System.exit(1);
			
		}
	}
	
	/**
	 * This method takes in a TradeInstruction object and sets a
	 * 'Working Week Profile' to the TradeInstruction based on it's
	 * assigned currency.
	 * 
	 * @param _ti The object that holds the Trade Instruction details.
	 */
	public static void assignWorkingWeekProfile(TradeInstruction _ti) {
		
		if(null != _ti.getCurrency()) {
			switch(_ti.getCurrency())
			{
			case AED:
				_ti.setWWProfile(WWProfile.SUN_THUR);
				break;
			case SAR:
				_ti.setWWProfile(WWProfile.SUN_THUR);
				break;
			default:
				_ti.setWWProfile(WWProfile.MON_FRI);
				break;
			}
		}
		else {
			System.out.println("Error - No currency has been assigned.");
		}
	}
	
	/**
	 * This method takes in a TradeInstruction object and makes an
	 * adjustment to the Trade Instruction if the requested Settlement
	 * Date does not land on a working day.
	 * 
	 * @param _ti The object that holds the Trade Instruction details.
	 */
	public static void adjustSettlementDate(TradeInstruction _ti) {
		
		java.time.DayOfWeek settlementDoW = _ti.getSettlementDate().getDayOfWeek();
		
		if(null != _ti.getWWProfile()) {
			switch(_ti.getWWProfile()) {
			case MON_FRI:
				if (java.time.DayOfWeek.SATURDAY == settlementDoW || java.time.DayOfWeek.SUNDAY == settlementDoW) {
					LocalDate newDate = _ti.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
					_ti.setSettlementDate(newDate);
				}
				break;
				
			case SUN_THUR:
				if (java.time.DayOfWeek.FRIDAY == settlementDoW || java.time.DayOfWeek.SATURDAY == settlementDoW) {
					LocalDate newDate = _ti.getSettlementDate().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					_ti.setSettlementDate(newDate);
				}
				break;
			}
		}
		else {
			System.out.println("Error - No profile has been assigned.");
		}
	}
	
	/**
	 * This method takes in a Trade Instruction object, and performs
	 * the dummy transaction. Depending on the request, Buy/Sell, the
	 * transaction is recorded in the appropriate Transaction log. 
	 * 
	 * @param _ti The object that holds the Trade Instruction details.
	 */
	public static void performTrade(TradeInstruction _ti) {
		
		/* Formula for performing the trade. Made final as this won't change. */
		final float tradeValueUSD = (_ti.getPricePerUnit() * _ti.getUnits() * _ti.getAgreedFx());
		
		if(null != _ti.getTradeAction()) {
			switch(_ti.getTradeAction()) {
				case BUY:
					outgoingTransactions.add(new Transaction(_ti.getEntityName(),
						_ti.getTradeAction(), tradeValueUSD, _ti.getSettlementDate()));
					break;
				case SELL:
					incomingTransactions.add(new Transaction(_ti.getEntityName(),
						_ti.getTradeAction(), tradeValueUSD, _ti.getSettlementDate()));
					break;
			}
		}
		else {
			System.out.println("Error - Not Trade Action assigned.");
		}
	}

	/**
	 * This method is responsible for generating the final outputted report.
	 * It uses the incoming and outgoing transaction logs, and produces sorted
	 * maps which are then output.
	 */
	public static void generateReportData() {
		
		/* Using an 'AtomicInteger' so that it can be access from within the Lambda functions. */
		AtomicInteger tmpRankCount = new AtomicInteger(0);
		
		/* Creating Linked Hash Maps as they preserve the order of entry. To be used
		 * for holding the sorted ranked entities and their values. */
		LinkedHashMap<Entity, Double> sortedOutgoingEntity = new LinkedHashMap<Entity, Double>();
		LinkedHashMap<Entity, Double> sortedIncomingEntity = new LinkedHashMap<Entity, Double>();
		
		/* Helper string for output formatting consistency. */
		String HozLine = "==========================================================================";
		
		System.out.println(HozLine);
		System.out.println("JP Morgan - Trading Report");
		System.out.println(HozLine);
		
		System.out.println("Total Transaction Values:");
		
		/* Generate the total outgoing & incoming totals */
		totalOutgoing += calculateTotals(outgoingTransactions);
		totalIncoming += calculateTotals(incomingTransactions);
		
		System.out.println("Total Outgoing (USD) : $" + totalOutgoing);
		System.out.println("Total Incoming (USD) : $" + totalIncoming);
		System.out.println(HozLine);
		
		/* Maps containing the total value of trades, grouped by
		 * date of transaction. */
		Map<LocalDate, Double> outgoingTotals = outgoingTransactions.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionDate,
						Collectors.summingDouble(Transaction::getTradeValue)));
		
		Map<LocalDate, Double> incomingTotals = incomingTransactions.stream()
				.collect(Collectors.groupingBy(Transaction::getTransactionDate,
						Collectors.summingDouble(Transaction::getTradeValue)));
		
		System.out.println("Daily Outgoing Trade Values - (Sorted: Most Recent)");
		outgoingTotals.forEach((tradeDate, tradeValue)->System.out.println("Transaction Date : "+ tradeDate.format(formatter)
			+ " | Total Trade Value (USD) : $" + tradeValue));
		
		System.out.println(HozLine);
		
		System.out.println("Daily Incoming Trade Values - (Sorted: Most Recent)");
		incomingTotals.forEach((tradeDate, tradeValue)->System.out.println("Transaction Date : " + tradeDate.format(formatter)
			+ " | Total Trade Value (USD) : $" + tradeValue));
		
		System.out.println(HozLine);
		
		/* analyse transactions so that we can rank the entities */
		sortedOutgoingEntity = sortEntitiesByRank(outgoingTransactions);
		sortedIncomingEntity = sortEntitiesByRank(incomingTransactions);
		
		System.out.println("Top Outgoing Entity - (Sorted: By Rank)");
		sortedOutgoingEntity.forEach((entity, value)->System.out.println("Entity Rank: " + (tmpRankCount.incrementAndGet()) 
			+ " | Entity Name: " + entity.toString() + " | Total Outgoing Value (USD) : $" + value));
		
		/* reset Atomic Integer rank counter. */
		tmpRankCount.set(0);
		
		System.out.println(HozLine);
		System.out.println("Top Incoming Entity - (Sorted: By Rank)");
		sortedIncomingEntity.forEach((entity, value)->System.out.println("Entity Rank: " + (tmpRankCount.incrementAndGet()) 
			+ " | Entity Name: " + entity.toString() + " | Total Incoming Value (USD) : $" + value));
		
		/* reset Atomic Integer rank counter. */
		tmpRankCount.set(0);
		
		System.out.println(HozLine);
		System.out.println("End of Report");
		System.out.println(HozLine);
	}
	
	/**
	 * This function takes in a list of transactions, totals the trade value,
	 * then assigns the value to a class variable for holding the running total.
	 * 
	 * @param _transactions List of transaction objects which contain all
	 * the transaction information.
	 * @return tempTotal returns the calculated tradeValue for the transaction
	 * list.
	 */
	public static float calculateTotals(List<Transaction> _transactions) {
		
		float tempTotal = 0.0f;
		
		if(!_transactions.isEmpty()) {
			for (Transaction transaction : _transactions) {
				tempTotal = tempTotal + transaction.getTradeValue();
			}		        
	    }
		
		return tempTotal;
	}
	
		
	
	/**
	 * This function takes a list of transactions and then collates them
	 * so that each each traded entity has a total traded value associated with it.
	 * The collated entities are then sorted by rank.
	 * 
	 * @param _transactions List of transactions to be sorted.
	 * @return a LinkedHashMap object of sorted Entities and their values.
	 */
	public static LinkedHashMap<Entity, Double> sortEntitiesByRank(List<Transaction> _transactions) {
		
		/* Linked Hash Map that will be returned */
		LinkedHashMap<Entity, Double> tempSortedMap = new LinkedHashMap<Entity, Double>();
		
		if(!_transactions.isEmpty()) {
			/* Temporary map that will hold the initial grouping of values */
			Map<Entity, Double> tempMap;
			
			/* Maps hold the 'Top' entity results, however, these still need to be sorted
			 * by their rank, based on Trade Value. */
			tempMap = _transactions.stream()
					.collect(Collectors.groupingBy(Transaction::getEntity,
							Collectors.summingDouble(Transaction::getTradeValue)));
			
			/* Sort the 'Top' Entity lists and copy the contents into the Linked Hash Maps
			 * so their new sorted order is preserved. */
			tempMap.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> tempSortedMap.put(x.getKey(), x.getValue()));
		}
		
		return tempSortedMap;
	}
}
