/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import com.ccpsalerts.tasks.*;
import com.ccpsalerts.calendar.*;

import org.joda.time.*;
//import org.joda.time.DateTimeConstants.*;

import org.apache.commons.cli.*;

import java.util.Date;

import java.text.SimpleDateFormat;

/*
* Last Day		Done
* Prom
*	Winters Mill	May 03
*	Westminster	    May 30
* Graduation
*	South Carroll	June 04		7:00pm
*	Liberty		    June 05		2:00pm
*	Winters Mill	June 05		7:30pm
*	Man. Val.	    June 06		2:00pm
*	FSK		        June 06		7:30pm
*	Century		    June 07		1:30pm
*	North Carroll	June 07		7:30pm
*	Westminster     June 08		2:00pm
* Senior Dinner
*	Westminster     June 05
*/

public class Driver
{
	/** Are we debugging? */
	public static final boolean DEBUGGING = false;

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
		catch(UnrecognizedOptionException e)
		{
			Driver.errorln(String.format("Unknown option '%s'", e.getOption()));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}

		if(objCL == null)
			Driver.errorln("Unable to parse command line. Please check your configuration and try again.", true);

		Driver.setup();

		assert objCL != null;
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
		Driver.taskManager.add(new Task("Announcement Parser")
			{
				@Override
				public void run()
				{
					AnnouncementParser objParser = new AnnouncementParser(30);
					objParser.start();
				}
			});

		Driver.taskManager.add(new Task("Last Day Countdown")
			{
				@Override
				public void run()
				{
					Countdown objLastDay = new LastDayCountdown();
					objLastDay.start();
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

	public static SchoolCalendar getCalendar()
	{
		return Driver.schoolCalendar;
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
