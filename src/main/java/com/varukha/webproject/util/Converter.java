package com.varukha.webproject.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;



public class Converter {

    public static <T> List<T> toList(Optional<T> opt) {
        return opt
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }


}
