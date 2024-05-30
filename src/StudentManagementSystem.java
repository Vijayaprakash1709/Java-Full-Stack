import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentManagementSystem extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextArea displayArea;

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout setup
        setLayout(new BorderLayout());

        // Top panel for input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        JButton addButton = new JButton("Add Student");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Center panel for display
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Bottom panel for action buttons
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Students");
        actionPanel.add(viewButton);
        JButton deleteButton = new JButton("Delete Student");
        actionPanel.add(deleteButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        viewButton.addActionListener(e -> viewStudents());
        deleteButton.addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());

        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "root";
        String password = "";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO students (name, age) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student.");
        }
    }

    private void viewStudents() {
        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "root";
        String password = "";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            displayArea.setText("");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                displayArea.append("ID: " + id + ", Name: " + name + ", Age: " + age + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving students.");
        }
    }

    private void deleteStudent() {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to delete:");

        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "root";
        String password = "";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM students WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystem frame = new StudentManagementSystem();
            frame.setVisible(true);
        });
    }
}

