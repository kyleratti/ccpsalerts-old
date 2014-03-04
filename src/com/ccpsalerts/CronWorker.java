/**
* @author Kyle Ratti
* @version 1.0, 03/04/14
*/

package com.ccpsalerts;

public class CronWorker implements Runnable
{
	private final int delay;

	public CronWorker(int iDelay)
	{
		this.delay = iDelay;
	}

	public int getDelay()
	{
		return this.delay;
	}

	@Override
	public void run()
	{
		Driver.errorln("No override specified for CronWorker");
	}
}