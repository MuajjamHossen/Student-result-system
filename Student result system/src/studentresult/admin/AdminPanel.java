package studentresult.admin;

import studentresult.model.*;
import studentresult.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminPanel {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public void showLoginFrame() {
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
                loginFrame.dispose();
                showAdminDashboard();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
            }
        });
    }

    public void showAdminDashboard() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton addSemesterBtn = new JButton("Add Semester");
        JButton addCourseBtn = new JButton("Add Course");
        JButton addResultBtn = new JButton("Add Student Result");

        panel.add(addSemesterBtn);
        panel.add(addCourseBtn);
        panel.add(addResultBtn);

        frame.add(panel);
        frame.setVisible(true);

        addSemesterBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter Semester Name:");
            if (name != null && !name.isEmpty()) {
                Semester s = new Semester(name);
                ArrayList<Semester> semesters = FileUtils.load("semesters.txt");
                semesters.add(s);
                FileUtils.save("semesters.txt", semesters);
                JOptionPane.showMessageDialog(frame, "Semester Added.");
            }
        });

        addCourseBtn.addActionListener(e -> {
            ArrayList<Semester> semesters = FileUtils.load("semesters.txt");
            String[] semNames = semesters.stream().map(Semester::getName).toArray(String[]::new);
            JComboBox<String> semBox = new JComboBox<>(semNames);

            JTextField titleField = new JTextField();
            JTextField idField = new JTextField();
            Object[] message = {
                    "Course Title:", titleField,
                    "Course ID:", idField,
                    "Select Semester:", semBox
            };
            int option = JOptionPane.showConfirmDialog(frame, message, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String title = titleField.getText();
                String id = idField.getText();
                String semester = (String) semBox.getSelectedItem();
                if (!title.isEmpty() && !id.isEmpty()) {
                    Course c = new Course(id, title, semester);
                    ArrayList<Course> courses = FileUtils.load("courses.txt");
                    courses.add(c);
                    FileUtils.save("courses.txt", courses);
                    JOptionPane.showMessageDialog(frame, "Course Added.");
                }
            }
        });

        addResultBtn.addActionListener(e -> {
            ArrayList<Semester> semesters = FileUtils.load("semesters.txt");
            String[] semNames = semesters.stream().map(Semester::getName).toArray(String[]::new);
            JComboBox<String> semBox = new JComboBox<>(semNames);

            JTextField idField = new JTextField();
            Object[] msg = {
                    "Student ID:", idField,
                    "Select Semester:", semBox
            };

            int op = JOptionPane.showConfirmDialog(frame, msg, "Add Result", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                String studentId = idField.getText();
                String selectedSemester = (String) semBox.getSelectedItem();

                ArrayList<Course> allCourses = FileUtils.load("courses.txt");
                ArrayList<Course> filtered = (ArrayList<Course>) allCourses.stream()
                        .filter(c -> selectedSemester.equals(c.getSemester()))
                        .collect(Collectors.toList());

                if (filtered.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No courses found for this semester.");
                    return;
                }

                String[] courseTitles = filtered.stream().map(Course::getCourseTitle).toArray(String[]::new);
                JComboBox<String> courseBox = new JComboBox<>(courseTitles);
                JTextField marksField = new JTextField();

                Object[] input = {
                        "Select Course:", courseBox,
                        "Enter Marks:", marksField
                };
                int res = JOptionPane.showConfirmDialog(frame, input, "Course Marks", JOptionPane.OK_CANCEL_OPTION);
                if (res == JOptionPane.OK_OPTION) {
                    String selectedTitle = (String) courseBox.getSelectedItem();
                    Course selectedCourse = filtered.stream()
                            .filter(c -> selectedTitle.equals(c.getCourseTitle()))
                            .findFirst().orElse(null);

                    if (selectedCourse != null) {
                        try {
                            double marks = Double.parseDouble(marksField.getText());
                            double gpa = calculateGPA(marks);
                            Result r = new Result(studentId, selectedSemester, selectedCourse.getCourseId(), marks);
                            ArrayList<Result> results = FileUtils.load("results.txt");
                            results.add(r);
                            FileUtils.save("results.txt", results);
                            JOptionPane.showMessageDialog(frame, "Result Added.");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid Marks.");
                        }
                    }
                }
            }
        });
    }

    private double calculateGPA(double marks) {
        if (marks >= 80) return 4.0;
        else if (marks >= 75) return 3.75;
        else if (marks >= 70) return 3.5;
        else if (marks >= 65) return 3.25;
        else if (marks >= 60) return 3.0;
        else if (marks >= 55) return 2.75;
        else if (marks >= 50) return 2.5;
        else if (marks >= 45) return 2.25;
        else if (marks >= 40) return 2.0;
        else return 0.0;
    }
}
