package com.inventorsoft.Controller;

import com.inventorsoft.DataStorage;
import com.inventorsoft.Model.Ingredient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredientController {

    public IngredientController() {

    }

    public void addIngredient(String ingredient){
        try {
            new DataStorage().saveIngredientToFile(new Ingredient(ingredient));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getIngredients(){
        List<String> ingredientsList = new ArrayList<>();
        try {
            List<Ingredient> ingredients = new DataStorage().getIngredientsFromFile();
            for (Ingredient ingredient: ingredients) {
                ingredientsList.add(ingredient.getIngredientName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }


}
