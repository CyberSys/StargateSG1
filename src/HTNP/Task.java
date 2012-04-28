package HTNP;

import java.util.*;

import Faction.Faction;
//Todo: Make the task check for stuff work as follows:
//Check to see that every task looks like it has finished (this will have to be personalized to each task
//Check to see that we do the first task in our list that looks unfinished (i.e., it doesn't look completed)
public abstract class Task {
	//Tasks should 
	protected ArrayList<Task> tasks = new ArrayList<Task>();
	protected boolean isBaseTask;
	protected String name;

	public Task(boolean isBaseTask, String taskName) {
		this.isBaseTask = isBaseTask;
		this.name = taskName;
	}
	
	public abstract int stepsToCompletion(Faction faction);
	
	public abstract Task getNextStep(Faction faction);
	
	public abstract boolean isCompleted(Faction faction);
	
	public abstract boolean canPerform(Faction faction);
	
	public void perform(Faction faction) {
		System.out.println("Doing " + name);
		getNextStep(faction).perform(faction);
	}
	
	//Think of way to have this work generically
	//Flavors are war, science, diplomacy
	public abstract double getFlavorMatch(Faction faction);

	public boolean isBaseTask() {
		return isBaseTask;
	}
	
	
}
