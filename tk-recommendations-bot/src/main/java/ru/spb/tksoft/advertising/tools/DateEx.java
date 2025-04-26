package ru.spb.tksoft.advertising.tools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DateEx {

    private DateEx() {
    }

    public enum DateTimeSpecifier {
        UNKNOWN, DATE, TIME
    }

    /**
     * Try nomalize date OR time string representation.
     * 
     * @param str
     *            normalizing string
     * @param pattern
     *            valid datetime pattern
     * @return valid or not
     */
    public static String normalizeDateTime(final DateTimeSpecifier dts,
            final String input, final String delimiter, final String pattern) {
        try {
            List<String> parts = Arrays.asList(input.split("\\" + delimiter));
            final String str = parts.stream()
                    .map(part -> part.length() == 1 ? "0" + part : part)
                    .collect(Collectors.joining(delimiter));

            var formatter = DateTimeFormatter.ofPattern(pattern);

            switch (dts) {
                case DATE -> {
                    var localDate = LocalDate.parse(str, formatter);
                    return localDate.format(formatter);
                }
                case TIME -> {
                    var localTime = LocalTime.parse(str, formatter);
                    return localTime.format(formatter);
                }
                default -> {
                    return "";
                }
            }

        } catch (Exception e) {
            return "";
        }
    }

    public record TimestampUtcInfo(
            String datetimeStringLocal,
            String datetimeStringUtc,
            ZonedDateTime datetimeZoned,
            Instant instant) {
    }

    public static Optional<TimestampUtcInfo> getUtcTimestamp(
            final String datetimeStringLocal,
            final String pattern) {
        try {
            var formatter = DateTimeFormatter.ofPattern(pattern);
            var localDateTime = LocalDateTime.parse(datetimeStringLocal, formatter);

            var zoneId = ZoneId.of("UTC");
            ZonedDateTime datetimeZoned = localDateTime.atZone(zoneId);

            Instant instant = datetimeZoned.toInstant();
            final String datetimeStringUtc = datetimeZoned.format(formatter);

            return Optional.of(new TimestampUtcInfo(
                    datetimeStringLocal,
                    datetimeStringUtc,
                    datetimeZoned,
                    instant));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
