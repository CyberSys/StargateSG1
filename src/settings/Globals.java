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
	
	
	public static final int MAX_REPUTATION = 100;
	public static final int MIN_REPUTATION = 0;
	
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
	public static final double DEFENSE_STRENGTH_BONUS = 2;
	
	/**
	 * The mean resource amount of a planet.
	 */
	public static final int DEFAULT_RESOURCE_LEVEL = 50;
	
	/**
	 * Population and population cap stuffs.
	 */
	public static final int DEFAULT_POPULATION_LEVEL = 3;
	public static final int WORLD_TROOP_POPULATION_CAP = 200;
	public static final int WORLD_SHIP_POPULATION_CAP = 50;
	
	/**
	 * Morale controlling constants.
	 */
	public static final double MAX_MORALE = 0.5;
	
}
