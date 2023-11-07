package com.bank.bankproject.constants;


import java.math.BigInteger;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String X_ACTIVE_TAMEIO_ID = "X-Active-Tameio-ID";
    public static final String X_MENU_ID = "X-Menu-ID";
    public static final String X_REQUEST_TIME = "X-Request-Time";
    public static final String NO_X_REQUEST_ID = "N/A X-Request-ID";
    public static final String PREFERRED_USERNAME = "preferred_username";
    //date format
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    //iban validator constants
    public static final int EXPECTED_MOD_97 = 1;
    public static final int COUNTRY_CODE_LENGTH = 2;
    private static final int CHECK_DIGITS_LENGTH = 2;
    public static final int PREFIX_LENGTH = COUNTRY_CODE_LENGTH + CHECK_DIGITS_LENGTH;
    public static final BigInteger CHECK_INTEGER_97 = BigInteger.valueOf(97l);
    public static final int STEP = 7;

    public static final String BREAK = "/";
    public static final String COMMA = ",";

    private Constants() {
    }
}
