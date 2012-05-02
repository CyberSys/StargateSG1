package ui.prompt;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;

import planning.Task;

public class PromptTree 
{
	//
	// DATA
	//
	/**
	 * The children nodes of this tree.
	 */
	protected ArrayList<FilterPrompt> mChildren;
	
	/**
	 * The prompt title to display.
	 */
	protected String mTitle;
	
	/**
	 * The prompt message to display.
	 */
	protected String mMessage;
	
	/**
	 * Whether or not the current prompt is a leaf.
	 */
	protected boolean mIsLeaf;
	
	/**
	 * The parent of this prompt tree.
	 */
	protected PromptTree mParent;
	
	//
	// CTOR
	//
	public PromptTree(String title, String message)
	{
		mChildren = new ArrayList<FilterPrompt>();
		
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
		addChildPrompt(child, new PromptFilter()
		{
			@Override
			public boolean allowPrompt(PromptTree tree) 
			{
				return true;
			}
		});
	}
	
	public void addChildPrompt(PromptTreeLeaf child, Faction performer)
	{
		final Faction faction = performer;
		
		addChildPrompt(child, new PromptFilter()
		{
			@Override
			public boolean allowPrompt(PromptTree pt) 
			{
				return pt.getTask().canPerform(faction);
			}
		});
	}
	
	public void addChildPrompt(PromptTree child, PromptFilter filter)
	{
		child.mParent = this;
		mChildren.add(new FilterPrompt(child, filter));
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
			
			int i = 0;
			for(FilterPrompt fp : mChildren)
			{
				if(!fp.mFilter.allowPrompt(fp.mPrompt))
					continue;
				
				if(action == i)
					return fp.mPrompt;
				
				i++;
			}
			
			if(mParent != null)
				return mParent;
			else
				return this;
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
			for(FilterPrompt fp : mChildren)
			{
				if(!fp.mFilter.allowPrompt(fp.mPrompt))
					continue;
				
				doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
				doc.insertString(doc.getLength(), fp.mPrompt.mTitle + NL, null);
			}
			
			if(mParent != null)
			{
				doc.insertString(doc.getLength(), "" + i++ + ": ", boldStyle);
				doc.insertString(doc.getLength(), "Back" + NL, null);
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
	
	public boolean hasAllowableChildren()
	{
		boolean hasChildren = false;
		for(FilterPrompt fp : mChildren)
		{
			if(fp.mFilter.allowPrompt(fp.mPrompt))
			{
				hasChildren = true;
				break;
			}
		}
		return hasChildren;
	}
	
	//
	// Prompt Filtering stuffs.
	//
	protected class FilterPrompt
	{
		public PromptTree mPrompt;
		public PromptFilter mFilter;
		
		public FilterPrompt(PromptTree p, PromptFilter f)
		{
			mPrompt = p;
			mFilter = f;
		}
	}
}
