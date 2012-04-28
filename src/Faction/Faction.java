package Faction;

import java.util.*;

import World.*;

//Todo: The following:
//Think of stats for factions to have
//Think of way to keep track of current task so replanning not a bitch
public abstract class Faction {
	protected boolean isReadyToAttack = false;
	protected int numArmies;
	protected int numShips;
	protected int numResources;
	protected Faction enemy;
	protected List<World> gateAddress = new ArrayList<World>();
	protected List<World> knownWorldLocations = new ArrayList<World>();
	protected World world;
	
	public void setIsReadyToAttack(boolean isReady) {
		this.isReadyToAttack = isReady;
	}
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
	public boolean knowsGateAddress(World world) {
		return gateAddress.contains(world);
	}
	
	public String toString() {
		return "CombatStrength = " + getCombatStrength() + ", NumArmies = " + numArmies + ", numShips = " + numShips + ", NumResources = " + numResources;
	}
	public boolean isReadyToAttack() {
		return isReadyToAttack;
	}
	public World getWorld() {
		return world;
	}
	public boolean hasGate() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean knowsLocation(World world) {
		return true;
		//return knownWorldLocations.contains(world);
	}
	public void setWorld(World world) {
		this.world = world;	
	}
	
	public void addKnownWorldLocation(World world) {
		knownWorldLocations.add(world);
	}
}
