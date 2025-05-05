package com.example.playerdatabasesystem;

import java.util.Objects;

public class ServerThread implements Runnable {
    SocketWrapper socketWrapper;
    String clubName;

    ServerThread(SocketWrapper socketWrapper, String clubName) {
        this.socketWrapper = socketWrapper;
        this.clubName = clubName;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o instanceof Message msg) {
                    if (Objects.equals(msg.action, "getMyPlayers")) {
                        socketWrapper.write(Main.playerList.searchByClub(clubName));
                    }
                    if (Objects.equals(msg.action, "getAllPlayers")) {
                        socketWrapper.write(Main.playerList);
                    }
                    if (Objects.equals(msg.action, "sell")) {
                        System.out.println(clubName + " releases " + msg.string + " for sale");
                        Main.playerList.searchByName(msg.string).isAvailableForBuying = true;
                    }
                    if (Objects.equals(msg.action, "retract")) {
                        System.out.println(clubName + " retracts " + msg.string + " from sale");
                        Main.playerList.searchByName(msg.string).isAvailableForBuying = false;
                    }
                    if (Objects.equals(msg.action, "getAvailablePlayers")) {
                        socketWrapper.write(Main.playerList.getAvailablePlayers(clubName));
                    }
                    if (Objects.equals(msg.action, "buy")) {
                        Player player = Main.playerList.searchByName(msg.string);
                        if (player.isAvailableForBuying) {
                            System.out.println(clubName + " buys " + msg.string + " from " + player.club);
                            player.club = clubName;
                            player.isAvailableForBuying = false;
                            Main.writePlayerData();
                        }
                    }
                    if (Objects.equals(msg.action, "exit")) {
                        socketWrapper.closeConnection();
                        break;
                    }
                } else if (o instanceof Player playerToAdd) {
                    Main.playerList.add(playerToAdd);
                    Main.writePlayerData();
                    socketWrapper.write("success");
                    socketWrapper.closeConnection();
                    break;
                } else if (o instanceof String s) {
                    if (Objects.equals(s, "exit")) {
                        socketWrapper.closeConnection();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
