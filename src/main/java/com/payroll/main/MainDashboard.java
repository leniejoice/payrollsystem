/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.payroll.main;

import com.payroll.domain.ComboItem;
import com.payroll.domain.EmployeeAccount;
import com.payroll.domain.EmployeeDetails;
import com.payroll.domain.EmployeePosition;
import com.payroll.domain.EmployeeStatus;
import com.payroll.services.EmployeeDetailsService;
import com.payroll.services.EmployeeAccountService;
import com.payroll.util.DatabaseConnection;
import java.awt.CardLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import scala.Int;

/**
 *
 * @author leniejoice
 */


public class MainDashboard extends javax.swing.JFrame {
    private DatabaseConnection dbConnection;
    private CardLayout cardLayout;
    private EmployeeAccount empAccount;
    private EmployeeAccountService empAccountService;
    private EmployeeDetailsService empDetailsService;  // Class level declaration if used across multiple methods
    
    
    public MainDashboard(EmployeeAccount empAccount) {
        initComponents();
        cardLayout = (CardLayout)(mphCards.getLayout());
        this.empAccount=empAccount;
        this.dbConnection = new DatabaseConnection();
        if (!this.dbConnection.connect()) {
            System.out.println("Failed to connect to database");
            return; // or handle the error in another way
        }
        updateUserLabels(empAccount);
        this.empAccountService = new EmployeeAccountService(this.dbConnection);
        this.empDetailsService = new EmployeeDetailsService(this.dbConnection);         
        loadAllPositions();
        loadAllStatus();
        loadAllEmployee();
    }
    public MainDashboard(){
        
    }
    public EmployeeAccount getEmpAccount() {
        return empAccount;
    }
    
     public EmployeeAccount getUsername() {
        return empAccount;
    }

    public void setEmpAccount(EmployeeAccount empAccount) {
        this.empAccount = empAccount;
    }
    
    
    private void updateUserLabels(EmployeeAccount empAccount) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String tin = empAccount.getEmpDetails().getEmpTIN() != null ? empAccount.getEmpDetails().getEmpTIN() : "";
        String phone = empAccount.getEmpDetails().getEmpPhoneNumber() != null ? empAccount.getEmpDetails().getEmpPhoneNumber(): "" ;
        String pagIbig = empAccount.getEmpDetails().getEmpPagibig() != 0 ? String.valueOf(empAccount.getEmpDetails().getEmpPagibig()) : "";
        String philhealth = empAccount.getEmpDetails().getEmpPhilHealth() != 0 ? String.valueOf(empAccount.getEmpDetails().getEmpPhilHealth()) : "";
        String sss = empAccount.getEmpDetails().getEmpSSS() !=null ? empAccount.getEmpDetails().getEmpSSS() : "";
      
        
        usernameLabel.setText("@"+ empAccount.getEmpUserName()); 
        fullNameValue.setText(empAccount.getEmpDetails().getFormattedName());
        fullNameValue2.setText(empAccount.getEmpDetails().getFormattedName());
        empNumValue.setText(String.valueOf(empAccount.getEmpDetails().getEmpID()));
        empIDLabelValue.setText(String.valueOf(empAccount.getEmpDetails().getEmpID()));
        addressLabelValue.setText(empAccount.getEmpDetails().getEmpAddress());
        phoneLabelValue.setText(phone);
        basicSalaryLabelValue.setText("PHP " + String.valueOf(empAccount.getEmpDetails().getEmpBasicSalary()));
        riceLabelValue.setText("PHP " + String.valueOf(empAccount.getEmpDetails().getEmpRice()));
        phoneAllowanceValue.setText("PHP " + String.valueOf(empAccount.getEmpDetails().getEmpPhone()));
        clothingLabelValue.setText("PHP " + String.valueOf(empAccount.getEmpDetails().getEmpClothing()));
        hourlyrateLabelValue.setText("PHP " + String.valueOf(empAccount.getEmpDetails().getEmpHourlyRate()));
        tinLabelValue.setText(tin);
        pagibigLabelValue.setText(pagIbig);
        sssLabelValue.setText(sss);
        philhealthLabelValue.setText(philhealth);

        if(empAccount.getEmpDetails().getEmpImmediateSupervisor() != null){
            supervisorLabelValue.setText(empAccount.getEmpDetails().getEmpImmediateSupervisor().getFormattedName());
        }
        if(empAccount.getEmpDetails().getEmpPosition() != null){
            positionLabelValue.setText(empAccount.getEmpDetails().getEmpPosition().getPosition());
        }
        if(empAccount.getEmpDetails().getEmpStatus() != null){
            statusLabelValue.setText(empAccount.getEmpDetails().getEmpStatus().getStatus());
        }
        if(empAccount.getEmpDetails().getEmpBirthday() != null){
             bdayLabelValue.setText(formatter.format(empAccount.getEmpDetails().getEmpBirthday()));
        }
    }   
    
    private void loadAllPositions(){
        List<EmployeePosition> positions = empDetailsService.getAllPosition();
        positionDropdown.addItem(new ComboItem(null,"Select Position"));
        for(EmployeePosition empPosition : positions){
            positionDropdown.addItem(new ComboItem(empPosition.getId(),empPosition.getPosition()));
        }
    }  
    
    private void loadAllStatus(){
        List<EmployeeStatus> statuses = empDetailsService.getAllStatuses();
        statusDropdown.addItem(new ComboItem(null,"Select Status"));
        for(EmployeeStatus empStatus : statuses){
            statusDropdown.addItem(new ComboItem(empStatus.getId(),empStatus.getStatus()));
        }
    }
    
    private void loadAllEmployee(){
        List<EmployeeDetails> allEmployee = empDetailsService.getAllEmployee();
        supervisorDropdown.addItem(new ComboItem(null,"Select Supervisor"));
        for(EmployeeDetails employeeDetails: allEmployee){
            supervisorDropdown.addItem(new ComboItem(employeeDetails.getEmpID(),employeeDetails.getFormattedName()));
        }
    }   
 
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        motorphdash = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        navigatorSplitPane = new javax.swing.JSplitPane();
        navigation = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        profilePictureLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        empManagementButton = new javax.swing.JButton();
        payrollButton = new javax.swing.JButton();
        leaveManagementButton = new javax.swing.JButton();
        attendanceButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        fullNameValue2 = new javax.swing.JLabel();
        empNumValue = new javax.swing.JLabel();
        mphCards = new javax.swing.JPanel();
        profile = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        birthdayLabel = new javax.swing.JLabel();
        addressLabelValue = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sssLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        phealthLabel = new javax.swing.JLabel();
        tinLabel = new javax.swing.JLabel();
        pagibigLabel = new javax.swing.JLabel();
        empIDLabelValue = new javax.swing.JLabel();
        positionLabelValue = new javax.swing.JLabel();
        supervisorLabel = new javax.swing.JLabel();
        fullNameValue = new javax.swing.JLabel();
        riceSubsidyLabel = new javax.swing.JLabel();
        phoneAllowanceLabel = new javax.swing.JLabel();
        basicSalaryLabel = new javax.swing.JLabel();
        clothingLabel = new javax.swing.JLabel();
        phoneLabelValue = new javax.swing.JLabel();
        riceLabelValue = new javax.swing.JLabel();
        supervisorLabelValue = new javax.swing.JLabel();
        basicSalaryLabelValue = new javax.swing.JLabel();
        bdayLabelValue = new javax.swing.JLabel();
        philhealthLabelValue = new javax.swing.JLabel();
        sssLabelValue = new javax.swing.JLabel();
        tinLabelValue = new javax.swing.JLabel();
        pagibigLabelValue = new javax.swing.JLabel();
        clothingLabelValue = new javax.swing.JLabel();
        phoneAllowanceValue = new javax.swing.JLabel();
        hourlyrateLabel = new javax.swing.JLabel();
        hourlyrateLabelValue = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        statusLabelValue = new javax.swing.JLabel();
        profileLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        passwordSUp = new javax.swing.JPasswordField();
        passwordSUp1 = new javax.swing.JPasswordField();
        passwordSUp2 = new javax.swing.JPasswordField();
        attendance = new javax.swing.JPanel();
        attendanceLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        payroll = new javax.swing.JPanel();
        salarySlips = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        empManagement = new javax.swing.JPanel();
        empSectionLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        empFirstNameLabel = new javax.swing.JLabel();
        empLastNameLabel = new javax.swing.JLabel();
        empPositionLabel = new javax.swing.JLabel();
        empAddressLabel = new javax.swing.JLabel();
        lastNameTField = new javax.swing.JTextField();
        firstNameTField = new javax.swing.JTextField();
        addressTField = new javax.swing.JTextField();
        empDetailsLabel = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        empBirthdayLabel = new javax.swing.JLabel();
        birthdayTField = new javax.swing.JTextField();
        empPhoneLabel = new javax.swing.JLabel();
        phoneTField = new javax.swing.JTextField();
        empSalaryLabel = new javax.swing.JLabel();
        salaryTField = new javax.swing.JTextField();
        empHourlyLabel = new javax.swing.JLabel();
        riceTField = new javax.swing.JTextField();
        govIdsLabel = new javax.swing.JLabel();
        empSssLabel = new javax.swing.JLabel();
        empTinLabel = new javax.swing.JLabel();
        empPagibigLabel = new javax.swing.JLabel();
        empPhilhealthLabel = new javax.swing.JLabel();
        sssTField = new javax.swing.JTextField();
        pagibigTField = new javax.swing.JTextField();
        tinTField = new javax.swing.JTextField();
        philhealthTField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        viewAllButton = new javax.swing.JButton();
        salaryDetailsLabel = new javax.swing.JLabel();
        empBiMonthLabel = new javax.swing.JLabel();
        empRiceLabel = new javax.swing.JLabel();
        empPhoneAllowLabel = new javax.swing.JLabel();
        empClothAllowLabel = new javax.swing.JLabel();
        empStatusLabel = new javax.swing.JLabel();
        empSupervisorLabel = new javax.swing.JLabel();
        hourlyTField = new javax.swing.JTextField();
        phoneAllowTField = new javax.swing.JTextField();
        clothingTField = new javax.swing.JTextField();
        biMonthlyTField = new javax.swing.JTextField();
        supervisorDropdown = new javax.swing.JComboBox<>();
        positionDropdown = new javax.swing.JComboBox<>();
        statusDropdown = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        employeeIDTField = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        usernameTField = new javax.swing.JTextField();
        passwordTField = new javax.swing.JPasswordField();
        searchButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        leaveManagement = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        header.setBackground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  MotorPH   ");

        logoutButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout-final.png"))); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.setContentAreaFilled(false);
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

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutButton)
                .addGap(35, 35, 35))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(logoutButton)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        navigation.setBackground(new java.awt.Color(102, 102, 102));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/headphones-6-16.png"))); // NOI18N
        jButton1.setText("Need some help?");
        jButton1.setAlignmentY(0.0F);
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setInheritsPopupMenu(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        profilePictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        profilePictureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user (3).png"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("NAVIGATION");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(127, 127, 127))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        empManagementButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empManagementButton.setForeground(new java.awt.Color(255, 255, 255));
        empManagementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/employees.png"))); // NOI18N
        empManagementButton.setText(" Employees Section");
        empManagementButton.setContentAreaFilled(false);
        empManagementButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        empManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empManagementButtonActionPerformed(evt);
            }
        });

        payrollButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        payrollButton.setForeground(new java.awt.Color(255, 255, 255));
        payrollButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/wallet-16.png"))); // NOI18N
        payrollButton.setText(" Payroll");
        payrollButton.setContentAreaFilled(false);
        payrollButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        payrollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payrollButtonActionPerformed(evt);
            }
        });

        leaveManagementButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        leaveManagementButton.setForeground(new java.awt.Color(255, 255, 255));
        leaveManagementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/text-file-4-16.png"))); // NOI18N
        leaveManagementButton.setText(" Leave Management");
        leaveManagementButton.setContentAreaFilled(false);
        leaveManagementButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        leaveManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveManagementButtonActionPerformed(evt);
            }
        });

        attendanceButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        attendanceButton.setForeground(new java.awt.Color(255, 255, 255));
        attendanceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar-9-16.png"))); // NOI18N
        attendanceButton.setText(" Attendance");
        attendanceButton.setContentAreaFilled(false);
        attendanceButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        attendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendanceButtonActionPerformed(evt);
            }
        });

        profileButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-16.png"))); // NOI18N
        profileButton.setText(" Profile");
        profileButton.setContentAreaFilled(false);
        profileButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileButtonActionPerformed(evt);
            }
        });

        usernameLabel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usernameLabel.setText("sample");

        fullNameValue2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        fullNameValue2.setForeground(new java.awt.Color(255, 255, 255));
        fullNameValue2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fullNameValue2.setText("jLabel3");

        empNumValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        empNumValue.setForeground(new java.awt.Color(255, 255, 255));
        empNumValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        empNumValue.setText("empNum");

        javax.swing.GroupLayout navigationLayout = new javax.swing.GroupLayout(navigation);
        navigation.setLayout(navigationLayout);
        navigationLayout.setHorizontalGroup(
            navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(attendanceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(payrollButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(empManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(leaveManagementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(navigationLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(profilePictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(empNumValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fullNameValue2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navigationLayout.setVerticalGroup(
            navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigationLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navigationLayout.createSequentialGroup()
                        .addComponent(fullNameValue2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameLabel)
                        .addGap(5, 5, 5)
                        .addComponent(empNumValue))
                    .addComponent(profilePictureLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(profileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attendanceButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payrollButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(empManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leaveManagementButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 530, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        navigatorSplitPane.setLeftComponent(navigation);

        mphCards.setLayout(new java.awt.CardLayout());

        profile.setBackground(new java.awt.Color(229, 229, 229));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        birthdayLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        birthdayLabel.setText("Birthday");

        addressLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        addressLabelValue.setText("Address");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/128px user.png"))); // NOI18N

        sssLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        sssLabel.setText("SSS #");

        phoneNumberLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        phoneNumberLabel.setText("Phone #");

        phealthLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        phealthLabel.setText("Philhealth #");

        tinLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tinLabel.setText("TIN #");

        pagibigLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        pagibigLabel.setText("Pag-ibig #");

        empIDLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        empIDLabelValue.setText("EMP ID");

        positionLabelValue.setBackground(new java.awt.Color(255, 255, 255));
        positionLabelValue.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        positionLabelValue.setText("Position");

        supervisorLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        supervisorLabel.setText("Supervisor");

        fullNameValue.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        fullNameValue.setText("Name");

        riceSubsidyLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        riceSubsidyLabel.setText("Rice Subsidy");

        phoneAllowanceLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        phoneAllowanceLabel.setText("Phone Allow.");

        basicSalaryLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        basicSalaryLabel.setText("Basic Salary");

        clothingLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        clothingLabel.setText("Clothing Allow.");

        phoneLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        phoneLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phoneLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        riceLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        riceLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        riceLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        supervisorLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        supervisorLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        supervisorLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        basicSalaryLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        basicSalaryLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        basicSalaryLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        bdayLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        bdayLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bdayLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        philhealthLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        philhealthLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        philhealthLabelValue.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        philhealthLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        sssLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        sssLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sssLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        tinLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tinLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tinLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        pagibigLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        pagibigLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pagibigLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        clothingLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        clothingLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        clothingLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        phoneAllowanceValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        phoneAllowanceValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        phoneAllowanceValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        hourlyrateLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        hourlyrateLabel.setText("Hourly Rate");

        hourlyrateLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        hourlyrateLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hourlyrateLabelValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image-removebg-preview (3) (1)_1.png"))); // NOI18N

        statusLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        statusLabel.setText("Status");

        statusLabelValue.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(statusLabel))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supervisorLabel)
                            .addComponent(phoneNumberLabel)
                            .addComponent(birthdayLabel)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tinLabel)
                            .addComponent(sssLabel)
                            .addComponent(phealthLabel)
                            .addComponent(basicSalaryLabel)
                            .addComponent(hourlyrateLabel)
                            .addComponent(phoneAllowanceLabel)
                            .addComponent(clothingLabel)
                            .addComponent(riceSubsidyLabel)
                            .addComponent(pagibigLabel))))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tinLabelValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clothingLabelValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(phoneAllowanceValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(hourlyrateLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(riceLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(186, 186, 186))
                            .addComponent(basicSalaryLabelValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addComponent(jLabel31)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressLabelValue)
                            .addComponent(fullNameValue)
                            .addComponent(positionLabelValue)
                            .addComponent(empIDLabelValue)
                            .addComponent(statusLabelValue)
                            .addComponent(phoneLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bdayLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(philhealthLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supervisorLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sssLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pagibigLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel6))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(fullNameValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressLabelValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(positionLabelValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(empIDLabelValue)))
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabel)
                    .addComponent(statusLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberLabel)
                    .addComponent(phoneLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supervisorLabel)
                    .addComponent(supervisorLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthdayLabel)
                    .addComponent(bdayLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tinLabel)
                    .addComponent(tinLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sssLabel)
                    .addComponent(sssLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagibigLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagibigLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phealthLabel)
                    .addComponent(philhealthLabelValue))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(basicSalaryLabel)
                    .addComponent(basicSalaryLabelValue))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hourlyrateLabel)
                            .addComponent(hourlyrateLabelValue))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneAllowanceValue)
                            .addComponent(phoneAllowanceLabel))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clothingLabel)
                            .addComponent(clothingLabelValue))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(riceSubsidyLabel)
                            .addComponent(riceLabelValue))
                        .addContainerGap(67, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addGap(23, 23, 23))))
        );

        profileLabel.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        profileLabel.setText("My Profile");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel2.setText("Change Password");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel17.setText("Existing Password:");

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("New Password:");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("Confirm Password:");

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Submit");

        passwordSUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordSUpActionPerformed(evt);
            }
        });

        passwordSUp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordSUp1ActionPerformed(evt);
            }
        });

        passwordSUp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordSUp2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel17)
                    .addComponent(jLabel2)
                    .addComponent(passwordSUp1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(passwordSUp2)
                    .addComponent(passwordSUp))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordSUp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(passwordSUp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(passwordSUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton2)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout profileLayout = new javax.swing.GroupLayout(profile);
        profile.setLayout(profileLayout);
        profileLayout.setHorizontalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileLabel)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        profileLayout.setVerticalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(profileLabel)
                .addGap(18, 18, 18)
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        mphCards.add(profile, "card3");

        attendance.setBackground(new java.awt.Color(229, 229, 229));

        attendanceLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        attendanceLabel1.setText("Attendance Tracker");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select Month---", "August", "September", "October", "November" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Day", "Time-in", "Time-out", "Time Worked"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select Year--", "2023", "2024", "2025" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout attendanceLayout = new javax.swing.GroupLayout(attendance);
        attendance.setLayout(attendanceLayout);
        attendanceLayout.setHorizontalGroup(
            attendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceLayout.createSequentialGroup()
                .addGroup(attendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(attendanceLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(attendanceLabel1))
                    .addGroup(attendanceLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        attendanceLayout.setVerticalGroup(
            attendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(attendanceLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        mphCards.add(attendance, "card2");

        payroll.setBackground(new java.awt.Color(229, 229, 229));

        salarySlips.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        salarySlips.setText("Payroll");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1186, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 672, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout payrollLayout = new javax.swing.GroupLayout(payroll);
        payroll.setLayout(payrollLayout);
        payrollLayout.setHorizontalGroup(
            payrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(payrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salarySlips))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        payrollLayout.setVerticalGroup(
            payrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payrollLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(salarySlips)
                .addGap(47, 47, 47)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        mphCards.add(payroll, "card4");

        empManagement.setBackground(new java.awt.Color(229, 229, 229));

        empSectionLabel.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        empSectionLabel.setText("Employees Section");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        addButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(255, 51, 51));
        deleteButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        empFirstNameLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empFirstNameLabel.setText("First Name");

        empLastNameLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empLastNameLabel.setText("Last Name");

        empPositionLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empPositionLabel.setText("Position");

        empAddressLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empAddressLabel.setText("Address");

        addressTField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        addressTField.setToolTipText("");
        addressTField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        addressTField.setMaximumSize(new java.awt.Dimension(64, 22));
        addressTField.setName(""); // NOI18N

        empDetailsLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        empDetailsLabel.setText("Employee Details");

        clearButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        empBirthdayLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empBirthdayLabel.setText("Birthday");

        empPhoneLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empPhoneLabel.setText("Phone #");

        empSalaryLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empSalaryLabel.setText("Basic Salary");

        empHourlyLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empHourlyLabel.setText("Hourly Rate");

        riceTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riceTFieldActionPerformed(evt);
            }
        });

        govIdsLabel.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
        govIdsLabel.setText("Government IDs");

        empSssLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empSssLabel.setText("SSS #");

        empTinLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empTinLabel.setText("TIN #");

        empPagibigLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empPagibigLabel.setText("PAG-IBIG #");

        empPhilhealthLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empPhilhealthLabel.setText("PhilHealth #");

        sssTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sssTFieldActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        employeeTable.setBackground(new java.awt.Color(222, 222, 222));
        employeeTable.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Emp. ID", "Last Name", "First Name", "SSS", "PhilHealth", "TIN", "PAG-IBIG"
            }
        ));
        employeeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(employeeTable);

        viewAllButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        viewAllButton.setText("View All Employees");
        viewAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewAllButton)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(viewAllButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        salaryDetailsLabel.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
        salaryDetailsLabel.setText("Salary Details");

        empBiMonthLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empBiMonthLabel.setText("Bi-Monthly");

        empRiceLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empRiceLabel.setText("Rice Subsidy");

        empPhoneAllowLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empPhoneAllowLabel.setText("Phone Allow.");

        empClothAllowLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empClothAllowLabel.setText("Clothing Allow.");

        empStatusLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empStatusLabel.setText("Status");

        empSupervisorLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        empSupervisorLabel.setText("Supervisor");

        clothingTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clothingTFieldActionPerformed(evt);
            }
        });

        positionDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionDropdownActionPerformed(evt);
            }
        });

        statusDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusDropdownActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Employee ID");

        employeeIDTField.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Username");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Password");

        passwordTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addGap(12, 12, 12)
                        .addComponent(updateButton)
                        .addGap(22, 22, 22)
                        .addComponent(deleteButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(empPagibigLabel)
                                    .addComponent(empTinLabel)
                                    .addComponent(empPhilhealthLabel))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(empSssLabel)
                                    .addGap(48, 48, 48)))
                            .addGap(25, 25, 25)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                    .addComponent(sssTField, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2))
                                .addComponent(tinTField, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pagibigTField, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(philhealthTField)))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(empSalaryLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(salaryTField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(empHourlyLabel)
                                        .addComponent(empBiMonthLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(hourlyTField)
                                        .addComponent(biMonthlyTField))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(empPhoneAllowLabel)
                                        .addComponent(empRiceLabel))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(riceTField)
                                        .addComponent(phoneAllowTField)))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(empClothAllowLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(clothingTField))))
                        .addComponent(govIdsLabel)
                        .addComponent(salaryDetailsLabel)
                        .addComponent(empDetailsLabel)
                        .addComponent(jLabel5)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(empLastNameLabel)
                                .addComponent(empAddressLabel)
                                .addComponent(empBirthdayLabel)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(empPositionLabel)
                                    .addComponent(empPhoneLabel))
                                .addComponent(jLabel3)
                                .addComponent(empSupervisorLabel))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addComponent(birthdayTField, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(empStatusLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(statusDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(phoneTField))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(addressTField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lastNameTField, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                            .addComponent(usernameTField))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(empFirstNameLabel)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(firstNameTField, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                            .addComponent(passwordTField)))
                                    .addComponent(clearButton))
                                .addComponent(supervisorDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(employeeIDTField)
                                .addComponent(positionDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(empDetailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(employeeIDTField)
                    .addComponent(clearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(usernameTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empLastNameLabel)
                    .addComponent(lastNameTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empFirstNameLabel)
                    .addComponent(firstNameTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empAddressLabel)
                    .addComponent(addressTField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empBirthdayLabel)
                    .addComponent(birthdayTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empStatusLabel)
                    .addComponent(statusDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empPhoneLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positionDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empPositionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empSupervisorLabel)
                    .addComponent(supervisorDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(govIdsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(empPagibigLabel)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sssTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empSssLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tinTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empTinLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pagibigTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(philhealthTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empPhilhealthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salaryDetailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empSalaryLabel)
                    .addComponent(salaryTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empRiceLabel)
                    .addComponent(riceTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empPhoneAllowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourlyTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneAllowTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empHourlyLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empBiMonthLabel)
                    .addComponent(empClothAllowLabel)
                    .addComponent(clothingTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(biMonthlyTField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(updateButton)
                    .addComponent(addButton))
                .addGap(59, 59, 59))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        searchButton.setBackground(new java.awt.Color(0, 0, 153));
        searchButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchTextField.setForeground(new java.awt.Color(153, 153, 153));
        searchTextField.setText("Enter the Employee ID here...");
        searchTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout empManagementLayout = new javax.swing.GroupLayout(empManagement);
        empManagement.setLayout(empManagementLayout);
        empManagementLayout.setHorizontalGroup(
            empManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, empManagementLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(empManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(empManagementLayout.createSequentialGroup()
                        .addComponent(empSectionLabel)
                        .addGap(631, 631, 631)
                        .addComponent(searchTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton)))
                .addGap(39, 39, 39))
        );
        empManagementLayout.setVerticalGroup(
            empManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empManagementLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(empManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empSectionLabel)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addGap(20, 20, 20)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        mphCards.add(empManagement, "card5");

        leaveManagement.setBackground(new java.awt.Color(229, 229, 229));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel28.setText("Leave request");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel28)
                .addGap(47, 679, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel28)
                .addContainerGap(529, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout leaveManagementLayout = new javax.swing.GroupLayout(leaveManagement);
        leaveManagement.setLayout(leaveManagementLayout);
        leaveManagementLayout.setHorizontalGroup(
            leaveManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leaveManagementLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
        );
        leaveManagementLayout.setVerticalGroup(
            leaveManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leaveManagementLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        mphCards.add(leaveManagement, "card6");

        navigatorSplitPane.setRightComponent(mphCards);

        javax.swing.GroupLayout motorphdashLayout = new javax.swing.GroupLayout(motorphdash);
        motorphdash.setLayout(motorphdashLayout);
        motorphdashLayout.setHorizontalGroup(
            motorphdashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(navigatorSplitPane)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        motorphdashLayout.setVerticalGroup(
            motorphdashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, motorphdashLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(navigatorSplitPane))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motorphdash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motorphdash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
    cardLayout.show(mphCards, "card3");        // TODO add your handling code here:
    }//GEN-LAST:event_profileButtonActionPerformed

    private void attendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceButtonActionPerformed
    cardLayout.show(mphCards, "card2"); 
    }//GEN-LAST:event_attendanceButtonActionPerformed

    private void leaveManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveManagementButtonActionPerformed
    cardLayout.show(mphCards, "card6"); 
    }//GEN-LAST:event_leaveManagementButtonActionPerformed

    private void payrollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payrollButtonActionPerformed
    cardLayout.show(mphCards, "card4"); 
    }//GEN-LAST:event_payrollButtonActionPerformed

    private void empManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empManagementButtonActionPerformed
    cardLayout.show(mphCards, "card5"); 
    }//GEN-LAST:event_empManagementButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        EmployeeDetails empDetails = updateEmpDetailValues();
        empDetailsService.saveEmployeeDetails(empDetails);
        
        EmployeeAccount empAccount = updateEmpAccountValues();
        empAccountService.saveUserAccount(empAccount,empDetails);
        JOptionPane.showMessageDialog(null, "Account added successfully!");
        refreshTable();
    }//GEN-LAST:event_addButtonActionPerformed

    private void refreshTable(){
        viewAllButtonActionPerformed(null);
    }
    
    private void viewAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllButtonActionPerformed
        List<EmployeeDetails> allEmployee =  empDetailsService.getAllEmployee(); 
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0);
            
            for(EmployeeDetails empDetails : allEmployee) {
                Vector<Object> rowData = new Vector<>();
                rowData.add(empDetails.getEmpID());
                rowData.add(empDetails.getLastName());
                rowData.add(empDetails.getFirstName());
                rowData.add(empDetails.getEmpSSS());
                rowData.add(empDetails.getEmpPhilHealth());
                rowData.add(empDetails.getEmpTIN());
                rowData.add(empDetails. getEmpPagibig());
                model.addRow(rowData);
            }
 
    }//GEN-LAST:event_viewAllButtonActionPerformed

    private void loadEmployeeValues(int empID){
        EmployeeDetails empDetails = empDetailsService.getByEmpID(empID);
        employeeIDTField.setText(String.valueOf(empDetails.getEmpID()));
        lastNameTField.setText(empDetails.getLastName());
        firstNameTField.setText(empDetails.getFirstName());
        sssTField.setText(empDetails.getEmpSSS());
        philhealthTField.setText(String.valueOf(empDetails.getEmpPhilHealth()));
        tinTField.setText(String.valueOf(empDetails.getEmpTIN()));
        pagibigTField.setText(String.valueOf(empDetails.getEmpPagibig()));
        addressTField.setText(empDetails.getEmpAddress());
        birthdayTField.setText(empDetails.getFormattedBirthday());
        phoneTField.setText(String.valueOf(empDetails.getEmpPhoneNumber()));
        salaryTField.setText(String.valueOf(empDetails. getEmpBasicSalary()));
        hourlyTField.setText(String.valueOf(empDetails.getEmpHourlyRate()));
        biMonthlyTField.setText(String.valueOf(empDetails.getEmpMonthlyRate()));
        riceTField.setText(String.valueOf(empDetails.getEmpRice()));
        phoneAllowTField.setText(String.valueOf(empDetails.getEmpPhone()));
        clothingTField.setText(String.valueOf(empDetails.getEmpClothing()));
        
        statusDropdown.setSelectedIndex(0);
        positionDropdown.setSelectedIndex(0);
        supervisorDropdown.setSelectedIndex(0);
        if(empDetails.getEmpStatus() != null){
            statusDropdown.setSelectedItem(new ComboItem(empDetails.getEmpStatus().getId(),empDetails.getEmpStatus().getStatus()));
        }
        if(empDetails.getEmpPosition() != null){
            positionDropdown.setSelectedItem(new ComboItem(empDetails.getEmpPosition().getId(),empDetails.getEmpPosition().getPosition()));
        }
        if(empDetails.getEmpImmediateSupervisor() != null){
            supervisorDropdown.setSelectedItem(new ComboItem(empDetails.getEmpImmediateSupervisor().getEmpID(),empDetails.getEmpImmediateSupervisor().getFormattedName()));
        }
        
        EmployeeAccount empAccount = empAccountService.getByEmpID(empID);
        usernameTField.setText(empAccount.getEmpUserName());
        passwordTField.setText(empAccount.getEmpPassword());
        
        
    }
    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked
           
        DefaultTableModel model  = (DefaultTableModel) employeeTable.getModel();
        int selectedIndex = employeeTable.getSelectedRow();
        int empID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        loadEmployeeValues(empID);
        
    }//GEN-LAST:event_employeeTableMouseClicked

    private EmployeeDetails updateEmpDetailValues(){
        String lastname = lastNameTField.getText().trim() !=null ? lastNameTField.getText() : "";
        String firstname= firstNameTField.getText().trim()!=null ?  firstNameTField.getText().trim() : "";
        //String birthday = birthdayTField.getText().trim()) !=null ? Date(birthdayTField.getText().trim()) : "";
        String address = addressTField.getText().trim() !=null ? addressTField.getText().trim():"";
        double salary = !salaryTField.getText().trim().equals("") ? Double.parseDouble(salaryTField.getText().trim()) : 0;
        String phoneNumber = phoneTField.getText().trim() !=null ? phoneTField.getText().trim() :"";
        double hourlyRate = !hourlyTField.getText().trim().equals("") ? Double.parseDouble(hourlyTField.getText().trim()) : 0;
        double biMonthly = !biMonthlyTField.getText().trim().equals("") ?  Double.parseDouble(biMonthlyTField.getText().trim()): 0;
        double rice = !riceTField.getText().trim().equals("") ? Double.parseDouble(riceTField.getText().trim()): 0;
        double phoneAllow= !phoneAllowTField.getText().trim().equals("") ? Double.parseDouble(phoneAllowTField.getText().trim()): 0;
        double clothing = !clothingTField.getText().trim().equals("")? Double.parseDouble(clothingTField.getText().trim()): 0;
        long pagibig = !pagibigTField.getText().trim().equals("")? Long.valueOf(pagibigTField.getText().trim()): 0;
        String sss = sssTField.getText().trim() !=null ? sssTField.getText().trim() :"";
        String tin = tinTField.getText().trim() !=null ? tinTField.getText().trim():"";
        long philhealth = !philhealthTField.getText().equals("") ? Long.valueOf(philhealthTField.getText().trim()): 0;
    
        
        EmployeeDetails empDetails = new EmployeeDetails();
        
        empDetails.setLastName(lastname);
        empDetails.setFirstName(firstname);
        try {
            // Parsing the string to java.util.Date
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayTField.getText().trim());

            // Converting java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            // Setting the sqlDate to empDetails
            empDetails.setEmpBirthday(sqlDate);
        } catch (ParseException ex) {
            //JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd");
            // Exit the method if the date format is invalid
        //empDetails.setEmpBirthday(firstNameTField.getText().trim());
        }
        if(!employeeIDTField.getText().trim().equals("")){
            empDetails.setEmpID(Integer.parseInt(employeeIDTField.getText().trim()));
        }
        
        empDetails.setEmpAddress(address);
        empDetails.setEmpBasicSalary(salary);
        empDetails.setEmpPhoneNumber(phoneNumber);
        empDetails.setEmpHourlyRate(hourlyRate);
        empDetails.setEmpMonthlyRate(biMonthly); 
        empDetails.setEmpRice(rice);
        empDetails.setEmpPhone(phoneAllow);
        empDetails.setEmpClothing(clothing);
        empDetails.setEmpPagibig(pagibig);
        empDetails.setEmpSSS(sss);
        empDetails.setEmpTIN(tin);
        empDetails.setEmpPhilHealth(philhealth);
        
        ComboItem positionValue = (ComboItem) positionDropdown.getSelectedItem();
        ComboItem statusValue = (ComboItem) statusDropdown.getSelectedItem();
        ComboItem supervisorValue = (ComboItem) supervisorDropdown.getSelectedItem();
        
        if(positionValue.getKey() != null){
            empDetails.setEmpPosition(empDetailsService.getPositionById(positionValue.getKey()));
        }
        if(statusValue.getKey() != null){
            empDetails.setEmpStatus(empDetailsService.getStatusById(statusValue.getKey()));
        }
        if(supervisorValue.getKey() !=null){
            empDetails.setEmpImmediateSupervisor(empDetailsService.getByEmpID(supervisorValue.getKey()));  
        }

        return empDetails;
    }
    private EmployeeAccount updateEmpAccountValues(){
        EmployeeAccount empAccount = new EmployeeAccount();
        empAccount.setEmpUserName(usernameTField.getText());
        empAccount.setEmpPassword(passwordTField.getText());
        return empAccount;
    }
    
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        EmployeeDetails empDetails = updateEmpDetailValues();
        empDetailsService.updateEmployeeDetails(empDetails); 
        
        EmployeeAccount empAccount = updateEmpAccountValues();
        empAccount.setEmpID(empDetails.getEmpID());
        empAccount.setEmpDetails(empDetails);
        empAccountService.updateEmployeeAccount(empAccount);
        refreshTable();
        if(getEmpAccount().getEmpDetails().getEmpID() == empAccount.getEmpID()){
          updateUserLabels(empAccount);
          JOptionPane.showMessageDialog(null, "Employee has been successfully updated");
        }   
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
       DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        int selectedIndex = employeeTable.getSelectedRow();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
        return;
    }

        int empID = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            empAccountService.deleteEmpAccount(empID);
            empDetailsService.deleteEmpDetails(empID);
            JOptionPane.showMessageDialog(this, "Employee successfully deleted");
            
        clearButtonActionPerformed(null);
        refreshTable();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed

        lastNameTField.setText("");
        firstNameTField.setText("");
        addressTField.setText("");
        birthdayTField.setText("");
        phoneTField.setText("");
        sssTField.setText("");
        tinTField.setText("");
        pagibigTField.setText("");
        philhealthTField.setText("");
        salaryTField.setText("");
        hourlyTField.setText("");
        riceTField.setText("");
        phoneAllowTField.setText("");
        clothingTField.setText("");
        usernameTField.setText("");
        passwordTField.setText("");
        biMonthlyTField.setText("");
        employeeIDTField.setText("");
        statusDropdown.setSelectedIndex(0);
        positionDropdown.setSelectedIndex(0);
        supervisorDropdown.setSelectedIndex(0);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        int empID = Integer.parseInt(searchTextField.getText().trim());
        loadEmployeeValues(empID); 
        refreshTable(); 
        
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (empID == Integer.parseInt(model.getValueAt(i, 0).toString())) {
                employeeTable.setRowSelectionInterval(i, i);
                employeeTable.scrollRectToVisible(employeeTable.getCellRect(i, 0, true));
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(this, "Employee Not Found!");
            clearButtonActionPerformed(evt); 
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void passwordSUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordSUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordSUpActionPerformed

    private void passwordSUp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordSUp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordSUp1ActionPerformed

    private void passwordSUp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordSUp2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordSUp2ActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        JOptionPane.showMessageDialog(null, "You will now be redirected to the login page.");
            LogIn info = new LogIn();
                    info.setVisible(true);
                    this.dispose(); 
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
             // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void sssTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sssTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sssTFieldActionPerformed

    private void clothingTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clothingTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clothingTFieldActionPerformed

    private void positionDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionDropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_positionDropdownActionPerformed

    private void riceTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riceTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_riceTFieldActionPerformed

    private void searchTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusGained
        if (searchTextField.getText().equals("Enter the Employee ID here...")) {
        searchTextField.setText("");
        searchTextField.setForeground(Color.BLACK);
    }
    }//GEN-LAST:event_searchTextFieldFocusGained

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusLost
        if (searchTextField.getText().isEmpty()) {
        searchTextField.setForeground(Color.GRAY);
        searchTextField.setText("Enter the Employee ID here...");
    }
    }//GEN-LAST:event_searchTextFieldFocusLost

    private void statusDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusDropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusDropdownActionPerformed

    private void passwordTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTFieldActionPerformed

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
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel addressLabelValue;
    private javax.swing.JTextField addressTField;
    private javax.swing.JPanel attendance;
    private javax.swing.JButton attendanceButton;
    private javax.swing.JLabel attendanceLabel1;
    private javax.swing.JLabel basicSalaryLabel;
    private javax.swing.JLabel basicSalaryLabelValue;
    private javax.swing.JLabel bdayLabelValue;
    private javax.swing.JTextField biMonthlyTField;
    private javax.swing.JLabel birthdayLabel;
    private javax.swing.JTextField birthdayTField;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel clothingLabel;
    private javax.swing.JLabel clothingLabelValue;
    private javax.swing.JTextField clothingTField;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel empAddressLabel;
    private javax.swing.JLabel empBiMonthLabel;
    private javax.swing.JLabel empBirthdayLabel;
    private javax.swing.JLabel empClothAllowLabel;
    private javax.swing.JLabel empDetailsLabel;
    private javax.swing.JLabel empFirstNameLabel;
    private javax.swing.JLabel empHourlyLabel;
    private javax.swing.JLabel empIDLabelValue;
    private javax.swing.JLabel empLastNameLabel;
    private javax.swing.JPanel empManagement;
    private javax.swing.JButton empManagementButton;
    private javax.swing.JLabel empNumValue;
    private javax.swing.JLabel empPagibigLabel;
    private javax.swing.JLabel empPhilhealthLabel;
    private javax.swing.JLabel empPhoneAllowLabel;
    private javax.swing.JLabel empPhoneLabel;
    private javax.swing.JLabel empPositionLabel;
    private javax.swing.JLabel empRiceLabel;
    private javax.swing.JLabel empSalaryLabel;
    private javax.swing.JLabel empSectionLabel;
    private javax.swing.JLabel empSssLabel;
    private javax.swing.JLabel empStatusLabel;
    private javax.swing.JLabel empSupervisorLabel;
    private javax.swing.JLabel empTinLabel;
    private javax.swing.JLabel employeeIDTField;
    private javax.swing.JTable employeeTable;
    private javax.swing.JTextField firstNameTField;
    private javax.swing.JLabel fullNameValue;
    private javax.swing.JLabel fullNameValue2;
    private javax.swing.JLabel govIdsLabel;
    private javax.swing.JPanel header;
    private javax.swing.JTextField hourlyTField;
    private javax.swing.JLabel hourlyrateLabel;
    private javax.swing.JLabel hourlyrateLabelValue;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lastNameTField;
    private javax.swing.JPanel leaveManagement;
    private javax.swing.JButton leaveManagementButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel motorphdash;
    private javax.swing.JPanel mphCards;
    private javax.swing.JPanel navigation;
    private javax.swing.JSplitPane navigatorSplitPane;
    private javax.swing.JLabel pagibigLabel;
    private javax.swing.JLabel pagibigLabelValue;
    private javax.swing.JTextField pagibigTField;
    private javax.swing.JPasswordField passwordSUp;
    private javax.swing.JPasswordField passwordSUp1;
    private javax.swing.JPasswordField passwordSUp2;
    private javax.swing.JPasswordField passwordTField;
    private javax.swing.JPanel payroll;
    private javax.swing.JButton payrollButton;
    private javax.swing.JLabel phealthLabel;
    private javax.swing.JLabel philhealthLabelValue;
    private javax.swing.JTextField philhealthTField;
    private javax.swing.JTextField phoneAllowTField;
    private javax.swing.JLabel phoneAllowanceLabel;
    private javax.swing.JLabel phoneAllowanceValue;
    private javax.swing.JLabel phoneLabelValue;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneTField;
    private javax.swing.JComboBox<ComboItem> positionDropdown;
    private javax.swing.JLabel positionLabelValue;
    private javax.swing.JPanel profile;
    private javax.swing.JButton profileButton;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JLabel profilePictureLabel;
    private javax.swing.JLabel riceLabelValue;
    private javax.swing.JLabel riceSubsidyLabel;
    private javax.swing.JTextField riceTField;
    private javax.swing.JLabel salaryDetailsLabel;
    private javax.swing.JLabel salarySlips;
    private javax.swing.JTextField salaryTField;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel sssLabel;
    private javax.swing.JLabel sssLabelValue;
    private javax.swing.JTextField sssTField;
    private javax.swing.JComboBox<ComboItem> statusDropdown;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel statusLabelValue;
    private javax.swing.JComboBox<ComboItem> supervisorDropdown;
    private javax.swing.JLabel supervisorLabel;
    private javax.swing.JLabel supervisorLabelValue;
    private javax.swing.JLabel tinLabel;
    private javax.swing.JLabel tinLabelValue;
    private javax.swing.JTextField tinTField;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTField;
    private javax.swing.JButton viewAllButton;
    // End of variables declaration//GEN-END:variables
}
