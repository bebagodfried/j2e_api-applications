package dao;

import java.util.List;

import model.Book;

public interface Dao<T> {
    public Book get(Long id);
    public List<T> getAll();
    
    public void store(T t);
    public void update(T t, Long id);
    public void delete(T t);
}
