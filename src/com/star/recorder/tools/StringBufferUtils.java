package com.star.recorder.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StringBufferUtils {

	/**
	 * get current time string in specified date format.
	 * 
	 * @param dateFormat the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS
	 */
	public String formatedTime(String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(new Date());
	}

	/**
	 * get specified time string in specified date format.
	 * 
	 * @param addDays days after or before current date, use + and - to add.
	 * @param dateFormat the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public String addDaysByFormatter(int addDays, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, addDays);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of next month in specified date format.
	 * 
	 * @param dateFormat the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public String firstDayOfNextMonth(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of specified month and specified year in specified date format.
	 * 
	 * @param year the year of the date.
	 * @param month the month of the date.
	 * @param dateFormat the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public String firstDayOfMonth(int year, int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of specified month of current year in specified date format.
	 * 
	 * @param month the month of the date.
	 * @param dateFormat the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public String firstDayOfMonth(int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get the system current milliseconds.
	 */
	public String getMilSecNow() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * count the times for a string appears in anothor string.
	 * 
	 * @param myString the string to search.
	 * @param myChar the char to count in the string.
	 */
	public int countStrRepeat(String myString, String myChar) {
		int count = 0, start = 0;
		while ((start = myString.indexOf(myChar, start)) >= 0) {
			start += myChar.length();
			count++;
		}
		return count;
	}

	/**
	 * create folders before file write, if father folder not exists.
	 * 
	 * @param fileName the file to be analyzed.
	 */
	public void createFoldersNeeded(String fileName) {
		String[] folders = fileName.split("\\\\");
		String folderName = folders[0] + "\\";
		for (int i = 1; i < folders.length - 1; i++) {
			folderName += folders[i] + "\\";
		}
		if (!new File(folderName).exists()) {
			new File(folderName).mkdirs();
		}
	}

	/**
	 * generate specified length string with numbers.
	 * 
	 * @param lengthOfNumber the length of the number string to be created.
	 */
	public String getRndNumByLen(int lengthOfNumber) {
		int i, count = 0;

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();

		while (count < lengthOfNumber) {
			i = Math.abs(rnd.nextInt(9));
			if (i == 0 && count == 0) {
			} else {
				randomStr.append(String.valueOf(i));
				count++;
			}
		}
		return randomStr.toString();
	}

	/**
	 * generate specified length string with chars.
	 * 
	 * @param lengthOfString the length of the string to be created.
	 */
	public String getRndStrByLen(int lengthOfString) {
		int i, count = 0;
		final String chars = "1,2,3,4,5,6,7,8,9,0,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] charArr = chars.split(",");

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();

		while (count < lengthOfString) {
			i = Math.abs(rnd.nextInt(35) % charArr.length);
			randomStr.append(charArr[i]);
			count++;
		}
		return randomStr.toString();
	}


	/**
	 * replace element from list.
	 * 
	 * @param list the original list.
	 * @param index the position to replace element.
	 * @param newElement the new element for the list.
	 */
	public List<String> listElementReplace(List<String> list, int index, String newElement) {
		list.remove(index);
		list.add(index, newElement);
		return list;
	}

	/**
	 * merge to list and drop the same element.
	 * 
	 * @param list1 the first list.
	 * @param list2 the second list.
	 * 
	 * @return the merged and distincted list.
	 */
	public List<String> listDistinctMerge(List<String> list1, List<String> list2) {
		Iterator<String> it = list2.iterator();
		while (it.hasNext()) {
			list1.add(it.next());
		}

		List<String> newList = new ArrayList<String>();
		Set<String> distinct = new HashSet<String>();

		for (Iterator<String> iter = list1.iterator(); iter.hasNext();) {
			String element = iter.next();
			if (distinct.add(element)) {
				newList.add(element);
			} else {
				System.err.println("element: 【" + element + "】 has more than one of the same object!");
				try {
					Thread.currentThread().join(100);
				} catch (InterruptedException ie) {
				}
			}
		}
		return newList;
	}

	/**
	 * merge to list and return element which not equals in two lists.
	 * 
	 * @param list1 the first list.
	 * @param list2 the second list.
	 * 
	 * @return the un-same emlent list.
	 */
	public List<String> listMinus(List<String> list1, List<String> list2) {
		List<String> bigger;
		List<String> smaller;
		if (list1.size() >= list2.size()) {
			bigger = list1;
			smaller = list2;
		} else {
			bigger = list2;
			smaller = list1;
		}

		Iterator<String> it = smaller.iterator();
		while (it.hasNext()) {
			bigger.remove(it.next());
		}
		return bigger;
	}
}