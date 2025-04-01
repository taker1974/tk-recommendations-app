package ru.spb.tksoft.advertising.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Описание правила рекомендации. По сути - описание RMI в виде
 * "query:имя_метода,arguments:аргумент_метода_1;.."
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Value
@RequiredArgsConstructor
public class Rule {

    private final long id;

    private final String query;
    private final List<String> arguments;

    private final boolean negate;

    public List<String> getArguments() {
        return Collections.unmodifiableList(null == arguments ? new ArrayList<>() : arguments);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(query)
                .append('(')
                .append(String.join(",", arguments))
                .append(')');
        return sb.toString();
    }
}
