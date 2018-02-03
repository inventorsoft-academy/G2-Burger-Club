package com.inventorsoft.service;

import com.inventorsoft.exception.ContainsIllegalCharactersException;
import com.inventorsoft.exception.DataAlreadyExistsException;
import com.inventorsoft.exception.EmptyDataException;
import com.inventorsoft.exception.WrongDataSizeException;
import com.inventorsoft.model.Burger;
import com.inventorsoft.model.Ingredient;

import java.util.List;

public class DataVerification {


    public String verifyData(String data) throws EmptyDataException, WrongDataSizeException, ContainsIllegalCharactersException {
        if (data.length() == 0){
            throw new EmptyDataException("ERROR: Empty data");
        } else if (data.length() < 3 || data.length() > 20 ){
            throw  new WrongDataSizeException("ERROR: Wrong data size");
        } else if (data.contains(",") || data.contains(";") || data.contains(" ")){
            throw new ContainsIllegalCharactersException("ERROR: Data contains illegal characters");
        }
        return data;
    }

    public boolean matchCheck(Ingredient ingredient, List<Ingredient> list) throws DataAlreadyExistsException {
        Boolean isSuccess = list.stream().anyMatch(o -> o.getIngredientName().
                equals(ingredient.getIngredientName()));
        if (isSuccess){
            return true;
        } else {
            return false;
        }
    }

    public boolean matchCheck(Burger burger, List<Burger> list) throws DataAlreadyExistsException {
        Boolean isSuccess = list.stream().anyMatch(o -> o.getName().
                equals(burger.getName()) );
        if (isSuccess){
            throw new DataAlreadyExistsException("ERROR: Burger with such name already exists");
        } else {
            return true;
        }
    }

}
