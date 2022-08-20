package tr.com.obss.jip.springfinal.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final String username;
    private final boolean isAdmin;

    public JwtResponse(String jwtToken, String username, Boolean isAdmin) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }
}
