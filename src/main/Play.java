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
		
		/*Faction f,f2,f3 = f2 = f = null;
		for(Faction faction : Universe.factions) {
			if(faction instanceof GoauldFaction)
				f = faction;
			else if(faction instanceof HumanityFaction)
				f2 = faction;
			else if(faction instanceof TokraFaction)
				f3 = faction;
		}
		f.learnWorldLocation(f2.getHomeWorld());

//		f.decreaseReputation(f2, 50);
		f2.decreaseReputation(f, 50);
	
		MainLoop:
		while(true) {
			Universe.elapseTime();
			for(Faction faction : Universe.factions) {
				if(faction.didWin())
					break MainLoop;
			}
			
			System.out.println(f.getHomeWorld());
			System.out.println(f2.getHomeWorld());
			System.out.println(f3.getHomeWorld());
			
			System.out.println(f3.getReputation(f));
			System.out.println(f.getReputation(f3));
		}
		for(Faction fact : new Faction[] {f, f2, f3})
			System.out.println(fact + " " + fact.getControlledWorlds());
		System.out.println(f.didWin() ? "The Goa'uld have conquered all!" : "The Goa'uld are defeated!");*/
	}
}
