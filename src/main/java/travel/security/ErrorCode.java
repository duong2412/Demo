package travel.security;

public enum ErrorCode {
    INVALID_TOKEN("AUTH-01", "Token is invalid"),
    EXPIRED_TOKEN("AUTH-02", "Expired token");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
