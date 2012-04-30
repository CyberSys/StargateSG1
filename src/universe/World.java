package universe;

import java.util.*;

import planning.ConquerTask;
import settings.Globals;

import faction.Faction;


public class World {
	//
	// DATA
	//
	public String name;
	public String address;
	public boolean hasGate;
	Faction controllingFaction;
	
	private int baseResources;
	private int basePopulation;
	
	private Map<Faction, FactionWorldStats> factionStats;
	private List<Faction> spies = new ArrayList<Faction>();
	
	//
	// CTOR
	//
	public World()
	{
		baseResources = Globals.DEFAULT_RESOURCE_LEVEL;
		basePopulation = Globals.DEFAULT_POPULATION_LEVEL;
		
		hasGate = true;
		
		factionStats = new HashMap<Faction, FactionWorldStats>();
	}
	
	//
	// Naming
	//
	public void setName(String name)
	{
		Universe.updateName(this, name);
	}
	
	//
	// Controlling Faction
	//
	public Faction getControllingFaction() 
	{
		return controllingFaction;
	}

	public void setControllingFaction(Faction faction) 
	{
		if(controllingFaction != null && controllingFaction.equals(faction))
			return;
		
		if(controllingFaction != null)
			controllingFaction.loseWorldControl(this);
		controllingFaction = faction;
		if(!factionStats.containsKey(faction))
		{
			factionStats.put(faction, new FactionWorldStats());
		}
		controllingFaction.gainWorldControl(this);
	}

	//
	// Troops and Ships
	//
	public void addTroops(Faction f, int amount)
	{
		if(!factionStats.containsKey(f))
			factionStats.put(f, new FactionWorldStats());
		
		FactionWorldStats s = factionStats.get(f);
		s.troopCount += amount;
	}
	
	public void removeTroops(Faction f, int amount)
	{
		if(!factionStats.containsKey(f))
			factionStats.put(f, new FactionWorldStats());
		
		FactionWorldStats s = factionStats.get(f);
		s.troopCount -= amount;
		if(s.troopCount < 0)
			s.troopCount = 0;
	}
	
	public int getTroopCount(Faction f)
	{
		if(factionStats.containsKey(f))
			return factionStats.get(f).troopCount;
		else
			return 0;
	}
	
	public void addShips(Faction f, int amount)
	{
		if(!factionStats.containsKey(f))
			factionStats.put(f, new FactionWorldStats());
		
		FactionWorldStats s = factionStats.get(f);
		s.shipCount += amount;
	}
	
	public void removeShips(Faction f, int amount)
	{
		if(!factionStats.containsKey(f))
			factionStats.put(f, new FactionWorldStats());
		
		FactionWorldStats s = factionStats.get(f);
		s.shipCount -= amount;
		if(s.shipCount < 0)
			s.shipCount = 0;
	}
	
	public int getShipCount(Faction f)
	{
		if(factionStats.containsKey(f))
			return factionStats.get(f).shipCount;
		else
			return 0;
	}
	
	public boolean hasSpy(Faction faction) {
		return spies.contains(faction);
	}
	
	public void plantSpy(Faction faction) {
		if(!hasSpy(faction))
			spies.add(faction);
	}
	
	public void exposeSpy(Faction faction) {
		spies.remove(faction);
	}
	
	//
	// RESOURCES
	//
	public int getPassiveResources()
	{
		return baseResources;
	}
	
	public int getActiveResources()
	{
		int activeMultiplier = 3;
		return (getPassiveResources() * activeMultiplier);
	}		
	
	//
	// PASSIVE TROOP GAIN
	//
	public int getPassiveTroops()
	{
		return basePopulation; 
	}
	
	//
	// BOOKKEEPING
	//
	public boolean equals(World other) 
	{
		return other.address.equals(address);
	}
	
	public int hashCode()
	{
		return address.hashCode();
	}
	
	public String toString() {
		String string = "";
		for(Faction  f : factionStats.keySet()) {
			string += factionStats.get(f).toString();
			string += "\n";
		}
		return string;
	}
	
	//
	// INNER CLASS
	//
	private class FactionWorldStats
	{
		int troopCount;
		int shipCount;
		
		public String toString() {
			return "Troop Count: " + troopCount + ", Ship Count: " + shipCount;
		}
	}

	public void doCombat() {
		Map<Faction, Integer> cm = new HashMap<Faction, Integer>();
		for(Faction f : factionStats.keySet()) {
			cm.put(f, getControllingFaction() == f ? (int)((f.getDefenseStrength(this)*Globals.DEFENSE_STRENGTH_BONUS)/100 + 1) : (int)(f.getAttackStrength(this)/100 + 1));
		}
		for(Faction f : factionStats.keySet()) {
			for(Faction f2 : factionStats.keySet()) {
				if(f == f2)
					continue;
				else if(f.isEnemy(f2) && (getTroopCount(f) > 0 || getShipCount(f) > 0) && (getTroopCount(f2) > 0 || getShipCount(f2) > 0)) {
					removeShips(f, cm.get(f2));
					removeShips(f2, cm.get(f));
					removeTroops(f, cm.get(f2));
					removeTroops(f2, cm.get(f));
				}
			}
		}
		for(Faction f : factionStats.keySet()) {
			if(f != getControllingFaction() && f.getAttackStrength(this) > getControllingFaction().getDefenseStrength(this) && f.isEnemy(getControllingFaction())) {
				new ConquerTask(this, null).perform(f);
			}
		}
	}
}
