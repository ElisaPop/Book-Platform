package controller;

import DTO.RatedBookDto;
import entity.Book;
import entity.Rating;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class RatingController {

    @PostMapping(path = "/rating", consumes = "application/json", produces = "application/json")
    public void addRating(@RequestBody Rating rating) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO ratings VALUES (?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, rating.getBookId());
            preparedStmt.setInt(2, rating.getUserId());
            preparedStmt.setInt(3, rating.getRating());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            if(e instanceof SQLIntegrityConstraintViolationException) {
                updateRating(rating);
            }
            else e.printStackTrace();
        }
    }

//    @GetMapping("/rating")
//    public ArrayList<Rating> getRating(@RequestParam(value = "id", defaultValue = "") String id) {
//        ArrayList<Rating> results = new ArrayList<>();
//
//        try {
//            Connection connection = DatabaseConfig.configurateDatabase();
//            Statement statement = connection.createStatement();
//
//            String query;
//            if (!id.equals("")) query = "SELECT * FROM ratings WHERE user_id = " + id;
//            else query = "SELECT * FROM ratings";
//
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                int bookId = resultSet.getInt(1);
//                int userId = resultSet.getInt(2);
//                int rate = resultSet.getInt(3);
//
//                results.add(new Rating(bookId, userId, rate));
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

    @DeleteMapping("/rating")
    public void deleteRating(@RequestParam(value = "id", defaultValue = "") String id) {

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "DELETE FROM ratings WHERE book_id = " + id;
            statement.executeQuery(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/rating")
    public void updateRating(@RequestBody Rating rating) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "UPDATE ratings SET rating = ? WHERE book_id = ? AND user_id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(2, rating.getBookId());
            preparedStmt.setInt(3, rating.getUserId());
            preparedStmt.setFloat(1, rating.getRating());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/rating")
    public ArrayList<RatedBookDto> getRating(@RequestParam(value = "id") Integer id,
                                             @RequestParam(value = "rating") Integer rating) {
        ArrayList<RatedBookDto> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (rating != 0) query = "SELECT * FROM ratings INNER JOIN book ON bookId = book_id WHERE user_id = " + id + " AND rating = " + rating;
            else query = "SELECT * FROM ratings INNER JOIN book ON bookId = book_id WHERE user_id = " + id ;

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Book newBook = new Book(resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getFloat(7),
                        resultSet.getInt(8),resultSet.getInt(9),
                        resultSet.getInt(10),resultSet.getString(11));

                int rate = resultSet.getInt(3);

                results.add(new RatedBookDto(newBook, rate));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

}
