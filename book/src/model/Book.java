package model;

public class Book {
    // attributes
    public Long id;
    public String title;
    public String description;
    public String author;

    // constructors
    public Book() {}

    public Book(Long id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    // 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // json format
    public String toJson() {
        String pretty = "{\n\t\"id\" : " + id +
                        ",\n\t\"title\" : \"" + title + "\"" +
                        ",\n\t\"description\" : \"" + description + "\"" +
                        ",\n\t\"author\" : \"" + author + "\"\n}";
        return pretty;
    }   
}
