package com.chat_server.auth.util;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    public static String toIso8601(Date date) {
        return date.toInstant()
                .atOffset(ZoneOffset.of("+09:00")) // 한국 시간 기준
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}