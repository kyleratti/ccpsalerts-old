/**
* @author Kyle Ratti
* @version 1.0, 04/16/14
*/

package com.ccpsalerts.calendar;

import org.joda.time.*;
import org.joda.time.DateTimeConstants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/** Represents a school year (ie. the months in school during 2013) */
public class SchoolYear
{
	private final YearMonth yearMonth;
	private final HashMap<Integer, SchoolMonth> months;
	private int lastMonth;
	private int firstMonth;

	public SchoolYear(int iYear)
	{
		this.yearMonth = new YearMonth(iYear, DateTimeConstants.JANUARY);
		this.months = new HashMap<Integer, SchoolMonth>(6);
		this.lastMonth = 1;
		this.firstMonth = 12;
	}

	public YearMonth getYearMonth()
	{
		return this.yearMonth;
	}

	public int getYear()
	{
		return this.yearMonth.getYear();
	}

	public void add(SchoolMonth objMonth)
	{
		this.months.put(objMonth.getMonth(), objMonth);

		if(objMonth.getMonth() > this.lastMonth)
			this.lastMonth = objMonth.getMonth();

		if(objMonth.getMonth() < this.firstMonth)
			this.firstMonth = objMonth.getMonth();
	}

	public HashMap<Integer, SchoolMonth> getMonths()
	{
		return this.months;
	}

	public SchoolMonth getMonth(int iMonth)
	{
		if(this.months.containsKey(iMonth))
			return this.months.get(iMonth);

		return null;
	}

	public int getLastMonth()
	{
		return this.lastMonth;
	}

	public int getFirstMonth()
	{
		return this.firstMonth;
	}

	public int getNumSchoolDays()
	{
		int iDays = 0;

		HashMap<Integer, SchoolMonth> objMonths = this.getMonths();

		Iterator objIt = objMonths.entrySet().iterator();

		while(objIt.hasNext())
		{
			Map.Entry objPairs = (Map.Entry) objIt.next();
			SchoolMonth objMonth = (SchoolMonth) objPairs.getValue();

			iDays += objMonth.getNumSchoolDays();
		}

		return iDays;
	}

	public void setLastDayForSeniors(int iMonth, int iDay)
	{
		SchoolMonth objMonth = this.getMonth(iMonth);

		if(objMonth == null) return;
	}
}