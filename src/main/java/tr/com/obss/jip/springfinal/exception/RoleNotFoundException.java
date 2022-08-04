package tr.com.obss.jip.springfinal.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Role is not found!");
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
