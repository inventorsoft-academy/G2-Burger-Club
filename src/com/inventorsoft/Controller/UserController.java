package com.inventorsoft.Controller;

import com.inventorsoft.DataStorage;
import com.inventorsoft.Exception.*;
import com.inventorsoft.Model.User;
import com.inventorsoft.UserAuthentication;
import com.inventorsoft.UserVerification;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private UserAuthentication auth;
    private List<User> list = new ArrayList<>();

    private void updateData(){
        list = DataStorage.getAllUsers();
    }
    private void saveData(){
        DataStorage.saveUserToFile2(list);
    }



    public boolean login(String login, String password, boolean isAdmin) throws UserAlreadyExistsException, WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, WrongLoginPasswordException {
        updateData();

        User user = new UserVerification().createUser(login,password,isAdmin);
        auth = new UserAuthentication();
        return auth.isLoginSuccessful(user, list);
    }

    public boolean registration(String login, String password, boolean isAdmin) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, UserAlreadyExistsException {
        updateData();
        User user = new UserVerification().createUser(login,password,isAdmin);
        user.setMoney(10);
        Boolean isSuccess = new UserAuthentication().isRegistrationSuccessful(user,list);
        list.add(user);
        saveData();
        return isSuccess;
    }
}
