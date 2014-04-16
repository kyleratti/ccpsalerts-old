/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts;

import java.util.HashMap;

/** A class to manage multiple tasks */
public class TaskManager
{
	private final HashMap<String, Task> tasks = new HashMap<String, Task>();

	public void add(Task objTask)
	{
		this.tasks.put(objTask.getShortName(), objTask);
	}

	public boolean has(String strKey)
	{
		return this.tasks.containsKey(strKey);
	}

	public Task get(String strKey)
	{
		if(this.has(strKey))
			return this.tasks.get(strKey);

		return null;
	}

	public void launch(String strKey)
	{
		if(!this.has(strKey))
			return;

		this.get(strKey).start();
	}
}