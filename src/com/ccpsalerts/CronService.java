/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CronService extends Thread
{
	@Override
	public void run()
	{
		ScheduledExecutorService objThreadPool = Executors.newScheduledThreadPool(10);
		objThreadPool.scheduleAtFixedRate(new MinuteCron(), 0, 60, TimeUnit.SECONDS);
	}
}

class MinuteCron implements Runnable
{
	@Override
	public void run()
	{
		Driver.println("Launching WebsiteChecker");
		WebsiteChecker objChecker = new WebsiteChecker();
		objChecker.start();
	}
}