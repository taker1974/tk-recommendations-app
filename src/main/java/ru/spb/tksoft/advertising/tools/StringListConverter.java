package ru.spb.tksoft.advertising.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.NotNull;

/**
 * List<String> converter.
 * 
 * @see https://www.baeldung.com/java-jpa-persist-string-list
 *
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 * @version 0.1
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    public static final String SPLIT_CHAR = ";";

    @Override
    @NotNull
    public String convertToDatabaseColumn(@Nullable List<String> stringList) {

        if (stringList == null) {
            return "";
        }
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    @NotNull
    public List<String> convertToEntityAttribute(@Nullable String string) {

        if (string == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}
