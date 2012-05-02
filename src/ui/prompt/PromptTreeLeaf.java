package ui.prompt;

import planning.Task;

public class PromptTreeLeaf extends PromptTree 
{
	//
	// DATA
	//
	/**
	 * The task to be performed.
	 */
	private Task mTask;
	
	/**
	 * The paramaterizer for this leaf.
	 */
	private TaskParameterizer mTaskMaker;
	
	//
	// CTOR
	//
	public PromptTreeLeaf(Task t)
	{
		this(t, t.toString(), t.toString());
	}
	
	public PromptTreeLeaf(Task t, String title, String message)
	{
		super(title, message);
		
		mTask = t;
		mIsLeaf = true;
	}
	
	public PromptTreeLeaf(String title, String message, TaskParameterizer tm)
	{
		this(null, title, message);
		
		mTaskMaker = tm;
	}
	
	
	//
	// METHODS
	//	
	@Override
	public Task getTask()
	{
		if(mTaskMaker == null)
		{
			return mTask;
		}
		else
		{
			return mTaskMaker.generateTask();
		}
	}
}
