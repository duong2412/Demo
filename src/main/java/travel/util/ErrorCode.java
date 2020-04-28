package travel.util;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ErrorCode {
    NOT_BLANK(Code.NOT_BLANK, "object not blank", HttpStatus.BAD_REQUEST),
    USER_NAME_EXISTED(Code.USER_NAME_EXISTED, "Username Existed", HttpStatus.BAD_REQUEST),
    OBJECT_NOT_FOUND(Code.OBJECT_NOT_FOUND, "Object not found", HttpStatus.BAD_REQUEST),
    OBJECT_IS_NULL(Code.OBJECT_IS_NULL, "object not null", HttpStatus.BAD_REQUEST);

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ErrorCode getMessage(String code) {
        return Arrays
                .stream(values())
                .filter(errorCode -> errorCode.code.equals(code))
                .findFirst()
                .get();
    }

    public String message() {
        return message;
    }

    public HttpStatus status() {
        return httpStatus;
    }

    public String code() {
        return code;
    }

    public interface Code {
        String NOT_BLANK = "001";
        String OBJECT_IS_NULL = "002";
        String USER_NAME_EXISTED = "003";
        String OBJECT_NOT_FOUND = "004";
    }

}
