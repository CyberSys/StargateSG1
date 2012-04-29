package faction;

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
		increaseShips(1);
		
		tech.offensiveCapabilities = 2.5;
		tech.defensiveCapabilities = 2.5;
		tech.hyperdriveEfficiency = 2.5;
		tech.resourceEfficiency = 0.4;
	}
}
