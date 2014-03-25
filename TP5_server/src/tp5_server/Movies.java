/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_server;

/**
 *
 * @author ThomasFerrandini
 */
public class Movies {
    String [] names;
    String [] places;

    public Movies(String[] names, String[] places){
        this.names = names;
        this.places = places;
    }
    
    public Movies() {
        this.names = null;
        this.places = null;
    }
    
}