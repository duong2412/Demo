package travel.exception;

public class ValidateException extends RuntimeException {

    private String description;

    public ValidateException(String code, String description) {
        super(code);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
