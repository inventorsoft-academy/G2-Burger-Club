package com.inventorsoft;

import com.inventorsoft.Exception.ContainsIllegalCharactersException;
import com.inventorsoft.Exception.EmptyDataException;
import com.inventorsoft.Exception.WrongDataSizeException;
import com.inventorsoft.Model.User;

public class UserVerification {

    public User createUser(String login, String password, boolean isAdmin) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException {
        return new User(verifyData(login), verifyData(password),isAdmin);
    }

    private String verifyData(String data) throws EmptyDataException, WrongDataSizeException, ContainsIllegalCharactersException {
        if (data.length() == 0){
            throw new EmptyDataException("Empty data");
        } else if (data.length() < 3 || data.length() > 20 ){
            throw  new WrongDataSizeException("Wrong data size");
        } else if (data.contains(",") || data.contains(".") || data.contains(";")){
            throw new ContainsIllegalCharactersException("Data contains illegal characters");
        }
        return data;
    }



}
