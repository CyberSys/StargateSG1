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
			else if(faction instanceof HumanityFaction)
				f2 = faction;
		}
		f.learnWorldLocation(f2.getHomeWorld());

//		f.decreaseReputation(f2, 50);
		f2.decreaseReputation(f, 50);
		System.out.println(f.isEnemy(f2) + " " + f2.isEnemy(f));
		
		f.replan();
	
		MainLoop:
		while(true) {
			Universe.elapseTime();
			for(Faction faction : Universe.factions) {
				if(faction.didWin())
					break MainLoop;
			}
			
			System.out.println(f.getHomeWorld());
			System.out.println(f2.getHomeWorld());
		}
		System.out.println(f.didWin() ? "The Goa'uld have conquered all!" : "The humans are victorious!");
	}
}
