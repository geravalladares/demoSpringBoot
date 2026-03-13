package demospringboot.app.exception;

import demospringboot.util.ResponseCode;

public class TemplateNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final ResponseCode responseCode;
    private final String[] messages;

    public TemplateNotFoundException(ResponseCode responseCode, String... messages) {
        super(responseCode.message());
        this.responseCode = responseCode;
        this.messages = messages;
    }

}
