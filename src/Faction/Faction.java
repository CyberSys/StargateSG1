package Faction;

import java.util.*;

import World.*;

//Todo: The following:
//Think of stats for factions to have
//Think of way to keep track of current task so replanning not a bitch
public abstract class Faction {
	protected int numArmies;
	protected int numShips;
	protected int numResources;
	protected Faction enemy;
	protected List<World> gateAddress = new ArrayList<World>();
	
	public int getCombatStrength() {
		return numArmies;
	}
	public int getNumArmies() {
		return numArmies;
	}
	public void setNumArmies(int numArmies) {
		this.numArmies = numArmies;
	}
	public int getNumShips() {
		return numShips;
	}
	public void setNumShips(int numShips) {
		this.numShips = numShips;
	}
	public int getNumResources() {
		return numResources;
	}
	public void setNumResources(int numResources) {
		this.numResources = numResources;
	}
	public Faction getEnemy() {
		return enemy;
	}
	public void setEnemy(Faction enemy) {
		this.enemy = enemy;
	}
	
	public String toString() {
		return "CombatStrength = " + getCombatStrength() + ", NumArmies = " + numArmies + ", numShips = " + numShips + ", NumResources = " + numResources;
	}
	public boolean isReadyToAttack() {
		// TODO Auto-generated method stub
		return false;
	}
	public Faction getEnemy() {
		// TODO Auto-generated method stub
		return null;
	}
}
