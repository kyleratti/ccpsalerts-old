/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.joda.time.*;

/** A caldendar of school months */
public class SchoolCalendar
{
	public static final int SENIOR_OFFSET = 10;

	private final ArrayList<SchoolYear> calendar = new ArrayList<SchoolYear>(2);

	public void add(SchoolYear objYear)
	{
		this.calendar.add(objYear);
	}

	public int getNumSchoolDays()
	{
		int iDays = 0;

		for(SchoolYear objYear : this.calendar)
		{
			iDays += objYear.getNumSchoolDays();
		}

		return iDays;
	}

	public int getNumRemainingSchoolDays()
	{
		LocalDate objNow = new LocalDate();

		int iRemaining = 0;
		int iCurYear = objNow.getYear();
		int iCurMonth = objNow.getMonthOfYear();

		for(SchoolYear objYear : this.calendar)
		{
			if(iCurYear == objYear.getYear())
			{
				for(int i = iCurMonth; i <= objYear.getLastMonth(); i++)
					iRemaining += objYear.getMonth(i).getNumRemainingSchoolDays();

				break;
			}
		}

		return iRemaining;
	}
}