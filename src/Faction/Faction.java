package Faction;

import java.util.*;

import settings.Globals;
import universe.*;
import Faction.Reputation.ReputationLevel;
import HTNP.*;

//Todo: The following:
//Think of stats for factions to have
//Think of way to keep track of current task so replanning not a bitch
public abstract class Faction 
{
	//
	// DATA
	//
	// Faction Data
	protected String factionName;
	
	protected World homeWorld;
	protected Set<World> controlledWorlds = new HashSet<World>();
	
	protected int numResources;
	
	protected Set<World> knownGateAddresses = new HashSet<World>();
	protected Set<World> knownWorldLocations = new HashSet<World>();
	
	protected Map<Faction, Reputation> factionReputations = new HashMap<Faction, Reputation>();
	
	protected TechLevel tech = new TechLevel();
	
	// Personality
	protected int aggression;
	public int getAggression() {
		return aggression;
	}

	public int getDiplomacy() {
		return diplomacy;
	}

	public int getScience() {
		return science;
	}

	protected int diplomacy;
	protected int science;
	
	// Planning Data
	public boolean isPlayerControlled = false;
	public int timeToReplan = 0;
	public Stack<Task> plan = new Stack<Task>();
	
	//
	// TROOP AND SHIP MANAGEMENT
	//
	public void increaseTroops(int amount)
	{
		increaseTroops(amount, homeWorld);
	}
	
	public void increaseTroops(int amount, World w)
	{
		w.addTroops(this, amount);
	}
	
	public void decreaseTroops(int amount)
	{
		decreaseTroops(amount, homeWorld);
	}
	
	public void decreaseTroops(int amount, World w)
	{
		w.removeTroops(this, amount);
	}
	
	public int getNumArmies() 
	{
		int armies = 0;
		
		for(World w : controlledWorlds)
		{
			armies += getNumArmies(w);
		}
		
		return armies;
	}
	
	public int getNumArmies(World w)
	{
		return w.getTroopCount(this);
	}
	
	public void increaseShips(int amount)
	{
		increaseShips(amount, homeWorld);
	}
	
	public void increaseShips(int amount, World w)
	{
		w.addShips(this, amount);
	}
	
	public void decreaseShips(int amount)
	{
		decreaseShips(amount, homeWorld);
	}
	
	public void decreaseShips(int amount, World w)
	{
		w.removeShips(this, amount);
	}
	
	public int getNumShips() 
	{
		int armies = 0;
		
		for(World w : controlledWorlds)
		{
			armies += getNumShips(w);
		}
		
		return armies;
	}
	
	public int getNumShips(World w)
	{
		return w.getShipCount(this);
	}
	
	//
	// RESOURCES
	//
	public void gainResourcesPassive()
	{
		for(World w : controlledWorlds)
		{
			addResources((int)(tech.resourceEfficiency * w.getPassiveResources()));
		}
	}
	
	public void gainResourcesActive()
	{
		for(World w : controlledWorlds)
		{
			addResources((int)(tech.resourceEfficiency * w.getActiveResources()));
		}
	}
	
	public void addResources(int amount)
	{
		this.numResources += amount;
	}
	
	public void removeResources(int amount)
	{
		this.numResources -= amount;
	}
	
	public int getNumResources() 
	{
		return numResources;
	}
	
	//
	// REPUTATIONS
	//
	public boolean isEnemy(Faction f)
	{
		if(!factionReputations.containsKey(f))
			return false;
		
		Reputation r = factionReputations.get(f);
		return (r.compareReputation(ReputationLevel.ENEMY) <= 0);
	}
	
	public List<Faction> getEnemies()
	{
		List<Faction> enemies = new ArrayList<Faction>();
		
		for(Faction f : factionReputations.keySet())
		{
			if(isEnemy(f))
				enemies.add(f);
		}
		
		return enemies;
	}
	
	public void increaseReputation(Faction f, int amount)
	{
		if(!factionReputations.containsKey(f))
			factionReputations.put(f, new Reputation());
		
		factionReputations.get(f).adjustReputation(amount);
	}
	
	public void decreaseReputation(Faction f, int amount)
	{
		if(!factionReputations.containsKey(f))
			factionReputations.put(f, new Reputation());
		
		factionReputations.get(f).adjustReputation(-1 * amount);
	}
	
	//
	// KNOWLEDGE
	//
	public boolean knowsGateAddress(World world) 
	{
		return knownGateAddresses.contains(world);
	}
	
	public void learnGateAddress(World world)
	{
		knownGateAddresses.add(world);
	}
	
	public boolean knowsLocation(World world) 
	{
		return knownWorldLocations.contains(world);
	}
	
	public void learnWorldLocation(World world) 
	{
		knownWorldLocations.add(world);
	}
	
	//
	// WORLDS
	//
	public void setHomeWorld(World w)
	{
		gainWorldControl(w);
		homeWorld = w;
	}
	
	public World getHomeWorld()
	{
		return homeWorld;
	}
	
	public void gainWorldControl(World w)
	{
		if(!controlledWorlds.contains(w))
		{
			w.setControllingFaction(this);
			controlledWorlds.add(w);
		}
	}
	
	public void loseWorldControl(World w)
	{
		controlledWorlds.remove(w);
	}
	
	//
	// TURN SIMULATION
	//
	public void doTurn() 
	{
		// Upkeep
		gainResourcesPassive();
		
		// Planning
		getNextPlannedTask().perform(this);		
	}
	
	//
	// PLANNING STUFFS
	//
	
	public void replan() {
		//Do things
		//probably check all of the highest level tasks, find the one that matches flavor the most
		//or one that gives an end result of victory condition
		if(timeToReplan <= 0 || plan.isEmpty()) { 
			plan.clear(); 
			timeToReplan = 5;
			int enemyIndex = new Random().nextInt(getEnemies().size());
			Task attack = new AttackTask(getHomeWorld(), getEnemies().get(enemyIndex).getHomeWorld(), getEnemies().get(enemyIndex), 50, null);
			plan.add(attack);
//			Task sabotage = new SabotageTask(getEnemies().get(0), getEnemies().get(0).getHomeWorld(), null);
//			plan.add(sabotage);
		}
		while(plan.peek().isBaseTask() != true) {
			if(plan.peek().isCompleted(this)){ 
				plan.pop();
				if(plan.isEmpty()) replan();
			}
			else plan.add(plan.peek().getNextStep(this));
		}
	}
	
	public boolean needToReplan() {
		//Replan if your plan is empty, you need to replan, or you don't have a base task to perform
		return plan.isEmpty() || timeToReplan <= 0 || plan.peek().isBaseTask() != true;
	}
	
	public Task getNextPlannedTask() {
		if(needToReplan())
			replan();
		timeToReplan--;
		System.out.println(plan);
		return plan.pop();
	}
	
	public double getAttackStrength(World world) {
		return tech.offensiveCapabilities*(getNumArmies(world) + Globals.SHIP_TROOP_POWER_RATIO*getNumShips(world));
	}
	
	public double getDefenseStrength(World world) {
		return tech.defensiveCapabilities*(getNumArmies(world) + Globals.SHIP_TROOP_POWER_RATIO*getNumShips(world));
	}
	
	public String toString() {
		return "Resources: " + getNumResources();
	}
	//
	// Inner Class
	//
	public class TechLevel
	{
		//
		// RESOURCES
		//
		double resourceEfficiency = .2;
		
		//
		// TRANSIT SPEED
		//
		double hyperdriveEfficiency = 1;
		
		//
		// COMBAT PROWESS
		//
		double defensiveCapabilities = 1;
		double offensiveCapabilities = 1;
		
		public int compareTo(TechLevel techLevel) {
			return (int)((resourceEfficiency + hyperdriveEfficiency + defensiveCapabilities + offensiveCapabilities)
					- (techLevel.resourceEfficiency + techLevel.hyperdriveEfficiency + techLevel.defensiveCapabilities + techLevel.offensiveCapabilities));
		}

		public boolean isMinimum() {
			return (resourceEfficiency + hyperdriveEfficiency + defensiveCapabilities + offensiveCapabilities) == .1;
		}
	}
	
	public TechLevel getTechLevel() {
		return tech;
	}

	public void improveTechLevel() {
		switch(new Random().nextInt(4)) {
		case 0:
			tech.resourceEfficiency+=.1;
			break;
		case 1:
			tech.hyperdriveEfficiency+=.5;
			break;
		case 2:
			tech.defensiveCapabilities+=.5;
			break;
		case 3:
			tech.offensiveCapabilities+=.5;
		default:
			break;
		}
	}
	
	public void improveTechLevel(int direction) {
		switch(direction) {
		case 0:
			tech.resourceEfficiency+=.1;
			break;
		case 1:
			tech.hyperdriveEfficiency+=.5;
			break;
		case 2:
			tech.defensiveCapabilities+=.5;
			break;
		case 3:
			tech.offensiveCapabilities+=.5;
		default:
			break;
		}
	}
	
	public void reduceTechLevel() {
		switch(new Random().nextInt(4)) {
		case 0:
			tech.resourceEfficiency-=.1;
			if(tech.resourceEfficiency < .1) tech.resourceEfficiency = .1;
			break;
		case 1:
			tech.hyperdriveEfficiency-=.5;
			if(tech.hyperdriveEfficiency < 0) tech.hyperdriveEfficiency = 0;
			break;
		case 2:
			tech.defensiveCapabilities-=.5;
			if(tech.defensiveCapabilities < 0) tech.defensiveCapabilities = 0;
			break;
		case 3:
			tech.offensiveCapabilities-=.5;
			if(tech.offensiveCapabilities < 0) tech.offensiveCapabilities = 0;
		default:
			break;
		}
	}

	
}
