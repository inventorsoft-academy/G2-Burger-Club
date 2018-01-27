package com.inventorsoft.Model;

import java.util.List;

/**
 * Created by Sergey on 17.01.2018.
 */
public class Burger {

    private String name;
    private double price;
    private List<Ingredient> ingredient;

    public Burger(String name, double price, List<Ingredient> ingredient) {
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
    }

    public Burger(String name, double price) {
        this.name = name;
        this.price = price;
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
                ", price=" + price +
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
