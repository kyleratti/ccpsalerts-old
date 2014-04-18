/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts.calendar;

import org.joda.time.*;

/** Represents a day in a SchoolMonth */
public class SchoolDay
{
	public static final boolean FULL_DAY = true;
	public static final boolean HALF_DAY = false;

	private final SchoolMonth month;
	private final MonthDay monthDay;
	private boolean type;
	private String event;

	public SchoolDay(SchoolMonth objMonth, int iDay)
	{
		this.month = objMonth;
		this.monthDay = new MonthDay(objMonth.getMonth(), iDay);
		this.type = SchoolDay.FULL_DAY;
	}

	public SchoolMonth getMonth()
	{
		return this.month;
	}

	public int getDay()
	{
		return this.monthDay.getDayOfMonth();
	}

	public boolean hasEvent()
	{
		return this.event != null;
	}

	public void setEvent(String strEvent)
	{
		this.event = strEvent;
	}

	public String getEvent()
	{
		return this.event;
	}

	public void setFullDay(boolean bFull)
	{
		this.type = bFull;
	}

	public void setHalfDay(boolean bHalf)
	{
		this.type = bHalf;
	}
	
	public boolean isFullDay()
	{
		return this.type == SchoolDay.FULL_DAY;
	}

	public boolean isHalfDay()
	{
		return this.type == SchoolDay.HALF_DAY;
	}
}