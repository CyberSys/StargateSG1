package ui.prompt;

import planning.Task;

public interface TaskParameterizer 
{
	public abstract Task generateTask(PromptTreeParameter[] params);
}
