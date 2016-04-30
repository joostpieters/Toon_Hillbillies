package hillbillies.part3.programs.expression.logic;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;
import hillbillies.part3.programs.expression.unit.UnitExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class IsFriend extends BooleanExpression {

	/**
	 * 
	 * @param expression
	 * @param sourceLocation
	 */
	public IsFriend(MyExpression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		unitExpression = (UnitExpression)expression;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.logic.BooleanExpression#get(hillbillies.model.World, hillbillies.model.Unit)
	 */
	@Override
	public boolean get(World world, Unit unit) {
		return (unit.getFaction() == unitExpression.getUnit(world, unit).getFaction());
	}
	
	private static UnitExpression unitExpression;

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		return "IsFriend: " + this.get(unit.getWorld(), unit);
	}

}