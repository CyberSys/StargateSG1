package faction;

import universe.*;

public class GoauldFaction extends Faction 
{
	public GoauldFaction()
	{
		factionName = "Goa'uld";
		
		aggression = 80;
		diplomacy = 20;
		science = 50;
		
		World hw = Universe.generateWorld();
		hw.setName("Chulak");
		
		setHomeWorld(hw);
		
		increaseTroops(15);
		increaseShips(5);
		
		tech.offensiveCapabilities = 1;
		tech.defensiveCapabilities = 1;
		tech.resourceEfficiency = .1;
		tech.hyperdriveEfficiency = .5;
	}
}
