import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import enums.Entity;
import enums.TradeAction;

/**
 * Unit test checks the conversion from unordered transaction
 * list to collated and sorted by rank LinkedHashMap.
 * 
 * @author Scott Smith
 */
public class SortEntitiesByRankTest {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-yyyy");

	@Test
	public void sortOneEntityByRank() {
		/* Lists to hold all the transactions that take place.*/
		List<Transaction> transactions = new ArrayList<Transaction>();
		LinkedHashMap<Entity, Double> expectedMap = new LinkedHashMap<Entity, Double>();
		
		/* Sample transactions to be sorted by rank */
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		
		/* Populate the expectedMap with what is expected */
		expectedMap.put(Entity.FOO, 1000.00);
		
		LinkedHashMap<Entity, Double> outcome = TradeSystem.sortEntitiesByRank(transactions);
		assertEquals(expectedMap, outcome);
	}
	
	
	@Test
	public void sortThreeEntitiesByRank() {
		/* Lists to hold all the transactions that take place.*/
		List<Transaction> transactions = new ArrayList<Transaction>();
		LinkedHashMap<Entity, Double> expectedMap = new LinkedHashMap<Entity, Double>();
		
		/* Sample transactions to be sorted by rank */
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		
		/* Populate the expectedMap with what is expected... */
		expectedMap.put(Entity.FOO, 1000.00);
		expectedMap.put(Entity.BAR, 3000.00);
		expectedMap.put(Entity.BAZ, 2000.00);
		
		LinkedHashMap<Entity, Double> outcome = TradeSystem.sortEntitiesByRank(transactions);
		assertEquals(expectedMap, outcome);
	}
	
	@Test
	public void sortSixEntitiesByRank() {
		/* Lists to hold all the transactions that take place.*/
		List<Transaction> transactions = new ArrayList<Transaction>();
		LinkedHashMap<Entity, Double> expectedMap = new LinkedHashMap<Entity, Double>();
		
		/* Sample transactions to be sorted by rank */
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		
		/* Populate the expectedMap with what is expected... */
		expectedMap.put(Entity.FOO, 2000.00);
		expectedMap.put(Entity.BAR, 6000.00);
		expectedMap.put(Entity.BAZ, 4000.00);
		
		LinkedHashMap<Entity, Double> outcome = TradeSystem.sortEntitiesByRank(transactions);
		assertEquals(expectedMap, outcome);
	}

}
