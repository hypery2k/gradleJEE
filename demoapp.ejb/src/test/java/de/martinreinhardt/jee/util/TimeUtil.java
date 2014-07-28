/**
 * File: TimeUtil.java 21.01.2013, 20:11:32, author: mreinhardt
 * 
 * Project: PROFI Java Core Util
 * 
 * PROFI Engineering Systems AG
 * Otto-R??hm-Str. 18
 * 64293 Darmstadt
 * 
 * http://www.profi-ag.de
 */
package de.martinreinhardt.jee.util;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;

/**
 * This class need the jodatime library
 * 
 * @author mreinhardt
 * 
 */
public final class TimeUtil {

	private static final Logger LOG = Logger.getLogger(TimeUtil.class);

	private TimeUtil() {

	}

	static {
		try {
			DatatypeFactory.newInstance();
		} catch (final DatatypeConfigurationException dce) {
			throw new IllegalStateException("Exception while obtaining DatatypeFactory instance", dce);
		}
	}

	private static final int       HOURS_PER_DAY        = 24;

	private static final int       MINUTES_PER_DAY      = 60 * HOURS_PER_DAY;

	private static final int       SECONDS_PER_DAY      = 60 * MINUTES_PER_DAY;

	private static final int       MILLISECONDS_PER_DAY = 1000 * SECONDS_PER_DAY;

	public static Date now() {
		return new Date();
	}

	public static Date yearMonthNow() {
		final GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(now());

		final int lastDay = dateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dateCalendar.set(Calendar.DAY_OF_MONTH, lastDay);
		dateCalendar.set(Calendar.HOUR_OF_DAY, 12);
		dateCalendar.set(Calendar.MINUTE, 0);
		dateCalendar.set(Calendar.SECOND, 0);
		dateCalendar.set(Calendar.MILLISECOND, 0);

		LOG.debug("yearMonthNow() - yearMonthNow: " + dateCalendar.getTime());
		return dateCalendar.getTime();
	}

	public static Date yearMonthDayNow() {
		final GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(now());

		dateCalendar.set(Calendar.HOUR_OF_DAY, 12);
		dateCalendar.set(Calendar.MINUTE, 0);
		dateCalendar.set(Calendar.SECOND, 0);
		dateCalendar.set(Calendar.MILLISECOND, 0);

		LOG.debug("yearMonthDayNow() - yearMonthDayNow: " + dateCalendar.getTime());

		return dateCalendar.getTime();
	}

	/**
	 * Check if a date represented only by the year and month is in the past. <br />
	 * <br />
	 * e.g. <br />
	 * <br />
	 * We are in February 2012.<br />
	 * A date with the year 2012 and the month Jan would be valid.<br />
	 * A date with the year 2012 and the month Feb would be valid.<br />
	 * A date with the year 2012 and the month Mar would be invalid. <br />
	 * <br />
	 * If the date to check is null, this methods yields false.
	 */
	public static boolean yearMonthInPast(final Date dateToCheck) {

		if (dateToCheck == null) {
			return false;
		}
		final Date yearMonthNow = TimeUtil.yearMonthNow();

		LOG.debug("yearMonthInPast() - dateToCheck: " + dateToCheck);
		LOG.debug("yearMonthInPast() - yearMonthNow: " + yearMonthNow);

		return yearMonthBeforeYearMonth(dateToCheck, yearMonthNow);
	}

	/**
	 * Check if a date represented only by the year and month is after a date
	 * represented also just by the year and month. <br />
	 * <br />
	 * e.g. <br />
	 * <br />
	 * The date to check against is 2012, February.<br />
	 * A date 2012, February would be valid.<br />
	 * A date 2012, May would be valid.<br />
	 * A date 2012, January would be invalid.<br />
	 * A date 2011, November would be invalid. <br />
	 * <br />
	 * If the date to check or the date to check against are null, this methods
	 * yields false.
	 */
	public static boolean yearMonthAfterYearMonth(final Date dateToCheck,
	    final Date dateToCheckAgainst) {

		if (dateToCheck == null || dateToCheckAgainst == null) {
			return false;
		}

		LOG.debug("yearMonthAfterYearMonth() - dateToCheck: " + dateToCheck);
		LOG.debug("yearMonthAfterYearMonth() - yearMonthNow: " + dateToCheckAgainst);

		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(dateToCheck);
		final int lastDayCheckCal = checkCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		checkCal.set(Calendar.DAY_OF_MONTH, lastDayCheckCal);
		checkCal.set(Calendar.HOUR_OF_DAY, 12);
		checkCal.set(Calendar.MINUTE, 0);
		checkCal.set(Calendar.SECOND, 0);
		checkCal.set(Calendar.MILLISECOND, 0);

		final Calendar checkAgainstCal = new GregorianCalendar();
		checkAgainstCal.setTime(dateToCheckAgainst);
		final int lastDayCheckAgainstCal = checkAgainstCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		checkAgainstCal.set(Calendar.DAY_OF_MONTH, lastDayCheckAgainstCal);
		checkAgainstCal.set(Calendar.HOUR_OF_DAY, 12);
		checkAgainstCal.set(Calendar.MINUTE, 0);
		checkAgainstCal.set(Calendar.SECOND, 0);
		checkAgainstCal.set(Calendar.MILLISECOND, 0);

		// same years and months are also valid
		if (checkCal.get(Calendar.YEAR) == checkAgainstCal.get(Calendar.YEAR)
		    && checkCal.get(Calendar.MONTH) == checkAgainstCal.get(Calendar.MONTH)) {
			return true;
		}
		return !yearMonthBeforeYearMonth(checkCal.getTime(), checkAgainstCal.getTime());
	}

	public static boolean yearMonthBeforeYearMonth(final Date dateToCheck,
	    final Date dateToCheckAgainst) {

		if (dateToCheck == null || dateToCheckAgainst == null) {
			return false;
		}
		LOG.debug("isYearMonthBeforeYearMonth() - dateToCheck: " + dateToCheck);
		LOG.debug("isYearMonthBeforeYearMonth() - yearMonthNow: " + dateToCheckAgainst);

		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(dateToCheck);
		final int lastDayCheckCal = checkCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		checkCal.set(Calendar.DAY_OF_MONTH, lastDayCheckCal);
		checkCal.set(Calendar.HOUR_OF_DAY, 12);
		checkCal.set(Calendar.MINUTE, 0);
		checkCal.set(Calendar.SECOND, 0);
		checkCal.set(Calendar.MILLISECOND, 0);

		final Calendar checkAgainstCal = new GregorianCalendar();
		checkAgainstCal.setTime(dateToCheckAgainst);
		final int lastDayCheckAgainstCal = checkAgainstCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		checkAgainstCal.set(Calendar.DAY_OF_MONTH, lastDayCheckAgainstCal);
		checkAgainstCal.set(Calendar.HOUR_OF_DAY, 12);
		checkAgainstCal.set(Calendar.MINUTE, 0);
		checkAgainstCal.set(Calendar.SECOND, 0);
		checkAgainstCal.set(Calendar.MILLISECOND, 0);

		if (checkCal.get(Calendar.YEAR) > checkAgainstCal.get(Calendar.YEAR)) {
			return false;

		} else if (checkCal.get(Calendar.YEAR) == checkAgainstCal.get(Calendar.YEAR)
		    && checkCal.get(Calendar.MONTH) > checkAgainstCal.get(Calendar.MONTH)) {
			return false;

		}
		return true;
	}

	/**
	 * Check if a date represented only by the year and month and day is in the
	 * past. <br />
	 * <br />
	 * e.g. <br />
	 * <br />
	 * We are at 2012, February 23th.<br />
	 * A date 2012, Jan 12th would be valid.<br />
	 * A date 2012, Feb 22nd would be valid.<br />
	 * A date 2012, Feb 23rd would be invalid.<br />
	 * A date 2012, Feb 24th would be invalid.
	 * 
	 * @author $Author: simon $
	 * @version $Revision: 10310 $ $Date: 2012-08-22 13:57:00 +0200 (Mi, 22 Aug
	 *          2012) $ <br />
	 * <br />
	 *          If the date to check is null, this methods yields false.
	 */
	public static boolean yearMonthDayInPast(final Date dateToCheck) {

		if (dateToCheck == null) {
			return false;
		}
		final Date yearMonthDayNow = TimeUtil.yearMonthDayNow();

		LOG.debug("yearMonthDayInPast() - dateToCheck: " + dateToCheck);
		LOG.debug("yearMonthDayInPast() - yearMonthDayNow: " + yearMonthDayNow);

		return isYearMonthDayBeforeYearMonthDay(dateToCheck, yearMonthDayNow);
	}

	/**
	 * Check if a date represented only by the year and month is after a date
	 * represented by the year, month and day. Before the check, the date to check
	 * is normalized to the last possible day of the respective month at
	 * 12:00:00.000 pm. <br />
	 * <br />
	 * e.g. <br />
	 * <br />
	 * The date to check against is 2012, February 23th.<br />
	 * A date 2012, February would be valid.<br />
	 * A date 2012, May would be valid.<br />
	 * A date 2012, January would be invalid.<br />
	 * A date 2011, November would be invalid. <br />
	 * <br />
	 * If the date to check or the date to check against are null, this methods
	 * yields false.
	 */
	public static boolean yearMonthAfterYearMonthDay(final Date dateToCheck,
	    final Date dateToCheckAgainst) {

		if (dateToCheck == null || dateToCheckAgainst == null) {
			return false;
		}
		LOG.debug("isYearMonthAfterYearMonthDay() - dateToCheck: " + dateToCheck);
		LOG.debug("isYearMonthAfterYearMonthDay() - dateToCheckAgainst: " + dateToCheckAgainst);

		// normalize to last day in month at 12:00:00.000 pm
		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(dateToCheck);
		final int lastDay = checkCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		checkCal.set(Calendar.DAY_OF_MONTH, lastDay);
		checkCal.set(Calendar.HOUR_OF_DAY, 12);
		checkCal.set(Calendar.MINUTE, 0);
		checkCal.set(Calendar.SECOND, 0);
		checkCal.set(Calendar.MILLISECOND, 0);

		return !isYearMonthDayBeforeYearMonthDay(checkCal.getTime(), dateToCheckAgainst);
	}

	/**
	 * Check if a date represented only by the year and month is after a date
	 * represented by the year, month and day. <br />
	 * <br />
	 * e.g. <br />
	 * <br />
	 * The date to check against is 2012, February 23th.<br />
	 * A date 2012, February 24th would be valid.<br />
	 * A date 2012, May 7th would be valid.<br />
	 * A date 2012, February 23th would be invalid.<br />
	 * A date 2012, January 12th would be invalid.<br />
	 * A date 2011, November 27th would be invalid. <br />
	 * <br />
	 * If the date to check or the date to check against are null, this methods
	 * yields false.
	 */
	public static boolean yearMonthDayAfterYearMonthDay(final Date dateToCheck,
	    final Date dateToCheckAgainst) {

		if (dateToCheck == null || dateToCheckAgainst == null) {
			return false;
		}
		LOG.debug("yearMonthDayAfterYearMonthDay() - dateToCheck: " + dateToCheck);
		LOG.debug("yearMonthDayAfterYearMonthDay() - dateToCheckAgainst: " + dateToCheckAgainst);

		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(dateToCheck);
		checkCal.set(Calendar.HOUR_OF_DAY, 12);
		checkCal.set(Calendar.MINUTE, 0);
		checkCal.set(Calendar.SECOND, 0);
		checkCal.set(Calendar.MILLISECOND, 0);

		final Calendar checkAgainstCal = new GregorianCalendar();
		checkAgainstCal.setTime(dateToCheckAgainst);
		checkAgainstCal.set(Calendar.HOUR_OF_DAY, 12);
		checkAgainstCal.set(Calendar.MINUTE, 0);
		checkAgainstCal.set(Calendar.SECOND, 0);
		checkAgainstCal.set(Calendar.MILLISECOND, 0);

		// exclude exact same day
		if (checkCal.get(Calendar.YEAR) == checkAgainstCal.get(Calendar.YEAR)
		    && checkCal.get(Calendar.MONTH) == checkAgainstCal.get(Calendar.MONTH)
		    && checkCal.get(Calendar.DAY_OF_MONTH) == checkAgainstCal.get(Calendar.DAY_OF_MONTH)) {

			return false;
		}
		return !isYearMonthDayBeforeYearMonthDay(checkCal.getTime(), checkAgainstCal.getTime());
	}

	private static boolean isYearMonthDayBeforeYearMonthDay(final Date dateToCheck,
	    final Date dateToCheckAgainst) {

		if (dateToCheck == null || dateToCheckAgainst == null) {
			return false;
		}
		LOG.debug("isYearMonthBeforeYearMonthDay() - dateToCheck: " + dateToCheck);
		LOG.debug("isYearMonthBeforeYearMonthDay() - dateToCheckAgainst: " + dateToCheckAgainst);

		final Calendar checkCal = new GregorianCalendar();
		checkCal.setTime(dateToCheck);
		checkCal.set(Calendar.HOUR_OF_DAY, 12);
		checkCal.set(Calendar.MINUTE, 0);
		checkCal.set(Calendar.SECOND, 0);
		checkCal.set(Calendar.MILLISECOND, 0);

		final Calendar checkAgainstCal = new GregorianCalendar();
		checkAgainstCal.setTime(dateToCheckAgainst);
		checkAgainstCal.set(Calendar.HOUR_OF_DAY, 12);
		checkAgainstCal.set(Calendar.MINUTE, 0);
		checkAgainstCal.set(Calendar.SECOND, 0);
		checkAgainstCal.set(Calendar.MILLISECOND, 0);

		if (checkCal.get(Calendar.YEAR) > checkAgainstCal.get(Calendar.YEAR)) {

			return false;

		} else if (checkCal.get(Calendar.YEAR) == checkAgainstCal.get(Calendar.YEAR)) {

			if (checkCal.get(Calendar.MONTH) > checkAgainstCal.get(Calendar.MONTH)) {

				return false;

			} else if (checkCal.get(Calendar.MONTH) == checkAgainstCal.get(Calendar.MONTH)
			    && checkCal.get(Calendar.DAY_OF_MONTH) >= checkAgainstCal.get(Calendar.DAY_OF_MONTH)) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Returns a date in the past given by the date to be used and the months in
	 * the past
	 * 
	 * @param dateToDiminish
	 *          as starting point
	 * @param months
	 *          to be substracted
	 * @return the date in the past
	 */
	public static Date yearMonthMinusMonths(final Date dateToDiminish, final int months) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// normalize to last day in month at 12:00:00.000 pm
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// subtract months from date
		calendar.add(Calendar.MONTH, -months);

		return calendar.getTime();
	}

	/**
	 * Returns a date in the future given by the date to be used and the months in
	 * the past
	 * 
	 * @param dateToDiminish
	 *          as starting point
	 * @param months
	 *          to be substracted
	 * @return the date in the future
	 */
	public static Date yearMonthPlusMonths(final Date dateToDiminish, final int months) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// normalize to last day in month at 12:00:00.000 pm
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// subtract months from date
		calendar.add(Calendar.MONTH, +months);

		return calendar.getTime();
	}

	/**
	 * Returns a date in the past given by the date to be used and the years in
	 * the past
	 * 
	 * @param dateToDiminish
	 *          as starting point
	 * @param years
	 *          to be substracted
	 * @return the date in the past
	 */
	public static Date yearMonthMinusYears(final Date dateToDiminish, final int years) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// normalize to last day in month at 12:00:00.000 pm
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// subtract months from date
		calendar.add(Calendar.YEAR, -years);

		return calendar.getTime();
	}

	/**
	 * Returns a date in the future given by the date to be used and the years in
	 * the past
	 * 
	 * @param dateToDiminish
	 *          as starting point
	 * @param years
	 *          to be substracted
	 * @return the date in the future
	 */
	public static Date yearMonthPlusYears(final Date dateToDiminish, final int years) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// normalize to last day in month at 12:00:00.000 pm
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// subtract months from date
		calendar.add(Calendar.YEAR, +years);

		return calendar.getTime();
	}

	public static Date yearMonthDayMinusMonths(final Date dateToDiminish, final int months) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// subtract months from date
		calendar.add(Calendar.MONTH, -months);

		// normalize day to 12:00:00.000 pm
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date yearMonthDayPlusMonths(final Date dateToDiminish, final int months) {

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDiminish);

		// add months to date
		calendar.add(Calendar.MONTH, months);

		// normalize day to 12:00:00.000 pm
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static boolean yearMonthsEqual(final Date firstDate, final Date secondDate) {

		final Calendar firstCal = Calendar.getInstance();
		firstCal.setTime(firstDate);

		final Calendar secondCal = Calendar.getInstance();
		secondCal.setTime(secondDate);

		return firstCal.get(Calendar.MONTH) == secondCal.get(Calendar.MONTH)
		    && firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR);
	}

	public static boolean yearMonthDaysEqual(final Date firstDate, final Date secondDate) {

		final Calendar firstCal = Calendar.getInstance();
		firstCal.setTime(firstDate);

		final Calendar secondCal = Calendar.getInstance();
		secondCal.setTime(secondDate);

		return firstCal.get(Calendar.DAY_OF_MONTH) == secondCal.get(Calendar.DAY_OF_MONTH)
		    && firstCal.get(Calendar.MONTH) == secondCal.get(Calendar.MONTH)
		    && firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR);
	}

	/**
	 * Return normalized year month date (last day in month at 12:00:00.000 pm)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getYearMonth(final int year, final int month) {
		final Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		// normalize to last day in month at 12:00:00.000 pm
		final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Return normalized year month day date (desired day in month at 00:00:00.000
	 * pm)
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getYearMonthDay(final int year, final int month, final int day) {
		final Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);

		// normalize time to 00:00:00.000 pm
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date startOfDay(final Date date) {
		final GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);

		return new GregorianCalendar(dateCalendar.get(YEAR), dateCalendar.get(MONTH),
		    dateCalendar.get(DAY_OF_MONTH)).getTime();
	}

	public static Date endOfDay(final Date date) {
		final GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);

		final GregorianCalendar calendar = new GregorianCalendar(dateCalendar.get(YEAR),
		    dateCalendar.get(MONTH),
		    dateCalendar.get(DAY_OF_MONTH));
		calendar.add(MILLISECOND, MILLISECONDS_PER_DAY - 1);

		return calendar.getTime();
	}

	public static Date startOfNextDay(final Date date, final TimeZone timeZone) {
		final Calendar cal = Calendar.getInstance(timeZone);
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);

		return cal.getTime();
	}

	public static Date getDatePlusMinutes(final Date date, final int minutes) {
		final GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);
		dateCalendar.add(Calendar.MINUTE, minutes);

		return dateCalendar.getTime();
	}

	public static int yearsDifference(final Date a, final Date b) {
		final Calendar calendarA = Calendar.getInstance();
		final Calendar calendarB = Calendar.getInstance();
		int multiplier;
		if (b.getTime() - a.getTime() > 0) {
			multiplier = -1;
			calendarA.setTime(b);
			calendarB.setTime(a);
		} else {
			multiplier = 1;
			calendarA.setTime(a);
			calendarB.setTime(b);
		}

		int years = calendarA.get(Calendar.YEAR) - calendarB.get(Calendar.YEAR);
		final int months = calendarA.get(Calendar.MONTH) - calendarB.get(Calendar.MONTH);
		final int days = calendarA.get(Calendar.DAY_OF_MONTH) - calendarB.get(Calendar.DAY_OF_MONTH);
		if (years > 0 && (months < 0 || months == 0 && days < 0)) {
			years -= 1;
		}
		return years * multiplier;
	}

	public static int monthDifference(final Date a, final Date b) {
		final Calendar calendarA = Calendar.getInstance();
		final Calendar calendarB = Calendar.getInstance();
		int multiplier;
		if (b.getTime() - a.getTime() > 0) {
			multiplier = -1;
			calendarA.setTime(b);
			calendarB.setTime(a);
		} else {
			multiplier = 1;
			calendarA.setTime(a);
			calendarB.setTime(b);
		}

		int result = 0;
		final int years = calendarA.get(Calendar.YEAR) - calendarB.get(Calendar.YEAR);
		final int months = calendarA.get(Calendar.MONTH) - calendarB.get(Calendar.MONTH);
		final int days = calendarA.get(Calendar.DAY_OF_MONTH) - calendarB.get(Calendar.DAY_OF_MONTH);

		result += years * 12;
		result += months;

		if (days < 0) {
			result -= 1;
		}
		return result * multiplier;
	}

	/**
	 * Generate from a date object from a given date-string (yyyy-MM), like
	 * 2011-01
	 * 
	 * @param pDate
	 *          - the date string
	 * @return an date representation of the string
	 * @throws ParseException
	 *           if the date can not be transformed
	 */
	public static Date dateFromYearMonthString(final String pDate) throws ParseException {
		final SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM");
		return sdfToDate.parse(pDate);
	}

	/**
	 * Generate from a date object from a given date-string (yyyy-MM-dd), like
	 * 2011-01-03
	 * 
	 * @param pDate
	 *          - the date string
	 * @return an date representation of the string
	 * @throws ParseException
	 *           if the date can not be transformed
	 */
	public static Date dateFromYearMonthDayString(final String pDate) throws ParseException {
		if (pDate != null) {
			final SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd");
			return sdfToDate.parse(pDate);
		} else {
			return null;
		}
	}

	/**
	 * Generate from a date object from a given date-string (dd.MM.yyyy), like
	 * 03.01.2011
	 * 
	 * @param pDate
	 *          - the date string
	 * @return an date representation of the string
	 * @throws ParseException
	 *           if the date can not be transformed
	 */
	public static Date dateFromYearMonthDayString2(final String pDate) throws ParseException {
		if (pDate != null) {
			final SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
			return sdfToDate.parse(pDate);
		} else {
			return null;
		}
	}

	/**
	 * Get given data as nice formated String (yyyy-MM-dd), like 2011-01-03
	 * 
	 * @param pDate
	 * @return
	 * @throws ParseException
	 */
	public static String dateAsString(final Date pDate) throws ParseException {
		if (pDate != null) {
			final SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd");
			return sdfToDate.format(pDate);
		} else {
			return null;
		}
	}

	/**
	 * Get given data as nice formated String (dd.MM.yyyy), like 03.01.2011
	 * 
	 * @param pDate
	 * @return
	 * @throws ParseException
	 */
	public static String dateAsString2(final Date pDate) throws ParseException {
		if (pDate != null) {
			final SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
			return sdfToDate.format(pDate);
		} else {
			return null;
		}
	}

	/**
	 * Generate from a date object from a given date-string (HH:mm), like 12:13
	 * 
	 * @param pTime
	 *          to convert
	 * @return an date representation of the string
	 * @throws ParseException
	 *           if the date can not be transformed
	 */
	public static Date dateFromHourAndMinutes(final String pTime) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.parse(pTime);
	}

	/**
	 * Sort dates. Note this returns a set so multiples of the same date will be
	 * removed.
	 * 
	 * @param dates
	 *          The input dates
	 * @return The sorted dates
	 */
	public static Set<Date> sortDates(final List<Date> pDates) {
		final Set<Date> sortedList = new TreeSet<Date>(new Comparator<Date>() {

			@Override
			public int compare(final Date date1, final Date date2) {
				if (date1.before(date2)) {
					return -1;
				}
				if (date2.before(date1)) {
					return 1;
				}
				return 0;
			}
		});
		for (final Date date : pDates) {
			if (date != null) {
				sortedList.add(date);
			}
		}
		return sortedList;
	}

}
