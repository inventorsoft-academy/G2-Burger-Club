package com.inventorsoft.Controller;

import com.inventorsoft.Exception.ContainsIllegalCharactersException;
import com.inventorsoft.Exception.DataAlreadyExistsException;
import com.inventorsoft.Exception.EmptyDataException;
import com.inventorsoft.Exception.WrongDataSizeException;
import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;
import com.inventorsoft.Model.User;
import com.inventorsoft.Service.DataFileStorage;
import com.inventorsoft.Service.DataFileStorageBurger;
import com.inventorsoft.Service.DataVerification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class BurgerController {

    private List<Burger> burgers = new ArrayList<>();
    private DataVerification dv = new DataVerification();
    private DataFileStorage<Burger> dataStorage = new DataFileStorageBurger();
    private IngredientController ic = new IngredientController();
    private UserController uc = new UserController();

    private void updateDataFromFile(){
        burgers = dataStorage.getDataFromFileByList();
    }

    private void saveDataToFile(){
        dataStorage.saveDataToFileByList(burgers);
    }

    public String addBurgerToList(String name, String author, List<String> ingredientList) throws DataAlreadyExistsException, WrongDataSizeException, EmptyDataException, ContainsIllegalCharactersException {
        updateDataFromFile();

        Burger burger = createBurgerObject(dv.verifyData(name),(author),ingredientList);
        if (dv.matchCheck(burger, burgers)){
            burgers.add(burger);
        }
        saveDataToFile();
        return name + ingredientList;
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


    public List<String> getAvailableBurgersList() throws EmptyDataException {

        updateDataFromFile();
        boolean isAvailable = false;
        List<String> list = new ArrayList<>();
        for (Burger burger: burgers) {
            if (burger.isAvailable()){
                String text = "";
                isAvailable = true;
                for (Ingredient ingredient: burger.getIngredient()) {
                    text += " "+ingredient.getIngredientName()+"($"+ingredient.getPrice()+")";
                }
                list.add(burger.getName()+"($"+burger.getPrice()+") : "+text+" ["+burger.getCreator()+"]");
            }
        }
        if (!isAvailable){
                throw new EmptyDataException("ERROR: Available burgers are over");
        }

        saveDataToFile();
        return list;
    }

    public List<String> getFullBurgersListForCustomer(){

        updateDataFromFile();

        List<String> list = new ArrayList<>();
        for (Burger burger: burgers) {
            String text = "";
            for (Ingredient ingredient: burger.getIngredient()) {
                text += " "+ingredient.getIngredientName()+"("+ingredient.getAmount()+")";
            }
            list.add(burger.getName()+"($"+burger.getPrice()+") : "+text);
        }
        return list;
    }

    public String getBurgerString(int id){
        updateDataFromFile();

        return burgers.get(id).getName();
    }

    public String orderBurgerByName(String nameBurger, String nameCustomer) throws EmptyDataException {
        updateDataFromFile();
        Burger orderedBurger = burgers.stream().filter(o -> o.getName().equals(nameBurger)).findFirst().orElse(null);
        User customer = uc.getUserByName(nameCustomer);
        User creator = uc.getUserByName(orderedBurger.getCreator());

        if (customer.getMoney() >= orderedBurger.getPrice()){

            for (Ingredient ingredient: orderedBurger.getIngredient()) {
                ic.reduceIngredientAmount(ingredient.getIngredientName());
            }

        } else {
            throw new EmptyDataException("ERROR: Not enough money");
        }

        double creatorProfitWithCommissions = (orderedBurger.getPrice() * customer.getCommissions())/100;
        double companyProfitWithCommissions = (orderedBurger.getPrice() * (100 - customer.getCommissions()))/100;

        if (creator != null){
            uc.setCustomerLoss(customer,customer.getMoney()-orderedBurger.getPrice());
            uc.setCreatorProfit(creator,creatorProfitWithCommissions);
            uc.setCompanyProfit(companyProfitWithCommissions);

            return "This burger has creator: "+creator.getLogin()+", his profit: $"+creatorProfitWithCommissions+" company profit: $"+companyProfitWithCommissions+" | current commissions: "+customer.getCommissions()+"%";

        } else {
            uc.setCustomerLoss(customer,customer.getMoney()-orderedBurger.getPrice());
            uc.setCompanyProfit(orderedBurger.getPrice());

            return "This burger has no creator, company profit: $"+orderedBurger.getPrice();
        }


    }

    public void markOrderedBurger(String name){
        updateDataFromFile();
        burgers.stream().filter(o -> o.getName().equals(name)).forEach(o -> o.setOrdersNumber(o.getOrdersNumber()+1));
        saveDataToFile();
    }

    public List<String> getSortedList(){
        updateDataFromFile();
        burgers.sort(comparing(Burger::getOrdersNumber));
        List<Burger> burgersList =
                burgers.stream()
                        .sorted(Comparator.comparingInt(Burger::getOrdersNumber).reversed())
                .collect(toList());

        List<String> list = new ArrayList<>();
        for (Burger burger: burgersList) {
            if (burger.isAvailable()){
                list.add(burger.getOrdersNumber()+" "+burger.getName()+" : "+burger.getIngredient().stream()
                        .map(Ingredient::getIngredientName).collect(toList()));
            }
        }
        return list;
    }


}
