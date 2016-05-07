package hillbillies.part3.programs.expression.position;

import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Value;
import hillbillies.model.Boulder;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.expression.MyExpression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
@Value
public class BoulderPosition extends MyExpression<int[]> {
	
	public BoulderPosition(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	public int[] evaluateExpression(Unit unit){
		World world = unit.getWorld();
		Set<Boulder> boulders = world.getBoulders();
		int random = new Random().nextInt(boulders.size());
		int i = 0;
		for (Boulder boulder: boulders){
			if(i == random)
				return boulder.getCubePosition();
			i += 1;
		}
		//will never happen
		return null;
	}

	/* (non-Javadoc)
	 * @see hillbillies.part3.programs.expression.MyExpression#toString(hillbillies.model.Unit)
	 */
	@Override
	public String toString(Unit unit) {
		int[] position = this.evaluateExpression(unit);
		return "BoulderPosition: " + "{" + position[0] + "," + position[1]+","+position[2]+"}";
	}

}
