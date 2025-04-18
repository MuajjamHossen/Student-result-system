package studentresult.student;

import studentresult.model.*;
import studentresult.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentView {
    public void showStudentPanel() {
        JFrame frame = new JFrame("Student Result");
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel semLabel = new JLabel("Semester:");

        ArrayList<Semester> semesters = FileUtils.load("semesters.txt");
        String[] semNames = semesters.stream().map(Semester::getName).toArray(String[]::new);
        JComboBox<String> semBox = new JComboBox<>(semNames);

        JButton viewBtn = new JButton("View Result");

        panel.add(idLabel);
        panel.add(idField);
        panel.add(semLabel);
        panel.add(semBox);
        panel.add(new JLabel());
        panel.add(viewBtn);

        frame.add(panel);
        frame.setVisible(true);

        viewBtn.addActionListener(e -> {
            String id = idField.getText();
            String sem = (String) semBox.getSelectedItem();

            ArrayList<Result> results = FileUtils.load("results.txt");
            ArrayList<Course> courses = FileUtils.load("courses.txt");

            StringBuilder output = new StringBuilder();
            double totalGPA = 0;
            int count = 0;

            for (Result r : results) {
                if (r.getStudentId().equals(id) && r.getSemester().equals(sem)) {
                    String courseTitle = courses.stream()
                            .filter(c -> c.getCourseId().equals(r.getCourseId()))
                            .map(Course::getCourseTitle)
                            .findFirst().orElse("Unknown");

                    output.append("Course: ").append(courseTitle)
                            .append(" | Marks: ").append(r.getMarks())
                            .append(" | GPA: ").append(r.getGPA()).append("\n");

                    totalGPA += r.getGPA();
                    count++;
                }
            }

            if (count == 0) {
                output.append("No results found for this student in this semester.");
            } else {
                double cgpa = totalGPA / count;
                output.append("\nTotal GPA: ").append(cgpa);
            }

            JTextArea textArea = new JTextArea(output.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(frame, scrollPane, "Student Result", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
