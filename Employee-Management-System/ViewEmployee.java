package ems;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    JComboBox<String> cemployeeId;
    JButton search, print, update, back;

    ViewEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);

        cemployeeId = new JComboBox<>();
        cemployeeId.setBounds(180, 20, 150, 25);
        add(cemployeeId);

        loadEmployeeIds();

        // Initially load all data
        loadEmployeeData(null);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 25);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 25);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 25);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 70, 80, 25);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private void loadEmployeeIds() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empId FROM employee");
            while (rs.next()) {
                cemployeeId.addItem(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeData(String empId) {
        String query;
        if (empId == null) {
            query = "SELECT * FROM employee";
        } else {
            query = "SELECT * FROM employee WHERE empId = '" + empId + "'";
        }

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);

            // Define column headers exactly matching your employee table columns
            String[] columnNames = { "Name", "Father's Name", "DOB", "Salary", "Address", "Phone",
                    "Email", "Education", "Designation", "Aadhar", "EmpId" };

            // Temporarily store rows as list of Object arrays
            ArrayList<Object[]> rows = new ArrayList<>();

            while (rs.next()) {
                Object[] row = new Object[columnNames.length];
                row[0] = rs.getString("name");
                row[1] = rs.getString("fname");
                row[2] = rs.getString("dob");
                row[3] = rs.getString("salary");
                row[4] = rs.getString("address");
                row[5] = rs.getString("phone");
                row[6] = rs.getString("email");
                row[7] = rs.getString("education");
                row[8] = rs.getString("designation");
                row[9] = rs.getString("aadhar");
                row[10] = rs.getString("empId");
                rows.add(row);
            }

            // Convert ArrayList to 2D Object array for JTable
            Object[][] data = new Object[rows.size()][columnNames.length];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }

            // If table exists, remove old one
            if (table != null) {
                remove(table.getParent()); // remove the JScrollPane holding the table
            }

            // Create new table with new data and headers
            table = new JTable(data, columnNames);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setBounds(0, 100, 900, 600);
            add(jsp);

            revalidate();
            repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String selectedEmpId = (String) cemployeeId.getSelectedItem();
            loadEmployeeData(selectedEmpId);
        } else if (ae.getSource() == print) {
            try {
                if (table != null) {
                    table.print();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            String selectedEmpId = (String) cemployeeId.getSelectedItem();
            setVisible(false);
            new UpdateEmployee(selectedEmpId);
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
