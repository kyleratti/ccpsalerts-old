/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts;

/** An task to process */
public class Task extends Thread
{
	private final String shortName;

	public Task(String strName)
	{
		super(strName + " worker");
		this.shortName = strName.replace(" ", "").replace("-", "").toLowerCase();
	}

	public String getShortName()
	{
		return this.shortName;
	}
}