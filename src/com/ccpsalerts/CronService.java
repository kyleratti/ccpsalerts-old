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

		CronWorker objWebsiteCheck = new CronWorker(50)
		{
			@Override
			public void run()
			{
				Driver.println("Launching WebsiteChecker");
				WebsiteChecker objChecker = new WebsiteChecker(this);
				objChecker.start();
			}
		};

		objThreadPool.scheduleAtFixedRate(objWebsiteCheck, 0, objWebsiteCheck.getDelay(), TimeUnit.SECONDS);
	}
}