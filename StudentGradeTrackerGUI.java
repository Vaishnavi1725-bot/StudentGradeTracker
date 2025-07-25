import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Student {
    String name;
    int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private java.util.List<Student> students;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker - CodeAlpha");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        students = new ArrayList<>();

        // Top Panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        inputPanel.add(addButton);

        JButton calculateButton = new JButton("Show Report");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Add Button Action
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (name.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
                return;
            }

            try {
                int grade = Integer.parseInt(gradeText);
                students.add(new Student(name, grade));
                outputArea.append(name + " added with grade " + grade + "\n");
                nameField.setText("");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Grade must be a number.");
            }
        });

        // Calculate and Display Report
        calculateButton.addActionListener(e -> displayReport());
    }

    private void displayReport() {
        if (students.isEmpty()) {
            outputArea.setText("No students added.\n");
            return;
        }

        students.sort((a, b) -> b.grade - a.grade);

        int total = 0;
        int highest = students.get(0).grade;
        int lowest = students.get(students.size() - 1).grade;

        StringBuilder report = new StringBuilder("--- Sorted Summary Report ---\n");
        for (Student s : students) {
            report.append(s.name)
                  .append(" - ")
                  .append(s.grade)
                  .append(" (")
                  .append(getGradeCategory(s.grade))
                  .append(")\n");
            total += s.grade;
        }

        double average = (double) total / students.size();

        report.append("\nAverage Grade: ").append(String.format("%.2f", average)).append("\n");
        report.append("Highest Grade: ").append(highest).append("\n");
        report.append("Lowest Grade: ").append(lowest).append("\n");

        outputArea.setText(report.toString());
    }

    private String getGradeCategory(int grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}
