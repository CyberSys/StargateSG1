package ui.prompt;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;

import universe.World;

public class PromptTreeWorldParameter extends PromptTreeParameter 
{
	//
	// DATA
	//	
	/**
	 * The filter on the worlds to pick from.
	 */
	private WorldFilter mFilter;
	
	//
	// CTOR
	//
	public PromptTreeWorldParameter(String title, String message, Faction f, WorldFilter filter)
	{
		super(title, message, f);

		mFilter = filter;
	}
	
	@Override
	public PromptTree getNextPrompt(String input) 
	{
		try
		{
			int num = Integer.parseInt(input);
			if(num == getWorldList().length)
				return mParent;
			
			mValue = getWorldList()[num];
			
			return mChildren.get(0).mPrompt;
		}
		catch(Exception e)
		{
			return this;
		}
	}

	@Override
	public void writePrompt(JTextPane pane) 
	{
		final String NL = System.getProperty("line.separator");
		
		final SimpleAttributeSet boldStyle = new SimpleAttributeSet();
		boldStyle.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		
		Document doc = new DefaultStyledDocument();
		
		try 
		{
			doc.insertString(doc.getLength(), mMessage + NL, null);
			
			World[] worlds = getWorldList();
			
			int i = 0;
			for(World w : worlds)
			{
				doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
				doc.insertString(doc.getLength(), w + NL, null);
			}
			
			doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
			doc.insertString(doc.getLength(), "Back" + NL, null);
		} 
		catch (BadLocationException e) {}
		
		pane.setDocument(doc);
	}

	private World[] getWorldList()
	{
		switch(mFilter)
		{
		case CONTROLLED_WORLD:
			return mFaction.getControlledWorlds().toArray(new World[0]);
		case UNCONTROLLED_WORLD:
			ArrayList<World> ret = new ArrayList<World>();
			ret.addAll(mFaction.getKnownWorlds());
			ret.removeAll(mFaction.getControlledWorlds());
			return ret.toArray(new World[0]);
		case WORLD_WITH_UNITS:
			ArrayList<World> r = new ArrayList<World>();
			for(World w : mFaction.getKnownWorlds())
				if(w.getShipCount(mFaction) > 0 || w.getTroopCount(mFaction) > 0)
					r.add(w);
			return r.toArray(new World[0]); // TODO: Finish this.
		case ANY_WORLD:
			return mFaction.getKnownWorlds().toArray(new World[0]);
		default:
			return new World[0];
		}
	}
	
	
	//
	// WORLD FILTER
	//
	public enum WorldFilter
	{
		CONTROLLED_WORLD,
		UNCONTROLLED_WORLD,
		WORLD_WITH_UNITS,
		ANY_WORLD;
	}
}