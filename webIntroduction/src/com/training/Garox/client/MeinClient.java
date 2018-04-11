package com.training.Garox.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class MeinClient {

    public static void main(String[] args) {
        MeinClient client = new MeinClient("192.168.0.10", 8001);
        client.sendMessage("Dzień Dobry, Panie Server. Jak pan się dziś ma?");

    }

    private InetSocketAddress address;

    public MeinClient(String hostname, int port) {
        address = new InetSocketAddress(hostname, port);
    }

    public void sendMessage(String msg) {

        try {
            System.out.println("[Client] lącze z serverem ....");
            Socket socket = new Socket();
            socket.connect(address, 5000);
            System.out.println("[CLient] połączono.");

            System.out.println("[Client] wysyłam wiadomość...");
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println(msg);
            pw.flush();
            System.out.println("[Client] wiadomość wysłana.");

            Scanner s = new Scanner(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            if (s.hasNextLine()) {
                System.out.println("[Client] Odpowiedź od servera: " + s.nextLine());
            }

            // Zamykanie połączenia
            pw.close();
            s.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
