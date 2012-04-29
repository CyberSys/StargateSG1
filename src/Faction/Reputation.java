package Faction;

import java.util.Arrays;

public class Reputation 
{
	//
	// DATA
	//
	private static final int MAX_REP = 100;
	private static final int MIN_REP = 0;
	
	int currentRep;
	ReputationLevel reputationLevel; 
	
	//
	// CTOR
	//
	public Reputation()
	{
		this(50);
	}
	
	public Reputation(int currRep)
	{
		setReputation(currRep);
	}
	
	//
	// METHODS
	//	
	public void adjustReputation(int amount)
	{
		setReputation(currentRep + amount);
	}
	
	public int compareReputation(ReputationLevel rl)
	{
		return this.reputationLevel.compareRep(rl);
	}
	
	private void setReputation(int amount)
	{
		currentRep = Math.min(Math.max(MIN_REP, amount), MAX_REP);
		reputationLevel = ReputationLevel.getRepLevel(currentRep);
	}
	
	//
	// ENUM
	//
	public enum ReputationLevel
	{
		DIRE_ENEMY("Dire Enemy", 10),
		ENEMY("Enemy", 25),
		DISTRUST("Distrust", 40),
		NEUTRAL("Neutral", 60),
		TRUST("Trust", 75),
		FRIEND("Friend", 90),
		COMRADE("Comrade", 100);		
		
		private int threshold;
		private String name;
		
		ReputationLevel(String n, int t)
		{
			name = n;
			threshold = t;
		}
		
		public String toString()
		{
			return name;
		}
		
		private static ReputationLevel getRepLevel(int rep)
		{
			ReputationLevel ret = null;
			
			for(ReputationLevel rl : ReputationLevel.values())
			{
				ret = rl;
				
				if(rep <= rl.threshold)
					break;
			}
			
			return ret;
		}
		
		private int compareRep(ReputationLevel rl)
		{
			return this.threshold - rl.threshold;
		}
	}
}
