/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.payroll.services;

import com.payroll.domain.EmployeeAccount;
import com.payroll.domain.EmployeeDetails;
import com.payroll.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @leniejoice
 */
public class EmployeeAccountService {
    private Connection connection;
    private EmployeeDetailsService empDetailsService;
    
    
    public EmployeeAccountService(DatabaseConnection dbConnection){
        this.connection = dbConnection.getConnection();  
        this.empDetailsService = new EmployeeDetailsService(dbConnection);
    }

    public EmployeeAccountService() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

    public EmployeeAccount getUserAccount(String username, String password){
        EmployeeAccount employeeAccount = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee_account where username = ? and password = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet.next()){
                    employeeAccount = new EmployeeAccount();
                    employeeAccount.setAccountID(resultSet.getInt("account_id"));
                    employeeAccount.setEmpUserName(resultSet.getString("username"));
                    employeeAccount.setEmpPassword(resultSet.getString("password"));

                    int empID = resultSet.getInt("employee_id");
                    EmployeeDetails employeeDetails = empDetailsService.getByEmpID(empID);
                    employeeAccount.setEmpDetails(employeeDetails);
                    
                }
                 
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeAccount;
    } 
    
     public EmployeeAccount getByEmpID(int empID){
        EmployeeAccount employeeAccount = null ;
            if (connection != null) {
            String Query = "SELECT * FROM public.employee_account where employee_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setInt(1,empID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    employeeAccount = new EmployeeAccount();
                    employeeAccount.setAccountID(resultSet.getInt("account_id"));
                    employeeAccount.setEmpUserName(resultSet.getString("username"));
                    employeeAccount.setEmpPassword(resultSet.getString("password"));

                    EmployeeDetails employeeDetails = empDetailsService.getByEmpID(empID);
                    employeeAccount.setEmpDetails(employeeDetails);
                }
                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeAccount;
    } 
    
    public void updateEmployeeAccount(EmployeeAccount empAccount){
         
        if(connection !=null){
            String Query = "UPDATE public.employee_account SET username = ?, password = ? where employee_id = ?";
        
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,empAccount.getEmpUserName());
                preparedStatement.setString(2,empAccount.getEmpPassword());
                preparedStatement.setInt(3,empAccount.getEmpID());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }                                   
        }
    }
     
    public List<EmployeeAccount> getAllUserAccount(){
        List<EmployeeAccount> allEmployeeAccount = new ArrayList<>();
            if (connection != null) {
            String Query = "SELECT * FROM public.employee_account";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    EmployeeAccount employeeAccount = new EmployeeAccount();
                    employeeAccount.setAccountID(resultSet.getInt("account_id"));
                    employeeAccount.setEmpUserName(resultSet.getString("username"));
                    employeeAccount.setEmpPassword(resultSet.getString("password"));

                    int empID = resultSet.getInt("employee_id");
                    EmployeeDetails employeeDetails = empDetailsService.getByEmpID(empID);
                    employeeAccount.setEmpDetails(employeeDetails);
                    allEmployeeAccount.add(employeeAccount);
                }
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return allEmployeeAccount;
    }
   
    
       public EmployeeDetails saveUserAccount(EmployeeAccount empAccount,EmployeeDetails empDetails){
            if (connection != null) {
            String Query = "INSERT into public.employee_account (employee_id, username, password) VALUES (?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,empDetails.getEmpID());                
                preparedStatement.setString(2,empAccount.getEmpUserName());
                preparedStatement.setString(3,empAccount.getEmpPassword());
                
                
                int affectedrows = preparedStatement.executeUpdate();
                    if(affectedrows > 0){
                     try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            empAccount.setAccountID(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Creating user failed, no ID obtained.");
                         }
                    }
            };
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return empDetails;
    }
    
    public void deleteEmpAccount(int empID){
        if (connection != null) {
            String Query = "delete from public.employee_account where employee_id =?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,empID);    
                
                preparedStatement.executeUpdate();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                              
    }   
       
       
    public EmployeeAccount changePassword(String username, String password){
        
        return null;
        
    }
    
    
}
