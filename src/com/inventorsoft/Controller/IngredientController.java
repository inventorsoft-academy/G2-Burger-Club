package com.inventorsoft.Controller;

import com.inventorsoft.DataStorage;
import com.inventorsoft.DataVerification;
import com.inventorsoft.Exception.*;
import com.inventorsoft.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientController {

    private List<Ingredient> ingredients = new ArrayList<>();
    private DataVerification dv = new DataVerification();

    private void updateData(){
        ingredients = new DataStorage().getIngredientsFromFile();
    }

    private void saveData(){
        new DataStorage().saveIngredientsToFile(ingredients);
    }

    public void addIngredient(String ingrName, double ingrPrice, int ingrAmount) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, DataAlreadyExistsException {
        updateData();
        Ingredient ingredient = new Ingredient(dv.verifyData(ingrName),ingrPrice,ingrAmount);
        if (dv.matchCheck(ingredient,ingredients)){
            ingredients.add(ingredient);
        }
        saveData();

    }

    public List<String> getIngredients(){
        updateData();
        List<String> ingredientsList = new ArrayList<>();
        for (Ingredient ingredient: ingredients) {
            ingredientsList.add(ingredient.getIngredientName());
        }
        return ingredientsList;
    }


}
