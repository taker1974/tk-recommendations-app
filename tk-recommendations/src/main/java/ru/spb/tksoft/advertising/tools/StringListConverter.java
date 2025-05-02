package ru.spb.tksoft.advertising.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.NotNull;

/**
 * {@code List<String>} converter.
 * 
 * @see <a href="https://www.baeldung.com/java-jpa-persist-string-list">Baeldung</a> for the example.
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    /** Default delimiter string. */
    public static final String SPLIT_CHAR = ";";

    /**
     * {@inheritDoc} Convert string list to database column.
     * 
     * @param stringList List of strings to be converted to database column.
     * @return Converted delimited string.
     */
    @Override
    @NotNull
    public String convertToDatabaseColumn(@Nullable List<String> stringList) {

        if (stringList == null) {
            return "";
        }
        return String.join(SPLIT_CHAR, stringList);
    }

    /**
     * {@inheritDoc} Convert database column to string list.
     * 
     * @param string Delimited string to be converted to string list.
     * @return Converted string list.
     */
    @Override
    @NotNull
    public List<String> convertToEntityAttribute(@Nullable String string) {

        if (string == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}
