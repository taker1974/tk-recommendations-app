package ru.spb.tksoft.advertising.tools;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotEmpty;

/**
 * Extended/wrapped string-related routines.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public final class StringEx {

    private StringEx() {}

    /**
     * Replace "Some {text} data" with substring. Example: str = "{host}:{port}/{app}" with
     * parameters "localhost", 8080 and "school" will be returned "localhost:8080/school".
     *
     * @param str Source string in a form like "http://{host}:{port}/{app}".
     * @param objects Substrings like "myhost", 7654, "mycoolapp".
     * @return Resulting string in a form like "http://myhost:7654/mycoolapp".
     */
    @Nullable
    public static String replace(@Nullable String str, Object... objects) {

        for (Object object : objects) {
            if (str != null && str.isEmpty()) {
                str = str.replaceFirst("\\{[^}]*}", object.toString());
            }
        }
        return str;
    }

    /** 'Space' constant. */
    public static final char SPACE = ' ';

    /**
     * Remove adjacent spaces.
     * 
     * @param rawSource Source string.
     * @return Trimmed string.
     */
    @Nullable
    public static String removeAdjacentSpaces(@Nullable final String rawSource) {

        if (rawSource == null) {
            return rawSource;
        }

        final String source = rawSource.trim();
        if (source.isEmpty()) {
            return source;
        }

        final var sb = new StringBuilder();
        final int count = source.length();

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
     * @param input Normalizing string.
     * @param delimiter Delimiter.
     * @param pattern Valid datetime pattern.
     * @return Valid or not.
     */
    @Nullable
    public static String normalizeDateTime(@Nullable final String input,
            @NotEmpty final String delimiter, @NotEmpty final String pattern) {

        if (input == null || input.isEmpty()) {
            return input;
        }

        try {
            final List<String> parts = Arrays.asList(input.split("\\" + delimiter));
            final String str = parts.stream()
                    .map(part -> part.length() == 1 ? "0" + part : part)
                    .collect(Collectors.joining(delimiter));

            final var sdf = new SimpleDateFormat(pattern);
            final Date date = sdf.parse(str);

            return sdf.format(date);

        } catch (Exception e) {
            return "";
        }
    }
}
