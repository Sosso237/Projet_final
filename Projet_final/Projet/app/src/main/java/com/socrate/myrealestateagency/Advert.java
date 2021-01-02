package com.socrate.myrealestateagency;

public class Advert {

    private int id;
    private String Type;
    private String price;
    private String surface;
    private String rooms;
    private String description;
    private String address;
    private String status;
    private String Creation_date;
    private String Update_date ;
    private String Agent;


    public Advert() {
    }

    //Designed class to the advert Object

    public Advert(int id, String type, String price, String surface, String rooms, String description, String address, String status, String creation_date, String update_date, String agent) {
        this.id = id;
        this.Type = type;
        this.price = price;
        this.surface = surface;
        this.rooms = rooms;
        this.description = description;
        this.address = address;
        this.status = status;
        this.Creation_date = creation_date;
        this.Update_date = update_date;
        this.Agent = agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(String creation_date) {
        Creation_date = creation_date;
    }

    public String getUpdate_date() {
        return Update_date;
    }

    public void setUpdate_date(String update_date) {
        Update_date = update_date;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    @Override
    public String toString() {
        return
                "\n id = " + id +
                "\n Type = " + Type +
                "\n price = " + price +
                "\n surface = " + surface +
                "\n rooms = " + rooms +
                "\n description = " + description +
                "\n address = " + address +
                "\n status = " + status +
                "\n Creation_date = " + Creation_date +
                "\n Update_date = " + Update_date  +
                "\n Agent = " + Agent  +
                '\n';
    }


}
