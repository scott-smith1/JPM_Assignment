import java.time.LocalDate;

import enums.Entity;
import enums.TradeAction;

/**
 * This class defines the Transactions. It is used to hold all
 * the information of the transactions that take place in the system.
 * 
 * Access to the class variables are controlled via the Accessor
 * and Mutator functions.
 * 
 * @author Scott Smith
 */
public class Transaction implements Comparable<Transaction>
{
    /* Class variables */
	private Entity entityName;
	private TradeAction tradeAction;
	private float tradeValue;
	private LocalDate transactionDate;

	/**
	 * Constructor for each Transaction object.
	 * 
	 * @param _name Name of the entity being bought or sold.
	 * @param _action The action, Buy or Sell.
	 * @param _tradeValue The value of the transaction in USD.
	 * @param _transactionDate The date the transaction took place.
	 */
	public Transaction(Entity _name, TradeAction _action, float _tradeValue, LocalDate _transactionDate)
	{
		setEntity(_name);
		setTradeAction(_action);
		setTradeValue(_tradeValue);
		setTransactionDate(_transactionDate);
	}
	
	/* Accessors */
	public Entity getEntity() 				{ return entityName; }
	public TradeAction getTradeAction() 	{ return tradeAction; }
	public float getTradeValue() 			{ return tradeValue; }
	public LocalDate getTransactionDate() 	{ return transactionDate; }
	
	/* Mutators */
	public void setEntity(Entity _name) 				{ entityName = _name; }
	public void setTradeAction (TradeAction _action) 	{ tradeAction = _action; }
	public void setTradeValue (float _value) 			{ tradeValue = _value; }
	public void setTransactionDate (LocalDate _date) 	{ transactionDate = _date; }

	@Override
	public int compareTo(Transaction o) {
		/* Auto-generated method stub */
		return 0;
	}
}
