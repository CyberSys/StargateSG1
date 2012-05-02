package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import planning.Task;
import universe.Universe;

public class PromptTree 
{
	//
	// DATA
	//
	/**
	 * The children nodes of this tree.
	 */
	protected ArrayList<PromptTree> mChildren;
	
	/**
	 * The prompt title to display.
	 */
	private String mTitle;
	
	/**
	 * The prompt message to display.
	 */
	private String mMessage;
	
	/**
	 * Whether or not the current prompt is a leaf.
	 */
	protected boolean mIsLeaf;
	
	//
	// CTOR
	//
	public PromptTree(String title, String message)
	{
		mChildren = new ArrayList<PromptTree>();
		
		mIsLeaf = false;
		
		mTitle = title;
		mMessage = message;
	}
	
	
	//
	// METHODS
	//
	/**
	 * Adds a child to the prompt tree.
	 * 
	 * @param child the child to add.
	 */
	public void addChildPrompt(PromptTree child)
	{
		mChildren.add(child);
	}
	
	/**
	 * Asks the task tree for the next layer of output.
	 * 
	 * @param input the user input to determine the next prompt from.
	 * @return the next prompt.
	 */
	public PromptTree getNextPrompt(String input)
	{
		try
		{
			int action = Integer.parseInt(input);
			
			return mChildren.get(action);
		}
		catch(Exception e)
		{
			return this;
		}
	}
	
	/**
	 * Writes the prompt onto the given pane.
	 * 
	 * @param pane the text pane to write the prompt into.
	 */
	public void writePrompt(JTextPane pane)
	{
		final String NL = System.getProperty("line.separator");
		
		final SimpleAttributeSet boldStyle = new SimpleAttributeSet();
		boldStyle.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		
		Document doc = new DefaultStyledDocument();
		
		try
		{
			//doc.insertString(doc.getLength(), mTitle + NL, boldStyle);
			doc.insertString(doc.getLength(), mMessage + NL, null);
			
			int i = 0;
			for(PromptTree pt : mChildren)
			{
				doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
				doc.insertString(doc.getLength(), pt.mTitle + NL, null);
			}
		}
		catch (BadLocationException ex){}
		
		pane.setDocument(doc);
	}
	
	/**
	 * Returns whether or not the given tree is a leaf.
	 * 
	 * @return true if the tree is a leaf, false otherwise.
	 */
	public boolean isLeaf()
	{
		return mIsLeaf;
	}
	
	/**
	 * Returns the Task for this prompt tree node. Will be null at non-leaf nodes.
	 * 
	 * @return the task of this prompt or null if there is no task.
	 */
	public Task getTask()
	{
		return null;
	}
}
