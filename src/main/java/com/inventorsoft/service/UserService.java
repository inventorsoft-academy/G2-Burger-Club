package com.inventorsoft.service;

import com.inventorsoft.dao.DataFileStorage;
import com.inventorsoft.dao.DataFileStorageUser;
import com.inventorsoft.exception.*;
import com.inventorsoft.model.User;
import com.inventorsoft.validator.DataVerification;
import com.inventorsoft.validator.UserAuthentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private DataFileStorage<User> dataStorage = new DataFileStorageUser();
    private UserAuthentication auth = new UserAuthentication();

    private DataVerification dv = new DataVerification();

    private void updateData(){
        users = dataStorage.getDataFromFileByList();
    }

    private void saveData(){
        dataStorage.saveDataToFileByList(users);
    }

    public boolean login(String login, String password, boolean isAdmin) throws DataAlreadyExistsException, WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, WrongLoginPasswordException {
        updateData();
        User user = new User(dv.verifyData(login),dv.verifyData(password),isAdmin);
        return auth.isLoginSuccessful(user, users);
    }

    public boolean registration(String login, String password, boolean isAdmin) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, DataAlreadyExistsException {
        updateData();

        User user = new User(dv.verifyData(login),dv.verifyData(password),isAdmin);
        user.setMoney(10);
        if (users.get(0) != null){
            user.setCommissions(users.get(0).getCommissions());
        } else {
            user.setCommissions(20);
        }

        Boolean isSuccess = new UserAuthentication().isRegistrationSuccessful(user,users);
        users.add(user);
        saveData();
        return isSuccess;
    }

    public User getUserByName(String name){
        updateData();
        return users.stream().filter(o -> o.getLogin().equals(name)).findFirst().orElse(null);
    }

    public String getBalance(String name){
        updateData();
        User user = getUserByName(name);
        return "BALANCE: $"+user.getMoney()+" Commissions for creators: "+user.getCommissions()+"%";
    }

    public void setCommissions(int amount){
        updateData();
        users.forEach(o -> o.setCommissions(amount));
        saveData();
    }


    public void setCompanyProfit(double companyProfit) {
        updateData();
        User user = users.stream().filter(User::isAdmin).findFirst().orElse(null);
        double profit = user.getMoney() + companyProfit;
        users.stream().filter(User::isAdmin).forEach(o -> o.setMoney(profit));
        saveData();
    }


    public void setCustomerLoss(User customer, double loss) {
        updateData();
        users.stream().filter(o -> o.getLogin().equals(customer.getLogin())).forEach(o -> o.setMoney(loss));
        saveData();

    }

    public void setCreatorProfit(User creator, double creatorProfit) {
        updateData();
        try {
            users.stream().filter(o -> o.getLogin().equals(creator.getLogin())).forEach(o -> o.setMoney(o.getMoney()+creatorProfit));
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        saveData();
    }
}
