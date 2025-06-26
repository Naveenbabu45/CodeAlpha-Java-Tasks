// Import necessary Swing and AWT classes for GUI components and event handling
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Import ArrayList for dynamic data storage
import java.util.ArrayList;
import java.util.Collections; // For finding min/max in ArrayList

/**
 * Represents a single student with their name and a list of grades.
 * Provides methods to calculate average, highest, and lowest grades for this student.
 */
class Student {
    private String name; // The name of the student
    private ArrayList<Double> grades; // List of grades for the student

    /**
     * Constructor for the Student class.
     * @param name The name of the student.
     * @param grades An ArrayList of Double representing the student's grades.
     */
    public Student(String name, ArrayList<Double> grades) {
        this.name = name;
        this.grades = grades;
    }

    /**
     * Gets the name of the student.
     * @return The student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of grades for the student.
     * @return An ArrayList of Double representing the student's grades.
     */
    public ArrayList<Double> getGrades() {
        return grades;
    }

    /**
     * Calculates the average of the student's grades.
     * @return The average grade, or 0.0 if there are no grades.
     */
    public double calculateAverage() {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    /**
     * Finds the highest grade among the student's grades.
     * @return The highest grade, or 0.0 if there are no grades.
     */
    public double findHighest() {
        if (grades == null || grades.isEmpty()) {
            return 0.0; // Or throw an exception, depending on desired behavior
        }
        return Collections.max(grades);
    }

    /**
     * Finds the lowest grade among the student's grades.
     * @return The lowest grade, or 0.0 if there are no grades.
     */
    public double findLowest() {
        if (grades == null || grades.isEmpty()) {
            return 0.0; // Or throw an exception
        }
        return Collections.min(grades);
    }
}

/**
 * The main GUI class for the Student Grade Manager application.
 * Extends JFrame to create the main window.
 */
public class GradeManagerGUI extends JFrame {

    // --- GUI Components ---
    private JTextField studentNameField; // Text field for student's name
    private JTextField gradesField;      // Text field for student's grades (comma-separated)
    private JTextArea reportArea;        // Text area to display the summary report
    private JButton addStudentButton;    // Button to add a student
    private JButton generateReportButton; // Button to generate the report

    // --- Data Storage ---
    private ArrayList<Student> students; // List to store all Student objects

    /**
     * Constructor for the GradeManagerGUI.
     * Initializes the GUI components and sets up the layout and event listeners.
     */
    public GradeManagerGUI() {
        // Set window properties
        setTitle("Student Grade Manager");
        setSize(800, 600); // Set initial window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLocationRelativeTo(null); // Center the window on the screen

        students = new ArrayList<>(); // Initialize the list of students

        // --- Create Main Panel and set Border Layout ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // 10px gaps
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding around the panel

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns, with gaps
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student Grades")); // Add a title border

        // Student Name input
        inputPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField(20); // 20 columns wide
        inputPanel.add(studentNameField);

        // Grades input
        inputPanel.add(new JLabel("Grades (comma-separated, e.g., 85.5, 92, 78):"));
        gradesField = new JTextField(20);
        inputPanel.add(gradesField);

        // Buttons
        addStudentButton = new JButton("Add Student");
        // Add an ActionListener to the "Add Student" button
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent(); // Call the addStudent method when button is clicked
            }
        });
        inputPanel.add(addStudentButton);

        generateReportButton = new JButton("Generate Report");
        // Add an ActionListener to the "Generate Report" button
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport(); // Call the generateReport method when button is clicked
            }
        });
        inputPanel.add(generateReportButton);

        // --- Report Panel ---
        reportArea = new JTextArea(20, 50); // 20 rows, 50 columns
        reportArea.setEditable(false); // Make the text area non-editable
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use a monospaced font for better alignment
        JScrollPane scrollPane = new JScrollPane(reportArea); // Add scrollability to the report area
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Grade Report")); // Add a title border

        // --- Add panels to main frame ---
        mainPanel.add(inputPanel, BorderLayout.NORTH);  // Input panel at the top
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Report area in the center (scrollable)

        add(mainPanel); // Add the main panel to the JFrame
        setVisible(true); // Make the window visible
    }

    /**
     * Handles the logic for adding a new student to the system.
     * Retrieves data from input fields, parses grades, creates a Student object,
     * and adds it to the students list. Provides feedback to the user.
     */
    private void addStudent() {
        String name = studentNameField.getText().trim(); // Get student name and trim whitespace
        String gradesText = gradesField.getText().trim(); // Get grades string and trim whitespace

        // Input validation: Check if name is empty
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Input validation: Check if grades are empty
        if (gradesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter grades.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Double> currentGrades = new ArrayList<>();
        try {
            // Split the grades string by comma and parse each part to a double
            String[] gradeStrings = gradesText.split(",");
            for (String s : gradeStrings) {
                currentGrades.add(Double.parseDouble(s.trim())); // Parse to double and trim whitespace
            }
        } catch (NumberFormatException e) {
            // Handle cases where grade input is not a valid number
            JOptionPane.showMessageDialog(this, "Invalid grade format. Please enter numbers separated by commas.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new Student object and add it to the list
        Student student = new Student(name, currentGrades);
        students.add(student);

        // Clear input fields after successful addition
        studentNameField.setText("");
        gradesField.setText("");

        // Provide success feedback
        JOptionPane.showMessageDialog(this, "Student '" + name + "' added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Generates and displays a comprehensive report of all students' grades.
     * Calculates individual student stats and overall stats, then formats and
     * appends the information to the report JTextArea.
     */
    private void generateReport() {
        // Clear previous report content
        reportArea.setText("");

        if (students.isEmpty()) {
            reportArea.append("No students added yet. Please add students to generate a report.");
            return;
        }

        // Use a StringBuilder for efficient string concatenation when building the report
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("--- Student Grade Report ---\n\n");

        double overallSum = 0;
        double overallHighest = 0; // Initialize with a small value for finding max
        double overallLowest = Double.MAX_VALUE; // Initialize with a large value for finding min
        int totalGradesCount = 0;

        // Iterate through each student to generate their individual report section
        for (Student student : students) {
            reportBuilder.append("Student Name: ").append(student.getName()).append("\n");
            reportBuilder.append("  Grades: ").append(student.getGrades().toString()).append("\n");

            if (!student.getGrades().isEmpty()) {
                double avg = student.calculateAverage();
                double highest = student.findHighest();
                double lowest = student.findLowest();

                reportBuilder.append(String.format("  Average Score: %.2f%n", avg)); // Format to 2 decimal places
                reportBuilder.append(String.format("  Highest Score: %.2f%n", highest));
                reportBuilder.append(String.format("  Lowest Score: %.2f%n", lowest));

                // Update overall statistics
                overallSum += (avg * student.getGrades().size()); // Sum of all grades
                totalGradesCount += student.getGrades().size();

                if (highest > overallHighest) {
                    overallHighest = highest;
                }
                if (lowest < overallLowest) {
                    overallLowest = lowest;
                }
            } else {
                reportBuilder.append("  No grades entered for this student.\n");
            }
            reportBuilder.append("\n"); // Add a blank line for readability between students
        }

        // --- Overall Statistics ---
        reportBuilder.append("\n--- Overall Summary ---\n");
        if (totalGradesCount > 0) {
            reportBuilder.append(String.format("Overall Average Score: %.2f%n", overallSum / totalGradesCount));
            reportBuilder.append(String.format("Overall Highest Score: %.2f%n", overallHighest));
            reportBuilder.append(String.format("Overall Lowest Score: %.2f%n", overallLowest));
        } else {
            reportBuilder.append("No grades available to calculate overall summary.\n");
        }

        // Set the generated report text to the JTextArea
        reportArea.setText(reportBuilder.toString());
    }

    /**
     * Main method to run the application.
     * Ensures the GUI is created and updated on the Event Dispatch Thread (EDT).
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GradeManagerGUI(); // Create and run the GUI
            }
        });
    }
}
