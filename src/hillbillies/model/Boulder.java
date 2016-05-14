package hillbillies.model;

/**
 * A class of Boulders
 * 
 * @invar  The World of each Boulder must be a valid World for any
 *         Boulder.
 *       | isValidWorld(getWorld())
 *
 * @author  Toon Deburchgrave
 * @version 1.0
 */
public class Boulder extends GameItem {
	
	/** 
	 * initialize the Boulder. 
	 * The Boulder has a given position and world
	 * 
	 * @param  world
	 *         The World for this new Boulder.
 	 * @effect The World of this new Boulder is set to
 	 *         the given World.
 	 *       | this.setWorld(world)
	 */
	public Boulder(int[] position, World world) throws IllegalArgumentException{
		super(position, world);
	}

}
