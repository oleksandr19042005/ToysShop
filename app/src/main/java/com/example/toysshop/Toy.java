package com.example.toysshop;

public class Toy {

    public int getQuantity;
    private String name;
    private String material;
    private String ageForUse;
    private String size;
    private String price;
    private String toy_id;
    private String purchase_date;
    private int quantity;
    public Toy(String name, String material, String ageForUse, String size, String price) {
        this.name = name;
        this.material = material;
        this.ageForUse = ageForUse;
        this.size = size;
        this.price = price;
    }
    public void setToy_id(String toy_id) {
        this.toy_id = toy_id;
    }
    public String getToy_id() {
        return toy_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }


    public Toy() {

    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAgeForUse() {
        return ageForUse;
    }

    public void setAgeForUse(String ageForUse) {
        this.ageForUse = "Age for use:" + ageForUse;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Toy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
