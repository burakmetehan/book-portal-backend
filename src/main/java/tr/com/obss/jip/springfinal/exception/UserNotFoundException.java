package tr.com.obss.jip.springfinal.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("uer is not found!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
