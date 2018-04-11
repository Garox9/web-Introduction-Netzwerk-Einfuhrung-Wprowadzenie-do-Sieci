package com.training.Garox.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MeinServer {

    public static void main(String[] args) {

        MeinServer server = new MeinServer(8001);
        server.strartListening();

    }

    private int port;

    public MeinServer(int port) {
        this.port = port;
    }

    public void strartListening() {
        System.out.println("[Server] uruchamianie Servera....");
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {

                        ServerSocket serverSocket = new ServerSocket(port);
                        System.out.println("[Server] oczekiwanie na połączenie....");
                        Socket remoteClientSocket = serverSocket.accept();
                        System.out.println("[Server] klient połączony: " + remoteClientSocket.getRemoteSocketAddress());

                        Scanner s = new Scanner(new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
                        if (s.hasNextLine()) {
                            System.out.println("[Server] nowa wiadomość od klienta " + s.nextLine());
                        }

                        PrintWriter pw = new PrintWriter(new OutputStreamWriter(remoteClientSocket.getOutputStream()));
                        pw.println("Dziś piękny dzień, dziękuję, że pytasz.");
                        pw.flush();

                        // Zamykam połączenie
                        s.close();
                        pw.close();
                        remoteClientSocket.close();
                        serverSocket.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
