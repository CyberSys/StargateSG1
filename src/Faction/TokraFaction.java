package Faction;

import universe.*;

public class TokraFaction extends Faction 
{
	public TokraFaction()
	{
		aggression = 50;
		diplomacy = 80;
		science = 20;
		
		World hw = Universe.generateWorld();
		hw.setName("Vorash");
		
		setHomeWorld(hw);
		
		increaseTroops(10);
		
		tech.defensiveCapabilities = 2;
		tech.offensiveCapabilities = .5;
		tech.hyperdriveEfficiency = 0;
		tech.resourceEfficiency = .2;
	}
}
