package com.inventorsoft;

import com.inventorsoft.Exception.DataAlreadyExistsException;
import com.inventorsoft.Exception.WrongLoginPasswordException;
import com.inventorsoft.Model.User;

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
