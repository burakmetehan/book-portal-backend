package tr.com.obss.jip.springfinal.entity;

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
    private Integer pageCount;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @ManyToMany(mappedBy = "readList")
    private Set<User> readListUsers;

    @ManyToMany(mappedBy = "favoriteList")
    private Set<User> favoriteListUsers;
}
