package Play;

import Faction.*;
import HTNP.*;

public class Play {
	public static void main(String[] args) {
		Faction f = new TestFaction();
		Task t = new AttackTask();
		while(!t.isCompleted(f)) {
			System.out.println(f);
			System.out.println(t.stepsToCompletion(f));
			t.perform(f);
		}
		
		System.out.println(f);
		System.out.println(t.stepsToCompletion(f));
			
	}

}
