/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.Database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author asjad
 */

//Class to retrieve connection for database and login verfication.
public class ConnectionFactory {

    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/inventory";
    static String username;
    static String password;

    Properties prop;

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public ConnectionFactory(){
        try {
            //Username and Password saved as configurable properties to allow changes without recompilation.
            prop = new Properties();
            prop.loadFromXML(new FileInputStream("lib/DBCredentials.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        username = prop.getProperty("username");
        password = prop.getProperty("password");

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

     public String generateUniqueCode(char prefix) throws SQLException {
        String uniqueCode;
        boolean isUnique;

        do {
            // Gerar um número aleatório de 4 dígitos
            Random random = new Random();
            int number = 1000 + random.nextInt(9000); // Garante um número de 4 dígitos

            // Combinar o prefixo com o número gerado
            uniqueCode = prefix + String.valueOf(number);

            // Verificar se o código já existe no banco de dados
            isUnique = checkIfCodeIsUnique(uniqueCode);

        } while (!isUnique);

        return uniqueCode;
    }

    private boolean checkIfCodeIsUnique(String code) throws SQLException {
        String sql = "SELECT COUNT(*) FROM products WHERE productcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        }
        return false;
    }

    //Login verification method
    public boolean checkLogin(String username, String password, String userType){
        String query = "SELECT * FROM users WHERE username='"
                + username
                + "' AND password='"
                + password
                + "' AND usertype='"
                + userType
                + "' LIMIT 1";

        try {
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
