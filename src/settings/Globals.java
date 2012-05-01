package settings;

public class Globals 
{
	/**
	 * The length of a world's gate address.  All worlds, even those without a gate have an address.
	 */
	public static final int ADDRESS_LENGTH = 7;
	
	/**
	 * Data to determine the standard name generation convention for planets.
	 */
	public static final int WORLD_NAME_SUFFIX_LENGTH = 5;
	
	/**
	 * Determines how much more powerful ships are than troops.
	 */
	public static final int SHIP_TROOP_POWER_RATIO = 3;
	
	/**
	 * Determines the price of various things.
	 */
	public static final int SHIP_RESOURCE_BUILD_COST = 5;
	public static final int SHIP_RESOURCE_BUY_COST = 20;

	/**
	 * Home field advantage during combat.
	 */
	public static final double DEFENSE_STRENGTH_BONUS = 3;
	
	/**
	 * The mean resource amount of a planet.
	 */
	public static final int DEFAULT_RESOURCE_LEVEL = 50;
	
	/**
	 * The rate at which a planet generates troops.
	 */
	public static final int DEFAULT_POPULATION_LEVEL = 10;
}
