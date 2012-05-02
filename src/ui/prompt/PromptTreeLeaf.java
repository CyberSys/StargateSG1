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
	 * The parameter nodes that are to be used for creating the task.
	 */
	private PromptTreeParameter[] mParams;
	
	/**
	 * The paramaterizer for this leaf.
	 */
	
	
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
	
	
	//
	// METHODS
	//
	public void setParams(PromptTreeParameter[] params)
	{
		mParams = params;
	}
	
	@Override
	public Task getTask()
	{
		if(mParams == null)
			return mTask;
		else
			return null;
	}
}
