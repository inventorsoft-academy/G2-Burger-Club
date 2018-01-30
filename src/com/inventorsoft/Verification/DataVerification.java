package com.inventorsoft.Verification;

import com.inventorsoft.Exception.ContainsIllegalCharactersException;
import com.inventorsoft.Exception.DataAlreadyExistsException;
import com.inventorsoft.Exception.EmptyDataException;
import com.inventorsoft.Exception.WrongDataSizeException;
import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;

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
