package tr.com.obss.jip.springfinal.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BookNotFoundException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class})
    public ResponseEntity<?> handleBookNotFound(HttpServletRequest request, Exception exception) {
        Map<String, String> map = new HashMap<>();
        map.put("Error", exception.getMessage());
        map.put("Error Code", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
