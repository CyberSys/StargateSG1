package HTNP;

import Faction.Faction;

public class GatherShipTask extends Task {

	public GatherShipTask() {
		super(false, "Gather Ships Task");
		tasks.add(new GatherResourcesTask(1));
		tasks.add(new BuildShipTask());
		tasks.add(new StealShipTask());
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		if(tasks.get(1).getFlavorMatch(faction) > tasks.get(2).getFlavorMatch(faction))
		{
			return tasks.get(0).stepsToCompletion(faction);
		}
		else return tasks.get(2).stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(tasks.get(1).getFlavorMatch(faction) > tasks.get(2).getFlavorMatch(faction))
		{
			if(tasks.get(1).canPerform(faction))
				return tasks.get(1);
			else return tasks.get(0);
		}
		else return tasks.get(2);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() > 0;
	}

	@Override
	public boolean canPerform(Faction faction) {
		return false;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
