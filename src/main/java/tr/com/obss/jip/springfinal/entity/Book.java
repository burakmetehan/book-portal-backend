package tr.com.obss.jip.springfinal.entity;

import tr.com.obss.jip.springfinal.model.BookDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "BOOKS")
public class Book extends EntityBase {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PAGE_COUNT")
    private int pageCount;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PUBLISHER")
    private String publisher;

    @ManyToMany(mappedBy = "readList")
    private Set<User> readListUsers;

    @ManyToMany(mappedBy = "favoriteList")
    private Set<User> favoriteListUsers;

    public Book() {}

    public Book(BookDTO bookDTO) {
        this.setName(bookDTO.getName());
        this.setAuthor(bookDTO.getAuthor());
        this.setPageCount(bookDTO.getPageCount());
        this.setType(bookDTO.getType());
        this.setPublisher(bookDTO.getPublisher());
        this.readListUsers = null;
        this.favoriteListUsers = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Set<User> getReadListUsers() {
        return readListUsers;
    }

    public void setReadListUsers(Set<User> readListUsers) {
        this.readListUsers = readListUsers;
    }

    public Set<User> getFavoriteListUsers() {
        return favoriteListUsers;
    }

    public void setFavoriteListUsers(Set<User> favoriteListUsers) {
        this.favoriteListUsers = favoriteListUsers;
    }
}
