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
	// METHODS
	//
	public Faction getControllingFaction() 
	{
		return controllingFaction;
	}

	public void setControllingFaction(Faction faction) 
	{
		controllingFaction = faction;
		if(!factionStats.containsKey(faction))
		{
			factionStats.put(faction, new FactionWorldStats());
		}
	}

	public int getTroopCount(Faction f)
	{
		if(factionStats.containsKey(f))
			return factionStats.get(f).troopCount;
		else
			return 0;
	}
	
	public int getShipCount(Faction f)
	{
		if(factionStats.containsKey(f))
			return factionStats.get(f).shipCount;
		else
			return 0;
	}
	
	public int getPassiveResources(Faction faction)
	{
		return baseResources;
	}
	
	public int getActiveResources(Faction faction)
	{
		int activeMultiplier = 3;
		return (getPassiveResources(faction) * activeMultiplier);
	}		
	
	public boolean equals(World other) 
	{
		return other.address.equals(address);
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
