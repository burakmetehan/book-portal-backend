package tr.com.obss.jip.springfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class})
public class JipSpringFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JipSpringFinalProjectApplication.class, args);
    }

}
