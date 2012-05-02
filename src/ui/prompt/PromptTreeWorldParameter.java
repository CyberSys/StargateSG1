package ui.prompt;

import java.util.List;

import javax.swing.JTextPane;

import faction.Faction;

import universe.World;

public class PromptTreeWorldParameter extends PromptTreeParameter 
{
	//
	// DATA
	//
	/**
	 * The faction who will be performing this action.
	 */
	private Faction mFaction;
	
	
	//
	// CTOR
	//
	public PromptTreeWorldParameter(String title, String message, Faction f)
	{
		super(title, message);
		
		mFaction = f;
	}
	
	@Override
	public PromptTree getNextPrompt(String input) 
	{
		try
		{
			int num = Integer.parseInt(input);
			mValue = num;
			
			return mChildren.get(0);
		}
		catch(Exception e)
		{
			return this;
		}
	}

	@Override
	public void writePrompt(JTextPane pane) 
	{
		// TODO Auto-generated method stub

	}

	private List<World> getWorldList()
	{
		return null;
	}
	
	
	//
	// WORLD FILTER
	//
	public enum WorldFilter
	{
		CONTROLLED_WORLD,
		UNCONTROLLED_WORLD,
		WORLD_WITH_TROOPS,
		ANY_WORLD;
	}
}