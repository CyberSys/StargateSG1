package main;

import ui.GameFrame;
import universe.Universe;

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
	
		MainLoop:
		while(true) {
			Universe.elapseTime();
			for(Faction faction : Universe.factions) {
				if(faction.didWin())
					break MainLoop;
			}
			System.out.println("---------------------------------");
			if(f.plan.firstElement() instanceof AttackTask) System.out.println("Teal'c thinks the Goa'uld are getting ready to attack somebody");
			if(f.plan.firstElement() instanceof ResearchTask) System.out.println("Carter thinks the Goa'uld are trying to increase their technology");		
			if(f.plan.firstElement() instanceof SabotageTask) System.out.println("Daniel thinks the Goa'uld are doing something sneaky");
			System.out.println("---------------------------------");
//			System.out.println(f.getHomeWorld());
//			System.out.println(f2.getHomeWorld());
//			System.out.println(f3.getHomeWorld());
		}
		for(Faction fact : new Faction[] {f, f2, f3})
			System.out.println(fact + " " + fact.getControlledWorlds());
		System.out.println(f.didWin() ? "The Goa'uld have conquered all!" : "The Goa'uld are defeated!");*/
	}
}
