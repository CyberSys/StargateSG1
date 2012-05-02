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
	
	
	public static final double MAX_REPUTATION = 100;
	public static final double MIN_REPUTATION = 0;
	
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
	public static final int DEFAULT_POPULATION_LEVEL = 12;

	public static final int TROOP_RESOURCE_BUILD_COST = 1;
	
	public static final int WORLD_TROOP_POPULATION_CAP = 200;
	public static final int WORLD_SHIP_POPULATION_CAP = 50;
	
	/**
	 * Morale controlling constants.
	 */
	public static final double MAX_MORALE = 0.5;
	public static final double MIN_MORALE = 0.0;

	public static final double MAX_RESOURCE_EFFICIENCY = 1;
	//public static final int MAX_HYPERDRIVE_EFFICIENCY = 5;
	public static final int MAX_DEFENSIVE_CAPABILITIES = 5;
	public static final int MAX_OFFENSIVE_CAPABILITIES = 5;
	public static final double MAX_TECH_LEVEL = MAX_RESOURCE_EFFICIENCY /*+ MAX_HYPERDRIVE_EFFICIENCY*/ + MAX_DEFENSIVE_CAPABILITIES + MAX_OFFENSIVE_CAPABILITIES;
	
	public static final double MIN_RESOURCE_EFFICIENCY = .1;
	//public static final int MIN_HYPERDRIVE_EFFICIENCY = 0;
	public static final int MIN_DEFENSIVE_CAPABILITIES = 0;
	public static final int MIN_OFFENSIVE_CAPABILITIES = 0;
	public static final double MIN_TECH_LEVEL = MIN_RESOURCE_EFFICIENCY /*+ MIN_HYPERDRIVE_EFFICIENCY*/ + MIN_DEFENSIVE_CAPABILITIES + MIN_OFFENSIVE_CAPABILITIES;
	

	public static final int RESOURCE_RESEARCH = 0;
	//public static final int HYPERDRIVE_RESEARCH = 3;
	public static final int DEFENSE_RESEARCH = 1;
	public static final int OFFENSE_RESEARCH = 2;
	
}
