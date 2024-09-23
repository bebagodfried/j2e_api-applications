package handler.api.v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import com.sun.net.httpserver.*;

import dao.BookDAO;
import model.Book;

public class BookHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract the ID from the path (assuming URL pattern: /api/book/{id})
        String path = exchange.getRequestURI().getPath();

        // 
        if ("GET".equals(exchange.getRequestMethod())) {
            BookDAO bookDao = new BookDAO();
            List<Book> books = bookDao.getAll();

            try {
                Long bookId = (long) Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                
                // System.out.println(bookId);
                
                String res = bookDao.get(bookId).toJson();

                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                             os.write(res.getBytes());
                             os.close();

            } catch (Exception e) {
                e.printStackTrace();

                String res = "{\n\t\"message\":\"not found!\"\n}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                OutputStream os = exchange.getResponseBody();
                             os.write(res.getBytes());
                             os.close();
            }
            
            // Get all books ------------------------------------------------------------
            if (books != null && !books.isEmpty()) {
                exchange.sendResponseHeaders(200, 0);

                // formatting Array to Json
                StringBuilder response = new StringBuilder("["); // opening response

                response.append(books.get(0).toJson());

                for (int i = 1; i < books.size(); i++) {
                    response.append(",\n").append(books.get(i).toJson());
                }

                response.append("]"); // close response

                // return response
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();

            } else {
                exchange.sendResponseHeaders(200, 0);

                OutputStream os = exchange.getResponseBody();
                os.write("no book registered".getBytes());
                os.close();
            }

        } else if ("POST".equals(exchange.getRequestMethod())) {
            
            BookDAO bookDAO = new BookDAO();
            Book book = new Book();

            // get the request body
            InputStream is  = exchange.getRequestBody();
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String  body    = scanner.hasNext() ? scanner.next() : "";
                              scanner.close();
            
            // dump
            // System.out.println("book :" + body); // log

            // manual parse body to json
            /**
             * after InputStream scaned to `body`,
             * -- eg: get the title
             * 1. `body.split("\"title\":\"")[1]` => creating an array splited at `title label`
             * then get the second item in the array. ??? the 1st item is the opening `{`
             * 2. `.split("\"")[0]` => creating an array from the second part of the previous array splited at `"`
             *     then get the first one, that's the title
             */
            // System.out.println("title :" + body.split("\"title\":\"")[1].split("\"")[0]); // log
            
            book.title = body.split("\"title\":\"")[1].split("\"")[0];
            book.description = body.split("\"description\":\"")[1].split("\"")[0];
            book.author = body.split("\"author\":\"")[1].split("}")[0]; // split the closing `}`

            try {
                bookDAO.store(book);
                String res = "{\"message\":\"Book added successfully\"}";
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, res.length());

                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();

            } catch (Exception e) {
                exchange.sendResponseHeaders(500, -1);
            }

        } else if ("PUT".equals(exchange.getRequestMethod())) {
            BookDAO bookDAO = new BookDAO();
            Book book = new Book();
            
            // body
            InputStream is = exchange.getRequestBody();
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String body = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            // parsing ref line:87
            book.title = body.split("\"title\":\"")[1].split("\"")[0];
            book.description = body.split("\"description\":\"")[1].split("\"")[0];
            book.author = body.split("\"author\":\"")[1].split("}")[0];

            try {
                Long bookId = (long) Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

                System.out.println(bookId);
                bookDAO.update(book, bookId);
                String res = "{\"message\":\"Book updated successfully\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, res.length());

                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                    os.close();
            } catch (Exception e) {
                exchange.sendResponseHeaders(500, -1);
            }

        } else if("DELETE".equals(exchange.getRequestMethod())) {
            BookDAO bookDao = new BookDAO();
            Book book = new Book();

            try {
                Long bookId = (long) Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));

                book.setId(bookId);
                bookDao.delete(book);

                String res = "Book deleted successfully";

                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();

                String res = "{\n\t\"message\":\"not found!\"\n}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        } else {
            System.out.println("not supported yet");
        }
    }
    
}
