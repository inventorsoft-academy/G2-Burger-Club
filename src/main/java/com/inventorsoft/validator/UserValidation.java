package com.inventorsoft.validator;

import com.inventorsoft.exception.*;
import com.inventorsoft.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidation {

    public void isLoginSuccessful(User user, List<User> list) throws WrongLoginPasswordException {

        Boolean isSuccess = list.stream().anyMatch(o -> o.getLogin().
                equals(user.getLogin()) && o.getPassword().equals(user.getPassword()) && o.isAdmin() == user.isAdmin());
        if (!isSuccess){
            throw new WrongLoginPasswordException("Wrong login or password");
        }

    }

    public boolean isRegistrationSuccessful(User user, List<User> list) throws DataAlreadyExistsException {

        Boolean isSuccess = list.stream().anyMatch(o -> o.getLogin().
                equals(user.getLogin()) && o.getPassword().equals(user.getPassword()));
        if (isSuccess){
            throw new DataAlreadyExistsException("User already exists");
        } else {
            return true;
        }
    }

    public String verifyUserData(String data) throws EmptyDataException, WrongDataSizeException, ContainsIllegalCharactersException {
        if (data.length() == 0){
            throw new EmptyDataException("ERROR: Enter data");
        } else if (data.length() < 3 || data.length() > 20 ){
            throw  new WrongDataSizeException("ERROR: You have entered an invalid data size");
        } else if (data.contains(",") || data.contains(";") || data.contains(" ")){
            throw new ContainsIllegalCharactersException("ERROR: You have entered an invalid character or characters");
        }
        return data;
    }

    public void validateCommissions(int commission) throws WrongDataSizeException {
        if (commission > 50 || commission < 0){
            throw new WrongDataSizeException("ERROR: You have entered an invalid amount of data");
        }

    }
}
