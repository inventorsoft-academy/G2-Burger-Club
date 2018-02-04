package com.inventorsoft.service;

import com.inventorsoft.dao.DataFileStorage;
import com.inventorsoft.dao.DataFileStorageIngredient;
import com.inventorsoft.exception.ContainsIllegalCharactersException;
import com.inventorsoft.exception.DataAlreadyExistsException;
import com.inventorsoft.exception.EmptyDataException;
import com.inventorsoft.exception.WrongDataSizeException;
import com.inventorsoft.model.Ingredient;
import com.inventorsoft.validator.DataVerification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

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
