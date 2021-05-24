package com.rbkmoney.dark.api.util;

import com.rbkmoney.dark.api.exceptions.DeadlineException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineUtils {

    public static void checkDeadline(String requestDeadline, String requestId) {
        if (requestDeadline == null) {
            return;
        }
        if (containsRelativeValues(requestDeadline, requestId)) {
            return;
        }
        try {
            Instant instant = Instant.parse(requestDeadline);

            if (Instant.now().isAfter(instant)) {
                throw new DeadlineException(String.format("Deadline is expired, xRequestId=%s ", requestId));
            }
        } catch (Exception ex) {
            throw new DeadlineException(
                    String.format("Deadline has invalid 'Instant' format, xRequestId=%s ", requestId));
        }
    }

    public static boolean containsRelativeValues(String requestDeadline, String requestId) {
        return (extractMinutes(requestDeadline, requestId) + extractSeconds(requestDeadline, requestId) +
                extractMilliseconds(requestDeadline, requestId)) > 0;
    }

    public static Long extractMinutes(String requestDeadline, String requestId) {
        String format = "minutes";

        checkNegativeValues(requestDeadline, requestId, "([-][0-9]+([.][0-9]+)?(?!ms)[m])", format);

        Double minutes = extractValue(requestDeadline, "([0-9]+([.][0-9]+)?(?!ms)[m])", requestId, format);

        return Optional.ofNullable(minutes).map(min -> min * 60000.0).map(Double::longValue).orElse(0L);
    }

    public static Long extractSeconds(String requestDeadline, String requestId) {
        String format = "seconds";

        checkNegativeValues(requestDeadline, requestId, "([-][0-9]+([.][0-9]+)?[s])", format);

        Double seconds = extractValue(requestDeadline, "([0-9]+([.][0-9]+)?[s])", requestId, format);

        return Optional.ofNullable(seconds).map(s -> s * 1000.0).map(Double::longValue).orElse(0L);
    }

    public static Long extractMilliseconds(String requestDeadline, String requestId) {
        String format = "milliseconds";

        checkNegativeValues(requestDeadline, requestId, "([-][0-9]+([.][0-9]+)?[m][s])", format);

        Double milliseconds = extractValue(requestDeadline, "([0-9]+([.][0-9]+)?[m][s])", requestId, format);

        if (milliseconds != null && Math.ceil(milliseconds % 1) > 0) {
            throw new DeadlineException(
                    String.format("Deadline 'milliseconds' parameter can have only integer value, xRequestId=%s ",
                            requestId));
        }

        return Optional.ofNullable(milliseconds).map(Double::longValue).orElse(0L);
    }

    private static void checkNegativeValues(String requestDeadline, String requestId, String regex, String format) {
        if (!match(regex, requestDeadline).isEmpty()) {
            throw new DeadlineException(
                    String.format("Deadline '%s' parameter has negative value, xRequestId=%s ", format, requestId));
        }
    }

    private static Double extractValue(String requestDeadline, String formatRegex, String requestId, String format) {
        String numberRegex = "([0-9]+([.][0-9]+)?)";

        List<String> doubles = new ArrayList<>();
        for (String string : match(formatRegex, requestDeadline)) {
            doubles.addAll(match(numberRegex, string));
        }
        if (doubles.size() > 1) {
            throw new DeadlineException(
                    String.format("Deadline '%s' parameter has a few relative value, xRequestId=%s ", format,
                            requestId));
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
