package ui.prompt;

import javax.swing.JTextPane;

import faction.Faction;

public abstract class PromptTreeParameter extends PromptTree 
{
	//
	// DATA
	//	
	/**
	 * The value of the parameter entered.
	 */
	protected Object mValue;
	
	/**
	 * The faction who will be performing this action.
	 */
	protected Faction mFaction;
	
	
	//
	// CTOR
	//
	public PromptTreeParameter(String title, String message, Faction f)
	{
		super(title, message);
		
		mFaction = f;
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
	public void addChildPrompt(PromptTree pt, PromptFilter filter)
	{
		pt.mParent = this;
		
		mChildren.clear();
		mChildren.add(new FilterPrompt(pt, filter));
	}
	
	@Override
	public abstract PromptTree getNextPrompt(String input);
	
	@Override
	public abstract void writePrompt(JTextPane pane);
}
