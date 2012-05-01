package faction;

import java.util.Arrays;

import settings.Globals;

public class Reputation 
{
	//
	// DATA
	//	
	double currentRep;
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
	
	private void setReputation(double d)
	{
		currentRep = Math.min(Math.max(Globals.MIN_REPUTATION, d), Globals.MAX_REPUTATION);
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
		
		public int threshold;
		public String name;
		
		ReputationLevel(String n, int t)
		{
			name = n;
			threshold = t;
		}
		
		public String toString()
		{
			return name;
		}
		
		private static ReputationLevel getRepLevel(double rep)
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
