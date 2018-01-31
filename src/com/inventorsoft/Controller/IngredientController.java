package com.inventorsoft.Controller;

import com.inventorsoft.Exception.ContainsIllegalCharactersException;
import com.inventorsoft.Exception.DataAlreadyExistsException;
import com.inventorsoft.Exception.EmptyDataException;
import com.inventorsoft.Exception.WrongDataSizeException;
import com.inventorsoft.Model.Ingredient;
import com.inventorsoft.Service.DataFileStorage;
import com.inventorsoft.Service.DataFileStorageIngredient;
import com.inventorsoft.Service.DataVerification;

import java.util.ArrayList;
import java.util.List;

public class IngredientController {

    private List<Ingredient> ingredients = new ArrayList<>();
    private DataVerification dv = new DataVerification();
    private DataFileStorage<Ingredient> dataStorage = new DataFileStorageIngredient();

    private void updateData(){
        ingredients = dataStorage.getDataFromFileByList();
    }

    private void saveData(){
        dataStorage.saveDataToFileByList(ingredients);
    }


    public void addIngredient(String ingrName, double ingrPrice, int ingrAmount) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, DataAlreadyExistsException {
        updateData();

        Ingredient ingredient = new Ingredient(dv.verifyData(ingrName),ingrPrice,ingrAmount);

        if (dv.matchCheck(ingredient,ingredients)){
            ingredients.stream()
                    .filter(o -> o.getIngredientName().equals(ingredient.getIngredientName()))
                    .peek(o -> o.setPrice(ingrPrice))
                    .forEach(o -> o.setAmount(ingrAmount));

        } else {
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

    public List<String> getIngredientsForCustomer(){
        updateData();
        List<String> ingredientsList = new ArrayList<>();
        for (Ingredient ingredient: ingredients) {
            ingredientsList.add("($"+ingredient.getPrice()+")"+ingredient.getIngredientName()+"("+ingredient.getAmount()+")");
        }
        return ingredientsList;
    }

    public Ingredient getIngredientByName(String name){
        updateData();
        return ingredients.stream()
                .filter(o -> o.getIngredientName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean reduceIngredientAmount(String name) throws EmptyDataException {
        updateData();

        Ingredient ingredient = getIngredientByName(name);
        int amount = ingredient.getAmount();
        if (amount > 0){
            ingredients.stream()
                    .filter(o -> o.getIngredientName().equals(ingredient.getIngredientName()))
                    .forEach(o -> o.setAmount(amount-1));
            saveData();
            return true;
        } else {

            throw new EmptyDataException("ERROR: Not enough ingredients");
        }

    }


}
