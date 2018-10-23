import static org.junit.Assert.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import enums.Currency;
import enums.Entity;
import enums.TradeAction;

/**
 * Unit tests for the adjustment of the Settlement Date
 * to Trade Instructions.
 * 
 * @author Scott Smith
 */
public class SettlementDateAdjustmentTest {
	
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Test
	public void MON_FRI_NoAdjustment() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.USD,
			(LocalDate.parse("11-10-2018", formatter)) , (LocalDate.parse("12-10-2018", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		TradeSystem.adjustSettlementDate(ti);
		assertEquals(LocalDate.parse("12-10-2018", formatter), ti.getSettlementDate());
	}
	
	@Test
	public void MON_FRI_Adjustment() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.USD,
			(LocalDate.parse("12-10-2018", formatter)) , (LocalDate.parse("13-10-2018", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		TradeSystem.adjustSettlementDate(ti);
		assertEquals(LocalDate.parse("15-10-2018", formatter), ti.getSettlementDate());
	}
	
	@Test
	public void SUN_THUR_NoAdjustment() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.AED,
			(LocalDate.parse("11-10-2018", formatter)) , (LocalDate.parse("16-10-2018", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		TradeSystem.adjustSettlementDate(ti);
		assertEquals(LocalDate.parse("16-10-2018", formatter), ti.getSettlementDate());
	}
	
	@Test
	public void SUN_THUR_Adjustment() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.AED,
			(LocalDate.parse("12-10-2018", formatter)) , (LocalDate.parse("19-10-2018", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		TradeSystem.adjustSettlementDate(ti);
		assertEquals(LocalDate.parse("21-10-2018", formatter), ti.getSettlementDate());
	}

}
