/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.payroll.main;

import com.payroll.domain.EmployeeDetails;
import com.payroll.domain.EmployeeHours;
import com.payroll.services.EmployeeDetailsService;
import com.payroll.util.DatabaseConnection;
import com.payroll.util.PayrollUtils;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Computer
 */
public class Dashboard extends javax.swing.JFrame {
    private DatabaseConnection dbConnection;
    private EmployeeDetailsService empService;
    private PayrollUtils payrollUtils;
    /**
     * Creates new form Dashboard
     */
    public Dashboard() {  
        initializeDBConnection();
        initComponents();
    }
    private void initializeDBConnection() {
        dbConnection = new DatabaseConnection();
        dbConnection.connect(); // Establish the database connection
        empService = new EmployeeDetailsService(dbConnection); // Initialize services with the db connection
        payrollUtils = new PayrollUtils(dbConnection);
    }
  private void showDatesOfWeekInTable(int empID, int weekOfYear, int year) {
    DefaultTableModel model = (DefaultTableModel) showDatesOfWeekInTable.getModel();
    model.setRowCount(0);

    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Start of the week
    String startDate = dateFormat.format(calendar.getTime());

    // Move to the end of the week, Friday
    calendar.add(Calendar.DATE, 4);
    String endDate = dateFormat.format(calendar.getTime());

    // Ensure proper casting in the SQL query
    String query = "SELECT date, time_in, time_out FROM employee_hours WHERE employee_id = ? AND date >= CAST(? AS DATE) AND date <= CAST(? AS DATE) AND EXTRACT(DOW FROM date) BETWEEN 1 AND 5";

    try {
        PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
        statement.setInt(1, empID);
        statement.setString(2, startDate);
        statement.setString(3, endDate);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Date date = resultSet.getDate("date");
            Timestamp timeInTimestamp = resultSet.getTimestamp("time_in");
            Timestamp timeOutTimestamp = resultSet.getTimestamp("time_out");

            LocalTime timeIn = (timeInTimestamp != null) ? timeInTimestamp.toLocalDateTime().toLocalTime() : null;
            LocalTime timeOut = (timeOutTimestamp != null) ? timeOutTimestamp.toLocalDateTime().toLocalTime() : null;

            String dateStr = dateFormat.format(date);
            String timeInStr = (timeIn != null) ? timeIn.format(DateTimeFormatter.ofPattern("HH:mm")) : "N/A";
            String timeOutStr = (timeOut != null) ? timeOut.format(DateTimeFormatter.ofPattern("HH:mm")) : "N/A";

            if (!("00:00".equals(timeInStr) && "00:00".equals(timeOutStr))) {
                model.addRow(new Object[]{dateStr, timeInStr, timeOutStr});
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle database errors appropriately
    }
}
    
    private void calculatePayrollForWeek(int empID, int weekOfYear) {
    EmployeeDetails employeeDetails = empService.getByEmpID(empID);
    if (employeeDetails == null) {
        JOptionPane.showMessageDialog(this, "Employee not found", "Search Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Assuming totalHoursMap is populated from a data source, such as a database
    Map<Integer, Duration> totalHoursMap = fetchTotalHoursMapForEmployee(empID); // You need to implement this method

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    // Check if the weekOfYear has hours worked
    if (totalHoursMap.containsKey(weekOfYear) && totalHoursMap.get(weekOfYear).toHours() > 0) {
        
        Calendar startOfWeek = Calendar.getInstance();
        startOfWeek.set(Calendar.YEAR, 2022); // Consider making the year dynamic or based on current year
        startOfWeek.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        Calendar endOfWeek = (Calendar) startOfWeek.clone();
        endOfWeek.add(Calendar.DAY_OF_YEAR, 4); // Assuming a workweek from Monday to Friday
        
        Duration hoursWorked = totalHoursMap.get(weekOfYear);
        long hours = hoursWorked.toHours();
        double hourlyRateValue = employeeDetails.getEmpHourlyRate();
        double weeklySalaryValue = hourlyRateValue * hoursWorked.toHours();
        
        double sssContributionValue = payrollUtils.calculateSssContribution(empID)/4;
        double philHealthContributionValue = payrollUtils.calculatePhilHealthContribution(employeeDetails.getEmpBasicSalary())/8;
        double pagibigContributionValue = payrollUtils.calculatePagibigContribution(employeeDetails.getEmpBasicSalary())/4;
        double withholdingTaxValue = payrollUtils.calculateWithholdingTax(employeeDetails.getEmpBasicSalary())/4;
        double totalContributionsValue = sssContributionValue + philHealthContributionValue + pagibigContributionValue;
        double taxableIncomeValue = employeeDetails.getEmpBasicSalary() - totalContributionsValue;

        double totalDeductionsValue = totalContributionsValue + withholdingTaxValue;
        double netPayValue = weeklySalaryValue - totalDeductionsValue;

        // Display or use the calculated values in your GUI components
        // Example: Setting values to JTextField components in the GUI
            totalHoursWorked.setText(String.format("%d", hours));
            hourlyRate.setText(String.format("%.2f", hourlyRateValue));
            weekSalary.setText(String.format("%.2f", weeklySalaryValue));
            sss.setText(String.format("%.2f", sssContributionValue));
            philhealth.setText(String.format("%.2f", philHealthContributionValue));
            pagibig.setText(String.format("%.2f", pagibigContributionValue));
            tax.setText(String.format("%.2f", withholdingTaxValue/4));
            totalDeductions.setText(String.format("%.2f", totalDeductionsValue));
            netPay.setText(String.format("%.2f", netPayValue));
        // Add more GUI component updates as needed

    } else {
        JOptionPane.showMessageDialog(this, "Week not found or no hours worked", "Search Error", JOptionPane.ERROR_MESSAGE);
            totalHoursWorked.setText("");
            hourlyRate.setText("");
            weekSalary.setText("");
            totalDeductions.setText("");
            sss.setText("");
            philhealth.setText("");
            pagibig.setText("");
            tax.setText("");
            netPay.setText("");
    }
}

// This is a placeholder. You need to implement fetching logic based on your application's data source.
private Map<Integer, Duration> fetchTotalHoursMapForEmployee(int empID) {
    Map<Integer, Duration> totalHoursMap = new HashMap<>();
    Calendar cal = Calendar.getInstance();
    List<EmployeeHours> employeeHoursList = empService.getEmpHoursByEmpID(empID);

    for (EmployeeHours e : employeeHoursList) {
        cal.setTime(e.getDate());
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        
        LocalTime fixedInTime = LocalTime.of(8, 10);
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime inTime = e.getTimeIn();
        LocalTime outTime = e.getTimeOut();

        if (inTime != null && outTime != null && !inTime.equals(LocalTime.MIDNIGHT)) {
            // Calculate lunch break duration and hours worked considering lunch break
            Duration lunchBreak = Duration.between(lunchStart, lunchEnd);
            Duration hoursWorked = Duration.between(inTime, outTime).minus(lunchBreak);

            // Calculate late coming period, if any
            Duration latePeriod = Duration.between(fixedInTime, inTime);
            if (latePeriod.toMinutes() > 0) {
                System.out.println("Date: " + e.getDate() + " " + latePeriod.toMinutes() + " minutes late");
            }

            // Accumulate total hours for each week
            totalHoursMap.merge(week, hoursWorked, Duration::plus);
        }
    }

    // Assuming you want to set the year dynamically based on the date obtained from EmployeeHours
    int year = cal.get(Calendar.YEAR);
    
    // Display total hours for each week
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    for (Map.Entry<Integer, Duration> entry : totalHoursMap.entrySet()) {
        if (entry.getValue().toHours() > 0) {
            
            
         
            Calendar startOfWeek = Calendar.getInstance();
            startOfWeek.set(Calendar.YEAR, year); // Set the year dynamically
            startOfWeek.set(Calendar.WEEK_OF_YEAR, entry.getKey());
            startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            
          
            // The following line could be used for debugging purposes
            //System.out.println("Week of " + dateFormat.format(startOfWeek.getTime()) + ": " + entry.getValue().toHours() + " hours worked.");
        }
    }
    
    return totalHoursMap;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        birthDateLabel = new javax.swing.JLabel();
        firstName = new javax.swing.JTextField();
        lastName = new javax.swing.JTextField();
        birthDate = new javax.swing.JTextField();
        weekLabel = new javax.swing.JLabel();
        datesWeek = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        totalDeductionsLabel = new javax.swing.JLabel();
        hourlyRate = new javax.swing.JTextField();
        totalHoursWorked = new javax.swing.JTextField();
        totalDeductions = new javax.swing.JTextField();
        totalHoursWorkedLabel = new javax.swing.JLabel();
        hourlyRateLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        showDatesOfWeekInTable = new javax.swing.JTable();
        deductionsLabel = new javax.swing.JLabel();
        philhealth = new javax.swing.JTextField();
        sss = new javax.swing.JTextField();
        pagibig = new javax.swing.JTextField();
        sssLabel = new javax.swing.JLabel();
        philhealthLabel = new javax.swing.JLabel();
        pagibigLabel = new javax.swing.JLabel();
        taxLabel = new javax.swing.JLabel();
        tax = new javax.swing.JTextField();
        netPayLabel = new javax.swing.JLabel();
        netPay = new javax.swing.JTextField();
        weekSalaryLabel = new javax.swing.JLabel();
        weekSalary = new javax.swing.JTextField();
        addressLabel = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        phoneNumberLabel = new javax.swing.JLabel();
        phoneNumber = new javax.swing.JTextField();
        weekSelector = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        weekRange = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("MotorPH Payroll System");

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setText("Employee ID:");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel3.setText("EMPLOYEE DETAILS");

        lastNameLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        lastNameLabel.setText("Last Name:");

        firstNameLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        firstNameLabel.setText("First Name:");

        birthDateLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        birthDateLabel.setText("Birthday:");

        firstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameActionPerformed(evt);
            }
        });

        lastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameActionPerformed(evt);
            }
        });

        birthDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthDateActionPerformed(evt);
            }
        });

        weekLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        weekLabel.setText("WEEK");

        datesWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datesWeekActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setText("EMPLOYEE DETAILS");

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N

        totalDeductionsLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        totalDeductionsLabel.setText("Total Deductions:");

        hourlyRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourlyRateActionPerformed(evt);
            }
        });

        totalHoursWorked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalHoursWorkedActionPerformed(evt);
            }
        });

        totalDeductions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalDeductionsActionPerformed(evt);
            }
        });

        totalHoursWorkedLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        totalHoursWorkedLabel.setText("Total Hours Worked:");

        hourlyRateLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        hourlyRateLabel.setText("Hourly Rate:");

        showDatesOfWeekInTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Time-in", "Time-out"
            }
        ));
        jScrollPane1.setViewportView(showDatesOfWeekInTable);

        deductionsLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        deductionsLabel.setText("DEDUCTIONS");

        philhealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                philhealthActionPerformed(evt);
            }
        });

        sss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sssActionPerformed(evt);
            }
        });

        pagibig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagibigActionPerformed(evt);
            }
        });

        sssLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        sssLabel.setText("SSS Contribution:");

        philhealthLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        philhealthLabel.setText("Philhealth Contribution:");

        pagibigLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        pagibigLabel.setText("PAG-IBIG Contribution:");

        taxLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        taxLabel.setText("Withholding Tax:");

        tax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxActionPerformed(evt);
            }
        });

        netPayLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        netPayLabel.setText("NET SALARY:");

        netPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netPayActionPerformed(evt);
            }
        });

        weekSalaryLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        weekSalaryLabel.setText("Salary for the Week:");

        weekSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekSalaryActionPerformed(evt);
            }
        });

        addressLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        addressLabel.setText("Address:");

        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });

        phoneNumberLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        phoneNumberLabel.setText("Phone no.:");

        phoneNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberActionPerformed(evt);
            }
        });

        weekSelector.setText("Go");
        weekSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekSelectorActionPerformed(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        weekRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekRangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton)
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(firstNameLabel)
                                                .addComponent(lastNameLabel))
                                            .addComponent(birthDateLabel))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(birthDate))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(firstName)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 0, Short.MAX_VALUE))))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(addressLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9))
                                            .addComponent(phoneNumberLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(phoneNumber)
                                            .addComponent(address)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(sssLabel)
                                        .addGap(48, 48, 48)
                                        .addComponent(sss, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(pagibigLabel)
                                                    .addComponent(taxLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tax, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(pagibig, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(57, 57, 57))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(philhealthLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(philhealth, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(deductionsLabel))
                                .addGap(58, 58, 58)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(weekLabel)
                                .addGap(18, 18, 18)
                                .addComponent(datesWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weekSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(weekRange, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(weekSalaryLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(weekSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(hourlyRateLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(hourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(totalHoursWorkedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(totalHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(netPayLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(netPay, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(totalDeductionsLabel)
                                        .addGap(72, 72, 72)
                                        .addComponent(totalDeductions, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(43, 43, 43))))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(jLabel8)
                    .addContainerGap(738, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(logoutButton))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton)
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(weekLabel)
                    .addComponent(datesWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weekSelector))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameLabel))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstNameLabel)
                                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(birthDateLabel)
                                    .addComponent(birthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addressLabel)
                                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(phoneNumberLabel)
                                    .addComponent(phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 20, Short.MAX_VALUE)
                        .addComponent(deductionsLabel)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(weekRange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalHoursWorkedLabel))
                        .addGap(9, 9, 9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sssLabel)
                    .addComponent(sss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourlyRateLabel)
                    .addComponent(hourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(philhealthLabel)
                    .addComponent(philhealth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weekSalaryLabel)
                    .addComponent(weekSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagibigLabel)
                    .addComponent(pagibig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalDeductionsLabel)
                    .addComponent(totalDeductions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taxLabel)
                    .addComponent(tax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(netPayLabel)
                    .addComponent(netPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(185, 185, 185)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // search text field :
         Scanner inp = new Scanner(System.in);
        
        System.out.print("Employee ID: ");
            int empID = inp.nextInt();
            EmployeeDetails employeeDetails = empService.getByEmpID(empID);
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // search button:
        try {
        int empID = Integer.parseInt(searchTextField.getText().trim());
        EmployeeDetails employeeDetails = empService.getByEmpID(empID);
        
        if (employeeDetails != null) {
            lastName.setText(employeeDetails.getLastName());
            firstName.setText(employeeDetails.getFirstName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String formattedBirthday = dateFormat.format(employeeDetails.getEmpBirthday());
            birthDate.setText(formattedBirthday);
            address.setText(employeeDetails.getEmpAddress());
            phoneNumber.setText(employeeDetails.getEmpPhoneNumber());
            // You can also display other employee details in similar text fields or labels
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found", "Search Error", JOptionPane.ERROR_MESSAGE);
            lastName.setText("");
            firstName.setText("");
            birthDate.setText("");
            address.setText("");
            phoneNumber.setText("");
            // Reset other fields if necessary
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid ID format", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_searchButtonActionPerformed

    private void firstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameActionPerformed
        // first name:
    
    }//GEN-LAST:event_firstNameActionPerformed

    private void lastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameActionPerformed
        // last name:
      
    }//GEN-LAST:event_lastNameActionPerformed

    private void birthDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthDateActionPerformed
        // birthday:
    }//GEN-LAST:event_birthDateActionPerformed

    private void datesWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datesWeekActionPerformed
        // dates of the week
       Scanner inp = new Scanner(System.in); // Consider managing Scanner lifecycle outside this method
        Calendar cal = Calendar.getInstance(); // Assuming cal is defined somewhere, ensure it's initialized
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR); 
    }//GEN-LAST:event_datesWeekActionPerformed

    private void hourlyRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourlyRateActionPerformed
        // hourly rate
    }//GEN-LAST:event_hourlyRateActionPerformed

    private void totalHoursWorkedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalHoursWorkedActionPerformed
        // total hours worked
    }//GEN-LAST:event_totalHoursWorkedActionPerformed

    private void totalDeductionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalDeductionsActionPerformed
        // total deductions
    }//GEN-LAST:event_totalDeductionsActionPerformed

    private void philhealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_philhealthActionPerformed
        // Philhealth Contri
    }//GEN-LAST:event_philhealthActionPerformed

    private void sssActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sssActionPerformed
        // SSS Contri
    }//GEN-LAST:event_sssActionPerformed

    private void pagibigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagibigActionPerformed
        // Pagibig contri
    }//GEN-LAST:event_pagibigActionPerformed

    private void taxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxActionPerformed
        // Withholding tax:
    }//GEN-LAST:event_taxActionPerformed

    private void netPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netPayActionPerformed
        // FINAL NET PAY
    }//GEN-LAST:event_netPayActionPerformed

    private void weekSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekSalaryActionPerformed
        // salary for the week
    }//GEN-LAST:event_weekSalaryActionPerformed

    private void addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressActionPerformed
        // Address
    }//GEN-LAST:event_addressActionPerformed

    private void phoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberActionPerformed
        // Phone number
    }//GEN-LAST:event_phoneNumberActionPerformed

    private void weekSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekSelectorActionPerformed
        // GO button
    
        try {
            int empID = Integer.parseInt(searchTextField.getText().trim());
            int weekOfYear = Integer.parseInt(datesWeek.getText().trim());

            // Assume the current year initially
            int year = 2022;

            // Now, pass this year value to your methods
            showDatesOfWeekInTable(empID, weekOfYear, 2022);
            calculatePayrollForWeek(empID, weekOfYear);
            
              // Calculate the start and end date of the specified week and year
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
            calendar.set(Calendar.YEAR, 2022);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            String startOfWeek = dateFormat.format(calendar.getTime());

            // Move the calendar to the last day of the week (Fruday)
            calendar.add(Calendar.DATE, 4); // Adding 4 days to get Friday
            String endOfWeek = dateFormat.format(calendar.getTime());
            
            weekRange.setText("From " + startOfWeek + " to " + endOfWeek);
            

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid input format", "Input Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_weekSelectorActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();
        LogIn.visible(true);
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void weekRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekRangeActionPerformed
        
    }//GEN-LAST:event_weekRangeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField birthDate;
    private javax.swing.JLabel birthDateLabel;
    private javax.swing.JTextField datesWeek;
    private javax.swing.JLabel deductionsLabel;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField hourlyRate;
    private javax.swing.JLabel hourlyRateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastName;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JTextField netPay;
    private javax.swing.JLabel netPayLabel;
    private javax.swing.JTextField pagibig;
    private javax.swing.JLabel pagibigLabel;
    private javax.swing.JTextField philhealth;
    private javax.swing.JLabel philhealthLabel;
    private javax.swing.JTextField phoneNumber;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable showDatesOfWeekInTable;
    private javax.swing.JTextField sss;
    private javax.swing.JLabel sssLabel;
    private javax.swing.JTextField tax;
    private javax.swing.JLabel taxLabel;
    private javax.swing.JTextField totalDeductions;
    private javax.swing.JLabel totalDeductionsLabel;
    private javax.swing.JTextField totalHoursWorked;
    private javax.swing.JLabel totalHoursWorkedLabel;
    private javax.swing.JLabel weekLabel;
    private javax.swing.JTextField weekRange;
    private javax.swing.JTextField weekSalary;
    private javax.swing.JLabel weekSalaryLabel;
    private javax.swing.JButton weekSelector;
    // End of variables declaration//GEN-END:variables

    
}
