package com.tecsup.app.micro.user.domain.constant;

public final class ValidationConstants {

    private ValidationConstants() {
    } // Prevenimos que alguien la instancie

    // User Profile
    public static final int USERNAME_MAX = 60;
    public static final int EMAIL_MAX = 254;
    public static final int FULL_NAME_MIN = 2;
    public static final int FULL_NAME_MAX = 120;
    public static final int PHONE_MAX = 30;

    // Address
    public static final int ADDRESS_LABEL_MAX = 60;
    public static final int ADDRESS_LINE_MAX = 160;
    public static final int ADDRESS_CITY_MAX = 80;
    public static final int ADDRESS_STATE_MAX = 80;
    public static final int ADDRESS_COUNTRY_MIN = 2;
    public static final int ADDRESS_COUNTRY_MAX = 2;
    public static final int ADDRESS_POSTAL_MAX = 20;
    public static final int ADDRESS_REF_MAX = 200;
}