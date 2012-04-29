package universe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import settings.Globals;
import ui.GameFrame;

import Faction.*;

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
		
		GameFrame.getGameFrame().addToLog("Begin Round " + roundNumber++, "");
		
		//playerFaction = new HumanityFaction();
		
		//factions.add(playerFaction);
		
		//factions.add(new GoauldFaction());
		//factions.add(new AsgardFaction());
		//factions.add(new TokraFaction());
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
		GameFrame.getGameFrame().addToLog("Begin Round " + roundNumber, "");
		
		for(Faction f : factions)
		{
			f.doTurn();
		}
		
		roundNumber++;
		GameFrame.getGameFrame().enableInput();
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
			name = "";
			suffix = "";
			
			for(int i = 0; i < Globals.WORLD_NAME_PREFIX_LENGTH; i++)
			{
				int r = rand.nextInt(26);
				char letter = (char)('A' + r);
				name += letter;
			}
			
			name += "-";
			
			for(int i = 0; i < Globals.WORLD_NAME_SUFFIX_LENGTH - 1; i++)
			{
				int r = rand.nextInt(10);
				
				if(r == 0 && suffix.length() == 0)
					continue;
				
				char num = (char)('0' + r);
				suffix += num;
			}
			
			int r = rand.nextInt(10);
			char num = (char)('0' + r);
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
}
