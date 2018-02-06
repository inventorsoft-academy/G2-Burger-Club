package com.inventorsoft.service;

import com.inventorsoft.dao.DataFileStorage;
import com.inventorsoft.exception.*;
import com.inventorsoft.model.User;
import com.inventorsoft.ui.ConsoleInterface;
import com.inventorsoft.validator.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger log = Logger.getLogger(ConsoleInterface.class.getName());

    private List<User> users;
    private DataFileStorage<User> dataStorage;
    private UserValidation userValidation;

    @Autowired
    public UserService(DataFileStorage<User> dataStorage, UserValidation userValidation) {
        this.dataStorage = dataStorage;
        this.userValidation = userValidation;
    }
    @PostConstruct
    private void updateData(){
        users = dataStorage.getDataFromFileByList();
    }

    @PreDestroy
    private void saveData(){
        dataStorage.saveDataToFileByList(users);
    }

    public void login(String login, String password, boolean isAdmin) throws DataAlreadyExistsException, WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, WrongLoginPasswordException {
        updateData();
        User user = new User(userValidation.verifyUserData(login),userValidation.verifyUserData(password),isAdmin);
        userValidation.isLoginSuccessful(user, users);
    }

    public boolean registration(String login, String password, boolean isAdmin) throws WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException, DataAlreadyExistsException {
        updateData();

        User user = new User(userValidation.verifyUserData(login),userValidation.verifyUserData(password),isAdmin);
        user.setMoney(10);
        if (users.get(0) != null){
            user.setCommissions(users.get(0).getCommissions());
        } else {
            user.setCommissions(20);
        }

        Boolean isSuccess = new UserValidation().isRegistrationSuccessful(user,users);
        users.add(user);
        saveData();
        return isSuccess;
    }

    public List<User> getUsersList() {

        log.info("burgers(size): "+users.size());
        return users;
    }

    public User getUsersById(int id){
        return users.get(id-1);
    }

    public User getUserByName(String name){
        return users.stream().filter(o -> o.getLogin().equals(name)).findFirst().orElse(null);
    }

    public double getBalanceByUserName(String name){
        User user = getUserByName(name);
        double balance = user.getMoney();
        return Math.floor(balance * 100)/100;
    }

    public double getCommissionsByUserName(String name){
        User user = getUserByName(name);
        double commission = user.getCommissions();
        return Math.floor(commission * 100)/100;
    }

    public void setCommissions(int amount) throws WrongDataSizeException {
        userValidation.validateCommissions(amount);
        users.forEach(o -> o.setCommissions(amount));
        saveData();
    }


    public void setCompanyProfit(double companyProfit) {
        User user = users.stream().filter(User::isAdmin).findFirst().orElse(null);
        double profit = user.getMoney() + companyProfit;
        users.stream().filter(User::isAdmin).forEach(o -> o.setMoney(profit));
        saveData();
    }


    public void setCustomerLoss(User customer, double loss) {
        users.stream().filter(o -> o.getLogin().equals(customer.getLogin())).forEach(o -> o.setMoney(loss));
        saveData();

    }

    public void setCreatorProfit(User creator, double creatorProfit) {
        try {
            users.stream().filter(o -> o.getLogin().equals(creator.getLogin())).forEach(o -> o.setMoney(o.getMoney()+creatorProfit));
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        saveData();
    }
}
