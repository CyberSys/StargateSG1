package ui.prompt;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import faction.Faction;

public class PromptTreeNumericParameter extends PromptTreeParameter 
{
	//
	// CTOR
	// 
	public PromptTreeNumericParameter(String title, String message, Faction f)
	{
		super(title, message, f);
	}

	
	//
	// METHODS
	//
	@Override
	public PromptTree getNextPrompt(String input) 
	{
		try
		{
			int num = Integer.parseInt(input);
			if(num <= 0)
				return mParent;
			
			mValue = num;
			
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
		
		Document doc = new DefaultStyledDocument();
		
		try 
		{
			doc.insertString(doc.getLength(), mMessage + NL, null);
			doc.insertString(doc.getLength(), "Enter a positive integer, or a non-positive to return to previous." + NL, null);
		} 
		catch (BadLocationException e) {}
		
		pane.setDocument(doc);
	}
}
