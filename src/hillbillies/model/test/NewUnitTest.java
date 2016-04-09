package hillbillies.model.test;

import static org.junit.Assert.*;
import org.junit.Test;

import hillbillies.model.*;

import ogp.framework.util.Util;

import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;

import org.junit.*;

import javax.vecmath.*;

/**
 * A class of unit tests
 *
 * @author  ...
 * @version 1.0
 */
public class NewUnitTest {
	private static int[][][] terrainType = 
		{{	{1,1,1},
			{1,1,1},
			{1,1,1}},
				
		 {	{0,0,0},
			{0,0,0},
			{0,0,0}},
		 
		 {	{0,0,0},
			{0,0,0},
			{0,0,0}}};
		
	private static World world;
	private static Faction faction1;
	private static Faction faction2;
	private Log log;
	private Boulder boulder;
	private Unit testUnit, otherUnit, farUnit, otherFactionUnit;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		TerrainChangeListener defaultListener = new DefaultTerrainChangeListener();
		world = new World(terrainType, defaultListener);
		
		faction1 = new Faction(world);
		faction2 = new Faction(world);
		world.addFaction(faction1);
		world.addFaction(faction2);	
	}
	
	@Before
	public void setUp(){
		int[] position = {1,1,1};
		Log log = new Log(position, this.world);
		this.world.addLog(log);
		Boulder boulder = new Boulder(position, this.world);
		this.world.addBoulder(boulder);
		
		int[] unitPosition = {0,0,1};
		Unit testUnit = new Unit("TestUnit", unitPosition, 50,50,50,50, this.world, this.faction1);
		this.world.addUnit(testUnit);
		this.faction1.addUnit(testUnit);
		
		Unit otherUnit = new Unit("OtherUnit", unitPosition, 50,50,50,50, this.world, this.faction1);
		this.world.addUnit(otherUnit);
		this.faction1.addUnit(otherUnit);
		
		int[] farPosition = {2,2,1};
		Unit farUnit = new Unit("TestUnit", farPosition, 50,50,50,50, this.world, this.faction2);
		this.world.addUnit(farUnit);
		this.faction2.addUnit(farUnit);
		
		Unit otherFactionUnit = new Unit("OtherFactionUnit", unitPosition, 50,50,50,50, this.world, this.faction2);
		this.world.addUnit(otherFactionUnit);
		this.faction2.addUnit(otherFactionUnit);
	}
	
	@Test
	public void testConstructorLegalCase(){
		int[] position = {0, 0, 1};
		Vector3d vectorPosition = new Vector3d(0.5,0.5,1.5);
		Unit unit = new Unit("TestSubject", position, 50, 50, 50, 50, this.world, this.faction1);
		
		assertEquals(unit.getPosition(), vectorPosition);
		assertEquals(unit.getStrength(), 50);
		assertEquals(unit.getAgility(), 50);
		assertEquals(unit.getWeight(), 50);
		assertEquals(unit.getToughness(), 50);
		assertEquals(unit.getName(), "TestSubject");
	}
	
	
}
