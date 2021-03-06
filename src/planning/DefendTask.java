package planning;

import java.util.ArrayList;
import java.util.List;

import faction.Faction;
import settings.Globals;
import universe.*;

public class DefendTask extends Task {
	
	private World world;
	public DefendTask(World world, Task parent) {
		super(false, "Defend Task", parent);
		this.world = world;
		
	}
	
	protected List<Task> getTaskList(Faction faction) {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new TrainTroopsTask(world, Globals.WORLD_TROOP_POPULATION_CAP * 3 / 4, this));
		taskList.add(new GatherShipTask(world, Globals.WORLD_SHIP_POPULATION_CAP * 3 / 4, this));
		taskList.add(new DirectedResearchTask(Globals.DEFENSE_RESEARCH, this));
		taskList.add(new RaiseMoraleTask(this));
		return taskList;
	}

	public int stepsToCompletion(Faction faction) {
		return getFlavorMatchTask(faction).stepsToCompletion(faction);
	}

	public Task getNextStep(Faction faction) 
	{
		return getFlavorMatchTask(faction);
	}

	public boolean isCompleted(Faction faction) {
		return world.getTroopCount(faction) >= Globals.WORLD_TROOP_POPULATION_CAP * 3 / 4 && world.getShipCount(faction) >= Globals.WORLD_SHIP_POPULATION_CAP * 3 / 4
				&& faction.getTechLevel().defensiveCapabilities >= Globals.MAX_DEFENSIVE_CAPABILITIES * 3 / 4;
	}

	public boolean canPerform(Faction faction) {
		return world != null && getFlavorMatchTask(faction) != null &&  getFlavorMatchTask(faction).canPerform(faction);
	}

	@Override
	public double getFlavorMatch(Faction faction) 
	{
		double defenseFactor = 0;
		
		for(Faction e : faction.getEnemies())
		{
			defenseFactor += e.getAttackStrength(world);
		}
		
		defenseFactor = defenseFactor / Math.max(faction.getDefenseStrength(world), 1);
		
		return (defenseFactor) * (faction.getAggression() + faction.getDiplomacy() + faction.getScience()) / 3.0;
	}
}
