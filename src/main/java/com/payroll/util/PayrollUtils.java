/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.payroll.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leniejoice
 */
public class PayrollUtils {
    private Connection connection;
    public PayrollUtils(DatabaseConnection dbConnection){
        this.connection = dbConnection.getConnection();  
    }
    
    public float  calculateSssContribution(int empID) {
    String employeeSalaryQuery = """
            select basic_salary from employee
            where "employee_id" = ?;
            """;
    String sssContributionQuery = """
            select contribution from sss
            where (cr_above is null or ? > cr_above)
            and (cr_below is null or ? < cr_below)  
            """;
     float contribution = 0f;

     try {
        // Retrieve employee basic salary
        try (PreparedStatement employeeSalaryStatement = connection.prepareStatement(employeeSalaryQuery)) {
            employeeSalaryStatement.setInt(1, empID);
            try (ResultSet employeeSalaryResult = employeeSalaryStatement.executeQuery()) {
                if (!employeeSalaryResult.next()) return 0f;
                double employeeSalary = employeeSalaryResult.getDouble("basic_salary");

                // Calculate SSS contribution based on the salary
                try (PreparedStatement sssContributionStatement = connection.prepareStatement(sssContributionQuery)) {
                    sssContributionStatement.setDouble(1, employeeSalary);
                    sssContributionStatement.setDouble(2, employeeSalary);
                    try (ResultSet sssContributionResult = sssContributionStatement.executeQuery()) {
                        if (!sssContributionResult.next()) return 0f;
                        contribution = sssContributionResult.getFloat("contribution");
                    }
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("An error occurred: " + e.getMessage());
        // Proper error handling should be done here
    }
           return contribution;
} 
     public static double calculatePhilHealthContribution(double empSalary)  {
        if (empSalary <= 10000) {
            return empSalary * 0.030;
        } else if (empSalary <= 59999.99) {
            return empSalary * 0.030;
        } else if (empSalary == 60000) {
            return empSalary * 0.030;
        }
        return (empSalary * 0.030);
    }
    
     public static double calculatePagibigContribution(double empSalary)  {
        double pagibig;
        if (empSalary <= 1499.99) {
            pagibig = empSalary * 0.01;
        } else if (empSalary > 1500) {
            pagibig = empSalary * 0.02;
        } else {
            pagibig = 0.0;
        }
        double maxPagibig = 100.0;
        return Math.min(pagibig, maxPagibig);
    }

     public double calculateWithholdingTax(double taxableIncome) {
        double tax;
        if (taxableIncome <= 20832) {
            tax = 0;
        } else if (taxableIncome <= 33333) {
            tax = (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 66667) {
            tax = 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166667) {
            tax = 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome <= 666667) {
            tax = 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            tax = 200833.33 + (taxableIncome - 666667) * 0.35;
        }
        return tax;
    }

}   