/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import org.apache.commons.cli.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.text.SimpleDateFormat;

/*
* - Last day
* - Prom
* - Graduation
* - Senior Dinner
*/

public class Driver
{
	/** Are we debugging? */
	public static final boolean DEBUGGING = true;

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s a");
	private static final TaskManager taskManager = new TaskManager();
	private static final HashMap<String, Task> tasks = new HashMap<String, Task>();

	public static void main(String[] args)
	{
		Options objOptions = new Options();
		Option objTask = new Option("t", "task", true, "the task to execute");
		
		objOptions.addOption(objTask);

		CommandLineParser objParser = new GnuParser();
		CommandLine objCL = null;
		
		try
		{
			objCL = objParser.parse(objOptions, args);
		}
		catch(MissingArgumentException e)
		{
			Option objTarget = e.getOption();
			Driver.errorln(String.format("Missing argument for '-%s' or '-%s'", objTarget.getOpt(), objTarget.getLongOpt()));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}

		if(objCL == null)
			Driver.errorln("Unable to parse command line. Please check your configuration and try again.", true);

		Driver.setupTasks();

		if(objCL.hasOption("t"))
		{
			String strTask = objCL.getOptionValue("t", null);

			if(strTask == null)
				Driver.errorln("No task set", true);

			if(Driver.taskManager.has(strTask))
			{
				Driver.println("Launching " + strTask);
				Driver.taskManager.launch(strTask);
			}
			else
				Driver.errorln("Task '" + strTask + "' not found", true);
		}
		else
			Driver.errorln("No task set", true);
	}

	private static void setupTasks()
	{
		Driver.taskManager.add(new Task("Website Checker")
			{
				@Override
				public void run()
				{
					WebsiteChecker objChecker = new WebsiteChecker(30);
					objChecker.start();
				}
			});

		Driver.taskManager.add(new Task("Last Day Countdown")
			{
				@Override
				public void run()
				{
					Calendar objLastDay = new GregorianCalendar(2014, Calendar.MAY, 30, 2, 20);
					System.out.println(objLastDay);
					//Driver.println("Theoretically calculating the school days left and tweeting it");
				}
			});

		Driver.taskManager.add(new Task("Prom Countdown")
			{
				@Override
				public void run()
				{
					Driver.println("Theoretically calculating time until each prom and posting a picture with details");

					/*
					* This is going to be a PITA
					*
					* TODO
					*	- Find the date for every school
					*	- Calculate days util each
					*	- Write to image
					*	- Tweet image
					*	- Clean up image
					*/
				}
			});
	}

	public static void println(String strMsg)
	{
		System.out.printf("[%s] %s%n", Driver.simpleDateFormat.format(new Date()), strMsg);
	}

	public static void errorln(String strMsg)
	{
		Driver.errorln(strMsg, false);
	}

	public static void errorln(String strMsg, boolean bQuit)
	{
		Driver.println("ERROR: " + strMsg);

		if(bQuit)
			System.exit(0);
	}
}