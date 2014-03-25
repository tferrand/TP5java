/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_server;

/**
 *
 * @author ThomasFerrandini
 */

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TP5_server implements Serializable {

    public static void main(String[] args) throws IOException {
        new TP5_server().begin(4444);
    }
    
    ServerSocket serverSocket;
    
    public void begin(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for clients to connect on port " + port + "...");
            new TP5_server.ProtocolThread(serverSocket.accept()).start();
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;
        Database database;
        Gson gson;

        public ProtocolThread(Socket socket) {
            gson = new Gson();
            database = new Database();
            System.out.println("Accepting connection from " + socket.getInetAddress() + "...");
            this.socket = socket;
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Success to connect to bdd");
        }
        
        
        @Override
        public void run() {
            try {
                while(true){
                    System.out.println("Waiting for instructions from client...");
                    SendData data = gson.fromJson(in_socket.readLine(), SendData.class);
                    System.out.println("action : " + data.action);
                    switch (data.action) {
                        case "getMovies":
                            System.out.println("Sending Movies list to client");
                            out_socket.println(gson.toJson(database.getMoviesFromDb()));
                            break;
                        case "getPlaces":
                            System.out.println("Sending Places left for '"+data.movieName+"' to client");
                            out_socket.println(database.getPlacesForMovie(data.movieName));
                            break;
                        case "reserveMovie":
                            System.out.println("Verifying possibility to create reservation");
                            out_socket.println(database.checkPlacesLeft(data));
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("Closing connection.");
                    socket.close();
                    try {
                        database.close();
                    } catch (Exception ex) {
                        Logger.getLogger(ProtocolThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}

