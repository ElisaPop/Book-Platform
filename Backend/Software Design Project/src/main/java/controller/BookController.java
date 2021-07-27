package controller;

import entity.Book;
import entity.BookTag;
import entity.Tag;
import entity.User;
import org.springframework.web.bind.annotation.*;
import utils.CryptoUtils;

import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class BookController {

    @PostMapping(path = "/book", consumes = "application/json", produces = "application/json")
    public void addBook(@RequestBody Book book) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO book(title, authors, publication_year, author_id, photo) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, book.getTitle());
            preparedStmt.setString(2, book.getAuthors());
            preparedStmt.setInt(3,book.getPublicationYear());
            preparedStmt.setInt(4, book.getAuthorId());
            preparedStmt.setString(5, book.getPhoto());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/book")
    public ArrayList<Book> getBook(@RequestParam(value = "id", defaultValue = "") String id) {
        ArrayList<Book> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM book WHERE bookID = " + id + " LIMIT 50;";
            else query = "SELECT * FROM book LIMIT 50; " ;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable Integer id) {
        Book result = new Book();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM book WHERE bookID = " + id + " LIMIT 50;";
            else query = "SELECT * FROM book LIMIT 50;";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                result = new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Integer id) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "DELETE FROM book WHERE bookID = " + id;
            statement.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/book")
    public void updateBook(@RequestBody Book book) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "UPDATE book SET title = ?, authors = ?, publication_year = ?, " +
                    "author_id = ?, photo = ? WHERE bookID = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(6, book.getBookId());
            preparedStmt.setString(1, book.getTitle());
            preparedStmt.setString(2, book.getAuthors());
            preparedStmt.setInt(3,book.getPublicationYear());
            preparedStmt.setInt(4, book.getAuthorId());
            preparedStmt.setString(5, book.getPhoto());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/book/search")
    public ArrayList<Book>  searchBook(@RequestParam(value = "title") String name) {
        ArrayList<Book> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM book WHERE title LIKE \'%" + name + "%\'" + " LIMIT 50;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @GetMapping("/book/written")
    public ArrayList<Book>  searchBook(@RequestParam(value = "id") int id) {
        ArrayList<Book> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM book WHERE author_id = " + id ;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @GetMapping("/book/top")
    public ArrayList<Book>  topBooks() {
        ArrayList<Book> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM book ORDER BY average_rating DESC LIMIT 4;" ;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @GetMapping("/book/new")
    public ArrayList<Book>  newBooks() {
        ArrayList<Book> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM book ORDER BY publication_year DESC LIMIT 4;" ;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getFloat(4),
                        resultSet.getInt(5),resultSet.getInt(6),
                        resultSet.getInt(7),resultSet.getString(8)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
