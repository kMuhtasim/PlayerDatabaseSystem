package com.example.playerdatabasesystem;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ClientWindow {
    static SocketWrapper socketWrapper;
    static PlayerList myPlayerList;
    static PlayerList availablePlayerList;

    public ClientWindow() throws Exception {
        socketWrapper = LoginWindow.socketWrapper;
        refreshPlayerList();
    }

    @FXML private TableView<Player> myTable;
    @FXML private TableColumn<Player, String> myName;
    @FXML private TableColumn<Player, String> myCountry;
    @FXML private TableColumn<Player, String> myAge;
    @FXML private TableColumn<Player, String> myHeight;
    @FXML private TableColumn<Player, String> myClub;
    @FXML private TableColumn<Player, String> myPosition;
    @FXML private TableColumn<Player, String> myJersey;
    @FXML private TableColumn<Player, String> mySalary;
    @FXML private TableColumn<Player, Void> myAction;

    @FXML private TextField filterByNameText;

    @FXML
    protected void filterByName() throws IOException, ClassNotFoundException {
        myTable.getItems().clear();
        refreshPlayerList();
        textFieldToTitleCase(filterByNameText);
        if (myPlayerList == null || myPlayerList.isEmpty()) {return;}
        List<Player> playerList = myPlayerList.filterByName(filterByNameText.getText());
        if (playerList == null || playerList.isEmpty()) {return;}
        for (Player player : playerList) {
            Main.displayPlayer(player, myTable, myName, myCountry, myAge, myHeight, myClub, myPosition, myJersey, mySalary);
            myAction.setCellFactory(column -> new TableCell<Player, Void>() {
                private final Button actionButton = new Button();
                private final HBox centeredBox = new HBox(actionButton);
                {
                    centeredBox.setAlignment(Pos.CENTER);
                    actionButton.setOnAction(event -> {
                        Player player = getTableView().getItems().get(getIndex());
                        if (player == null) return;
                        if (player.isAvailableForBuying) {
                            try {
                                socketWrapper.write(new Message("retract", player.name));
                                player.isAvailableForBuying = false;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                socketWrapper.write(new Message("sell", player.name));
                                player.isAvailableForBuying = true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    actionButton.setOnMouseReleased(event -> {
                        try {
                            filterByName();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                private void updateButtonState(Player player) {
                    if (player.isAvailableForBuying) {
                        actionButton.setText("Retract");
                    } else {
                        actionButton.setText("Sell");
                    }
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Player player = getTableView().getItems().get(getIndex());
                        if (player != null) {
                            updateButtonState(player);
                            setGraphic(centeredBox);
                        }
                    }
                }
            });
        }
    }

    @FXML private TableView<Player> myTableBuy;
    @FXML private TableColumn<Player, String> myNameBuy;
    @FXML private TableColumn<Player, String> myCountryBuy;
    @FXML private TableColumn<Player, String> myAgeBuy;
    @FXML private TableColumn<Player, String> myHeightBuy;
    @FXML private TableColumn<Player, String> myClubBuy;
    @FXML private TableColumn<Player, String> myPositionBuy;
    @FXML private TableColumn<Player, String> myJerseyBuy;
    @FXML private TableColumn<Player, String> mySalaryBuy;
    @FXML private TableColumn<Player, Void> myActionBuy;

    @FXML private TextField filterByNameTextBuy;

    @FXML
    protected void filterByNameBuy() throws IOException, ClassNotFoundException {
        myTableBuy.getItems().clear();
        socketWrapper.write(new Message("getAvailablePlayers"));
        availablePlayerList = (PlayerList) socketWrapper.read();
        if (availablePlayerList == null || availablePlayerList.isEmpty()) {return;}
        textFieldToTitleCase(filterByNameTextBuy);
        List<Player> playerList = availablePlayerList.filterByName(filterByNameText.getText());
        if (playerList == null || playerList.isEmpty()) {return;}
        for (Player player : playerList) {
            Main.displayPlayer(player, myTableBuy, myNameBuy, myCountryBuy, myAgeBuy, myHeightBuy, myClubBuy, myPositionBuy, myJerseyBuy, mySalaryBuy);
            myActionBuy.setCellFactory(column -> new TableCell<Player, Void>() {
                private final Button buyButton = new Button("Buy");
                private final HBox centeredBox = new HBox(buyButton);
                {
                    centeredBox.setAlignment(Pos.CENTER);
                    buyButton.setOnAction(event -> {
                        Player player = getTableView().getItems().get(getIndex());
                        try {
                            socketWrapper.write(new Message("buy", player.name));
                            refreshPlayerList();
                            myPlayerList.searchByName(player.name).isAvailableForBuying = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    buyButton.setOnMouseReleased(event -> {
                        try {
                            filterByNameBuy();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(centeredBox);
                    }
                }
            });
        }
    }
    @FXML
    private void exit() throws IOException {
        socketWrapper.write(new Message("exit"));
        HelloApplication.stage.close();
    }
    // helper functions below
    private void refreshPlayerList() throws IOException, ClassNotFoundException {
        socketWrapper.write(new Message("getMyPlayers"));
        myPlayerList = (PlayerList) socketWrapper.read();
    }
    private void textFieldToTitleCase(TextField textField) {
        int caretPosition = textField.getCaretPosition();
        textField.setText(Main.toTitleCase(textField.getText().toLowerCase()));
        textField.positionCaret(caretPosition);
    }
}
