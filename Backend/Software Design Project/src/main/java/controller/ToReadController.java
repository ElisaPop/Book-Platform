package controller;

import entity.Book;
import entity.BookTag;
import entity.ToRead;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class ToReadController {
    @PostMapping(path = "/toread", consumes = "application/json", produces = "application/json")
    public void addToRead(@RequestBody ToRead toRead) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO to_read VALUES (?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, toRead.getUserId());
            preparedStmt.setInt(2, toRead.getBookId());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @GetMapping("/toread")
//    public ArrayList<ToRead> getToRead(@RequestParam(value = "id", defaultValue = "") String id) {
//        ArrayList<ToRead> results = new ArrayList<>();
//
//        try {
//            Connection connection = DatabaseConfig.configurateDatabase();
//            Statement statement = connection.createStatement();
//
//            String query;
//            if (!id.equals("")) query = "SELECT * FROM to_read WHERE user_id = " + id;
//            else query = "SELECT * FROM to_read";
//
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                int bookId = resultSet.getInt(2);
//                int userId = resultSet.getInt(1);
//
//                results.add(new ToRead(userId, bookId));
//            }
//
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return results;
//    }

    @DeleteMapping("/toread")
    public void deleteToRead(@RequestParam(value = "bookid") int bookId,
                             @RequestParam(value = "userid") int userId) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "DELETE FROM to_read WHERE user_id =" + userId + " AND book_id =" + bookId;
            statement.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*@PutMapping("/toread")
    public void updateToRead(@RequestBody BookTag bookTag) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "UPDATE book_tags SET count = ? WHERE book_id = ? AND tag_id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(2, bookTag.getBookId());
            preparedStmt.setInt(3, bookTag.getTagId());
            preparedStmt.setInt(1, bookTag.getCount());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    @GetMapping("/toread")
    public ArrayList<Book> getToRead(@RequestParam(value = "id") Integer id) {
        ArrayList<Book> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM to_read INNER JOIN book ON bookID = book_id WHERE user_id = " + id;

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Book newBook = new Book(resultSet.getInt(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getFloat(6),
                        resultSet.getInt(7),resultSet.getInt(8),
                        resultSet.getInt(9),resultSet.getString(10));

                results.add(newBook);
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

}
