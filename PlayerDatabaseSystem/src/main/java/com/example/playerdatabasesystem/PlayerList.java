package com.example.playerdatabasesystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

class Player implements Serializable {
    public String name;
    public String country;
    public int age;
    public double height;
    public String club;
    public String position;
    public String jerseyNumber;
    public int salary;

    public boolean isAvailableForBuying;

    Player(String name, String country, int age, double height, String club, String position, String jerseyNumber, int salary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.salary = salary;

        this.isAvailableForBuying = false;
    }
    Player(String name, String country, String age, String height, String club, String position, String jerseyNumber, String salary) {
        this.name = name;
        this.country = country;
        this.age = Integer.parseInt(age);
        this.height = Double.parseDouble(height);
        this.club = club;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.salary = Integer.parseInt(salary);

        this.isAvailableForBuying = false;
    }
    @Override
    public String toString() {
        return name + ',' + country + ',' + age + ',' + height + ',' + club + ',' + position + ',' + jerseyNumber + ',' + salary;
    }
    public StringProperty nameProperty() {
        return new SimpleStringProperty(this.name);
    }
    public StringProperty countryProperty() {
        return new SimpleStringProperty(this.country);
    }
    public StringProperty ageProperty() {
        return new SimpleStringProperty(String.valueOf(this.age));
    }
    public StringProperty heightProperty() {
        return new SimpleStringProperty(String.valueOf(this.height));
    }
    public StringProperty clubProperty() {
        return new SimpleStringProperty(this.club);
    }
    public StringProperty positionProperty() {
        return new SimpleStringProperty(this.position);
    }
    public StringProperty jerseyProperty() {
        return new SimpleStringProperty(this.jerseyNumber);
    }
    public StringProperty salaryProperty() {
        return new SimpleStringProperty(String.valueOf(this.salary));
    }
}

public class PlayerList implements Serializable {
    private java.util.List<Player> playerList = new java.util.ArrayList<>();
    public void add(Player player) {
        playerList.add(player);
    }
    public Player at(int i) {
        return playerList.get(i);
    }
    public Player searchByName(String name) {
        name = name.trim();
        for (Player player:playerList) {
            if (player.name.toLowerCase().equals(name.toLowerCase())) {
                return player;
            }
        }
        return null;
    }
    public java.util.List<Player> filterByName(String name) {
        name = name.trim();
        if (name.isEmpty()) return playerList;
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.name.toLowerCase().contains(name.toLowerCase())) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public java.util.List<Player> searchByClub(String country, String club) {
        country = country.trim();
        if (country.equalsIgnoreCase("Afghanistan")) country = "Islamic Emirate of Afghanistan";
        club = club.trim();
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.country.toLowerCase().equals(country.toLowerCase())) {
                if (club.toUpperCase().equals("ANY") || player.club.toLowerCase().equals(club.toLowerCase())) {
                    selectedPlayerList.add(player);
                }
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public PlayerList searchByClub(String club) {
        club = club.trim();
        PlayerList selectedPlayerList = new PlayerList();
        for (Player player:playerList) {
            if (player.club.equalsIgnoreCase(club)) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.getList().isEmpty()) return null;
        return selectedPlayerList;
    }
    public java.util.List<Player> searchByPosition(String position) {
        position = position.trim();
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.position.toLowerCase().equals(position.toLowerCase())) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public java.util.List<Player> searchBySalaryRange(double lowerLimit, double upperLimit) {
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.salary >= lowerLimit && player.salary <= upperLimit) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public java.util.Map<String, Integer> countryWiseCount() {
        java.util.Map<String, Integer> countryWiseMap = new java.util.HashMap<>();
        for (Player player:playerList) {
            if (countryWiseMap.containsKey(Main.toTitleCase(player.country))) {
                countryWiseMap.put(Main.toTitleCase(player.country), countryWiseMap.get(Main.toTitleCase(player.country)) + 1);
            } else {
                countryWiseMap.put(Main.toTitleCase(player.country), 1);
            }
        }
        return countryWiseMap;
    }
    public java.util.List<Player> richestInClub(String club) {
        club = club.trim();
        double maximumSalary = Double.MIN_VALUE;
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.salary > maximumSalary) {
                maximumSalary = player.salary;
            }
        }
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.salary == maximumSalary) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public java.util.List<Player> eldestInClub(String club) {
        club = club.trim();
        int maximumAge = Integer.MIN_VALUE;
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.age > maximumAge) {
                maximumAge = player.age;
            }
        }
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.age == maximumAge) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public java.util.List<Player> tallestInClub(String club) {
        club = club.trim();
        double maximumHeight = Double.MIN_VALUE;
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.height > maximumHeight) {
                maximumHeight = player.height;
            }
        }
        java.util.List<Player> selectedPlayerList = new java.util.ArrayList<>();
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase()) && player.height == maximumHeight) {
                selectedPlayerList.add(player);
            }
        }
        if (selectedPlayerList.size() == 0) return null;
        return selectedPlayerList;
    }
    public double totalYearlySalaryOfClub(String club) {
        club = club.trim();
        double totalWeeklySalary = 0;
        int counter = 0;
        for (Player player:playerList) {
            if (player.club.toLowerCase().equals(club.toLowerCase())) {
                totalWeeklySalary += player.salary;
                ++counter;
            }
        }
        if (counter == 0) return Double.MIN_VALUE;
        return (totalWeeklySalary / 7) * 365;
    }
    public java.util.List<Player> getList() {
        return playerList;
    }

    public PlayerList getAvailablePlayers(String clubName) {
        PlayerList availablePlayerList = new PlayerList();
        for (Player player:playerList) {
            if (player.isAvailableForBuying && !(player.club.equalsIgnoreCase(clubName))) {
                availablePlayerList.add(player);
            }
        }
        if (availablePlayerList.isEmpty()) return null;
        return availablePlayerList;
    }

    public boolean isEmpty() {
        return playerList.isEmpty();
    }
}
