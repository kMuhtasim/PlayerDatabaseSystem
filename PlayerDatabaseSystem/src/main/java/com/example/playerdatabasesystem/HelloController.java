package com.example.playerdatabasesystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML private TextField playerName;
    @FXML private TableView<Player> searchByNameTable;
    @FXML private TableColumn<Player,String> searchByNameName;
    @FXML private TableColumn<Player,String> searchByNameCountry;
    @FXML private TableColumn<Player,String> searchByNameAge;
    @FXML private TableColumn<Player,String> searchByNameHeight;
    @FXML private TableColumn<Player,String> searchByNameClub;
    @FXML private TableColumn<Player,String> searchByNamePosition;
    @FXML private TableColumn<Player,String> searchByNameJersey;
    @FXML private TableColumn<Player,String> searchByNameSalary;
    @FXML
    protected void onSearchByNameButtonClick() {
        textFieldToTitleCase(playerName);
        searchByNameTable.getItems().clear();
        List<Player> playerList = Main.playerList.filterByName(playerName.getText());
        if (playerList == null || playerList.isEmpty()) {return;}
        Main.displayPlayers(playerList, searchByNameTable, searchByNameName, searchByNameCountry, searchByNameAge, searchByNameHeight, searchByNameClub, searchByNamePosition, searchByNameJersey, searchByNameSalary);
//        Player player = Main.playerList.searchByName(playerName.getText());
//        searchByNameTable.getItems().clear();
//        Main.displayPlayer(player, searchByNameTable, searchByNameName,searchByNameCountry,searchByNameAge,searchByNameHeight,searchByNameClub,searchByNamePosition,searchByNameJersey,searchByNameSalary);
    }

    @FXML private TextField playerCountry;
    @FXML private TextField playerClub;
    @FXML private TableView<Player> searchByCCTable;
    @FXML private TableColumn<Player,String> searchByCCName;
    @FXML private TableColumn<Player,String> searchByCCCountry;
    @FXML private TableColumn<Player,String> searchByCCAge;
    @FXML private TableColumn<Player,String> searchByCCHeight;
    @FXML private TableColumn<Player,String> searchByCCClub;
    @FXML private TableColumn<Player,String> searchByCCPosition;
    @FXML private TableColumn<Player,String> searchByCCJersey;
    @FXML private TableColumn<Player,String> searchByCCSalary;
    @FXML
    protected void onSearchByCCButtonClick() {
        textFieldToTitleCase(playerCountry);
        textFieldToTitleCase(playerClub);
        java.util.List<Player> playerList = Main.playerList.searchByClub(playerCountry.getText(),playerClub.getText());
        searchByCCTable.getItems().clear();
        if (playerList != null && !playerList.isEmpty()) {
            Main.displayPlayers(playerList, searchByCCTable, searchByCCName, searchByCCCountry, searchByCCAge, searchByCCHeight, searchByCCClub, searchByCCPosition, searchByCCJersey, searchByCCSalary);
        }
    }

    @FXML private TextField playerPosition;
    @FXML private TableView<Player> searchByPositionTable;
    @FXML private TableColumn<Player,String> searchByPositionName;
    @FXML private TableColumn<Player,String> searchByPositionCountry;
    @FXML private TableColumn<Player,String> searchByPositionAge;
    @FXML private TableColumn<Player,String> searchByPositionHeight;
    @FXML private TableColumn<Player,String> searchByPositionClub;
    @FXML private TableColumn<Player,String> searchByPositionPosition;
    @FXML private TableColumn<Player,String> searchByPositionJersey;
    @FXML private TableColumn<Player,String> searchByPositionSalary;
    @FXML
    protected void onSearchByPositionButtonClick() {
        textFieldToTitleCase(playerPosition);
        java.util.List<Player> playerList = Main.playerList.searchByPosition(playerPosition.getText());
        searchByPositionTable.getItems().clear();
        if (playerList != null && !playerList.isEmpty()) {
            Main.displayPlayers(playerList, searchByPositionTable, searchByPositionName, searchByPositionCountry, searchByPositionAge, searchByPositionHeight, searchByPositionClub, searchByPositionPosition, searchByPositionJersey, searchByPositionSalary);
        }
    }

    @FXML private TextField lowerLimit;
    @FXML private TextField upperLimit;
    @FXML private TableView<Player> searchBySalaryRangeTable;
    @FXML private TableColumn<Player,String> searchBySalaryRangeName;
    @FXML private TableColumn<Player,String> searchBySalaryRangeCountry;
    @FXML private TableColumn<Player,String> searchBySalaryRangeAge;
    @FXML private TableColumn<Player,String> searchBySalaryRangeHeight;
    @FXML private TableColumn<Player,String> searchBySalaryRangeClub;
    @FXML private TableColumn<Player,String> searchBySalaryRangePosition;
    @FXML private TableColumn<Player,String> searchBySalaryRangeJersey;
    @FXML private TableColumn<Player,String> searchBySalaryRangeSalary;
    @FXML
    protected void onSearchBySalaryRangeButtonClick() {
        try {
            java.util.List<Player> playerList = Main.playerList.searchBySalaryRange(Integer.parseInt(lowerLimit.getText()), Integer.parseInt(upperLimit.getText()));
            searchBySalaryRangeTable.getItems().clear();
            if (playerList != null && !playerList.isEmpty()) {
                Main.displayPlayers(playerList, searchBySalaryRangeTable, searchBySalaryRangeName, searchBySalaryRangeCountry, searchBySalaryRangeAge, searchBySalaryRangeHeight, searchBySalaryRangeClub, searchBySalaryRangePosition, searchBySalaryRangeJersey, searchBySalaryRangeSalary);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML TableView<java.util.Map.Entry<String,Integer>> countryWiseCountTable;
    @FXML TableColumn<java.util.Map.Entry<String,Integer>,String> countryWiseCountCountry;
    @FXML TableColumn<java.util.Map.Entry<String,Integer>,String> countryWiseCountCount;
    @FXML
    protected void onCountryWiseCount() {
        java.util.Map<String,Integer> countryWiseMap = Main.playerList.countryWiseCount();
        countryWiseCountTable.getItems().clear();
        for (java.util.Map.Entry<String,Integer> x : countryWiseMap.entrySet()) {
            countryWiseCountTable.getItems().add(x);
            countryWiseCountCountry.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<java.util.Map.Entry<String,Integer>, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<java.util.Map.Entry<String,Integer>, String> p) {
                    return new SimpleStringProperty(p.getValue().getKey());
                }
            });
            countryWiseCountCount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<java.util.Map.Entry<String,Integer>, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<java.util.Map.Entry<String,Integer>, String> p) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getValue()));
                }
            });
        }
    }

    @FXML private TextField clubName;
    @FXML private TableView<Player> searchByClubSalaryTable;
    @FXML private TableColumn<Player,String> searchByClubSalaryName;
    @FXML private TableColumn<Player,String> searchByClubSalaryCountry;
    @FXML private TableColumn<Player,String> searchByClubSalaryAge;
    @FXML private TableColumn<Player,String> searchByClubSalaryHeight;
    @FXML private TableColumn<Player,String> searchByClubSalaryClub;
    @FXML private TableColumn<Player,String> searchByClubSalaryPosition;
    @FXML private TableColumn<Player,String> searchByClubSalaryJersey;
    @FXML private TableColumn<Player,String> searchByClubSalarySalary;

    @FXML private TableView<Player> searchByClubAgeTable;
    @FXML private TableColumn<Player,String> searchByClubAgeName;
    @FXML private TableColumn<Player,String> searchByClubAgeCountry;
    @FXML private TableColumn<Player,String> searchByClubAgeAge;
    @FXML private TableColumn<Player,String> searchByClubAgeHeight;
    @FXML private TableColumn<Player,String> searchByClubAgeClub;
    @FXML private TableColumn<Player,String> searchByClubAgePosition;
    @FXML private TableColumn<Player,String> searchByClubAgeJersey;
    @FXML private TableColumn<Player,String> searchByClubAgeSalary;

    @FXML private TableView<Player> searchByClubHeightTable;
    @FXML private TableColumn<Player,String> searchByClubHeightName;
    @FXML private TableColumn<Player,String> searchByClubHeightCountry;
    @FXML private TableColumn<Player,String> searchByClubHeightAge;
    @FXML private TableColumn<Player,String> searchByClubHeightHeight;
    @FXML private TableColumn<Player,String> searchByClubHeightClub;
    @FXML private TableColumn<Player,String> searchByClubHeightPosition;
    @FXML private TableColumn<Player,String> searchByClubHeightJersey;
    @FXML private TableColumn<Player,String> searchByClubHeightSalary;

    @FXML private Label totalYearlySalaryLabel;

    @FXML
    protected void onClubNameEntered() {
        if (clubName.getText().isEmpty()) {
            searchByClubSalaryTable.setPlaceholder(new Label("Please enter a club name"));
            searchByClubAgeTable.setPlaceholder(new Label("Please enter a club name"));
            searchByClubHeightTable.setPlaceholder(new Label("Please enter a club name"));
        } else {
            searchByClubSalaryTable.setPlaceholder(new Label("No player found"));
            searchByClubAgeTable.setPlaceholder(new Label("No player found"));
            searchByClubHeightTable.setPlaceholder(new Label("No player found"));
        }
        textFieldToTitleCase(clubName);
        java.util.List<Player> playerList = Main.playerList.richestInClub(clubName.getText());
        searchByClubSalaryTable.getItems().clear();
        if (playerList != null && !playerList.isEmpty()) {
            Main.displayPlayers(playerList, searchByClubSalaryTable, searchByClubSalaryName, searchByClubSalaryCountry, searchByClubSalaryAge, searchByClubSalaryHeight, searchByClubSalaryClub, searchByClubSalaryPosition, searchByClubSalaryJersey, searchByClubSalarySalary);
        }
        playerList = Main.playerList.eldestInClub(clubName.getText());
        searchByClubAgeTable.getItems().clear();
        if (playerList != null && !playerList.isEmpty()) {
            Main.displayPlayers(playerList, searchByClubAgeTable, searchByClubAgeName, searchByClubAgeCountry, searchByClubAgeAge, searchByClubAgeHeight, searchByClubAgeClub, searchByClubAgePosition, searchByClubAgeJersey, searchByClubAgeSalary);
        }
        playerList = Main.playerList.tallestInClub(clubName.getText());
        searchByClubHeightTable.getItems().clear();
        if (playerList != null && !playerList.isEmpty()) {
            Main.displayPlayers(playerList, searchByClubHeightTable, searchByClubHeightName, searchByClubHeightCountry, searchByClubHeightAge, searchByClubHeightHeight, searchByClubHeightClub, searchByClubHeightPosition, searchByClubHeightJersey, searchByClubHeightSalary);
        }
        java.text.NumberFormat yearlySalaryFormat = new java.text.DecimalFormat("#0");
        double totalYearlySalary = Main.playerList.totalYearlySalaryOfClub(clubName.getText());
        String totalYearlySalaryString = yearlySalaryFormat.format(totalYearlySalary);
        if (clubName.getText().isEmpty()) {
            totalYearlySalaryLabel.setText("Please enter a club name");
        } else if (totalYearlySalary == 0 || totalYearlySalary == Double.MIN_VALUE) {
            totalYearlySalaryLabel.setText("No player found");
        } else {
            totalYearlySalaryLabel.setText("Total yearly salary of " + Main.toTitleCase(clubName.getText().toLowerCase()) + " is: " + totalYearlySalaryString);
        }
    }

    @FXML TextField nameAdd;
    @FXML TextField countryAdd;
    @FXML TextField ageAdd;
    @FXML TextField heightAdd;
    @FXML TextField clubAdd;
    @FXML TextField positionAdd;
    @FXML TextField jerseyAdd;
    @FXML TextField salaryAdd;

    @FXML
    protected void onAddPlayerButtonClick() throws IOException, ClassNotFoundException {
        textFieldToTitleCase(nameAdd);
        textFieldToTitleCase(countryAdd);
        textFieldToTitleCase(clubAdd);
        textFieldToTitleCase(positionAdd);
        textFieldToTitleCase(jerseyAdd);
        String errorMessage = "";
        if (Main.playerList.searchByName(nameAdd.getText()) != null) {
            errorMessage = errorMessage + "- Player with entered name already exists.\n";
        }
        if (nameAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Name' cannot be empty.\n";
        }
        if (countryAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Country' cannot be empty.\n";
        }
        if (ageAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Age' cannot be empty.\n";
        } else if (!isInteger(ageAdd.getText()) || Integer.parseInt(ageAdd.getText()) <= 0) {
            errorMessage = errorMessage + "- Invalid age.\n";
        }
        if (heightAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Height' cannot be empty.\n";
        } else if (!isDouble(heightAdd.getText()) || Double.parseDouble(heightAdd.getText()) <= 0) {
            errorMessage = errorMessage + "- Invalid height.\n";
        }
        if (clubAdd.getText().isEmpty()) {
            errorMessage += errorMessage + "- 'Club' cannot be empty.\n";
        }
        if (positionAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Position' cannot be empty.\n";
        } else if (!Main.validPositions.contains(positionAdd.getText())) {
            errorMessage = errorMessage + "- Invalid position.\n";
        }
        if (!jerseyAdd.getText().isEmpty() && (!isInteger(jerseyAdd.getText()) || Integer.parseInt(jerseyAdd.getText()) <= 0)) {
            errorMessage = errorMessage + "- Invalid jersey number.\n";
        }
        if (salaryAdd.getText().isEmpty()) {
            errorMessage = errorMessage + "- 'Salary' cannot be empty.\n";
        } else if (!isInteger(salaryAdd.getText()) || Integer.parseInt(salaryAdd.getText()) <= 0) {
            errorMessage = errorMessage + "- Invalid salary.\n";
        }

        if (!errorMessage.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid input");
            errorAlert.setContentText(errorMessage);
            errorAlert.showAndWait();
        } else {
            Player playerToAdd = new Player(nameAdd.getText(), countryAdd.getText(), ageAdd.getText(), heightAdd.getText(), clubAdd.getText(), positionAdd.getText(), jerseyAdd.getText(), salaryAdd.getText());
            Main.playerList.add(playerToAdd);
            SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1", 22222);
            socketWrapper.write("playerToAdd");
            socketWrapper.write(playerToAdd);
            socketWrapper.read();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Done");
            Label successLabel = new Label("Player added successfully");
            successLabel.setStyle("-fx-text-fill: green;");
            successAlert.getDialogPane().setContent(successLabel);
            successAlert.setWidth(300);
            successAlert.showAndWait();
        }
    }

    static Stage stage;
    @FXML void openLoginWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage = HelloApplication.stage;
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // helper methods below
    private void textFieldToTitleCase(TextField textField) {
        int caretPosition = textField.getCaretPosition();
        textField.setText(Main.toTitleCase(textField.getText().toLowerCase()));
        textField.positionCaret(caretPosition);
    }
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}