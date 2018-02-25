package com.project.wissemcomp.veloapp.mvp.model;


import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Bike {

    private int id;
    private String date;
    private String price;
    private String name;
    private String image;
    private String description;
    private String price_sold;
    private List<Piece> list_pieces;

    public Bike(){};

    public int getId(){ return id; }
    public String getPrice(){ return price; }
    public String getName(){ return name; }
    public String getImage(){ return image; }
    public String getDescription(){ return description; }
    public String getPrice_sold() {
        return price_sold;
    }
    public String getDate() {
        return date;
    }
    public List<Piece> getList_pieces() {return list_pieces;}

    public void setPrice_sold(String price_sold) {
        this.price_sold = price_sold;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setList_pieces(List<Piece> list_pieces) {this.list_pieces = list_pieces;}

    @Override
    public String toString() {
        return name;
    }


}

