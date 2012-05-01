package faction;

import universe.*;

public class HumanityFaction extends Faction 
{
	public HumanityFaction()
	{
		isPlayerControlled = false;
		
		factionName = "Humanity";
		
		aggression = 50;
		diplomacy = 50;
		science = 50;
		
		World earth = Universe.generateWorld();
		earth.setName("Earth");
		
		setHomeWorld(earth);
		
		increaseTroops(20);
		
		tech.hyperdriveEfficiency = 0;
		tech.offensiveCapabilities = .75;
		tech.defensiveCapabilities = .75;
		tech.resourceEfficiency = .2;
	}
	
	public boolean didWin() {
		return this.controlledWorlds.size() == 2;
	}
}
