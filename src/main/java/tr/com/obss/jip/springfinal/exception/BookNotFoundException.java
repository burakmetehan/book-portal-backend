package tr.com.obss.jip.springfinal.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book is not found!");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
