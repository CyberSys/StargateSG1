package faction;

import java.util.ArrayList;
import java.util.Random;

import planning.AttackTask;
import planning.DefendTask;
import planning.ResearchTask;
import planning.SabotageTask;
import planning.Task;
import universe.*;

public class AsgardFaction extends Faction 
{
	public AsgardFaction()
	{	
		factionName = "Asgard";
		
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
	
	protected ArrayList<Task> getTaskList() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for(Faction enemy : getEnemies()) {
			Task attack = new AttackTask(getHomeWorld(), enemy.getHomeWorld(), enemy, 
					new Random().nextInt(100) + 50, null);
			if(attack.canPerform(this))
				taskList.add(attack);
			for(World world : enemy.getControlledWorlds()) {
				Task sabotage = new SabotageTask(world, null);
				if(sabotage.canPerform(this))
					taskList.add(sabotage);
			}
		}
		
		for(Faction faction : Universe.factions) {
			if(faction != this) {
				
			}
		}
		Task defend = new DefendTask(getHomeWorld(), null);
		
		Task research = new ResearchTask(null);
		if(defend.canPerform(this))
			taskList.add(defend);
		
		if(research.canPerform(this))
			taskList.add(research);
		
		return taskList;
	}

	public boolean didWin() {
		for(Faction faction : Universe.factions)
			if(faction instanceof GoauldFaction)
				return faction.isDefeated();
		return false;
	}
}
