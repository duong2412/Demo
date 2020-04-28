package travel.exception;

public class ObjectNotFoundException extends RuntimeException {

    private String description;

    public ObjectNotFoundException(String code, String description) {
        super(code);
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
