package tr.com.obss.jip.springfinal.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class AuthDTO {
    private String username;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthDTO{" + "username='" + username + '\'' + ", token='" + token + '\'' + '}';
    }
}
