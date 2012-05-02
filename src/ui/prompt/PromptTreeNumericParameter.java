package ui.prompt;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

public class PromptTreeNumericParameter extends PromptTreeParameter 
{
	//
	// CTOR
	// 
	public PromptTreeNumericParameter(String title, String message)
	{
		super(title, message);
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
		final String NL = System.getProperty("line.separator");
		
		Document doc = new DefaultStyledDocument();
		
		try 
		{
			doc.insertString(doc.getLength(), mMessage + NL, null);
		} 
		catch (BadLocationException e) {}
		
		pane.setDocument(doc);
	}
}
