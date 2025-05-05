package com.example.playerdatabasesystem;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Main {
    static java.util.Scanner scanner;
    static PlayerList playerList;
    static java.util.List<String> validPositions = new java.util.ArrayList<String>(4);
    private static final String INPUT_FILE_NAME = "src/main/java/com/example/playerdatabasesystem/players.txt";
    private static final String OUTPUT_FILE_NAME = "src/main/java/com/example/playerdatabasesystem/players.txt";
    public static void loadPlayers() throws Exception {
        playerList = new PlayerList();
        if (validPositions.isEmpty()) {
            validPositions.add("Batsman");
            validPositions.add("Bowler");
            validPositions.add("Wicketkeeper");
            validPositions.add("Allrounder");
        }
//        scanner = new java.util.Scanner(System.in);
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) break;
            line = line.trim();
            String[] splitted = line.split(",");
            Player player = new Player(splitted[0],splitted[1],splitted[2],splitted[3],splitted[4],splitted[5],splitted[6],splitted[7]);
            playerList.add(player);
        }
        bufferedReader.close();
//        while (displayMainMenu()) {
//            continue;
//        }
//        scanner.close();
    }
    public static void writePlayerData() throws Exception {
        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(OUTPUT_FILE_NAME));
        java.util.List<Player> players = playerList.getList();
        for (int i = 0; i < players.size(); ++i) {
            bufferedWriter.write(players.get(i).toString());
            if (i < players.size()-1) bufferedWriter.write(System.lineSeparator());
        }
        bufferedWriter.close();
    }
    private static boolean displayMainMenu() {
        display(true, "Main Menu:");
        display(true, "(1) Search Players");
        display(true, "(2) Search Clubs");
        display(true, "(3) Add Player");
        display(true, "(4) Exit System");
        int input = takeIntInput("Invalid input, please enter a number within 1-4.");
        switch (input) {
            case 1:
                while (searchPlayers()) {
                    
                }
                break;
        
            case 2:
                while (searchClubs()) {
                    
                }
                break;
        
            case 3:
                addPlayer();
                break;
        
            case 4:
                return false;
        
            default:
                display(true, "Invalid input, please enter a number within 1-4.");
        }
        return true;
    }
    private static boolean searchPlayers() {
        display(true, "Player Searching Options:");
        display(true, "(1) By Player Name");
        display(true, "(2) By Club and Country");
        display(true, "(3) By Position");
        display(true, "(4) By Salary Range");
        display(true, "(5) Country-wise Player Count");
        display(true, "(6) Back to Main Menu");
        int input = takeIntInput("Invalid input, please enter a number within 1-6.");
        java.util.List<Player> players;
        switch (input) {
            case 1:
                display(false, "Name: ");
                String name = takeNextLineInput(true);
                Player player = playerList.searchByName(name);
                if (player == null) {
                    display(true, "No such player with this name.");
                } else {
                    display(true, player.toString());
                }
                break;
        
            case 2:
                display(false, "Country: ");
                String country = takeNextLineInput(true);
                display(false, "Club: ");
                String club = takeNextLineInput(true);
                players = playerList.searchByClub(country, club);
                if (players == null) {
                    display(true, "No such player with this country and club.");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 3:
                display(false, "Position: ");
                String position = takeNextLineInput(true);
                players = playerList.searchByPosition(position);
                if (players == null) {
                    display(true, "No such player with this position.");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 4:
                display(false, "Upper Limit: ");
                double upperLimit = takeDoubleInput("Invalid input, please try again.");
                display(false, "Upper Limit: ");
                double lowerLimit = takeDoubleInput("Invalid input, please try again.");
                players = playerList.searchBySalaryRange(lowerLimit, upperLimit);
                if (players == null) {
                    display(true, "No such player with this weekly salary range.");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 5:
                java.util.Map<String, Integer> countryWiseMap = playerList.countryWiseCount();
                for (java.util.Map.Entry<String, Integer> x : countryWiseMap.entrySet()) {
                    if (x.getValue() == 1) {
                        display(true, x.getValue() + " player from " + x.getKey());
                    } else {
                        display(true, x.getValue() + " players from " + x.getKey());
                    }
                }
                break;
        
            case 6:
                return false;
        
            default:
                display(true, "Invalid input, please enter a number within 1-6.");
        }
        return true;
    }
    private static boolean searchClubs() {
        display(true, "Club Searching Options:");
        display(true, "(1) Player(s) with maximum salary of a club");
        display(true, "(2) Player(s) with maximum age of a club");
        display(true, "(3) Player(s) with maximum height of a club");
        display(true, "(4) Total yearly salary of a club");
        display(true, "(5) Back to Main Menu");
        int input = takeIntInput("Invalid input, please enter a number within 1-5.");
        java.util.List<Player> players;
        String club;
        switch (input) {
            case 1:
                display(false, "Club: ");
                club = takeNextLineInput();
                players = playerList.richestInClub(club);
                if (players == null) {
                    display(true, "No such club with this name");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 2:
                display(false, "Club: ");
                club = takeNextLineInput();
                players = playerList.eldestInClub(club);
                if (players == null) {
                    display(true, "No such club with this name");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 3:
                display(false, "Club: ");
                club = takeNextLineInput();
                players = playerList.tallestInClub(club);
                if (players == null) {
                    display(true, "No such club with this name");
                } else {
                    displayPlayers(players);
                }
                break;
        
            case 4:
                display(false, "Club: ");
                club = takeNextLineInput();
                double totalYearlySalary = playerList.totalYearlySalaryOfClub(club);
                if (totalYearlySalary == Double.MIN_VALUE) {
                    display(true, "No such club with this name");
                } else {
                    java.text.NumberFormat yearlySalaryFormat = new java.text.DecimalFormat("#0");
                    display(true, yearlySalaryFormat.format(totalYearlySalary));
                }
                break;
        
            case 5:
                return false;
        
            default:
                display(true, "Invalid input, please enter a number within 1-5.");
        }
        return true;
    }
    private static void addPlayer() {
        String name;
        String country;
        int age;
        double height;
        String club;
        String position;
        String jerseyNumber;
        int salary;
        display(false, "Name: ");
        name = takeNextLineInput(true);
        if (playerList.searchByName(name) != null) {
            display(true, "Player with this name already exists.");
            return;
        }
        display(false, "Country: ");
        country = toTitleCase(takeNextLineInput(true).toLowerCase());
        display(false, "Age: ");
        age = takeIntInput("Doesn't seem to be an age, please try again.");
        display(false, "Height: ");
        height = takeDoubleInput("Invalid input, please try again.");
        display(false, "Club: ");
        club = toTitleCase(takeNextLineInput(true).toLowerCase());
        display(false, "Position: ");
        position = takeNextLineInput(true);
        while (!validPositions.contains(position)) {
            display(true, "Invalid position, please enter again.");
            display(false, "Position: ");
            position = takeNextLineInput(true);
        }
        display(false, "Jersey Number: ");
        jerseyNumber = takeNextLineInput();
        display(false, "Weekly Salary: ");
        salary = takeIntInput("Doesn't seem to be a reasonable weekly salary, please try again.");
        playerList.add(new Player(name, country, age, height, club, position, jerseyNumber, salary));
    }
    public static void displayPlayers(java.util.List<Player> players) {
        for (Player p:players) {
            display(true, p.toString());
        }
    }
    public static void displayPlayers(java.util.List<Player> players, TableView table, TableColumn nameColumn, TableColumn countryColumn, TableColumn ageColumn, TableColumn heightColumn, TableColumn clubColumn, TableColumn positionColumn, TableColumn jerseyColumn, TableColumn salaryColumn) {
        if (players != null && !players.isEmpty()) {
            table.getItems().addAll(players);
            nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().nameProperty();
                }
            });
            countryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().countryProperty();
                }
            });
            ageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().ageProperty();
                }
            });
            heightColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().heightProperty();
                }
            });
            clubColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().clubProperty();
                }
            });
            positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().positionProperty();
                }
            });
            jerseyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().jerseyProperty();
                }
            });
            salaryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().salaryProperty();
                }
            });
        }
    }
    public static void displayPlayer(Player player) {
        if (player == null) {
            display(true, "No player found!");
        } else {
            display(true, player.toString());
        }
    }
    public static void displayPlayer(Player player, TableView table, TableColumn nameColumn, TableColumn countryColumn, TableColumn ageColumn, TableColumn heightColumn, TableColumn clubColumn, TableColumn positionColumn, TableColumn jerseyColumn, TableColumn salaryColumn) {
        if (player == null) {
            display(true, "No player found!");
        } else {
            table.getItems().add(player);
            nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().nameProperty();
                }
            });
            countryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().countryProperty();
                }
            });
            ageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().ageProperty();
                }
            });
            heightColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().heightProperty();
                }
            });
            clubColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().clubProperty();
                }
            });
            positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().positionProperty();
                }
            });
            jerseyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().jerseyProperty();
                }
            });
            salaryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                    return p.getValue().salaryProperty();
                }
            });
        }
    }
    public static int takeIntInput(String errorMessage) {
        int input = 0;
        try {
            input = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            display(true, errorMessage);
            return takeIntInput(errorMessage);
        }
        return input;
    }
    public static double takeDoubleInput(String errorMessage) {
        double input = 0;
        try {
            input = scanner.nextDouble();
        } catch (Exception e) {
            scanner.nextLine();
            display(true, errorMessage);
            return takeDoubleInput(errorMessage);
        }
        scanner.nextLine();
        return input;
    }
    public static String takeNextLineInput() {
        String input = scanner.nextLine().trim();
        return input;
    }
    public static String takeNextLineInput(boolean strict) {
        String input = scanner.nextLine().trim();
        if (strict) {
            while (input.equals("")) {
                input = scanner.nextLine().trim();
            }
        }
        return input;
    }
    public static void display(boolean insertNewLine, String s) {
        if (insertNewLine == true) {
            System.out.println(s);
        } else {
            System.out.print(s);
        }
    }
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
