package tr.com.obss.jip.springfinal.exception;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("Role is not found!");
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}