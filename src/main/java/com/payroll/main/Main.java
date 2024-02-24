/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.payroll.main;
import com.payroll.domain.EmployeeDetails;
import com.payroll.domain.EmployeeHours;
import com.payroll.services.EmployeeDetailsService;
import com.payroll.services.Service;
import java.util.Scanner;
import com.payroll.util.DatabaseConnection;
import com.payroll.util.PayrollUtils;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *  @author leniejoice
 */
public class Main {

    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        
        dbConnection.connect();
        EmployeeDetailsService empService = new EmployeeDetailsService(dbConnection);
        PayrollUtils payrollUtils = new PayrollUtils(dbConnection);
        
        Scanner inp = new Scanner(System.in);
        
        System.out.print("Employee ID: ");
            int empID = inp.nextInt();
       
        EmployeeDetails employeeDetails = empService.getByEmpID(empID);
        
            if (employeeDetails != null){
                System.out.println("Employee Name: " + employeeDetails.getFirstName()+" " + employeeDetails.getLastName());
                System.out.println("Birthday: " + employeeDetails.getEmpBirthday());
                System.out.println(" ");
                //System.out.println("Hours Worked Per Week:");
                
          
            
 /*        }
        System.out.println("");
        System.out.print("Week: ");
            int weekNum = inp.nextInt();   
          
 */       
    List<EmployeeHours> employeeHoursList =  empService.getEmpHoursByEmpID(empID);
        Map<Integer, Duration> totalHoursMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
            for (EmployeeHours e : employeeHoursList) {
                cal.setTime(e.getDate());
        
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                
                LocalTime fixedInTime = LocalTime.of(8, 10);
                LocalTime lunchStart = LocalTime.of(12,0);
                LocalTime lunchEnd = LocalTime.of(13,0);
                LocalTime inTime = e.getTimeIn();
                LocalTime outTime = e.getTimeOut();

                if (inTime != null && outTime != null && !inTime.equals(LocalTime.MIDNIGHT)) {
                    Duration lunchBreak = Duration.between(lunchStart, lunchEnd);
                    Duration hoursWorked = Duration.between(inTime, outTime).minus(inTime.equals(LocalTime.MIDNIGHT) ? Duration.ZERO: lunchBreak);
                    Duration latePeriod = Duration.between(fixedInTime, inTime);
                    if (latePeriod.toMinutes() > 0 ){
                    System.out.println("Date: "+ e.getDate()+" "+ latePeriod.toMinutes()+ " minutes");
                    }

                    // Accumulate total hours for each week
                    totalHoursMap.put(week, totalHoursMap.getOrDefault(week, Duration.ZERO).plus(hoursWorked));
                }
            }

            // Display total hours for each week
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
            for (Map.Entry<Integer, Duration> entry : totalHoursMap.entrySet()) {
               if (entry.getValue().toHours() > 0) {
                Calendar startOfWeek = Calendar.getInstance();
                startOfWeek.set(Calendar.YEAR, 2022);
                startOfWeek.set(Calendar.WEEK_OF_YEAR, entry.getKey());
                startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                
                Calendar endOfWeek = (Calendar) startOfWeek.clone();
                endOfWeek.add(Calendar.DAY_OF_YEAR, 4); 
                
                double sssContribution = payrollUtils.calculateSssContribution((int)empID);
                double philHealthContribution = payrollUtils.calculatePhilHealthContribution(employeeDetails.getEmpBasicSalary());
                double pagibigContribution = payrollUtils.calculatePagibigContribution(employeeDetails.getEmpBasicSalary());
                double withholdingTax = payrollUtils.calculateWithholdingTax(employeeDetails.getEmpBasicSalary());
                double totalContributions = (sssContribution+ philHealthContribution + pagibigContribution);
                double taxableIncome = (employeeDetails.getEmpBasicSalary() - totalContributions);
                        

                System.out.println("Week " + entry.getKey() + ": " +
                        "From " + dateFormat.format(startOfWeek.getTime()) +
                        " to " + dateFormat.format(endOfWeek.getTime()) +
                        "\nTotal Hours Worked: " + entry.getValue().toHours() + " hours"+
                        "\nHourly Rate: "  + employeeDetails.getEmpHourlyRate()+  
                        "\nWeekly Salary: " + (employeeDetails.getEmpHourlyRate()*entry.getValue().toHours()+
                        "\n "+
                        "\nDeductions: "+
                        "\nSSS Contribution: "  + sssContribution/4 + 
                        "\nPhilhealth Contribution: "  + philHealthContribution/8 + 
                        "\nPag-ibig Contribution: "  + pagibigContribution/4+ 
                        "\nTotal Contributions: " + ((sssContribution/4) + (philHealthContribution/8) + (pagibigContribution/4))+
                        "\n "+    
                        "\nTax: "  + withholdingTax/4 + 
                        "\nTotal Deductions: " + ((sssContribution/4) + (philHealthContribution/8) + (pagibigContribution/4)+  (withholdingTax/4))+
                        "\n "+        
                        "\nTotal Net Pay for the Week: " + (employeeDetails.getEmpHourlyRate()*entry.getValue().toHours()- ((sssContribution/4) + (philHealthContribution/8) + (pagibigContribution/4)+  (withholdingTax/4)))+
                        "\n "));             
    }   else {
                System.out.println("Employee not found with ID");
       
            }  
       }  
      }  
    }
}
