package hillbillies.part3.programs.statement;

import java.util.Iterator;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public interface StatementIterator extends Iterator<MyStatement> {
	
	public abstract boolean isTerminal();
	
	/**
	 * @return Null if 
	 */
	@Override
	public abstract MyStatement next();
}