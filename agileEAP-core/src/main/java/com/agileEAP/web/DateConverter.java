package com.agileEAP.web;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 根据日期字符串长度判断是长日期还是短日期。只支持yyyy-MM-dd，yyyy-MM-dd HH:mm:ss两种格式。
 */
public class DateConverter implements Converter<String, Date> {
	public final DateFormat DF_LONG = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 短类型日期长度
	 */
	public static final int SHORT_DATE = 10;

	@Override
	public Date convert(String source) {

		source = source.trim();
		if (!StringUtils.hasText(source)) {
			return null;
		}
		try {
			return source.length() <= SHORT_DATE ? DF_SHORT.parse(source)
					: DF_LONG.parse(source);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}