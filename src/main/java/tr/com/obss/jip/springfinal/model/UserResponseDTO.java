package tr.com.obss.jip.springfinal.model;

import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;

import java.util.Date;
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
    private final String username;
    private final String operationType;
    private final Date createDate;
    private final Date updateDate;
    private final boolean active;

    private final Set<Book> readList;
    private final Set<Book> favoriteList;
    private final Set<Role> roles;

    public UserResponseDTO(
            String username,
            String operationType,
            Date createDate,
            Date updateDate,
            boolean active,
            Set<Book> readList,
            Set<Book> favoriteList,
            Set<Role> roles) {
        this.username = username;
        this.operationType = operationType;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.active = active;
        this.readList = readList;
        this.favoriteList = favoriteList;
        this.roles = roles;
    }

    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.operationType = user.getOperationType();
        this.createDate = user.getCreateDate();
        this.updateDate = user.getUpdateDate();
        this.active = user.isActive();
        this.readList = user.getReadList();
        this.favoriteList = user.getFavoriteList();
        this.roles = user.getRoles();
    }

    public String getUsername() {
        return username;
    }

    public String getOperationType() {
        return operationType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public boolean isActive() {
        return active;
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
