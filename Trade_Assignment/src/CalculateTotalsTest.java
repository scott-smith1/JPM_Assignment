import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import enums.Entity;
import enums.TradeAction;

/**
 * Unit test is used to check that the total value of transactions
 * can be calculated correctly.
 * 
 * @author Scott Smith
 */
public class CalculateTotalsTest {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Test
	public void emptyTransactionList() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		float outcome = TradeSystem.calculateTotals(transactions);
		assertEquals(0.0f, outcome, 0.0f);
	}
	
	@Test
	public void calculateTotalsWithThreeTransactions() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		
		float outcome = TradeSystem.calculateTotals(transactions);
		assertEquals(6000.0f, outcome, 0.0f);
	}
	
	@Test
	public void calculateTotalsWithSixTransactions() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.FOO, TradeAction.BUY, 1000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAR, TradeAction.BUY, 3000.0f, LocalDate.parse("01-01-2018", formatter)));
		transactions.add(new Transaction(Entity.BAZ, TradeAction.BUY, 2000.0f, LocalDate.parse("01-01-2018", formatter)));
		
		float outcome = TradeSystem.calculateTotals(transactions);
		assertEquals(12000.0f, outcome, 0.0f);
	}

}
