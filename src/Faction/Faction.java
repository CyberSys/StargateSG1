package Faction;

import java.util.*;

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
	protected int diplomacy;
	protected int science;
	
	// Planning Data
	public int timeToReplan = 0;
	public Stack<Task> plan = new Stack<Task>();
	protected boolean isReadyToAttack = false;
	
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
	public String toString() 
	{
		return "CombatStrength = " + getCombatStrength() + ", NumArmies = " + getNumArmies() + ", numShips = " + getNumShips() + ", NumResources = " + numResources;
	}
	
	public boolean isReadyToAttack() {
		return isReadyToAttack;
	}
	
	public void replan() {
		//Do things
		//probably check all of the highest level tasks, find the one that matches flavor the most
		//or one that gives an end result of victory condition
		if(timeToReplan <= 0 || plan.isEmpty()) { 
			plan.clear(); 
			timeToReplan = 10;
			Task attack = new AttackTask(getEnemy().getWorld(), getEnemy(), null);
			plan.add(attack);
		}
		while(plan.peek().isBaseTask() != true) {
			if(plan.peek().isCompleted(this)) plan.pop();
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
	
	public boolean didWin() {
		Task attack = new AttackTask(getEnemy().getWorld(), getEnemy(), null);
		return attack.isCompleted(this);
	}
	
	public void setIsReadyToAttack(boolean isReady) 
	{
		this.isReadyToAttack = isReady;
	}
	
	public int getCombatStrength() 
	{
		return getNumArmies();
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
	}
}
