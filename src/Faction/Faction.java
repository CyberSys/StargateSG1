package faction;

import java.util.*;

import settings.Globals;
import faction.Reputation.ReputationLevel;

import planning.*;
import universe.*;

//Todo: The following:
//Think of stats for factions to have
//Think of way to keep track of current task so replanning not a bitch
public abstract class Faction 
{
	//
	// DATA
	//
	// Faction Data
	public String factionName;
	
	protected World homeWorld;
	protected Set<World> controlledWorlds = new HashSet<World>();
	protected Set<World> knownWorlds = new HashSet<World>();
	
	protected int numResources;
	public double morale = 0.25;
	
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
	public Task mNextTask;
	
	//
	// TROOP AND SHIP MANAGEMENT
	//
	
	public void increaseMorale() {
		morale += morale >= .5 ? .05 : 0;
	}
	
	public void decreaseMorale() {
		morale -= morale <= 0 ? .05 : 0;
	}
	
	public void increaseTroops(int amount)
	{
		increaseTroops(amount, homeWorld);
	}
	
	public void increaseTroops(int amount, World w)
	{
		w.addTroops(this, amount);
	}
	
	public void gainTroopsPassive()
	{
		for(World w : controlledWorlds)
		{
			gainTroopsPassive(w);
		}
	}
	
	public void gainTroopsPassive(World w)
	{
		increaseTroops((int)(w.getPassiveTroops()), w);
	}
	
	public void gainTroopsActive(World w)
	{
		int numTroops = Math.min((int)(w.getPassiveTroops() * morale) * Globals.TROOP_RESOURCE_BUILD_COST, numResources);
		increaseTroops(numTroops, w);
		removeResources(numTroops);
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
		
		for(World w : knownWorlds)
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
		
		for(World w : knownWorlds)
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
	private void gainResourcesPassive()
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
	
	public int getReputationNumber(Faction f)
	{		
		if(!factionReputations.containsKey(f))
			factionReputations.put(f, new Reputation());
		
		return factionReputations.get(f).currentRep;
	}
	
	public ReputationLevel getReputation(Faction f)
	{
		if(!factionReputations.containsKey(f))
			factionReputations.put(f, new Reputation());
		
		return factionReputations.get(f).reputationLevel;
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
		if(world.hasGate) {
			knownGateAddresses.add(world);
			knownWorlds.add(world);
		}
	}
	
	public boolean knowsLocation(World world) 
	{
		return knownWorldLocations.contains(world);
	}
	
	public void learnWorldLocation(World world) 
	{
		knownWorldLocations.add(world);
		knownWorlds.add(world);
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
	
	public Set<World> getControlledWorlds()
	{
		return controlledWorlds;
	}
	
	public Set<World> getKnownWorlds() {
		return knownWorlds;
	}
	
	public void gainWorldControl(World w)
	{
		if(!controlledWorlds.contains(w))
		{
			w.setControllingFaction(this);
			controlledWorlds.add(w);
			learnGateAddress(w);
			learnWorldLocation(w);
		}
	}
	
	public void loseWorldControl(World w)
	{
		controlledWorlds.remove(w);
	}
	
	//
	// TURN SIMULATION
	//
	public void setNextAction(Task next)
	{
		mNextTask = next;
	}
	
	public void doTurn() 
	{
		// Upkeep
		gainResourcesPassive();
		gainTroopsPassive();
		
		// Planning
		if(isPlayerControlled)
		{
			mNextTask.perform(this);
		}
		else
		{
			getNextPlannedTask().perform(this);	
		}
	}
	
	public Task[] getAvailableActions()
	{
		if(isPlayerControlled)
		{
			return new Task[0];
		}
		else
		{
			return new Task[0];
		}
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
			timeToReplan = 1;
//			System.out.println(this);
//			System.out.println(getEnemies());
			Task attack = new AttackTask(getHomeWorld(), getEnemies().get(0).getHomeWorld(), getEnemies().get(0), 50, null);
			Task wait = new WaitTask(null);
			Task troopUp = new TrainTroopsTask(getHomeWorld(), 200, null);
			if(this instanceof HumanityFaction) plan.add(troopUp);
			else
			plan.add(attack);
			//Task sabotage = new SabotageTask(getEnemies().get(0), getEnemies().get(0).getHomeWorld(), null);
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
	
	public boolean didWin() {
		return this.controlledWorlds.size() == 2;
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
		return factionName + " Resources: " + getNumResources();
	}
	
	//
	// Inner Class
	//
	public class TechLevel
	{
		//
		// RESOURCES
		//
		public double resourceEfficiency = .2;
		
		//
		// TRANSIT SPEED
		//
		public double hyperdriveEfficiency = 1;
		
		//
		// COMBAT PROWESS
		//
		public double defensiveCapabilities = 1;
		public double offensiveCapabilities = 1;
		
		public int compareTo(TechLevel techLevel) {
			return (int)((resourceEfficiency + hyperdriveEfficiency + defensiveCapabilities + offensiveCapabilities)
					- (techLevel.resourceEfficiency + techLevel.hyperdriveEfficiency + techLevel.defensiveCapabilities + techLevel.offensiveCapabilities));
		}

		public boolean isMinimum() {
			return (resourceEfficiency + hyperdriveEfficiency + defensiveCapabilities + offensiveCapabilities) == .1;
		}
		
		public boolean isMaximum() {
			return (resourceEfficiency + hyperdriveEfficiency + defensiveCapabilities + offensiveCapabilities) == 16;
		}
	}
	
	public TechLevel getTechLevel() {
		return tech;
	}

	public void improveTechLevel() {
		improveTechLevel(new Random().nextInt(4));
	}
	
	public void improveTechLevel(int direction) {
		switch(direction) {
		case Globals.RESOURCE_RESEARCH:
			tech.resourceEfficiency+=.1;
			if(tech.resourceEfficiency > Globals.MAX_RESOURCE_EFFICIENCY) tech.resourceEfficiency = Globals.MAX_RESOURCE_EFFICIENCY;
			break;
		case 1:
			tech.hyperdriveEfficiency+=.5;
			if(tech.hyperdriveEfficiency > Globals.MAX_HYPERDRIVE_EFFICIENCY) tech.hyperdriveEfficiency = Globals.MAX_HYPERDRIVE_EFFICIENCY;
			break;
		case 2:
			tech.defensiveCapabilities+=.5;
			if(tech.defensiveCapabilities > Globals.MAX_DEFENSIVE_CAPABILITIES) tech.defensiveCapabilities = Globals.MAX_DEFENSIVE_CAPABILITIES;
			break;
		case 3:
			tech.offensiveCapabilities+=.5;
			if(tech.offensiveCapabilities > Globals.MAX_OFFENSIVE_CAPABILITIES) tech.offensiveCapabilities = Globals.MAX_OFFENSIVE_CAPABILITIES;
		default:
			break;
		}
	}
	
	public void reduceTechLevel() {
		switch(new Random().nextInt(4)) {
		case 0:
			tech.resourceEfficiency-=.1;
			if(tech.resourceEfficiency < Globals.MIN_RESOURCE_EFFICIENCY) tech.resourceEfficiency = Globals.MIN_RESOURCE_EFFICIENCY;
			break;
		case 1:
			tech.hyperdriveEfficiency-=.5;
			if(tech.hyperdriveEfficiency < Globals.MIN_HYPERDRIVE_EFFICIENCY) tech.hyperdriveEfficiency = Globals.MIN_HYPERDRIVE_EFFICIENCY;
			break;
		case 2:
			tech.defensiveCapabilities-=.5;
			if(tech.defensiveCapabilities < Globals.MIN_DEFENSIVE_CAPABILITIES) tech.defensiveCapabilities = Globals.MIN_DEFENSIVE_CAPABILITIES;
			break;
		case 3:
			tech.offensiveCapabilities-=.5;
			if(tech.offensiveCapabilities < Globals.MIN_OFFENSIVE_CAPABILITIES) tech.offensiveCapabilities = Globals.MIN_OFFENSIVE_CAPABILITIES;
		default:
			break;
		}
	}

	
}
