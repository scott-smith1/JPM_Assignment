import java.time.LocalDate;

import enums.Currency;
import enums.Entity;
import enums.TradeAction;
import enums.WWProfile;

/**
 * This class defines the Trade Instruction. It is used to hold all
 * the information required to conduct a trade operation.
 * 
 * Access to the class variables are controlled via the Accessor
 * and Mutator functions.
 * 
 * @author Scott Smith
 */
public class TradeInstruction implements Comparable<Transaction>
{
	/* class variables */
	private Entity		entityName;
	private TradeAction	tradeAction;
	private float		agreedFx;
	private Currency	currency;
	private LocalDate	instructionDate;
	private LocalDate	settlementDate;
	private int			units;
	private float		pricePerUnit;
	private WWProfile	wwProfile;
	
	/**
	 * The constructor for each Trade Instruction object.
	 * 
	 * @param _name The name of the entity being traded.
	 * @param _tAction The action, either Buy or Sell.
	 * @param _fx The agreed fixed exchange rate.
	 * @param _currency The currency of the trade before conversion.
	 * @param _iDate The date the trade request was received.
	 * @param _sDate The date the trade is requested to be performed on.
	 * @param _units The number of units being traded.
	 * @param _pricePerUnit The price per unit being traded.
	 */
	public TradeInstruction(Entity _name, TradeAction _tAction , float _fx, Currency _currency,
		LocalDate _iDate, LocalDate _sDate, int _units, float _pricePerUnit) {
		
		setEntityName(_name);
		setTradeAction(_tAction);
		setAgreedFx(_fx);
		setCurrency(_currency);
		setInstructionDate(_iDate);
		setSettlementDate(_sDate);
		setUnits(_units);
		setPricePerUnit(_pricePerUnit);
	}

	/* Accessors */
	public Entity getEntityName()			{ return entityName; }
	public TradeAction getTradeAction()		{ return tradeAction; }
	public float getAgreedFx()				{ return agreedFx; }
	public Currency getCurrency()			{ return currency; }
	public LocalDate getInstructionDate()	{ return instructionDate; }
	public LocalDate getSettlementDate()	{ return settlementDate; }
	public int getUnits()					{ return units; }
	public float getPricePerUnit()			{ return pricePerUnit; }
	public WWProfile getWWProfile()			{ return wwProfile; }
	
	/* Mutators */
	public void setEntityName(Entity _name) 			{ entityName = _name; }
	public void setTradeAction(TradeAction _tAction)	{ tradeAction = _tAction; }
	public void setCurrency(Currency _currency)			{ currency = _currency; }
	public void setInstructionDate(LocalDate _iDate)	{ instructionDate = _iDate; }
	public void setSettlementDate(LocalDate _sDate)		{ settlementDate = _sDate; }
	public void setWWProfile(WWProfile _wwProfile)		{ wwProfile = _wwProfile; }
	
	public void setUnits(int _units) { 
		if (0 > _units) {
			throw new IllegalArgumentException(String.format("Error - Trade Instruction 'Units' must be greater than Zero."));
		}
		else {
			units = _units;
		}
	}
	
	public void setPricePerUnit(float _pricePerUnit) { 
		if (0 > _pricePerUnit) {
			throw new IllegalArgumentException(String.format("Error - Trade Instruction 'Price Per Unit' must be greater than Zero."));
		}
		else {
			pricePerUnit = _pricePerUnit;
		}
	}
	
	public void setAgreedFx(float _fx) { 
		if (0 > _fx) {
			throw new IllegalArgumentException(String.format("Error - Trade Instruction 'Agreed Fix' must be greater than Zero."));
		}
		else {
			agreedFx = _fx;
		}
	}
	

	@Override
	public int compareTo(Transaction o) {
		/* Auto-generated method stub */
		return 0;
	}
}

