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

    @ManyToMany
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    @JsonManagedReference
    private Set<Role> roles;
}
