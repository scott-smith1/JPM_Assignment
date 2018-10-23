import static org.junit.Assert.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import enums.Currency;
import enums.Entity;
import enums.TradeAction;
import enums.WWProfile;

/**
 * Unit test checks for the correct assignment of the 
 * working week profile based on the currency of the
 * Trade Instruction.
 * 
 * @author Scott Smith
 */
public class WorkingWeekAssignmentTest {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@Test
	public void USDCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.USD,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.MON_FRI, ti.getWWProfile());
	}
	
	@Test
	public void GBPCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.GBP,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.MON_FRI, ti.getWWProfile());
	}
	
	@Test
	public void SGPCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.SGP,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.MON_FRI, ti.getWWProfile());
	}
	
	@Test
	public void AEDCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.AED,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.SUN_THUR, ti.getWWProfile());
	}
	
	@Test
	public void SARCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.SAR,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.SUN_THUR, ti.getWWProfile());
	}
	
	@Test
	public void EURCurrency() {
		TradeInstruction ti = new TradeInstruction(Entity.FOO, TradeAction.BUY, 0.50f, Currency.EUR,
				(LocalDate.parse("01-01-2016", formatter)) , (LocalDate.parse("02-01-2016", formatter)), 200, 100.25f );
		TradeSystem.assignWorkingWeekProfile(ti);
		assertEquals(WWProfile.MON_FRI, ti.getWWProfile());
	}

}
