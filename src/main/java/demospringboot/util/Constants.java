package demospringboot.util;

public final class Constants {

    private Constants() {throw new IllegalStateException("Utility class");}

    public static final String API_BASE_PATH = "/demo";
    public static final String API_VERSION = "/v1";
    public static final String API_CUSTOMERS = "/customers";

    public static final int CODE_SUCCESFULL_OK = 200;
    public static final int CODE_SUCCESFULL_CREATED = 201;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOT_AUTHORIZED = 401;
    public static final int CODE_INTERNAL_ERROR = 500;


}
