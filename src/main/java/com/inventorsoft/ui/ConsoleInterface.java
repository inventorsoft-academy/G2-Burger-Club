package com.inventorsoft.ui;

import com.inventorsoft.exception.*;
import com.inventorsoft.service.BurgerService;
import com.inventorsoft.service.IngredientService;
import com.inventorsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ConsoleInterface implements CommandLineRunner{

    private static final Logger log = Logger.getLogger(ConsoleInterface.class.getName());


    private UserService userService;
    private BurgerService burgerService;
    private IngredientService ingredientService;

    public ConsoleInterface() {
    }

    @Autowired
    public ConsoleInterface(UserService userService, BurgerService burgerService, IngredientService ingredientService) {
        this.userService = userService;
        this.burgerService = burgerService;
        this.ingredientService = ingredientService;
    }

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void run(String... strings) throws Exception {
        start();
    }

    private void start(){
        int role = askUserChoiceBetweenTwoNumbers("1 - CUSTOMER | 2 - FAST FOOD company");
        if (role == 0){
            start();
        }
        if (authorization(role)) {
            log.log(Level.INFO, "role: "+role);
            showMainChooseMenu(role);
        }
    }

    private boolean authorization(int role){

        boolean success = true;
        int num = askUserChoiceBetweenTwoNumbers("1 - LOGIN | 2 - REGISTRATION");
        if (num == 0){
            authorization(role);
        }
        switch (num){
            case 1:
                success = login(role);
                break;
            case 2:
                success = registration(role);
                break;
            default:
                break;
        }
        return success;
    }

    private boolean login(int role){
        boolean isAdmin = false;

        String login = askUserData("Login: ");
        String pass = askUserData("Password: ");
        if (role == 2){
            isAdmin = true;
        }
        try {
            userService.login(login,pass,isAdmin);
            setCurrentUser(login);
        } catch (DataAlreadyExistsException | EmptyDataException | ContainsIllegalCharactersException | WrongDataSizeException | WrongLoginPasswordException e) {
            log.log(Level.SEVERE, "login exception: ",e);
            System.out.println(e.getMessage());
            login(role);
        }
        return true;
    }

    private boolean registration(int role){
        boolean isAdmin = false;
        String login = askUserData("Login: ");
        String pass = askUserData("Password: ");
        if (role == 2){
            isAdmin = true;
        }
        try {
            userService.registration(login,pass,isAdmin);
            setCurrentUser(login);
        } catch (WrongDataSizeException | EmptyDataException | DataAlreadyExistsException | ContainsIllegalCharactersException e) {
            log.log(Level.SEVERE, "registration exception: ",e);
            System.out.println(e.getMessage());
            registration(role);
        }
        return true;
    }

    private String askUserData(String text){
        Scanner scan = new Scanner(System.in);
        System.out.print(text);
        try {
            text = scan.nextLine();
        } catch (Exception e){
            System.out.println(e.getMessage()+"Enter correct values!");
            askUserData(text);
        }
        return text;
    }


    private void showMainChooseMenu(int number){
    switch (number){
        case 1:
            do {
                chooseActionClientMenu();
                number = askUserMenuChoice("Enter '0' if you want continue, any another number to finish");
            } while(number == 0);
            break;
        case 2:
            do {
                chooseActionAdminMenu();
                number = askUserMenuChoice("Enter '0' if you want continue, any another number to finish");
            } while(number == 0);
            break;
        default:
            System.out.println("You might to press 1 or 2");
        }
    }

    private int askUserChoiceBetweenTwoNumbers(String text){
            Scanner scan = new Scanner(System.in);
            System.out.println(text);
            try {
                int i = scan.nextInt();
                if (i == 1 || i == 2) {
                    return i;
                }
                else {
                    throw new WrongDataSizeException();
                }
            } catch (InputMismatchException | WrongDataSizeException e){
                System.out.println("Enter correct value!");
                askUserChoiceBetweenTwoNumbers(text);

            }
                return 0;

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

    private int askUserMenuChoice(String text){
        int i = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println(text);
        try {
            i = scan.nextInt();
        } catch (Exception e){
            System.out.println("Enter correct value!");
            askUserMenuChoice(text);
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
            askUserChoiceDouble(text);
        }
        return i;
    }

    private void showAvailableBurgers(){
        try {
            log.log(Level.INFO, "showAvailableBurgers method");
            List<String> list = burgerService.getAvailableBurgersList();
            for (String text: list) {
                System.out.println(text);
            }
        } catch (EmptyDataException e) {
            System.out.println(e.getMessage());
        }

    }

    private void showCreatedBurgers(){
        List<String> list = burgerService.getFullBurgersListForCustomer();
        for (String text: list) {

            System.out.println(text);
        }
    }

    private void showIngredients(){
        List<String> list = ingredientService.getIngredientsForCustomer();
        int i = 1;
        for (String str:list) {
            System.out.print(i+": "+str+" ");
            i++;
        }
        System.out.println();
    }

    private String getIngredientById(int id){
        List<String> list = ingredientService.getIngredients();
        return list.get(id-1);
    }


    private void createBurger(){
        int number;
        Scanner scan = new Scanner(System.in);
        System.out.print("Name burger: ");
        String burgerName = scan.nextLine();
        System.out.println("Choose ingredient");
        List<String> ingredients = new ArrayList<>();
        ingredients.add("buns");
        do {
            showIngredients();
            number = scan.nextInt();
            String ingr = getIngredientById(number);
            if (!ingredients.contains(ingr)){
                ingredients.add(ingr);
            } else {
                System.out.println("Ingredient is already exists, choose another");
            }


            System.out.println("Press '0' to add one more ingredient, any another key to finish");
            number = scan.nextInt();
        } while(number == 0);
        try {
            String result = burgerService.addBurgerToList(burgerName,getCurrentUser(),ingredients);
            System.out.println("Burger: "+result+" just created!");
        } catch (DataAlreadyExistsException | WrongDataSizeException | EmptyDataException | ContainsIllegalCharactersException e) {
            System.out.println(e.getMessage());
            createBurger();
        }

    }

    private void buyBurger(){
        //BurgerService bc = new BurgerService();

        try {
            String nameBurger = getNameBurgerById();
            String profitInfo = burgerService.orderBurgerByName(nameBurger, getCurrentUser());
            burgerService.markOrderedBurger(nameBurger);
            System.out.println("You bought "+nameBurger);
            System.out.println(profitInfo);
        } catch (EmptyDataException e) {
            System.out.println(e.getMessage());

        }


    }

    private void addIngredient(){
        //IngredientService ic = new IngredientService();
        showIngredients();
        String ingrName = askUserData("Ingredient name: ");
        double ingrPrice = askUserChoiceDouble("Ingredient price: ");
        int ingrAmount = askUserChoiceInt("Ingredient amount: ");
        try {
            ingredientService.addIngredient(ingrName,ingrPrice,ingrAmount);
        } catch (WrongDataSizeException | EmptyDataException | ContainsIllegalCharactersException | IllegalArgumentException | DataAlreadyExistsException e) {
            System.out.println(e.getMessage());
            addIngredient();
        }
    }

    private String getNameBurgerById() throws EmptyDataException {
        int i = 1;
        //BurgerService co = new BurgerService();

        List<String> list = burgerService.getAvailableBurgersList();
        for (String text: list) {
            System.out.println(i+": "+text);
            i++;
        }
        int number = askUserChoiceInt("Type burger number: ");
        return burgerService.getBurgerString(number-1);

    }

    private void viewTop(){
        List<String> list = burgerService.getSortedList();
        for (String text: list) {
            System.out.println(text);
        }

    }

    private void showBalance(){
        System.out.println(userService.getBalance(getCurrentUser()));
    }

    private void setCommissions(){
        int comm = askUserChoiceInt("Commissions for creators (1% - 50%): ");
        userService.setCommissions(comm);
    }

    private void chooseActionClientMenu(){
        int number = askUserMenuChoice("1 - AVAILABLE BURGERS | 2 - BUY BURGERS | 3 - CREATE BURGERS | 4 - VIEW TOP | 5 - VIEW BALANCE");
        switch (number){
            case 1:
                showAvailableBurgers();
                break;
            case 2:
                buyBurger();
                break;
            case 3:
                createBurger();
                break;
            case 4:
                viewTop();
                break;
            case 5:
                showBalance();
                break;
            default:
                System.out.println("ERROR: Not Allowed");
                break;
        }
    }

    private void chooseActionAdminMenu(){
        int key = askUserMenuChoice("1 - CREATED BURGERS | 2 - VIEW INGREDIENTS | 3 - ADD INGREDIENT | 4 - VIEW BALANCE | 5 - SET UP COMMISSIONS");
        switch (key){
            case 1:
                showCreatedBurgers();
                break;
            case 2:
                showIngredients();
                break;
            case 3:
                addIngredient();
                break;
            case 4:
                showBalance();
                break;
            case 5:
                setCommissions();
                break;
            default:
                System.out.println("ERROR: Not allowed");
                break;
        }
    }


}
