/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528project;

/**
 *
 * @author Sanchit Das, Thiveyan Nadeswaran
 */
public class Customer
{
    private static String username;
    private String password;
    public static int points;
    private static String status;

    public Customer(String username, String password, int points){
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public static String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static int getPoints(){
        return points;
    }
    
    public static void setStatus(int points) 
    {
        if(points>=1000)
            status = "GOLD";
        else 
            status = "SILVER";
    }
    public static String getStatus() 
    {
        return status;
    }
}
