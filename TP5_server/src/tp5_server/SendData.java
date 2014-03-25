/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tp5_server;

/**
 *
 * @author Thomas
 * 
 */

public class SendData{
    String action;
    String name;
    String movieName;
    String places;
    
    public SendData(String action) {
        this.action = action;
        this.name = "";
        this.movieName = "";
        this.places = "";
    }
    
    public SendData(String action, String name, String movieName, String places) {
        this.action = action;
        this.name = name;
        this.movieName = movieName;
        this.places = places;
    }
    
}