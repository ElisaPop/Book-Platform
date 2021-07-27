package controller;

import entity.BookTag;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class BookTagController {

    @PostMapping(path = "/booktag", consumes = "application/json", produces = "application/json")
    public void addBookTag(@RequestBody BookTag bookTag) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO book_tags VALUES (?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, bookTag.getBookId());
            preparedStmt.setInt(2, bookTag.getTagId());
            preparedStmt.setInt(3, bookTag.getCount());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/booktag")
    public ArrayList<BookTag> getBookTag(@RequestParam(value = "id", defaultValue = "") String id) {
        ArrayList<BookTag> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM book_tags WHERE book_id = " + id;
            else query = "SELECT * FROM book_tags";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                int tagId = resultSet.getInt(2);
                int count = resultSet.getInt(3);

                results.add(new BookTag(bookId, tagId, count));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @DeleteMapping("/booktag")
    public void deleteBookTag(@RequestParam(value = "idBook", defaultValue = "") String idBook,
        @RequestParam(value = "idTag", defaultValue = "") String idTag){
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            System.out.println(" " + idBook + " " + idTag) ;
            query = "DELETE FROM book_tags WHERE book_id = " + idBook + " AND tag_id = " + idTag;
            statement.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/booktag")
    public void updateBookTag(@RequestBody BookTag bookTag) {
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
    }
}
