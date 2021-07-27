package controller;

import entity.Book;
import entity.Tag;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class TagController {

    @PostMapping(path = "/tag", consumes = "application/json", produces = "application/json")
    public void addTag(@RequestBody Tag tag) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO tags(tag_name) VALUES (?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, tag.getTagName());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tag/{id}")
    public Tag getTag(@PathVariable Integer id) {
        ArrayList<Tag> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM tags WHERE tag_id = " + id;
            else query = "SELECT * FROM tags";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String name = resultSet.getString(2);

                results.add(new Tag(tagId, name));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results.get(0);
    }

    @GetMapping("/tag")
    public ArrayList<Tag> getTag(@RequestParam(value = "id", defaultValue = "") String id) {
        ArrayList<Tag> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM tags WHERE tag_id = " + id;
            else query = "SELECT * FROM tags";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String name = resultSet.getString(2);

                results.add(new Tag(tagId, name));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/tag/{id}")
    public void deleteTag(@PathVariable Integer id) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "DELETE FROM tags WHERE tag_id = " + id;
            statement.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/tag")
    public void updateTag(@RequestBody Tag tag) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "UPDATE tags SET tag_name = ? WHERE tag_id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, tag.getTagName());
            preparedStmt.setInt(2, tag.getTagId());

            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tag/search")
    public ArrayList<Tag>  searchBook(@RequestParam(value = "name") String name) {
        System.out.println("dadsasdasdsad");
        ArrayList<Tag> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM tags WHERE tag_name LIKE \'%" + name + "%\'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String tagName = resultSet.getString(2);

                results.add(new Tag(tagId, tagName));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}

