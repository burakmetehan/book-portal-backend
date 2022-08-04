package tr.com.obss.jip.springfinal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UserController {

    @GetMapping("")
    public ResponseEntity<String> get(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }
}
