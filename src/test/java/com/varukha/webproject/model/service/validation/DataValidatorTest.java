package com.varukha.webproject.model.service.validation;


import com.varukha.webproject.exception.IncorrectInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


class DataValidatorTest extends Assertions {

    @ParameterizedTest
    @MethodSource("isPassword")
    void isPasswordValid(String input, boolean expectedResult) {
        assertEquals(expectedResult, DataValidator.isPasswordValid(input));
    }


    @ParameterizedTest
    @MethodSource("isNameOrSurname")
    void isNameValid(String input, boolean expectedResult) {
        assertEquals(expectedResult, DataValidator.isNameValid(input));
    }

    @ParameterizedTest
    @MethodSource("isPhoneNumber")
    void isPhoneNumberValid(String input, boolean expectedResult) {
        assertEquals(expectedResult, DataValidator.isPhoneNumberValid(input));
    }

    @ParameterizedTest
    @MethodSource("isEmail")
    void isEmailValid(String input, boolean expectedResult) {
        assertEquals(expectedResult, DataValidator.isEmailValid(input));
    }

//    @ParameterizedTest
//    @MethodSource("isOnlyText")
//    void isTextValid(String input, boolean expectedResult) {
//        assertEquals(expectedResult, DataValidator.isTextValid(input));
//    }


    @ParameterizedTest
    @MethodSource("isOnlyNumbersValid")
    void testIsNumberValid(String input, boolean expectedResult) throws IncorrectInputException {
        assertEquals(expectedResult, DataValidator.isNumberValid(input));
    }

    @ParameterizedTest
    @MethodSource("isAllNumbersValid")
    void isAllNumbers(String input, boolean expectedResult)  {
        assertEquals(expectedResult, DataValidator.isAllNumbersValid(input));
    }

//    private static Stream<Arguments> isOnlyText() {
//        return Stream.of(
//                Arguments.of("Oleg", true),
//                Arguments.of(null, false),
//                Arguments.of("", false),
//                Arguments.of("  ", false),
//                Arguments.of("dmytro", true),
//                Arguments.of("dmytrO", true),
//                Arguments.of("3Oleksandr", false),
//                Arguments.of("Oleksandr35", false),
//                Arguments.of("V@sulenko", false),
//                Arguments.of("V", false),
//                Arguments.of("Пристрій", true),
//                Arguments.of("інструменти", true),
//                Arguments.of("v", false)
//
//        );
//    }

    private static Stream<Arguments> isAllNumbersValid() {
        return Stream.of(
                Arguments.of("025", false),
                Arguments.of("12,345", true),
                Arguments.of("12.345", true),
                Arguments.of("12. 345", false),
                Arguments.of("123.456789", true),
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("25 24", false),
                Arguments.of("f2524", false),
                Arguments.of("35:4", false),
                Arguments.of("35,", false),
                Arguments.of("35.", false)
        );
    }


    private static Stream<Arguments> isOnlyNumbersValid() {
        return Stream.of(
                Arguments.of("025", true),
                Arguments.of("12345", true),
                Arguments.of("123456789", true),
                Arguments.of("25 24", false),
                Arguments.of("f2524", false),
                Arguments.of("35:4", false),
                Arguments.of("35,", false),
                Arguments.of("35.", false)
        );
    }


    private static Stream<Arguments> isPhoneNumber() {
        return Stream.of(
                Arguments.of("+380991785636", true),
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("380991785636", false),
                Arguments.of("0991785636", false),
                Arguments.of("+38(099)1785636", false),
                Arguments.of("+38(099)178 56 36", false),
                Arguments.of("38(099)178-56-36", false)

        );
    }

    private static Stream<Arguments> isNameOrSurname() {
        return Stream.of(
                Arguments.of("Oleg", true),
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("dmytro", false),
                Arguments.of("dmytrO", false),
                Arguments.of("3Oleksandr", false),
                Arguments.of("Oleksandr35", false),
                Arguments.of("V@sulenko", false),
                Arguments.of("V", false),
                Arguments.of("v", false)

        );
    }

    private static Stream<Arguments> isEmail() {
        return Stream.of(
                Arguments.of("user1@gmail.com", true),
                Arguments.of("User1@gmail.com", false),
                Arguments.of("user1@gmail3.com", false),
                Arguments.of("user1@gmail.Com", false),
                Arguments.of("user1@Gmail.Com", false),
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("john.doe.1@yahoo.com", false),
                Arguments.of("user4 @company.com", false),
                Arguments.of("user3@company.com", true),
                Arguments.of("user1@hotmail.com", true),
                Arguments.of("user2@gmail.com", true),
                Arguments.of("not_working@stackabuse.org", false)

        );
    }

    private static Stream<Arguments> isPassword() {
        return Stream.of(
                Arguments.of("Qaz123rewq", true),
                Arguments.of("_Qaz123rewq", true),
                Arguments.of("Qaz123йцук", true),
                Arguments.of("Qaz123", false),
                Arguments.of("qaz123456", false),
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("Password", false),
                Arguments.of("qwertty@", false),
                Arguments.of("Password1_@", true),
                Arguments.of("Пароль", false),
                Arguments.of("пароль", false)


        );
    }

}













