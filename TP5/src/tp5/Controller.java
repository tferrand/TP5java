/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ThomasFerrandini
 */
public class Controller{
    
    static Socket socket;
    static PrintWriter outSocket;
    static BufferedReader inSocket;
    theater_frame2 frame;
    Movies movies;
    Gson gson;
    
    public Controller() throws Exception{
        gson = new Gson();
        socket = new Socket("localhost", 4444);
        //Socket socket = new Socket("127.0.0.1", 4444);
        //to get the ip address
        System.out.println((java.net.InetAddress.getLocalHost()).toString());

        //true: it will flush the output buffer
        outSocket = new PrintWriter(socket.getOutputStream(), true);
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
        frame = new theater_frame2(this);
        frame.setSize(500, 400);
        frame.setTitle("AlloCine 1980");
        frame.setVisible(true);
        loadFrameContent();
        
        //add custom item listener to combox1 wich list all movies avaible
        MyItemListener actionListener = new MyItemListener();
        frame.jComboBox1.addItemListener(actionListener);
  
        
    }
    
    public void loadFrameContent() throws IOException{
        System.out.println("Requesting movies list from server");
        //first request to get the movies list
        outSocket.println(gson.toJson(new SendData("getMovies")));
        
        //transform the answer in object and create the frame with the data
        movies = gson.fromJson(inSocket.readLine(), Movies.class);
        
        //fill jComboBox1 with movies list
        frame.setjComboBox1(movies.names);
        
        //request to get the nbr of places for this movie
        System.out.println("Requesting places for movie '"+movies.names[0]+"' from server");
        outSocket.println(gson.toJson(new SendData("getPlaces",movies.names[0])));

        String placesLeft = inSocket.readLine();
        System.out.println(placesLeft + " left for the movie "+movies.names[0]);

        String arrayPlaces[] = new String[Integer.parseInt(placesLeft)];
        for(int i = 0; i<arrayPlaces.length;i++){
            arrayPlaces[i] = Integer.toString(i+1);
        }
        //fill jComboBox2 with number of places lefts for movie selected
        frame.setjComboBox2(arrayPlaces);

    }
    
    public void sendReservation(SendData sendData) throws IOException{
        outSocket.println(gson.toJson(sendData));
        
        //show reservation id
        frame.labelValidation.setText(inSocket.readLine() + ", Liste mise à jour");
        frame.labelValidation.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                frame.labelValidation.setText("");
            }
        }, 3000);
        
        //reload the two comboBox
        loadFrameContent();
    }

    class MyItemListener implements ItemListener {
        // This method is called only if a new item has been selected.
        public void itemStateChanged(ItemEvent evt) {
          JComboBox cb = (JComboBox) evt.getSource();

          if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Requesting places for movie '"+cb.getSelectedItem().toString()+"' from server");

            //request to get the nbr of places for this movie
            outSocket.println(gson.toJson(new SendData("getPlaces",cb.getSelectedItem().toString())));
            try {
                String placesLeft = inSocket.readLine();
                System.out.println(placesLeft + "left for the movie "+cb.getSelectedItem().toString());
                
                String arrayPlaces[] = new String[Integer.parseInt(placesLeft)];
                for(int i = 0; i<arrayPlaces.length;i++){
                    arrayPlaces[i] = Integer.toString(i+1);
                }
                //fill jComboBox2 with number of places lefts for movie selected
                frame.setjComboBox2(arrayPlaces);
                
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
          } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
            // Item is no longer selected
          }
        }

      }
}
