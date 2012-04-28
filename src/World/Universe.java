package World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Faction.Faction;

public class Universe 
{
	//
	// DATA
	//
	// World Data
	public static final int ADDRESS_LENGTH = 7;
	public static Map<String, World> addressBook = new HashMap<String, World>();
	
	// Faction Data
	public static final List<Faction> factions = new ArrayList<Faction>();
	
	private static final Random rand = new Random();
	
	//
	// METHODS
	//
	public static World generateWorld()
	{
		World w = new World();
		String address = generateUniqueAddress();
		
		w.address = address;
		addressBook.put(address, w);
		
		return w;
	}
	
	public static int getDistance(World w1, World w2)
	{
		return getDistance(w1.address, w2.address);
	}
	
	public static int getDistance(String addr1, String addr2)
	{
		int dist = 0;
		for(int i = 0; i < ADDRESS_LENGTH; i++)
		{
			dist += Math.abs(addr1.charAt(i) - addr2.charAt(i));
		}
		
		return dist;
	}
	
	// Could infinite loop....if we get really unlucky.
	private static String generateUniqueAddress()
	{
		String addr;
		do
		{
			addr = "";
			
			for(int i = 0; i < ADDRESS_LENGTH; i++)
			{
				int r = rand.nextInt(26);
				char letter = (char)('A' + r);
				addr += letter;
			}
		
		} while(addressBook.containsKey(addr));
			
		return addr;
	}
}
