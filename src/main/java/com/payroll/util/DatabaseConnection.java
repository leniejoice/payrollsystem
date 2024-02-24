/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.payroll.util;
import com.payroll.domain.EmployeeDetails;
import com.payroll.main.Main;
import com.payroll.services.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.payroll.domain.EmployeeHours;

/**
 *
 * @author leniejoice
 */
public class DatabaseConnection {
    Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/postgres"; // Modify the URL
    private final String username = "postgres"; // Modify the username
    private final String password = "postgres"; // Modify the password

    public boolean connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Connection getConnection(){
        return connection;
    }
    
///Database connection
}
