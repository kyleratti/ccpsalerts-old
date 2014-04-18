/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts.calendar;

import org.joda.time.*;
import org.joda.time.chrono.ISOChronology;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/** Represents a month in the school calendar */
public class SchoolMonth
{
	private final SchoolYear year;
	private final YearMonth yearMonth;
	private final HashMap<Integer, SchoolDay> days;

	public SchoolMonth(SchoolYear objYear, int iMonth)
	{
		this(objYear, iMonth, 0, 0);
	}

	public SchoolMonth(SchoolYear objYear, int iMonth, int iFirstDay)
	{
		this(objYear, iMonth, iFirstDay, 0);
	}

	public SchoolMonth(SchoolYear objYear, int iMonth, int iFirstDay, int iLastDay)
	{
		this.year = objYear;
		this.yearMonth = new YearMonth(objYear.getYear(), iMonth);
		this.days = new HashMap<Integer, SchoolDay>(31);

		int iDaysInMonth = ISOChronology.getInstance().dayOfMonth().getMaximumValue(this.yearMonth);

		for(int i = (iFirstDay != 0 ? iFirstDay : 1); i <= (iLastDay > 0 ? iLastDay : iDaysInMonth); i++)
		{
			LocalDate objDate = new LocalDate(objYear.getYear(), iMonth, i);

			if(objDate.getDayOfWeek() >= DateTimeConstants.MONDAY && objDate.getDayOfWeek() <= DateTimeConstants.FRIDAY)
				this.add(new SchoolDay(this, i));
		}
	}

	public SchoolYear getYear()
	{
		return this.year;
	}

	public int getMonth()
	{
		return this.yearMonth.getMonthOfYear();
	}

	public HashMap<Integer, SchoolDay> getDays()
	{
		return this.days;
	}

	public SchoolDay getDay(int iDay)
	{
		if(this.days.containsKey(iDay))
			return this.days.get(iDay);

		return null;
	}

	public int getNumSchoolDays()
	{
		return this.days.size();
	}

	public int getNumRemainingSchoolDays()
	{
		int iRemaining = 0;
		
		LocalDate objNow = new LocalDate();
		Iterator objIt = this.days.entrySet().iterator();

		while(objIt.hasNext())
		{
			Map.Entry objPairs = (Map.Entry) objIt.next();
			SchoolDay objDay = (SchoolDay) objPairs.getValue();

			if(this.getMonth() > objNow.getMonthOfYear() || (this.getMonth() == objNow.getMonthOfYear() && objDay.getDay() > objNow.getDayOfMonth()))
				iRemaining++;
		}

		return iRemaining;
	}

	public void add(SchoolDay objDay)
	{
		this.days.put(objDay.getDay(), objDay);
	}

	public SchoolMonth setFullDay(int iDay)
	{
		if(this.days.containsKey(iDay))
			this.days.get(iDay).setFullDay(true);

		return this;
	}

	public SchoolMonth setHalfDay(int iDay)
	{
		if(this.days.containsKey(iDay))
			this.days.get(iDay).setHalfDay(true);

		return this;
	}

	public SchoolMonth removeDay(int iDay)
	{
		if(this.days.containsKey(iDay))
			this.days.remove(iDay);

		return this;
	}

	public SchoolMonth setEvent(int iDay, String strEvent)
	{
		if(this.days.containsKey(iDay))
			this.days.get(iDay).setEvent(strEvent);

		return this;
	}
}