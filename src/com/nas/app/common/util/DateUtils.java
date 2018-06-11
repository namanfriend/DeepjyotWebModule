package com.nas.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.nas.app.common.exception.NASApplicationException;

public class DateUtils {

	public static final String YYYY_DD_MM = "YYYY-dd-MM";
	public static final String MMM_YYYY = "MMM-YYYY";
	public static final String FILE_DATE_EXT = "yyyy-MM-dd_HH-mm-ss-SSS";
	

	public static Date getDate(String dateStr, String dateFormat) throws NASApplicationException{
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new NASApplicationException(e);
		}
	}


	public static String getDateString(Date date, String dateFormat) {
		if(date == null){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}
}
