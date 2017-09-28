package com.star.recorder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class StringBufferUtils {
	public final String split_smark = ";";

	/**
	 * get current time string in specified date format.
	 * 
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static String formatedTime(String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(new Date());
	}

	/**
	 * @param dataMap
	 * @return
	 */
	public static Map<Object, Object> replaceNullMapValue(Map<Object, Object> dataMap) {
		if (MapUtils.isEmpty(dataMap)) {
			return dataMap;
		}
		Iterator<Entry<Object, Object>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> element = it.next();
			dataMap.put(element.getKey(), ObjectUtils.defaultIfNull(element.getValue(), ""));
		}
		return dataMap;
	}

	/**
	 * @param dataMap
	 * @return
	 */
	public static LinkedHashMap<String, String> dropNullElement(LinkedHashMap<String, String> dataMap) {
		if (MapUtils.isEmpty(dataMap)) {
			return dataMap;
		}
		
		LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> element = it.next();
			tempMap.put(element.getKey(), StringUtils.defaultIfEmpty(element.getValue(), ""));
		}
		return tempMap;
	}

	/**
	 * @param currentDate
	 * @param addDays
	 * @param dateFormat
	 * @return
	 */
	public static String addDaysByFormatter(String currentDate, int addDays, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formatter.parse(currentDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, addDays);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param addDays
	 * @param dateFormat
	 * @return
	 */
	public static String addDaysByFormatter(int addDays, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, addDays);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param dateFormat
	 * @return
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
	 * @param dateFormat
	 * @return
	 */
	public static String firstDayOfLastMonth(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param year
	 * @param month
	 * @param dateFormat
	 * @return
	 */
	public String firstDayOfMonth(int year, int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param month
	 * @param dateFormat
	 * @return
	 */
	public static String firstDayOfMonth(int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param month
	 * @param dateFormat
	 * @return
	 */
	public static String lastDayOfMonth(int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, month + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param dateFormat
	 * @return
	 */
	public static String firstDayOfCurrentMonth(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * @param dateFormat
	 * @return
	 */
	public static String lastDayOfCurrentMonth(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
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
	 * @param myString
	 * @param myChar
	 * @return
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
	 * @param fileName
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
	 * @param lengthOfNumber
	 * @return
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
	 * @param lengthOfString
	 * @return
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
	 * @param list
	 * @param index
	 * @param newElement
	 * @return
	 */
	public List<String> listElementReplace(List<String> list, int index, String newElement) {
		list.remove(index);
		list.add(index, newElement);
		return list;
	}

	/**
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> listDistinctMerge(List<String> list1, List<String> list2) {
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
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Map<String, String>> listMerge(List<Map<String, String>> list1, List<Map<String, String>> list2) {
		Iterator<Map<String, String>> it = list2.iterator();
		while (it.hasNext()) {
			list1.add(it.next());
		}

		List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
		Set<Map<String, String>> distinct = new HashSet<Map<String, String>>();

		for (Iterator<Map<String, String>> iter = list1.iterator(); iter.hasNext();) {
			Map<String, String> element = iter.next();
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
	 * @param list1
	 * @param list2
	 * @return
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

	/**
	 * @param url
	 * @return
	 */
	public String parseAddressFromUrl(String url) {
		if (!url.contains("://")) {
			return url;
		}
		url = url.substring(url.indexOf("://") + 3);

		if (url.contains("/")) {
			return url.split("/")[0];
		}
		return url;
	}

	/**
	 * @param ipAddress
	 * @return
	 */
	public boolean instanceOfIP(String ipAddress) {
		String ip = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	/**
	 * @param url
	 * @return
	 */
	public String parseIPFromUrl(String url) {
		String addr = parseAddressFromUrl(url);
		String[] addrArray = addr.split("\\.");
		String vip = "";
		if (addrArray.length < 4) {
			return addr;
		}
		for (int i = 0; i < addrArray.length; i++) {
			if (i == addrArray.length - 1) {
				String last = addrArray[i];
				last = (last.contains(":")) ? last.split(":")[0] : last;
				vip += last;
			} else {
				vip += addrArray[i] + ".";
			}
		}
		return vip;
	}

	/**
	 * @param tns
	 * @return sid of a tns string
	 */
	public String parseSid(String tns) {
		final String smark1 = "sid=";
		final String smark2 = "service_name=";
		String sid = null;
		if (null == tns) {
			throw new IllegalArgumentException("tns could not be null!");
		}
		try {
			tns = tns.replace(" ", "").trim().toLowerCase();
			if (tns.contains(smark1)) {
				sid = tns.split(smark1)[1];
			} else if (tns.contains(smark2)) {
				sid = tns.split(smark2)[1];
			} else {
				return "";
			}
			sid = sid.substring(0, sid.indexOf(")"));
		} catch (Exception e) {
			return "";
		}
		return sid.toLowerCase();
	}

	/**
	 * @param tns
	 * @return joined sids of tns list
	 */
	public String parseSidFromTns(String tns) {
		if (null == tns) {
			throw new IllegalArgumentException("tns could not be null!");
		}
		String newTns = trimLastReturn(tns);
		if (!newTns.contains(split_smark)) {
			return parseSid(newTns);
		}

		String[] tnsArray = newTns.split(split_smark);
		String sids = "";
		for (int i = 0; i < tnsArray.length; i++) {
			sids += (i == tnsArray.length - 1) ? parseSid(tnsArray[i]) : parseSid(tnsArray[i]) + " & ";
		}
		return sids;
	}

	/**
	 * @param param
	 * @return
	 */
	public String trimLastReturn(String param) {
		if (null == param || "" == param) {
			return "";
		}

		String temp = "";
		if (param.contains("\r\n")) {
			temp = param.replaceAll("\r\n+", split_smark);// 去除重复的换行，替换为分号
		} else {
			temp = param.replaceAll("\n+", split_smark);// 去除重复的换行，替换为分号
		}
		temp = temp.replaceAll(";+", split_smark);// 去除重复的分号

		String[] tempArray = temp.split(split_smark);
		String res = "";
		for (int i = 0; i < tempArray.length; i++) {
			res += (i == tempArray.length - 1) ? tempArray[i] : tempArray[i] + ";";
		}
		return res;
	}

	/*
	 * 将String转化为Json对象
	 */
	public static JSONObject strToJsonObject(String jStr) {

		JSONObject jsonobj = JSONObject.fromObject(jStr);// 将字符串转化成json对象

		return jsonobj;
	}

	/*
	 * 读取文件内容转化为String
	 */
	public static String readStringFromFile(String fileUrl, String codrType) throws Exception {
		StringBuffer sb = new StringBuffer();
		File txt = new File(fileUrl);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txt), codrType));

		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * @param urlString
	 * @param charSet
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromUrl(String urlString, String charSet) throws Exception {

		StringBuffer sb = new StringBuffer();
		URLConnection urlConn = null;

		URL url = new URL(urlString);
		InputStream input = null;
		try {
			urlConn = url.openConnection();
			input = urlConn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charSet));

		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		return sb.toString();
	}

	/*
	 * 数据写入文件
	 */
	public static void writeRequestDataToTempDirectory(byte[] requestData, String filePath) throws Exception {
		File result = new File(filePath);
		OutputStream out = new FileOutputStream(result);
		try {
			out.write(requestData);
		} finally {
			out.close();
		}
	}

	/**
	 * @param paramValue
	 * @return String
	 */
	public static String unescapeAll(String paramValue) {
		String unescaped = paramValue;
		while (unescaped.contains("%")) {
			unescaped = unescape(unescaped);
		}
		return unescaped;
	}

	/**
	 * @param s
	 * @return String
	 */
	public static String unescape(String paramValue) {
		StringBuffer sbuf = new StringBuffer();
		int l = paramValue.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			switch (ch = paramValue.charAt(i)) {
			case '%':
				ch = paramValue.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = paramValue.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
				sbuf.append((char) b); // Store in sbuf
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes
			} else /* if ((b & 0xfe) == 0xfc) */ { // 1111110x (yields 1 bit)
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes
			}
		}
		return sbuf.toString();
	}

	/**
	 * @param s
	 * @return String
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String unicodeToChar(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	
	public static void jdbcTimeStampFormat(List<Map<String, String>> query) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		for (int i = 0; i < query.size(); i++) {
			Map<String, String> tempMap = query.get(i);
			Iterator<Entry<String, String>> it = tempMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String keyName = entry.getKey();
				String value = entry.getValue();
				value = (null == value) ? "" : value;
				try {
					formatter.parse(value);
					value = value.split("\\.0")[0];
					tempMap.put(keyName, value);
					query.remove(i);
					query.add(i, tempMap);
				} catch (ParseException e) {
					continue;
				}
			}
		}
	}
	
	public static void timeStampFormat(List<LinkedHashMap<String, String>> query) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		for (int i = 0; i < query.size(); i++) {
			LinkedHashMap<String, String> tempMap = query.get(i);
			Iterator<Entry<String, String>> it = tempMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String keyName = entry.getKey();
				String value = entry.getValue();
				value = (null == value) ? "" : value;
				try {
					formatter.parse(value);
					value = value.split("\\.0")[0];
					tempMap.put(keyName, value);
					query.remove(i);
					query.add(i, tempMap);
				} catch (ParseException e) {
					continue;
				}
			}
		}
	}
}