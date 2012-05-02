package ui.prompt;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;

public abstract class PromptTreeParameter extends PromptTree 
{
	//
	// DATA
	//
	/**
	 * The type of parameter this node represents.
	 */
	private ParameterType mType;
	
	/**
	 * The faction used for determining the values of the parameters.
	 */
	private Faction mFaction;
	
	/**
	 * The value of the parameter entered.
	 */
	private Object mValue;
	
	
	//
	// CTOR
	//
	public PromptTreeParameter(ParameterType pt, Faction f, String title, String message)
	{
		super(title, message);
		
		mFaction = f;
		mType = pt;
	}
	
	
	//
	// METHODS
	//
	/**
	 * Returns the value of the parameter entered.
	 * 
	 * @return the value for this parameter.
	 */
	public Object getValue()
	{
		return mValue;
	}
	
	@Override
	public void addChildPrompt(PromptTree pt)
	{
		mChildren.clear();
		mChildren.add(pt);
	}
	
	@Override
	public PromptTree getNextPrompt(String input)
	{
		try
		{
			mValue = mType.parseParameter(input, mFaction);
			
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
		final String NL = System.getProperty("line.separator");
		
		Document doc = new DefaultStyledDocument();
		
		try 
		{
			doc.insertString(doc.getLength(), mMessage + NL, null);
			mType.writePrompt(doc, mFaction);
		} 
		catch (BadLocationException e) {}
		
		pane.setDocument(doc);
	}
	
	//
	// PARAMETER TYPE
	//
	public enum ParameterType
	{
		OWNED_WORLD,
		KNOWN_LOC_WORLD,
		KNOWN_GATE_WORLD,
		INTEGER;
		
		public Object parseParameter(String input, Faction f) throws NumberFormatException
		{
			switch(this)
			{
			case OWNED_WORLD:
			case KNOWN_LOC_WORLD:
			case KNOWN_GATE_WORLD:
				int i = Integer.parseInt(input);
				return getParamList(f)[i];
			case INTEGER:
				return Integer.parseInt(input);
			default:
				return new Object[0];
			}
		}
		
		public Object[] getParamList(Faction f)
		{
			switch(this)
			{
			case OWNED_WORLD:
				return f.getControlledWorlds().toArray();
			case KNOWN_LOC_WORLD:
				return f.getKnownWorldLocations().toArray();
			case KNOWN_GATE_WORLD:
				return f.getKnownGateAddresses().toArray();
			default:
				return new Object[0];
			}
		}
		
		public void writePrompt(Document doc, Faction f) throws BadLocationException
		{
			switch(this)
			{
			case OWNED_WORLD:
			case KNOWN_LOC_WORLD:
			case KNOWN_GATE_WORLD:
				writeWorldList(doc, f);
			case INTEGER:
				doc.insertString(doc.getLength(), "Please enter an integer:", null);
			}
		}
		
		private void writeWorldList(Document doc, Faction f) throws BadLocationException
		{
			final String NL = System.getProperty("line.separator");
			
			final SimpleAttributeSet boldStyle = new SimpleAttributeSet();
			boldStyle.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
			
			Object[] worlds = getParamList(f);
			
			int i = 0;
			for(Object w : worlds)
			{
				doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
				doc.insertString(doc.getLength(), w + NL, null);
			}
		}
	}
}
