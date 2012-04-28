package World;

import Faction.Faction;

public class World {
	Faction controllingFaction;

	public Object getControllingFaction() {
		return controllingFaction;
	}
	
	public boolean equals(World other) {
		return true;
	}

	public void setControllingFaction(Faction faction) {
		controllingFaction = faction;	
	}

}
