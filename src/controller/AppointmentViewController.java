package controller;

import dbaccess.DBAAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Appointment View Screen. Included are methods that display appointments in a table view. Navigation to an add and update form is included as well as navigation back to the Main Menu. Included also is a method for deleting a selected appointment.
 */
public class AppointmentViewController implements Initializable {

    @FXML
    private RadioButton allAppointmentsRBtn;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentContactIdCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdCol;

    @FXML
    private RadioButton appointmentsByMonthRBtn;

    @FXML
    private RadioButton appointmentsByWeekRBtn;

    /**
     * Initialize method to populate the Appointments TableView, style the TableView, and set the default Radio Button that displays all appointments to be selected.
     * @param url The url
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentsTableView.setStyle("-fx-selection-bar: #c2bff8");
        allAppointmentsRBtn.setStyle("-fx-selected-color: #c2bff8");
        appointmentsByMonthRBtn.setStyle("-fx-selected-color: #c2bff8");
        appointmentsByWeekRBtn.setStyle("-fx-selected-color: #c2bff8");
        allAppointmentsRBtn.setSelected(true);

        ObservableList<Appointment> allAppointments = DBAAppointment.getAllAppointments();

        displayAppointments(allAppointments);
        //test
        //System.out.println(allAppointments);

    }
    /**
     * Method for exiting the Appointment View and returning to the Main Menu
     * @param event The event of clicking on the Back button.
     * @throws Exception exception
     */
    @FXML
    void onActionBackToMainMenu(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for deleting an appointment that has been selected on the TableView. Gives user a confirmation message prompt. Appointments TableView will repopulate based on the deletion.
     * @param event The event of clicking the Delete Appointment button while having an appointment selected.
     */
    @FXML
    void onActionDeleteSelectedAppointment(ActionEvent event) {

        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();

            return;
        }

        //confirm choice
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("You are about to cancel Appointment with ID " + selectedAppointment.getApptId() +".");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();

        //confirm deletion
        if (result.orElseThrow() == ButtonType.OK){
            DBAAppointment.deleteAppointment(selectedAppointment.getApptId());
            Alert alert2 = new Alert((Alert.AlertType.CONFIRMATION));
            alert2.setTitle("Appointment Cancelled");
            alert2.setContentText("Appointment with ID: " + selectedAppointment.getApptId() + " and Type: " + selectedAppointment.getApptType() + " has been cancelled.");
            alert2.showAndWait();
        } else {
            alert.close();
        }

        if (allAppointmentsRBtn.isSelected()) {
            allAppointmentsRBtn.setSelected(true);

            ObservableList<Appointment> allAppointments = DBAAppointment.getAllAppointments();

            displayAppointments(allAppointments);
        }
        else if (appointmentsByMonthRBtn.isSelected()){
            appointmentsByMonthRBtn.setSelected(true);

            ObservableList<Appointment> allAppointmentsByMonth = DBAAppointment.getAppointmentsByDateRangeMonth();

            displayAppointments(allAppointmentsByMonth);
        }
        else if (appointmentsByWeekRBtn.isSelected()){
            appointmentsByWeekRBtn.setSelected(true);

            ObservableList<Appointment> allAppointmentsByWeek = DBAAppointment.getAppointmentsByDateRangeWeek();

            displayAppointments(allAppointmentsByWeek);
        }
    }

    /**
     * Method used to display appointments. Used in each Action Event for radio buttons.
     * @param appointmentsList The list of appointments to display
     */
    public void displayAppointments(ObservableList<Appointment> appointmentsList) {

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndDateTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("apptUserId"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("apptContactId"));

        appointmentsTableView.setItems(appointmentsList);
    }

    /**
     * The method to display all appointments in the TableView
     * @param event The event of selecting the All Appointments radio button.
     */
    @FXML
    void onActionDisplayAllAppointments(ActionEvent event) {

        allAppointmentsRBtn.setSelected(true);

        ObservableList<Appointment> allAppointments = DBAAppointment.getAllAppointments();

        displayAppointments(allAppointments);
    }

    /**
     * The method to display all appointments for the current month in the Appointments TableView
     * @param event The event of selecting the Appointments By Month radio button.
     */
    @FXML
    void onActionDisplayAppointmentsByMonth(ActionEvent event) {

        appointmentsByMonthRBtn.setSelected(true);

        ObservableList<Appointment> allAppointmentsByMonth = DBAAppointment.getAppointmentsByDateRangeMonth();

        displayAppointments(allAppointmentsByMonth);
    }

    /**
     * Method to display all appointments for the current week in the Appointments TableView.
     * @param event The event of selecting the Appointments By Week radio button.
     */
    @FXML
    void onActionDisplayAppointmentsByWeek(ActionEvent event) {

        appointmentsByWeekRBtn.setSelected(true);

        ObservableList<Appointment> allAppointmentsByWeek = DBAAppointment.getAppointmentsByDateRangeWeek();

        displayAppointments(allAppointmentsByWeek);
    }

    /**
     * Method for navigating to the Add Appointment Screen.
     * @param event The event of clicking the Add Appointment button.
     * @throws Exception exception
     */
    @FXML
    void onActionToAddAppointmentScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for navigating to the Update Appointment Screen when an appointment in the TableView is selected. Show error message for no selected appointment.
     * @param event The event of clicking the Update Appointment button.
     * @throws Exception exception
     */
    @FXML
    void onActionToUpdateAppointmentScreen(ActionEvent event) throws Exception {

        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no appointment selected. Please select an appointment.");
            alert.showAndWait();

            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController UAController = loader.getController();
        //test
        // System.out.println("selectedAppointment: " + selectedAppointment);

        UAController.sendAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Update Appointment");
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
