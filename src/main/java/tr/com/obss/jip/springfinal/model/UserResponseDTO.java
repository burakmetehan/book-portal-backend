package tr.com.obss.jip.springfinal.model;

import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;

import java.util.Set;

/**
 * Used for ResponseEntity. Hiding the password from response.
 * <p>
 * Can be initialized by
 * <ul>
 *     <li>providing all parameters</li>
 *     <li>providing {@link tr.com.obss.jip.springfinal.entity.User User} Object</li>
 * </ul>
 */
public class UserResponseDTO {
    private final long id;
    private final String username;

    private final Set<Book> readList;
    private final Set<Book> favoriteList;
    private final Set<Role> roles;

    public UserResponseDTO(long id, String username, Set<Book> readList, Set<Book> favoriteList, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.readList = readList;
        this.favoriteList = favoriteList;
        this.roles = roles;
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.readList = user.getReadList();
        this.favoriteList = user.getFavoriteList();
        this.roles = user.getRoles();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Book> getReadList() {
        return readList;
    }

    public Set<Book> getFavoriteList() {
        return favoriteList;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
