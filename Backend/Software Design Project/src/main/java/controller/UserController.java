package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.CryptoUtils;

import java.sql.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:8081")

@RestController
public class UserController {
    @Autowired MessageDispatcher md;

    @PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
    public User signUpUser(@RequestBody User user) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();
            String hashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(user.getPassword()));

            Boolean isUsernameUsed = false;
            Boolean isEmailUsed = false;

            String query1;
            query1 = "SELECT * FROM users WHERE username = \'" + user.getUsername() + "\' OR email = \'" +
            user.getEmail() + "\'";
            ResultSet resultSet = statement.executeQuery(query1);

            if (resultSet.next()) {
                System.out.println("aaaaaaaa");
                String username = resultSet.getString(2);
                String email = resultSet.getString(3);
                if(username.equals(user.getUsername())) isUsernameUsed = true;
                if(email.equals(user.getEmail())) isEmailUsed = true;
            }
            User newUser = new User();

            if(isEmailUsed || isUsernameUsed) {
                if(isEmailUsed) newUser.setEmail(user.getEmail());
                if(isUsernameUsed) newUser.setUsername(user.getUsername());
                return newUser;
            }

            String query = "INSERT INTO users(username,email,password,name,surname) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getEmail());
            preparedStmt.setString(3, hashPassword);
            preparedStmt.setString(4, user.getName());
            preparedStmt.setString(5, user.getSurname());
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public void addUser(@RequestBody User user) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query = "INSERT INTO users(username,email,name,surname,role) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getEmail());
            preparedStmt.setString(3, user.getName());
            preparedStmt.setString(4, user.getSurname());
            preparedStmt.setInt(5, user.getRole());
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/users")
    public ArrayList<User> getUser(@RequestParam(value = "id", defaultValue = "") String id) {
        ArrayList<User> results = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM users WHERE userID = " + id;
            else query = "SELECT * FROM users";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        User result = new User();

        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            if (!id.equals("")) query = "SELECT * FROM users WHERE userID = " + id;
            else query = "SELECT * FROM users";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result = new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/login")
    public User logInUser(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password) {
        System.out.println("dadsasdasdsad");
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM users WHERE username = \'" + username + "\'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                //there are no rows.
                return null;
            }
            String hashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(password));


            User user = new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7));

            connection.close();

            if(user.getPassword().equals(hashPassword)){
                System.out.println(user.getEmail());
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "DELETE FROM users WHERE userID = " + id;
            statement.executeUpdate(query);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String getRoleName(int i){
        if(i == 1) return "User";
        else if(i == 2) return "Author";
        else if(i == 3) return "Admin";
        return "";
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            int newRole = user.getRole();
            int lastRole;

            String query1 = "SELECT * FROM users WHERE userID = " + user.getUserId();
            ResultSet resultSet = statement.executeQuery(query1);
            if (resultSet.next()) {
                lastRole = resultSet.getInt(7);
                if(lastRole != newRole) {
                    System.out.println(user.getUserId() + ": Your role was changed from " + getRoleName(lastRole) +
                            " to " + getRoleName(newRole));
                    md.sendToClient(user.getUserId() + "","Your role was changed from " + getRoleName(lastRole) +
                            " to " + getRoleName(newRole));
                }
            }

            String query = "UPDATE users SET username = ?, email = ?, name = ?," +
                    "surname = ? , role = ? WHERE userID = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(6, user.getUserId());
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getEmail());
            preparedStmt.setString(3, user.getName());
            preparedStmt.setString(4, user.getSurname());
            preparedStmt.setInt(5, user.getRole());

            preparedStmt.execute();


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/users/search")
    public ArrayList<User>  searchUser(@RequestParam(value = "username") String username) {
        System.out.println("dadsasdasdsad```");
        ArrayList<User> results = new ArrayList<>();
        try {
            Connection connection = DatabaseConfig.configurateDatabase();
            Statement statement = connection.createStatement();

            String query;
            query = "SELECT * FROM users WHERE username LIKE \'%" + username + "%\'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7)));
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
