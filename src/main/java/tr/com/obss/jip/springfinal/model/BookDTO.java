package tr.com.obss.jip.springfinal.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class BookDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @NotBlank
    private Integer pageCount;

    @NotBlank
    private String type;

    @NotBlank
    private String publisher;

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

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
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
}
