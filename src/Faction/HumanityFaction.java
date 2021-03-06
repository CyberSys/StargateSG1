package faction;

import universe.*;

public class HumanityFaction extends Faction 
{
	public HumanityFaction()
	{
		isPlayerControlled = true;
		
		factionName = "Humanity";
		
		aggression = 50;
		diplomacy = 50;
		science = 50;
		
		World earth = Universe.generateWorld();
		earth.setName("Earth");
		
		setHomeWorld(earth);
		
		increaseTroops(20);
		
	//	tech.hyperdriveEfficiency = 0;
		tech.offensiveCapabilities = .75;
		tech.defensiveCapabilities = .75;
		tech.resourceEfficiency = .2;
	}
	
	public boolean didWin() 
	{
		for(Faction faction : Universe.factions)
			if(faction instanceof GoauldFaction)
				return faction.isDefeated();
		return false;
	}
}
