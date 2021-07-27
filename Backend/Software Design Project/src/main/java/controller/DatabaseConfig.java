package controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.*;

@CrossOrigin(origins = "http://localhost:8081")
public class DatabaseConfig {


    static public Connection configurateDatabase() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/goofreads", "root",
                ".P.assworD1");
        return con;
    }

    static public void setStatement(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
    }

    static public void executeQuery(Statement stmt) throws SQLException {
        ResultSet rs=stmt.executeQuery("select * from emp");
        while (rs.next()) System.out.println(rs.getInt(1) + "  " + rs.getString(2) +
                "  " + rs.getString(3));
    }

    static public void closeConnection(Connection con) throws SQLException {
        con.close();
    }

}
