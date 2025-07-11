package com.DB.LoginApp;
import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginApplication {
	private static final String DB_URL = "jdbc:sqlite:loginapp.db";

    public static void main(String[] args) {
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Scanner sc = new Scanner(System.in)) {

            String createTable = "CREATE TABLE IF NOT EXISTS userdata (" + 
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "username TEXT NOT NULL UNIQUE, " + 
                "password TEXT NOT NULL)";
            conn.createStatement().execute(createTable);

            System.out.println(" Welcome to the LogIn Page ");
            System.out.println(" 1. Login\n 2. Register ");
            System.out.println(" Choose Option ");

            int option = Integer.parseInt(sc.nextLine());

            if(option == 1) {
                System.out.println(" Username ");
                String user = sc.nextLine();

                System.out.println(" Password ");
                String pass = sc.nextLine();

                String sql = "SELECT * FROM userdata WHERE username = ? AND password = ? ";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, user);
                    pstmt.setString(2, pass);

                    ResultSet rs = pstmt.executeQuery();

                    if(rs.next()) {
                        System.out.println(" LogIn Succesfull ");
                    }
                    else System.out.println(" Invalid credentials ");
                }
            }
            else if ( option == 2 ) {
                System.out.println(" Choose an username ");
                String newUser = sc.nextLine();

                System.out.println(" Choose a password ");
                String newPAss = sc.nextLine();

                String insertSql = "INSERT INTO userdata (username, password) VALUES (?,?)";

                try(PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, newUser);
                    pstmt.setString(2, newPAss);
                    pstmt.executeUpdate();
                    System.out.println(" User Resgistered Successfully ");
                } catch (SQLException e) {
                    System.out.println(" Registration Failed " + e.getMessage());
                }
            }
                else {
                    System.out.println(" Invalid Option ");
                }
    }catch (Exception e) {
        System.out.println(" Error " + e.getMessage());
    }
}

}
