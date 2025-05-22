package Fontes;


import java.sql.*;

public class Conexao {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/crypto";
        String user = "crypto";
        String password = "crypto";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}