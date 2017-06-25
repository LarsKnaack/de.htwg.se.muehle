package service;

public class Endpoints {
    static final String BASE_HOST = "localhost";
    static final int BASE_PORT = 8080;
    public static final String BASE_URL = "http://" + BASE_HOST + ":" + BASE_PORT + "/";
    public static final String UPDATE = BASE_URL + "update/";
    public static final String HANDLE_INPUT = BASE_URL + "handleInput/";
}
