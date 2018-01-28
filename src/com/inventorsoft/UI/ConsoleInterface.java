package com.inventorsoft.UI;

import com.inventorsoft.Controller.BurgerController;
import com.inventorsoft.Controller.IngredientController;
import com.inventorsoft.Controller.UserController;
import com.inventorsoft.Exception.*;

import java.util.*;

public class ConsoleInterface {

    private String currentUser;

    public ConsoleInterface() {

        int role = askUserChoice("1 - CUSTOMER | 2 - FAST FOOD company");
        if (authorization(role)) {
            showMainChooseMenu(role);
        }
    }

    private boolean authorization(int role){
        boolean success = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("1 - LOGIN | 2 - REGISTRATION");
        int num = scan.nextInt();
        switch (num){
            case 1:
                success = login(role);
                break;
            case 2:
                success = registration(role);
                break;
        }
        return success;
    }


    private boolean login(int role){
        boolean isAdmin = false;
        UserController uc = new UserController();
        String login = askUserData("Login: ");
        String pass = askUserData("Password: ");
        if (role == 2){
            isAdmin = true;
        }
        try {
            uc.login(login,pass,isAdmin);
        } catch (DataAlreadyExistsException | EmptyDataException | ContainsIllegalCharactersException | WrongDataSizeException | WrongLoginPasswordException e) {
            System.out.println(e.getMessage());
            login(role);
        }
        currentUser = login;
        return true;
    }

    private boolean registration(int role){
        boolean isAdmin = false;
        UserController uc = new UserController();
        String login = askUserData("Login: ");
        String pass = askUserData("Password: ");
        if (role == 2){
            isAdmin = true;
        }
        try {
            uc.registration(login,pass,isAdmin);
        } catch (WrongDataSizeException | EmptyDataException | DataAlreadyExistsException | ContainsIllegalCharactersException e) {
            System.out.println(e.getMessage());
            registration(role);
        }
        currentUser = login;
        return true;
    }

    private String askUserData(String text){
        Scanner scan = new Scanner(System.in);
        System.out.print(text);
        try {
            text = scan.nextLine();
        } catch (Exception e){
            System.out.println("Enter correct values!");
            askUserChoice(askUserData(text));
        }
        return text;
    }

    private void showMainChooseMenu(int number){
    switch (number){
        case 1:
            do {
                chooseActionClientMenu();
                number = askUserChoice("Enter '0' if you want continue, any another number to finish");
            } while(number == 0);
            break;
        case 2:
            do {
                chooseActionAdminMenu();
                number = askUserChoice("Enter '0' if you want continue, any another number to finish");
            } while(number == 0);
            break;
        default:
            System.out.println("You might to press 1 or 2");
        }
    }

    private int askUserChoice(String text){
        int i = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println(text);
        try {
            i = scan.nextInt();
        } catch (Exception e){
            System.out.println("Enter correct value!");
            askUserChoice(text);
        }
         return i;
    }

    private int askUserChoiceInt(String text){
        int i = 0;
        Scanner scan = new Scanner(System.in);
        System.out.print(text);
        try {
            i = scan.nextInt();
        } catch (Exception e){
            System.out.println("Enter correct value!");
            askUserChoiceInt(text);
        }
        return i;
    }

    private double askUserChoiceDouble(String text){
        double i = 0.0;
        Scanner scan = new Scanner(System.in);
        scan.useLocale(Locale.US);
        System.out.print(text);
        try {
            i = scan.nextDouble();
        } catch (Exception e){
            System.out.println("Enter correct value!");
            askUserChoice(text);
        }
        return i;
    }

    private void showBurgers(){
        List<String> list = new BurgerController().getBurgersList();
        for (String str: list) {
            System.out.println(str);
        }
    }

    private void showIngredients(){
        List<String> list = new IngredientController().getIngredients();
        int i = 1;
        for (String str:list) {
            System.out.print(i+": "+str+" ");
            i++;
        }
        System.out.println();
    }

    private String getIngredientById(int id){
        List<String> list = new IngredientController().getIngredients();
        return list.get(id-1);
    }


    private void createBurger(){
        int number;
        Scanner scan = new Scanner(System.in);
        System.out.print("Name burger: ");
        String burgerName = scan.nextLine();
        System.out.println("Choose ingredient");
        List<String> ingredients = new ArrayList<>();
        do {
            showIngredients();
            number = scan.nextInt();
            ingredients.add(getIngredientById(number));
            System.out.println("Press '0' to add one more ingredient, any another key to finish");
            number = scan.nextInt();
        } while(number == 0);
        new BurgerController().saveBurger(burgerName,5,ingredients);
        System.out.println("Burger"+burgerName+" just created");

    }

    private void buyBurger(){
        String nameBurger = getNameBurgerById();
        new BurgerController().deleteBurger(nameBurger);

    }

    private void addIngredient(){
        IngredientController ic = new IngredientController();
        String ingrName = askUserData("Ingredient name: ");
        double ingrPrice = askUserChoiceDouble("Ingredient price: ");
        int ingrAmount = askUserChoiceInt("Ingredient amount: ");
        try {
            ic.addIngredient(ingrName,ingrPrice,ingrAmount);
        } catch (WrongDataSizeException | EmptyDataException | ContainsIllegalCharactersException | IllegalArgumentException | DataAlreadyExistsException e) {
            System.out.println(e.getMessage());
            addIngredient();
        }
    }

    private String getNameBurgerById() {
        int number;
        int i = 1;
        Scanner scan = new Scanner(System.in);
        BurgerController co = new BurgerController();

        List<String> list = co.getBurgersList();
        for (String str: list) {
            System.out.println(i+": "+str);
            i++;
        }
        number = scan.nextInt();
        //String str =  list.get(number-1);
        return co.getBurgerString(number-1);

    }

    private void chooseActionClientMenu(){
        System.out.println("1 - AVAILABLE BURGERS | 2 - BUY BURGERS | 3 - CREATE BURGERS | 4 - VIEW TOP | 5 - VIEW BALANCE");
        Scanner scan = new Scanner(System.in);
        int number = scan.nextInt();
        switch (number){
            case 1:
                showBurgers();
                break;
            case 2:
                buyBurger();
                break;
            case 3:
                createBurger();
                break;
            case 4:
                System.out.println("feature is not available yet");
                break;
            case 5:
                System.out.println("feature is not available yet");
                break;
            default:
                System.out.println("-Not Allowed");
                break;
        }
    }

    private void chooseActionAdminMenu(){
        int key = askUserChoice("1 - CREATED BURGERS | 2 - VIEW INGREDIENTS | 3 - ADD INGREDIENT | 4 - VIEW BALANCE");
        switch (key){
            case 1:
                showBurgers();
                break;
            case 2:
                showIngredients();
                break;
            case 3:
                addIngredient();
                break;
            case 4:
                System.out.println("feature is not available yet");
                break;
            default:
                System.out.println("-Not Allowed");
                break;
        }
    }

}
