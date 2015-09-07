package com.qingyang.mainlibrary.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TimeUtil {

    /**
     *
     * @return eg: GMT+01:00; GMT-01:00; GMT+11:00; GMT-11:00
     */
    public static String getTimeZoneText() {
        Date now = new Date();
        // Use SimpleDateFormat to format the GMT+00:00 string.
        SimpleDateFormat gmtFormatter = new SimpleDateFormat("ZZZZ");
        gmtFormatter.setTimeZone(TimeZone.getDefault());
        String gmtString = gmtFormatter.format(now);
        return gmtString;
    }

    /**
     * 得到int型的时区值，有正有负。以字符串返回。
     * @return
     */
    public static String getTimeZoneInt() {
        String timeZone = getTimeZoneText();
        String result = timeZone;
        if (timeZone.startsWith("GMT")) {
            String[] tmp = timeZone.replace("GMT", "").split(":");
            if (tmp != null && tmp.length > 1) {
                timeZone = tmp[0];
                if (timeZone.startsWith("+0")) {
                    result = timeZone.replace("+0", "");
                } else if (timeZone.startsWith("+")) {
                    result = timeZone.replace("+", "");
                } else if (timeZone.startsWith("-0")) {
                    result = "-" + timeZone.replace("-0", "");
                } else if (timeZone.startsWith("-")) {
                    result = timeZone;
                }
            }
        }
        return result;
    }

}
