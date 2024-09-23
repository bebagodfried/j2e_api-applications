package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import database.DBase;
import model.Book;

public class BookDAO implements Dao<Book> {

    public BookDAO() {}

    @Override
    public void delete(Book book) {
        String query = "delete                       from books where id = ?";

        try {
            PreparedStatement stmt = DBase.getConnection().prepareStatement(query);
                              stmt.setLong(1, book.getId());
                              stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book get(Long id) {
        String query = "select * from books where id = ?";

        try {
            PreparedStatement stmt = DBase.getConnection().prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            long   bookId = rs.getLong("id");
            String bookTitle = rs.getString("title");
            String bookDesc = rs.getString("description");
            String bookAuthor = rs.getString("author");

            Book book = new Book(bookId, bookTitle, bookDesc, bookAuthor);
            
            return book;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();

        try {
            String query = "select * from books";

            Statement stmt = DBase.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setDescription(rs.getString("description"));
                book.setAuthor(rs.getString("author"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return books;
    }

    @Override
    public void store(Book book) {
        String query = "insert into books (title, description, author) values (?, ?, ?)";

        try {
            PreparedStatement stmt = DBase.getConnection().prepareStatement(query);
                              stmt.setString(1, book.title);
                              stmt.setString(2, book.description);
                              stmt.setString(3, book.author);
                              stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book,Long bookID) {
        String query = "update books set title = ?, description = ?, author = ? where id = ?";

        try {
            PreparedStatement stmt = DBase.getConnection().prepareStatement(query);
            stmt.setLong(4, bookID);

            stmt.setString(1, book.title);
            stmt.setString(2, book.description);
            stmt.setString(3, book.author);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
