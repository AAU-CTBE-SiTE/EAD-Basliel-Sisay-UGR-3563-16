package com.site;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String url = dotenv.get("DB_URL", "jdbc:mysql://localhost:3306/Studentdb");
        String username = dotenv.get("DB_USER", "root");
        String password = dotenv.get("DB_PASSWORD");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully");
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}