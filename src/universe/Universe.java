package universe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import planning.*;

import faction.*;

import settings.Globals;
import ui.GameFrame;


public class Universe 
{
	//
	// DATA
	//
	// World Data
	public static Map<String, World> addressBook = new HashMap<String, World>();
	private static final Map<String, World> nameBook = new HashMap<String, World>();
	
	// Faction Data
	public static Faction playerFaction;
	public static final List<Faction> factions = new ArrayList<Faction>();
	
	// Book Keeping
	private static final Random rand = new Random();
	private static boolean isInitialized = false;
	private static int roundNumber = 0;
	public static boolean gameOver = false;
	
	//
	// METHODS
	//
	public static void initialize()
	{
		if(isInitialized)
			return;
		playerFaction = new HumanityFaction();
		GameFrame.getGameFrame();
		
		factions.add(playerFaction);
		
		Faction goauld = new GoauldFaction();
		Faction asgard = new AsgardFaction();
		Faction tokra = new TokraFaction();
		
		playerFaction.decreaseReputation(goauld, 50);
		playerFaction.increaseReputation(asgard, 0);
		playerFaction.increaseReputation(tokra, 0);
		
		playerFaction.learnGateAddress(goauld.getHomeWorld());
		playerFaction.learnWorldLocation(goauld.getHomeWorld());
		playerFaction.learnGateAddress(tokra.getHomeWorld());
		playerFaction.learnGateAddress(asgard.getHomeWorld());
		
		asgard.learnGateAddress(goauld.getHomeWorld());
		asgard.learnWorldLocation(goauld.getHomeWorld());
		asgard.learnGateAddress(playerFaction.getHomeWorld());
		asgard.learnWorldLocation(playerFaction.getHomeWorld());
		
		tokra.learnGateAddress(goauld.getHomeWorld());
		tokra.learnWorldLocation(goauld.getHomeWorld());
		tokra.learnGateAddress(playerFaction.getHomeWorld());
		tokra.learnWorldLocation(playerFaction.getHomeWorld());
		
		goauld.learnGateAddress(playerFaction.getHomeWorld());
		goauld.learnWorldLocation(playerFaction.getHomeWorld());
		goauld.learnGateAddress(tokra.getHomeWorld());
		goauld.learnGateAddress(asgard.getHomeWorld());
		
		factions.add(goauld);
		factions.add(asgard);
		factions.add(tokra);
	}
	
	public static World generateWorld()
	{
		World w = new World();
		String address = generateUniqueAddress();
		String name = generateUniqueName();
		
		w.address = address;
		addressBook.put(address, w);
		
		w.name = name;
		nameBook.put(name, w);
		
		return w;
	}
	
	public static void elapseTime()
	{
		GameFrame.addToLog("Begin Round " + roundNumber, "");
		
		
		
		for(Faction f : factions)
		{
			if(!f.isDefeated())
				f.doTurn();
			else if(f.isDefeated() && f == playerFaction)
			{
				GameFrame.addToLog("You have failed! Earth has been taken over. We trusted you!");
				gameOver = true;
			}
		}
		
		for(World w : addressBook.values()) {
			w.doCombat();
		}
		
		for(Faction f : factions)
		{
			if(f.didWin())
			{
				if(f == playerFaction)
				{
					GameFrame.addToLog("You are victorious! The Goa'uld have been defeated, and all is well in the galaxy!  Huzzah!");
					gameOver = true;
				}
				else if(f instanceof GoauldFaction)
				{
					GameFrame.addToLog("The Goa'uld have taken over the galaxy.  All is lost!");
					gameOver = true;
				}
				return;
			}
		}
		
		
		roundNumber++;
	}

	public static int getDistance(World w1, World w2)
	{
		return getDistance(w1.address, w2.address);
	}
	
	public static int getDistance(String addr1, String addr2)
	{
		int dist = 0;
		for(int i = 0; i < Globals.ADDRESS_LENGTH; i++)
		{
			dist += Math.abs(addr1.charAt(i) - addr2.charAt(i));
		}
		
		return dist;
	}
	
	public static void updateName(World w, String newName)
	{
		nameBook.remove(w.name);
		w.name = newName;
		nameBook.put(newName, w);
	}
	
	private static String generateUniqueName()
	{
		String name;
		String suffix;
		
		do
		{
			name = "P";
			suffix = "";
			
			int r = rand.nextInt(9);
			char num = (char)('1' + r);
			name += num;
			
			r = rand.nextInt(26);
			char letter = (char)('A' + r);
			name += letter;
			
			name += "-";
			
			for(int i = 0; i < Globals.WORLD_NAME_SUFFIX_LENGTH - 1; i++)
			{
				r = rand.nextInt(10);
				
				if(r == 0 && suffix.length() == 0)
					continue;
				
				num = (char)('0' + r);
				suffix += num;
			}
			
			r = rand.nextInt(10);
			num = (char)('0' + r);
			suffix += num;
			
			name += suffix;
			
		} while(addressBook.containsKey(name));
		
		return name;
	}
	
	// Could infinite loop....if we get really unlucky.
	private static String generateUniqueAddress()
	{
		String addr;
		do
		{
			addr = "";
			
			for(int i = 0; i < Globals.ADDRESS_LENGTH; i++)
			{
				int r = rand.nextInt(26);
				char letter = (char)('A' + r);
				addr += letter;
			}
		
		} while(addressBook.containsKey(addr));
			
		return addr;
	}

	public static void getMilitaryAdvice() {
		ArrayList<Faction> factionList = new ArrayList<Faction>();
		for(Faction faction : factions) {
			if(faction != playerFaction && !faction.plan.isEmpty() && faction.plan.firstElement() instanceof AttackTask)
				factionList.add(faction);
		}
		if(factionList.size() > 0) {
			Faction choice = factionList.get(new Random().nextInt(factionList.size()));
			GameFrame.addToLog("Teal'c says", "The " + choice.factionName + " might be preparing to attack. " + 
			(choice.plan.firstElement().target == playerFaction ? "" : "The ") + choice.plan.firstElement().target.factionName
			+ " should be cautious.");
		}
		else 
			GameFrame.addToLog("Teal'c says", "Indeed.");
	}

	public static void getDiplomacyAdvice() {
		ArrayList<Faction> factionList = new ArrayList<Faction>();
		for(Faction faction : factions) {
			if(faction != playerFaction && !faction.plan.isEmpty() && faction.plan.firstElement() instanceof SabotageTask)
				factionList.add(faction);
		}
		if(factionList.size() > 0) {
			Faction choice = factionList.get(new Random().nextInt(factionList.size()));
			GameFrame.addToLog("Daniel says", "The " + choice.factionName + " might be up to something sneaky. " + 
			(choice.plan.firstElement().target == playerFaction ? "" : "The ") + choice.plan.firstElement().target.factionName
			+ " should be on the lookout.");
		}
		else 
			GameFrame.addToLog("Daniel says", "Achoo!");
	}

	public static void getScienceAdvice() {
		ArrayList<Faction> factionList = new ArrayList<Faction>();
		for(Faction faction : factions) {
			if(faction != playerFaction && !faction.plan.isEmpty() && faction.plan.firstElement() instanceof ResearchTask)
				factionList.add(faction);
		}
		if(factionList.size() > 0) {
			Faction choice = factionList.get(new Random().nextInt(factionList.size()));
			GameFrame.addToLog("Carter says", "The " + choice.factionName + " seems to be focusing on their research.");
		}
		else 
			GameFrame.addToLog("Carter says", "Holy Hannah!");
	}
}
