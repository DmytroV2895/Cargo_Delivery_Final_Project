package com.varukha.webproject.util.validation;

/**
 * Class ValidationConstants contains all regex commands for {@link DataValidator}
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public final class ValidationConstants {
    public final static String EMAIL_REGEX = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
    public final static String NAME_REGEX = "[A-ZА-ЯІ][a-z-а-яії ]{1,39}";
    public final static String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
    public final static String PHONE_NUMBER_REGEX = "^\\+380\\d{3}\\d{2}\\d{2}\\d{2}$";
    public final static String INTEGERS_REGEX = "^[0-9]*$";
    public final static String ALL_NUMBERS_REGEX = "^(0|[1-9]\\d*)([.,]\\d+)?";
    public final static String ONLY_TEXT_REGEX = "[A-ZА-ЯІa-z-а-яії ]{5,1024}";
}
