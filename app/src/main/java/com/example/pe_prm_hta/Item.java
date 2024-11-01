package com.example.pe_prm_hta;

public class Item {
    private int id;
    private String name;
    private String creator;
    private String release_date;
    private String type;
    private String identifier;
    private int idType;

    // Default constructor
    public Item() {
    }

    // Parameterized constructor
    public Item(int id, String name, String date, String release_date, String type, String identifier, int idType) {
        this.id = id;
        this.name = name;
        this.creator = date;
        this.release_date = release_date;
        this.type = type;
        this.identifier = identifier;
        this.idType = idType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", release_date='" + release_date + '\'' +
                ", type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                ", idType=" + idType +
                '}';
    }
}
