package com.inventorsoft.service;

import com.inventorsoft.dao.DataFileStorage;
import com.inventorsoft.exception.ContainsIllegalCharactersException;
import com.inventorsoft.exception.DataAlreadyExistsException;
import com.inventorsoft.exception.EmptyDataException;
import com.inventorsoft.exception.WrongDataSizeException;
import com.inventorsoft.model.Burger;
import com.inventorsoft.model.Ingredient;
import com.inventorsoft.model.User;
import com.inventorsoft.ui.ConsoleInterface;
import com.inventorsoft.validator.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

@Service
public class BurgerService {

    private static final Logger log = Logger.getLogger(ConsoleInterface.class.getName());

    private List<Burger> burgers;

    private BurgerValidation burgerValidation;
    private DataFileStorage<Burger> dataStorage;
    private IngredientService ic;
    private UserService uc;

    @Autowired
    public BurgerService(BurgerValidation burgerValidation, DataFileStorage<Burger> dataStorage, IngredientService ic, UserService uc) {
        this.burgerValidation = burgerValidation;
        this.dataStorage = dataStorage;
        this.ic = ic;
        this.uc = uc;
    }

    @PostConstruct
    private void updateDataFromFile(){
        log.info("update burgers from file (size): "+ dataStorage.getDataFromFileByList().size());
        burgers = dataStorage.getDataFromFileByList();
    }

    @PreDestroy
    private void saveDataToFile(){
        log.info("save burgers to file (size): "+ dataStorage.getDataFromFileByList().size());
        dataStorage.saveDataToFileByList(burgers);

    }

    public Burger addBurgerToList(String name, String author, List<String> ingredientList) throws DataAlreadyExistsException, WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException {
        Burger burger = createBurgerObject(burgerValidation.verifyBurgerData(name),author,ingredientList);

        if (burgerValidation.isBurgerAlreadyExists(burger, burgers)){
            addBurger(burger);
        }
        return burger;
    }

    public List<Burger> getBurgersList() {

        log.info("burgers(size): "+burgers.size());
        return burgers;
    }

    public void addBurger(Burger burger){
        burgers.add(burger);
    }

    public Burger getBurgerById(int id){
        return burgers.get(id-1);
    }

    private Burger createBurgerObject(String name, String author, List<String> ingredientList){
        boolean isAvailable = true;
        double price = 0.0;
        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredientName: ingredientList) {
            Ingredient ingredient = ic.getIngredientByName(ingredientName);
            price = price + ingredient.getPrice();
            if (ingredient.getAmount() == 0){
                isAvailable = false;
            }
            ingredients.add(ingredient);
        }
        return new Burger(name, author, price,0, ingredients, isAvailable);
    }

    public void orderBurgerByName(Burger orderedBurger, String nameCustomer) throws EmptyDataException {
        log.info("orderBurgerByName in: orderedBurger:"+ orderedBurger+", nameCustomer: "+ nameCustomer);

        User customer = uc.getUserByName(nameCustomer);
        User creator = uc.getUserByName(orderedBurger.getCreator());

        if (customer.getMoney() >= orderedBurger.getPrice()){

            log.info("no money: customer money: "+customer.getMoney()+", price: "+orderedBurger.getPrice());

            for (Ingredient ingredient: orderedBurger.getIngredient()) {
                ic.reduceIngredientAmount(ingredient.getIngredientName());
            }
        } else {
            throw new EmptyDataException("ERROR: Not enough money");
        }

        double creatorProfitWithCommissions = (orderedBurger.getPrice() * customer.getCommissions())/100;
        double companyProfitWithCommissions = (orderedBurger.getPrice() * (100 - customer.getCommissions()))/100;

        log.info("condition creator != null:"+String.valueOf(creator != null)
                +"!Objects.equals(creator.getLogin(), customer.getLogin()): "
                +String.valueOf(!Objects.equals(creator.getLogin(), customer.getLogin())));

        if (creator != null && !Objects.equals(creator.getLogin(), customer.getLogin())){


            uc.setCustomerLoss(customer,customer.getMoney()-orderedBurger.getPrice());
            uc.setCreatorProfit(creator,creatorProfitWithCommissions);
            uc.setCompanyProfit(companyProfitWithCommissions);
            log.info("This burger has creator: "+creator.getLogin()+", his profit: $"+creatorProfitWithCommissions+" company profit: $"+companyProfitWithCommissions+" | current commissions: "+customer.getCommissions()+"%");

            markOrderedBurger(orderedBurger.getName());

        } else {
            uc.setCustomerLoss(customer,customer.getMoney()-orderedBurger.getPrice());
            uc.setCompanyProfit(orderedBurger.getPrice());
            log.info("This burger has no creator, company profit: $"+orderedBurger.getPrice());

            markOrderedBurger(orderedBurger.getName());
        }

    }

    public void markOrderedBurger(String name){
        log.info("markOrderedBurger in (name): "+name);
        burgers.stream().filter(o -> o.getName().equals(name)).forEach(o -> o.setOrdersNumber(o.getOrdersNumber()+1));
        saveDataToFile();
    }

    public List<Burger> getSortedList(){
        return burgers.stream()
                .sorted(Comparator.comparingInt(Burger::getOrdersNumber).reversed())
                .limit(5)
                .collect(toList());
    }
}
