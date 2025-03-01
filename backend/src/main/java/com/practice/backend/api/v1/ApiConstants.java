package com.practice.backend.api.v1;

public class ApiConstants {

    public static final String API_PREFIX = "/api";
    public static final String API_VERSION = "/v1";
    public static final String MATCH_ALL_PATHS = "/**";

    public static final String API = API_PREFIX + API_VERSION;

    // auth
    public static final String AUTH_PATH = API + "/auth";
    public static final String REGISTER_PATH = "/register";
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String REFRESH_TOKEN_PATH = "/refresh";

    // user
    public static final String USER_PATH = API + "/user";

    // product
    public static final String PRODUCT_PATH = API + "/product";
}
