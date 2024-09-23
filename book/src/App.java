import java.net.InetSocketAddress;
import java.sql.Connection;

import com.sun.net.httpserver.HttpServer;

import database.DBase;
import handler.api.v1.BookHandler;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            HttpServer  server = HttpServer.create(new InetSocketAddress(8000), 0);
                        server.createContext("/api/books", new BookHandler()); // get all books /or store a book
                        server.createContext("/api/books/{bookId}", new BookHandler()); // get a book
                        server.setExecutor(null);
                        server.start();

            System.out.println("\nAPI started on http://localhost:" + server.getAddress().getPort());

            // Check db connection
            try (Connection jdb = DBase.getConnection()) {
                if (jdb != null) {
                    System.out.println("Database connected");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.err.println("Database connection failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
