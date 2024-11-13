package com.example.kachuendavidlee_comp228lab4;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Exercise1Controller implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField provinceField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private CheckBox studentCouncil;
    @FXML
    private CheckBox volunteerWork;
    @FXML
    private ToggleGroup majorGroup;
    @FXML
    private RadioButton csRadio;
    @FXML
    private RadioButton businessRadio;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    private ListView<String> courseListView;
    @FXML
    private Button displayButton;
    @FXML
    private Button resetButton;
    @FXML
    private TextArea displayArea;


    private Lab4Exercise1 model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Lab4Exercise1();
        setupEventHandlers();
    }

    private void initializeCourseComboBox() {
        // Initialize with empty list
        courseComboBox.setItems(FXCollections.observableArrayList());
    }

    private void setupEventHandlers() {
        csRadio.setOnAction(e -> {
            courseComboBox.setItems(FXCollections.observableArrayList(
                    "Java", "Python", "C#", "JavaScript"
            ));
        });

        businessRadio.setOnAction(e -> {
            courseComboBox.setItems(FXCollections.observableArrayList(
                    "Economics", "Marketing", "Management", "Finance"
            ));
        });

        courseComboBox.setOnAction(e -> {
            String selectedCourse = courseComboBox.getValue();
            if (selectedCourse != null && !courseListView.getItems().contains(selectedCourse)) {
                courseListView.getItems().add(selectedCourse);
            }
        });
    }

    @FXML
    private void handleDisplay() {
        try {
            // run validation first
            validateFields();
            StringBuilder info = new StringBuilder();

            // Build up the string for display the information
            info.append("Student Information:\n");
            info.append("Name: ").append(nameField.getText()).append("\n");
            info.append("Address: ").append(addressField.getText()).append("\n");
            info.append("City: ").append(cityField.getText()).append("\n");
            info.append("Province: ").append(provinceField.getText()).append("\n");
            info.append("Postal Code: ").append(postalCodeField.getText()).append("\n");
            info.append("Phone: ").append(phoneField.getText()).append("\n");
            info.append("Email: ").append(emailField.getText()).append("\n\n");

            // Major selected
            info.append("Major: ");
            if (majorGroup.getSelectedToggle() != null) {
                info.append(((RadioButton) majorGroup.getSelectedToggle()).getText());
            }
            info.append("\n\n");
            // Courses
            info.append("Selected Courses:\n");
            if (!courseListView.getItems().isEmpty()) {
                courseListView.getItems().forEach(course ->
                        info.append("- ").append(course).append("\n")
                );
            } else {
                info.append("No courses selected\n");
            }
            info.append("\n");

            // Activities
            info.append("Activities:\n");
            if (studentCouncil.isSelected() || volunteerWork.isSelected()) {
                if (studentCouncil.isSelected()) info.append("- Student Council\n");
                if (volunteerWork.isSelected()) info.append("- Volunteer Work\n");
            } else {
                info.append("No activities selected\n");
            }

            displayArea.setText(info.toString());

        } catch (IllegalArgumentException e) {
            showAlert("Validation Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private void validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        // Check user input is not empty
        if (nameField.getText().trim().isEmpty()) errorMessage.append("Name is required\n");
        if (addressField.getText().trim().isEmpty()) errorMessage.append("Address is required\n");
        if (provinceField.getText().trim().isEmpty()) errorMessage.append("Province is required\n");
        if (cityField.getText().trim().isEmpty()) errorMessage.append("City is required\n");
        if (postalCodeField.getText().trim().isEmpty()) errorMessage.append("Postal Code is required\n");
        if (phoneField.getText().trim().isEmpty()) errorMessage.append("Phone Number is required\n");
        if (emailField.getText().trim().isEmpty()) errorMessage.append("Email is required\n");

        // Check major selection
        if (majorGroup.getSelectedToggle() == null) {
            errorMessage.append("Please select a major\n");
        }

        // Display error message if errorMessage is not empty
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleReset() {
        try {
            // Clear all fields
            nameField.clear();
            addressField.clear();
            provinceField.clear();
            cityField.clear();
            postalCodeField.clear();
            phoneField.clear();
            emailField.clear();

            // Reset selections
            studentCouncil.setSelected(false);
            volunteerWork.setSelected(false);
            majorGroup.selectToggle(null);

            // Clear course selections
            courseComboBox.getItems().clear();
            courseComboBox.setValue(null);
            courseListView.getItems().clear();
            displayArea.clear();

        } catch (Exception e) {
            showAlert("Error", "Error while resetting fields: " + e.getMessage());
        }
    }
}