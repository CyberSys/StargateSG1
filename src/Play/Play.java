package Play;

import universe.Universe;
import universe.World;
import Faction.*;
import HTNP.*;

public class Play {
	public static void main(String[] args) {
		Faction f = new TestFaction();
		Faction f2 = new TestFaction();
		World targetWorld = Universe.generateWorld();
		f2.setHomeWorld(targetWorld);
		f.learnWorldLocation(targetWorld);
		f2.increaseTroops(15);
		f.decreaseReputation(f2, 50);
		f.replan();
	
		while(true) {
			System.out.println(f);
			System.out.println(f2);
			//System.out.println(f.plan.firstElement().stepsToCompletion(f));
			f.doTurn();
			if(f.didWin())
				break;
		}
		System.out.println(f.didWin());
	}
}
