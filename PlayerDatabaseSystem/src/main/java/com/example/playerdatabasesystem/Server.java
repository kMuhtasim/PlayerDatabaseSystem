package com.example.playerdatabasesystem;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;

    public Server() throws Exception {
        serverSocket = new ServerSocket(22222);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
            String clubName = socketWrapper.read().toString();
            new ServerThread(socketWrapper, clubName);
        }
    }

    public static void main(String[] args) {
        try {
            Main.loadPlayers();
            new Server();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
