package entity;

public class User {
    int userId;
    String username;
    String email;
    String password;
    String name;
    String surname;
    int role;

    public User(){

    }

    public User(int userId, String username, String email, String password, String name, String surname, int role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getRole() {
        return role;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }
}

