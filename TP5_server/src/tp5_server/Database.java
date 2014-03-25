/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThomasFerrandini
 */

public class Database {
    Connection conn;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:8889/tp5_java";
            conn = DriverManager.getConnection(url, "root", "root");
            //conn.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void close() throws Exception {
        conn.close();
    }
    
    public Movies getMoviesFromDb() {
        //create two arrayLists to put the result of the query
        ArrayList<String> names = new ArrayList();

        String query = "SELECT name FROM movies WHERE places > 0";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("Fail to connect to database");
            System.err.println(ex.getMessage());
        }
        //create two arrays to put the items of the previous arraylists
        String[] arrNames = new String[names.size()];

        for (int i = 0; i < names.size(); i++) {
            arrNames[i] = names.get(i);
        }
        Movies moviesList = new Movies(arrNames);
        
        return moviesList;
    }
    
    public String getPlacesForMovie(String movieName){
      String places = "";

      String query = "SELECT places FROM movies WHERE name = '"+movieName+"'";
      try
      {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next())
        {
          places = rs.getString("places");
        }
        return places;
      }
      catch (SQLException ex)
      {
        System.err.println(ex.getMessage());
        return places;
      }
        
    }
    
    public String checkPlacesLeft(SendData data) {
        String query = "SELECT id, places FROM movies WHERE name = '" + data.movieName + "'";
        
        int placesLeft = 0;
        int movieId = 0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                movieId = Integer.parseInt(rs.getString("id"));
                placesLeft = Integer.parseInt(rs.getString("places"));
            }
            if (placesLeft < 0) {
                System.out.println("No tickets left");
                return "Impossible to reserve";
            } else {
                return reserveMovie(data, placesLeft, movieId);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "No movies with this name";
        }

    }
    
    public String reserveMovie(SendData data, int placesLeft, int movieId){
        
        placesLeft -= Integer.parseInt(data.places);

        try {
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE movies SET places=" + placesLeft + " WHERE id ='" + movieId + "'");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "An error occured";
        }
        
        int reservationId = (int) (Math.random() * 99999);
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO reservations (movieId, name, places, reservationId) "
                    + "VALUES ('" + movieId + "', '" + data.name + "', " + data.places + ", " + reservationId + ")");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Impossible d'effectuer votre réservation";
        }

        return "Reservation validée, id : " + reservationId;
    }

  
}
