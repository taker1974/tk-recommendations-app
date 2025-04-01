package ru.spb.tksoft.advertising.tools;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Extended/wrapped string-related routines.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 * @version 0.1
 */
public final class StringEx {

    private StringEx() {}

    /**
     * @return null || isEmpty || isBlank
     */
    public static boolean isNullOrWhitespace(String str) {
        return str == null || str.isBlank();
    }

    /**
     * @return null || isEmpty || isBlank
     */
    public static Optional<String> getMeaningful(String str, int minLength,
            int maxLength) {
        if (!isNullOrWhitespace(str) && str.length() >= minLength
                && str.length() <= maxLength) {
            return Optional.of(str);
        }
        return Optional.empty();
    }

    /**
     * Replace "Some {text} data" with substring. Example: str = "{host}:{port}/{app}" with
     * parameters "localhost", 8080 and "school" will be returned "localhost:8080/school".
     *
     * @param str source string in a form like "http://{host}:{port}/{app}"
     * @param objects substrings like "myhost", 7654, "mycoolapp"
     * @return resulting string in a form like "http://myhost:7654/mycoolapp"
     */
    public static String replace(String str, Object... objects) {
        for (Object object : objects) {
            str = str.replaceFirst("\\{[^}]*}", object.toString());
        }
        return str;
    }

    public static final char SPACE = ' ';

    /**
     * Remove adjacent spaces.
     * 
     * @param rawSource source string
     * @return trimmed string
     */
    public static String removeAdjacentSpaces(final String rawSource) {

        if (rawSource == null) {
            return rawSource;
        }

        final String source = rawSource.trim();
        if (source.isEmpty()) {
            return source;
        }

        var sb = new StringBuilder();
        int count = source.length();

        char lastAppended = 0;
        for (int i = 0; i < count; i++) {
            char currentChar = source.charAt(i);

            if (currentChar != SPACE || lastAppended != SPACE) {
                sb.append(currentChar);
            }
            lastAppended = currentChar;
        }

        return sb.toString();
    }

    /**
     * Try nomalize date OR time string representation.
     * 
     * @param str normalizing string
     * @param pattern valid datetime pattern
     * @return valid or not
     */
    public static String normalizeDateTime(final String input, final String delimiter,
            final String pattern) {
        try {
            List<String> parts = Arrays.asList(input.split("\\" + delimiter));
            final String str = parts.stream()
                    .map(part -> part.length() == 1 ? "0" + part : part)
                    .collect(Collectors.joining(delimiter));

            var sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(str);

            return sdf.format(date);

        } catch (Exception e) {
            return "";
        }
    }
}
