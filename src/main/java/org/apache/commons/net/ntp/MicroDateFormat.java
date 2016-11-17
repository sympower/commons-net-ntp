package org.apache.commons.net.ntp;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// inspired by http://stackoverflow.com/questions/7364443/how-to-format-a-date-in-java-me
public class MicroDateFormat {

  private static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

  /**
   * 2016-07-26T11:04:57.999
   */
  public static final int CODE_ISO8601_FULL = 1;
  public static final MicroDateFormat ISO8601_FULL = new MicroDateFormat(CODE_ISO8601_FULL);

  /**
   * 2016-07-26
   */
  public static final int CODE_ISO8601_DATE = 2;
  public static final MicroDateFormat ISO8601_DATE = new MicroDateFormat(CODE_ISO8601_DATE);

  /**
   * 2016-07-26T11:04:57
   */
  public static final int CODE_ISO8601_TIMESTAMP = 3;
  public static final MicroDateFormat ISO8601_TIMESTAMP = new MicroDateFormat(CODE_ISO8601_TIMESTAMP);

  /**
   * Fri, Sep 12 2003 21:06:23.860
   */
  public static final int CODE_NTP = 4;
  public static final MicroDateFormat NTP = new MicroDateFormat(CODE_NTP, TimeZone.getDefault());
  public static final MicroDateFormat NTP_UTC = new MicroDateFormat(CODE_NTP, TZ_UTC);

  private final int format;
  private TimeZone timeZone;

  public MicroDateFormat(int format, TimeZone timeZone) {
    this.format = format;
    this.timeZone = timeZone;
  }

  public MicroDateFormat(int format) {
    this(format, TZ_UTC);
  }

  public String format(Date date) {
    Calendar cal = Calendar.getInstance();
    if (timeZone != null) {
      cal.setTimeZone(timeZone);
    }
    cal.setTime(date);
    StringBuffer sb = new StringBuffer(30);
    switch (format) {
      case CODE_ISO8601_DATE:
        appendDate(cal, sb);
        break;
      case CODE_ISO8601_FULL:
        appendDate(cal, sb);
        sb.append('T');
        appendTime(cal, sb);
        sb.append('.');
        leftPadWithZeros(sb, cal.get(Calendar.MILLISECOND), 3);
        appendTimeZone(cal, sb);
        break;
      case CODE_ISO8601_TIMESTAMP:
        appendDate(cal, sb);
        sb.append('T');
        appendTime(cal, sb);
        appendTimeZone(cal, sb);
        break;
      case CODE_NTP:
        appendDay(cal, sb);
        sb.append(", ");
        appendMonth(cal, sb);
        sb.append(' ');
        leftPadWithZeros(sb, cal.get(Calendar.DAY_OF_MONTH), 2);
        sb.append(' ');
        leftPadWithZeros(sb, cal.get(Calendar.YEAR), 4);
        sb.append(' ');
        appendTime(cal, sb);
        sb.append('.');
        leftPadWithZeros(sb, cal.get(Calendar.MILLISECOND), 3);
        if (cal.getTimeZone() != null) {
          sb.append(' ');
          appendTimeZone(cal, sb);
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown format: " + format);
    }
    return sb.toString();
  }

  private void appendTime(Calendar cal, StringBuffer sb) {
    leftPadWithZeros(sb, cal.get(Calendar.HOUR_OF_DAY), 2);
    sb.append(':');
    leftPadWithZeros(sb, cal.get(Calendar.MINUTE), 2);
    sb.append(':');
    leftPadWithZeros(sb, cal.get(Calendar.SECOND), 2);
  }

  private void appendTimeZone(Calendar cal, StringBuffer sb) {
    if (cal.getTimeZone() != null) {
      sb.append(cal.getTimeZone().getID());
    }
  }

  private final static String[] DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
  private void appendDay(Calendar cal, StringBuffer sb) {
    sb.append(MONTHS[cal.get(Calendar.DAY_OF_WEEK)-1]);
  }

  private final static String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
  private void appendMonth(Calendar cal, StringBuffer sb) {
    sb.append(MONTHS[cal.get(Calendar.MONTH)]);
  }

  private void appendDate(Calendar cal, StringBuffer sb) {
    leftPadWithZeros(sb, cal.get(Calendar.YEAR), 4);
    sb.append('-');
    leftPadWithZeros(sb, cal.get(Calendar.MONTH) + 1, 2);
    sb.append('-');
    leftPadWithZeros(sb, cal.get(Calendar.DAY_OF_MONTH), 2);
  }

  public static void leftPadWithZeros(StringBuffer sb, int str, int minSize) {
    leftPad(sb, String.valueOf(str), minSize, '0');
  }

  private static void leftPad(StringBuffer sb, String str, int minSize, char padChar) {
    int numOfCharsToPad = minSize - str.length();
    for (int i = 0; i < numOfCharsToPad; i++) {
      sb.append(padChar);
    }
    sb.append(str);
  }

}
