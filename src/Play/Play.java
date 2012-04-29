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
		World homeWorld = Universe.generateWorld();
		f.setHomeWorld(homeWorld);
		f2.setHomeWorld(targetWorld);
		f.learnWorldLocation(targetWorld);
		targetWorld.setControllingFaction(f2);
		homeWorld.setControllingFaction(f);
		f.decreaseReputation(f2, 50);
		targetWorld.addTroops(f2, 10);
		targetWorld.addShips(f2, 5);
		
		f.replan();
	
		while(true) {
			System.out.println(homeWorld);
			System.out.println(targetWorld);
			System.out.println(f);
			System.out.println(f2);
			f.doTurn();
			if(targetWorld.getControllingFaction() == f)
				break;
		}
		System.out.println(targetWorld.getControllingFaction() == f);
	}
}
