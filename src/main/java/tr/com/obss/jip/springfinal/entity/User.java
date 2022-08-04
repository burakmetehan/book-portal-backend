package tr.com.obss.jip.springfinal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User extends EntityBase {
    @Column(name = "USERNAME", length = 255, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "READ_LIST",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID"))
    @JsonManagedReference
    private Set<Book> readList;

    @ManyToMany
    @JoinTable(
            name = "FAVORITE_LIST",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID"))
    @JsonManagedReference
    private Set<Book> favoriteList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    @JsonManagedReference
    private Set<Role> roles;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Book> getReadList() {
        return readList;
    }

    public void setReadList(Set<Book> readList) {
        this.readList = readList;
    }

    public Set<Book> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(Set<Book> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
