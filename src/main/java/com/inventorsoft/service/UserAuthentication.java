package com.inventorsoft.service;

import com.inventorsoft.exception.DataAlreadyExistsException;
import com.inventorsoft.exception.WrongLoginPasswordException;
import com.inventorsoft.model.User;

import java.util.List;

public class UserAuthentication  {

    public boolean isLoginSuccessful(User user, List<User> list) throws WrongLoginPasswordException {
        Boolean isSuccess = list.stream().anyMatch(o -> o.getLogin().
                equals(user.getLogin()) && o.getPassword().equals(user.getPassword()) && o.isAdmin() == user.isAdmin());
        if (!isSuccess){
            throw new WrongLoginPasswordException("Wrong login or password");
        }
        return true;
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
}
