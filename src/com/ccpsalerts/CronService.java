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
	private static final long DELAY = 60;

	@Override
	public void run()
	{
		ScheduledExecutorService objThreadPool = Executors.newScheduledThreadPool(5);
		objThreadPool.scheduleAtFixedRate(new MinuteCron(), 0, CronService.DELAY, TimeUnit.SECONDS);
	}
}

class MinuteCron implements Runnable
{
	@Override
	public void run()
	{
		Driver.println("Starting WebsiteChecker");
		WebsiteChecker objChecker = new WebsiteChecker();
		objChecker.start();
	}
}