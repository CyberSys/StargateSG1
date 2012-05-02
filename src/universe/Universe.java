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
		
		playerFaction.decreaseReputation(goauld, 25);
		playerFaction.increaseReputation(asgard, 10);
		playerFaction.increaseReputation(tokra, 0);
		
		playerFaction.learnGateAddress(goauld.getHomeWorld());	
		playerFaction.decreaseReputation(goauld, 25);
		
		asgard.learnGateAddress(goauld.getHomeWorld());
		asgard.learnWorldLocation(goauld.getHomeWorld());
		
		tokra.learnGateAddress(goauld.getHomeWorld());
		tokra.learnWorldLocation(goauld.getHomeWorld());
		
		goauld.learnGateAddress(playerFaction.getHomeWorld());
		
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
			if(f.didWin())
			{
				// TODO: Any other victory stuffs.
				return;
			}
		}
		
		for(Faction f : factions)
		{
			if(!f.isDefeated())
				f.doTurn();
		}
		
		for(World w : addressBook.values()) {
			//w.addTroops(w.getControllingFaction(), w.getPassiveTroops());
			w.doCombat();
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
