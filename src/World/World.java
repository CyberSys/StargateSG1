package World;

import java.util.HashMap;
import java.util.Map;

import Faction.Faction;

public class World {
	//
	// DATA
	//
	String address;
	boolean hasGate;
	Faction controllingFaction;
	
	private Map<Faction, FactionWorldStats> factionStats;
	private int baseResources;
	
	//
	// CTOR
	//
	public World()
	{
		baseResources = 50;
		hasGate = true;
		
		factionStats = new HashMap<Faction, FactionWorldStats>();
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
	
	//
	// INNER CLASS
	//
	private class FactionWorldStats
	{
		int troopCount;
		int shipCount;
	}
}
