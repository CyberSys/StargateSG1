package HTNP;

import Faction.Faction;

public class GatherShipTask extends Task {

	private int limit;
	public GatherShipTask(int limit) {
		super(false, "Gather Ships Task");
		this.limit = limit;
	}

	@Override
	public int stepsToCompletion(Faction faction) {
		if(new BuildShipTask(limit).getFlavorMatch(faction) > new StealShipTask(limit).getFlavorMatch(faction))
		{
			return new BuildShipTask(limit).stepsToCompletion(faction) + new GatherResourcesTask(limit).stepsToCompletion(faction);
		}
		else return new StealShipTask(limit).stepsToCompletion(faction);
	}

	@Override
	public Task getNextStep(Faction faction) {
		if(new BuildShipTask(limit).getFlavorMatch(faction) > new StealShipTask(limit).getFlavorMatch(faction))
		{
			if(new BuildShipTask(limit).canPerform(faction))
				return new BuildShipTask(limit);
			else return new GatherResourcesTask(limit);
		}
		else return new StealShipTask(limit);
	}

	@Override
	public boolean isCompleted(Faction faction) {
		return faction.getNumShips() >= limit;
	}

	@Override
	public boolean canPerform(Faction faction) {
		//TODO: 
		return false;
	}

	@Override
	public double getFlavorMatch(Faction faction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
