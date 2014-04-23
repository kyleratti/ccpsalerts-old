package com.ccpsalerts.tasks;

import com.ccpsalerts.Driver;
import com.ccpsalerts.twitter.TwitterAPI;
import com.ccpsalerts.twitter.TweetTooLongException;
import com.ccpsalerts.calendar.SchoolCalendar;
import twitter4j.TwitterException;

/**
 * @author Kyle
 * @version 1.0, 4/20/2014
 */
public class LastDayCountdown extends Countdown {
	@Override
	public void run()
	{
		int iRemaining = Driver.getCalendar().getNumRemainingSchoolDays();
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
			objBuilder.append(" to go");
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
		catch(TweetTooLongException e)
		{
			String strTweet = e.getMessage();

			Driver.errorln(String.format("\tTweet is too long (was %d, max 140)%n\t\tTweet: %s", strTweet.length(), strTweet));
		}
		catch(TwitterException e)
		{
			Driver.errorln("\tError sending status update: " + e.getMessage());
		}

		//System.out.println(objLastDay);
		//Driver.println("Theoretically calculating the school days left and tweeting it");
	}
}
