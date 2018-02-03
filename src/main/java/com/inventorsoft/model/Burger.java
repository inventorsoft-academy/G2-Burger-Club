package com.inventorsoft.model;

import java.util.List;

public class Burger {

    private String name;
    private double price;
    private boolean isAvailable;
    private String creator;
    private int ordersNumber;
    private List<Ingredient> ingredient;

    public Burger(String name, String creator, int ordersNumber, List<Ingredient> ingredient, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.creator = creator;
        this.ordersNumber = ordersNumber;
        this.ingredient = ingredient;
    }

    public Burger(String name, String creator, double price, int ordersNumber, List<Ingredient> ingredient,boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.creator = creator;
        this.ordersNumber = ordersNumber;
        this.ingredient = ingredient;

    }


    public int getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(int ordersNumber) {
        this.ordersNumber = ordersNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredient() {
        return ingredient;
    }


    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Burger{" +
                "name='" + name + '\'' +
                ", isAvailable=" + isAvailable +
                ", creator=" + creator +
                ", ingredient=" + ingredient +
                '}';
    }

    private String ingredientList(List<Ingredient> ingredients){
        String text = "";
        for (Ingredient ingredient: ingredients) {
            text += ingredient.getIngredientName()+" ";
        }
        return text;
    }

    public String showBurgerInfo(){
        return name + "($"+price+"): "+ingredientList(ingredient);
    }
}
