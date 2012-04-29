package Faction;

import universe.*;

public class AsgardFaction extends Faction 
{
	public AsgardFaction()
	{	
		aggression = 20;
		diplomacy = 50;
		science = 80;
		
		World val = Universe.generateWorld();
		val.setName("Valhalla");
		
		setHomeWorld(val);
		
		increaseTroops(5);
		increaseShips(2);
		
		tech.offensiveCapabilities = 2;
		tech.defensiveCapabilities = 2;
		tech.hyperdriveEfficiency = 2;
		tech.resourceEfficiency = 0.3;
	}
}
