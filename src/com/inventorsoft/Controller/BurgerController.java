package com.inventorsoft.Controller;

import com.inventorsoft.DataStorage;
import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BurgerController {

    public void saveBurger(String name, double price, List<String> ingredient){
        List<Ingredient> ingredients= new ArrayList<>();
        try {
            for (String str: ingredient) {
                ingredients.add(new DataStorage().getIngredientByNameString(str));
            }
            Burger burger = new Burger(name,price,ingredients);
            new DataStorage().saveBurgerToFile(burger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBurger(String name){
        new DataStorage().deleteBurgerFile(name);
    }

    public List<String> getBurgersList(){
        List<String> burgerString = new ArrayList<>();
        try {
            List<Burger> burger = new DataStorage().getAllBurgersList();
            for (Burger ingredient: burger) {
                burgerString.add(ingredient.showBurgerInfo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return burgerString;
    }

    public String getBurgerString(int id){
        String str = "";
        try {
            List<Burger> burgers = new DataStorage().getAllBurgersList();
            str = burgers.get(id).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
