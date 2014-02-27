/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

public class Driver
{
	public static void main(String[] args)
	{
		CronService objCron = new CronService();
		objCron.start();
	}
}