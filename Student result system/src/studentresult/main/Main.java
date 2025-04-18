package studentresult.main;

import studentresult.admin.AdminPanel;
import studentresult.student.StudentView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Admin", "Student"};
        int choice = JOptionPane.showOptionDialog(null, "Login As:", "Student Result Management", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            // Show Admin Login
            new AdminPanel().showLoginFrame();
        } else if (choice == 1) {
            // Show Student View
            new StudentView().showStudentPanel();
        }
    }
}
