package main;

import faction.*;
import planning.*;
import ui.GameFrame;
import universe.Universe;
import universe.World;

public class Play {
	public static void main(String[] args) 
	{
		Universe.initialize();
		GameFrame.getGameFrame().setVisible(true);
		
		Faction f = null, f2 = null;
		for(Faction faction : Universe.factions) {
			if(faction instanceof GoauldFaction)
				f = faction;
			else if(faction instanceof AsgardFaction)
				f2 = faction;
		}
		f.learnWorldLocation(f2.getHomeWorld());

		f.decreaseReputation(f2, 50);
		f2.decreaseReputation(f, 50);
		
		f.replan();
	
		while(true) {
			System.out.println(f.getHomeWorld());
			System.out.println(f2.getHomeWorld());
			System.out.println(f);
			System.out.println(f2);
			f.doTurn();
			f2.doTurn();
			if(f2.getHomeWorld().getControllingFaction() == f && f.getHomeWorld().getControllingFaction() == f)
				break;
			if(f2.getHomeWorld().getControllingFaction() == f2 && f.getHomeWorld().getControllingFaction() == f2)
				break;
//			if(f2.getHomeWorld().getControllingFaction() == f || f.getHomeWorld().getControllingFaction() == f2)
//				break;
			if(f2.getTechLevel().isMinimum() && f2.getNumArmies(f2.getHomeWorld()) == 0 && f2.getNumShips(f2.getHomeWorld()) == 0)
				break;
			if(f.getTechLevel().isMinimum() && f.getNumArmies(f.getHomeWorld()) == 0 && f.getNumShips(f.getHomeWorld()) == 0)
				break;
		}
//		System.out.println(f2.getHomeWorld().getControllingFaction() == f);
//		System.out.println(f.getHomeWorld().getControllingFaction() == f2);
		System.out.println(f2.getTechLevel().isMinimum() && f2.getNumArmies(f2.getHomeWorld()) == 0 && f2.getNumShips(f2.getHomeWorld()) == 0);
		System.out.println(f.getTechLevel().isMinimum() && f.getNumArmies(f.getHomeWorld()) == 0 && f.getNumShips(f.getHomeWorld()) == 0);
	}
}
