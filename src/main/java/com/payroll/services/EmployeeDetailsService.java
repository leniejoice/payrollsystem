/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.payroll.services;
import com.payroll.domain.EmployeeDetails;
import com.payroll.domain.EmployeeHours;
import com.payroll.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author leniejoice
 */
public class EmployeeDetailsService {
    
   private Connection connection;
    public EmployeeDetailsService(DatabaseConnection dbConnection){
        this.connection = dbConnection.getConnection();  
    }
    
  public void printname(EmployeeDetails employee){
        System.out.println(employee.getLastName()+" "+employee.getFirstName());
    }

    public EmployeeDetails getByEmpID(int empID){
        EmployeeDetails employeeDetails = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee where employee_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setInt(1,empID);
                ResultSet resultSet = preparedStatement.executeQuery();
                employeeDetails = toEmployeeDetails (resultSet);

                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeDetails;
    } 
    
    public EmployeeDetails getByEmpPhilHealthID(long empPhilHealthID){
        EmployeeDetails employeeDetails = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee where philhealth = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setLong(1,empPhilHealthID);
                ResultSet resultSet = preparedStatement.executeQuery();
                employeeDetails = toEmployeeDetails (resultSet);

                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeDetails;
    } 
    
    public EmployeeDetails getByEmpSSS(String empSSS){
        EmployeeDetails employeeDetails = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee where sss = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,empSSS);
                ResultSet resultSet = preparedStatement.executeQuery();
                employeeDetails = toEmployeeDetails (resultSet);

                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeDetails;
    } 
    
    public EmployeeDetails getByEmpTIN(String empTIN){
        EmployeeDetails employeeDetails = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee where tin = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setString(1,empTIN);
                ResultSet resultSet = preparedStatement.executeQuery();
                employeeDetails = toEmployeeDetails (resultSet);

                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeDetails;
    } 
    
        public EmployeeDetails getByEmpPagIbig(long empPagIbig){
        EmployeeDetails employeeDetails = null ;
            if (connection != null) {
            String Query = "SELECT * FROM employee where pag_ibig = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setLong(1,empPagIbig);
                ResultSet resultSet = preparedStatement.executeQuery();
                employeeDetails = toEmployeeDetails (resultSet);

                
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return employeeDetails;
    } 

    public List<EmployeeHours> getEmpHoursByEmpID(int empID){
        List<EmployeeHours> empHours = new ArrayList<>();
        if (connection != null) {
            String Query = "SELECT * FROM employee_hours where employee_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Query);
                preparedStatement.setInt(1,empID);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    EmployeeHours e = new EmployeeHours();
                    e.setEmpID(resultSet.getInt("employee_id"));
                    e.setDate(resultSet.getDate("date"));
                    e.setId(resultSet.getInt("id"));
                    e.setTimeIn(resultSet.getObject("time_in", LocalTime.class));
                    e.setTimeOut(resultSet.getObject("time_out", LocalTime.class));
                    empHours.add(e);
                          
                }          
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }          
        }                          
        return empHours;
    } 
    
    private EmployeeDetails toEmployeeDetails(ResultSet resultSet) 
        throws SQLException {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        while (resultSet.next()) {
            employeeDetails.setEmpID(resultSet.getInt("employee_id"));
            employeeDetails.setFirstName(resultSet.getString("firstname"));
            employeeDetails.setLastName(resultSet.getString("lastname"));
            employeeDetails.setEmpBirthday(resultSet.getDate("birthday"));
            employeeDetails.setEmpAddress(resultSet.getString("address"));
            employeeDetails.setEmpPhoneNumber(resultSet.getString("phone_number"));
            employeeDetails.setEmpSSS(resultSet.getString("sss"));
            employeeDetails.setEmpPhilHealth(resultSet.getLong("philhealth"));
            employeeDetails.setEmpTIN(resultSet.getString("tin"));
            employeeDetails.setEmpPagibig(resultSet.getLong("pag_ibig"));
            employeeDetails.setEmpStatus(resultSet.getString("status"));
            employeeDetails.setEmpPosition(resultSet.getString("position"));
            employeeDetails.setEmpImmediateSupervisor(resultSet.getInt("immediate_supervisor"));
            employeeDetails.setEmpBasicSalary(resultSet.getDouble("basic_salary"));
            employeeDetails.setEmpRice(resultSet.getDouble("rice_subsidy"));
            employeeDetails.setEmpPhone(resultSet.getDouble("phone_allowance"));
            employeeDetails.setEmpClothing(resultSet.getDouble("clothing_allowance"));
            employeeDetails.setEmpMonthlyRate(resultSet.getDouble("gross_semi_monthly_rate"));
            employeeDetails.setEmpHourlyRate(resultSet.getDouble("hourly_rate"));
        }       
        return employeeDetails;
        
        
    }

                     
}