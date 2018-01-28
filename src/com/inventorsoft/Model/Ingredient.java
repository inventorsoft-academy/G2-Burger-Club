package com.inventorsoft.Model;

public class Ingredient {

    public Ingredient() {
    }

    public Ingredient(String ingredientName, double price, int amount) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.amount = amount;
    }

    private String ingredientName;
    private double price;
    private int amount;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }
}
