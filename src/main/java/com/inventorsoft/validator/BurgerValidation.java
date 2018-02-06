package com.inventorsoft.validator;

import com.inventorsoft.exception.ContainsIllegalCharactersException;
import com.inventorsoft.exception.DataAlreadyExistsException;
import com.inventorsoft.exception.EmptyDataException;
import com.inventorsoft.exception.WrongDataSizeException;
import com.inventorsoft.model.Burger;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BurgerValidation {

    public String verifyBurgerData(String data) throws EmptyDataException, WrongDataSizeException, ContainsIllegalCharactersException {
        if (data.length() == 0){
            throw new EmptyDataException("ERROR: Enter data");
        } else if (data.length() < 3 || data.length() > 20 ){
            throw  new WrongDataSizeException("ERROR: You have entered an invalid data size!");
        } else if (data.contains(",") || data.contains(";") || data.contains(" ")){
            throw new ContainsIllegalCharactersException("ERROR: You have entered an invalid character or characters");
        }
        return data;
    }

    public boolean isBurgerAlreadyExists(Burger burger, List<Burger> list) throws DataAlreadyExistsException {
        Boolean isSuccess = list.stream().anyMatch(o -> o.getName().
                equals(burger.getName()) );
        if (isSuccess){
            throw new DataAlreadyExistsException("ERROR: Burger with such name already exists");
        } else {
            return true;
        }
    }
}
