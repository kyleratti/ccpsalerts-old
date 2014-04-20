/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import com.ccpsalerts.calendar.*;

import org.apache.commons.cli.*;

import org.joda.time.*;
import org.joda.time.DateTimeConstants.*;

import twitter4j.*;

import java.util.Date;
import java.util.HashMap;

import java.text.SimpleDateFormat;

/*
* Last Day 			- Done
* Prom
* Graduation
* Senior Dinner
*/

public class Driver
{
	/** Are we debugging? */
	public static final boolean DEBUGGING = true;

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:m:s a");
	private static final TaskManager taskManager = new TaskManager();
	private static final SchoolCalendar schoolCalendar = new SchoolCalendar();

	public static void main(String[] args)
	{
		Options objOptions = new Options();	
		objOptions.addOption(new Option("t", "task", true, "the task to execute"));

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

		Driver.setup();

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

	private static void setup()
	{
		Driver.setupCalendar();
		Driver.setupTasks();
	}

	private static void setupCalendar()
	{
		SchoolYear objFirstYear = new SchoolYear(2013);
			SchoolMonth objAug = new SchoolMonth(objFirstYear, DateTimeConstants.AUGUST, 26);

			SchoolMonth objSep = new SchoolMonth(objFirstYear, DateTimeConstants.SEPTEMBER);
				objSep.removeDay(2)
				.removeDay(5);

			SchoolMonth objOct = new SchoolMonth(objFirstYear, DateTimeConstants.OCTOBER);
				objOct.setHalfDay(7)
				.removeDay(18)
				.setHalfDay(30);

			SchoolMonth objNov = new SchoolMonth(objFirstYear, DateTimeConstants.NOVEMBER);
				objNov.setHalfDay(27)
				.removeDay(28)
				.removeDay(29);

			SchoolMonth objDec = new SchoolMonth(objFirstYear, DateTimeConstants.DECEMBER);
				objDec.setHalfDay(6)
				.removeDay(23)
				.removeDay(24)
				.removeDay(25)
				.removeDay(26)
				.removeDay(27)
				.removeDay(30)
				.removeDay(31);

			objFirstYear.add(objAug);
			objFirstYear.add(objSep);
			objFirstYear.add(objOct);
			objFirstYear.add(objNov);
			objFirstYear.add(objDec);

		SchoolYear objSecondYear = new SchoolYear(2014);
			SchoolMonth objJan = new SchoolMonth(objSecondYear, DateTimeConstants.JANUARY);
				objJan.removeDay(1)
				.removeDay(20)
				.removeDay(21);

			SchoolMonth objFeb = new SchoolMonth(objSecondYear, DateTimeConstants.FEBRUARY);
				objFeb.setHalfDay(7)
				.removeDay(17);

			SchoolMonth objMar = new SchoolMonth(objSecondYear, DateTimeConstants.MARCH);
				objMar.setHalfDay(10);

			SchoolMonth objApr = new SchoolMonth(objSecondYear, DateTimeConstants.APRIL);
				objApr.removeDay(14)
				.removeDay(15)
				.removeDay(16)
				.removeDay(17)
				.removeDay(18)
				.removeDay(21);

			SchoolMonth objMay = new SchoolMonth(objSecondYear, DateTimeConstants.MAY);
				objMay.setHalfDay(12)
				.removeDay(26)
				.setEvent(30, "Last day for seniors!");

			SchoolMonth objJun = new SchoolMonth(objSecondYear, DateTimeConstants.JUNE, 0, 13);

			objSecondYear.add(objJan);
			objSecondYear.add(objFeb);
			objSecondYear.add(objMar);
			objSecondYear.add(objApr);
			objSecondYear.add(objMay);
			objSecondYear.add(objJun);
			objSecondYear.setLastDayForSeniors(DateTimeConstants.MAY, 30);

		Driver.schoolCalendar.add(objFirstYear);
		Driver.schoolCalendar.add(objSecondYear);
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
					int iRemaining = Driver.schoolCalendar.getNumRemainingSchoolDays();
					int iRemainingSeniors = iRemaining - SchoolCalendar.SENIOR_OFFSET;

					if(iRemaining < 0)
						Driver.errorln("Ran Last Day Countdown after the last day for seniors happened", true);

					StringBuilder objBuilder = new StringBuilder("- Summer Countdown -\n\n");

					// All students
					if(iRemaining == 0)
						objBuilder.append("This is it! School's out for summer! https://www.youtube.com/watch?v=qga5eONXU_4");
					else if(iRemaining > 0)
					{
						objBuilder.append("Non-Seniors: ");
						objBuilder.append(iRemaining);
						objBuilder.append(" school day");
						objBuilder.append(iRemaining != 1 ? "s" : "");
						objBuilder.append(" to go!");
					}

					// Seniors
					if(iRemainingSeniors >= 0)
					{
						objBuilder.append("\nSeniors: ");

						if(iRemainingSeniors == 0)
						{
							objBuilder.append("This is it, our last day of high school!");
						}
						else
						{
							objBuilder.append("Only ");
							objBuilder.append(iRemainingSeniors);
							objBuilder.append(" school day");
							objBuilder.append(iRemainingSeniors != 1 ? "s" : "");
							objBuilder.append(" left!");
						}
					}

					try
					{
						TwitterAPI.updateStatus(objBuilder.toString());
						Driver.println("Tweet sent!");
					}
					catch(TwitterException e)
					{
						Driver.errorln("\tError sending status update: " + e.getMessage());
					}
					
					//System.out.println(objLastDay);
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
					*	- Find the date and time for every school
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