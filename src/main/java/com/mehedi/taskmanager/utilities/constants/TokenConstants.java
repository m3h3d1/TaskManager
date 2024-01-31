package com.mehedi.taskmanager.utilities.constants;

public class TokenConstants {
    public static final String TOKEN_SECRET = "MySecretMySecretMySecretMehediHasanMehediHasan";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Integer MAX_LOGIN_ATTEMPTS_LIMIT = 20;
}