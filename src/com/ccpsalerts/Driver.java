/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import java.util.Date;

import java.text.SimpleDateFormat;

public class Driver
{
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s a");

	public static void main(String[] args)
	{
		CronService objCron = new CronService();
		objCron.start();

		Driver.println("CronService started");
	}

	public static void println(String strMsg)
	{
		System.out.printf("[%s] %s%n", Driver.simpleDateFormat.format(new Date()), strMsg);
	}

	public static void errorln(String strMsg)
	{
		Driver.println("ERROR: " + strMsg);
	}
}