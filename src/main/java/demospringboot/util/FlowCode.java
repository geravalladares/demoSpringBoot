package demospringboot.util;

public enum FlowCode {

    SAVE_CUSTOMER("SAVE_CUSTOMER"),
    GET_CUSTOMER("GET_CUSTOMER"),
    UPDATE_CUSTOMER("UPDATE_CUSTOMER"),
    DELETE_CUSTOMER("DELETE_CUSTOMER"),

    GET_USER("GET_USER"),
    SAVE_USER("SAVE_USER"),
    UPDATE_USER("UPDATE_USER"),
    DELETE_USER("DELETE_USER");

    private String code;
    private FlowCode(String code) {this.code = code;}
    public String code() {return code;}

}
