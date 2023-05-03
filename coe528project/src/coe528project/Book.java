/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528project;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Sanchit Das, Thiveyan Nadeswaran
 */
public class Book {

    static void setSelected(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String name;
    public double price;
    public CheckBox select;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
        this.select = new CheckBox();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    public CheckBox getSelect(){
        return select;
    }
    public void setSelect(CheckBox select){
        this.select = select;
        select.setSelected(true);
    }
}
