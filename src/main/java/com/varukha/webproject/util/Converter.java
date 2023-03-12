package com.varukha.webproject.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class Converter is utility class that used to perform converting operations.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class Converter {

    /**
     * Method toList used to convert optional type to list in order
     * to process set of data and render it on user's display.
     */
    public static <T> List<T> toList(Optional<T> optional) {
        return optional
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }
}
