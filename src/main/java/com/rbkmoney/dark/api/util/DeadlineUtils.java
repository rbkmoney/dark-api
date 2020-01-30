package com.rbkmoney.dark.api.util;

import com.rbkmoney.dark.api.exceptions.DeadlineException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineUtils {

    public static void checkDeadline(String xRequestDeadline, String xRequestId) {
        if (xRequestDeadline == null) {
            return;
        }
        if (containsRelativeValues(xRequestDeadline, xRequestId)) {
            return;
        }
        try {
            Instant instant = Instant.parse(xRequestDeadline);

            if (Instant.now().isAfter(instant)) {
                throw new DeadlineException(String.format("Deadline is expired, xRequestId=%s ", xRequestId));
            }
        } catch (Exception ex) {
            throw new DeadlineException(String.format("Deadline has invalid 'Instant' format, xRequestId=%s ", xRequestId));
        }
    }

    public static boolean containsRelativeValues(String xRequestDeadline, String xRequestId) {
        return (extractMinutes(xRequestDeadline, xRequestId) + extractSeconds(xRequestDeadline, xRequestId)
                + extractMilliseconds(xRequestDeadline, xRequestId)) > 0;
    }

    public static Long extractMinutes(String xRequestDeadline, String xRequestId) {
        Double minutes = extractValue(xRequestDeadline, "([0-9]+([.][0-9]+)?(?!ms)[m])", xRequestId, "minutes");

        return Optional.ofNullable(minutes).map(min -> min * 60000.0).map(Double::longValue).orElse(0L);
    }

    public static Long extractSeconds(String xRequestDeadline, String xRequestId) {
        Double seconds = extractValue(xRequestDeadline, "([0-9]+([.][0-9]+)?[s])", xRequestId, "seconds");

        return Optional.ofNullable(seconds).map(s -> s * 1000.0).map(Double::longValue).orElse(0L);
    }

    public static Long extractMilliseconds(String xRequestDeadline, String xRequestId) {
        Double milliseconds = extractValue(xRequestDeadline, "([0-9]+([.][0-9]+)?[m][s])", xRequestId, "milliseconds");

        if (milliseconds != null && Math.round((milliseconds % 1) * 100) > 0) {
            throw new DeadlineException(String.format("Deadline 'milliseconds' parameter can have only integer value, xRequestId=%s ", xRequestId));
        }

        return Optional.ofNullable(milliseconds).map(Double::longValue).orElse(0L);
    }

    private static Double extractValue(String xRequestDeadline, String formatRegex, String xRequestId, String format) {
        String numberRegex = "([0-9]+([.][0-9]+)?)";

        List<String> doubles = new ArrayList<>();
        for (String string : match(formatRegex, xRequestDeadline)) {
            doubles.addAll(match(numberRegex, string));
        }
        if (doubles.size() > 1) {
            throw new DeadlineException(String.format("Deadline '%s' parameter has a few relative value, xRequestId=%s ", format, xRequestId));
        }
        if (doubles.isEmpty()) {
            return null;
        }
        return Double.valueOf(doubles.get(0));
    }

    private static List<String> match(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        List<String> strings = new ArrayList<>();
        while (matcher.find()) {
            strings.add(matcher.group());
        }
        return strings;
    }
}
