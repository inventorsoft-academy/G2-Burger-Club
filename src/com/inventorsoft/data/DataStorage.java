package com.inventorsoft.data;

import com.inventorsoft.Controller.IngredientController;
import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;
import com.inventorsoft.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataStorage {

    private final static String FILE_PATH = "src/com/inventorsoft/data";
    private final static String FILE_FORMAT = ".txt";


    /*
    *       USER:
    * */

    public static void saveUserToFile2(List<User> list) {
        File file = new File(FILE_PATH,"/users"+FILE_FORMAT);

        try( FileWriter fileWriter = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fileWriter);

             PrintWriter pw = new PrintWriter(bw)){
            for (User user:list) {
                pw.print(user.getLogin() + ",");
                pw.print(user.getPassword() + ",");
                pw.print(user.getMoney() + ",");
                pw.print(user.getCommissions() + ",");
                pw.print(String.valueOf(user.isAdmin()) + ";");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static List<User> getAllUsers(){
        File file = new File(FILE_PATH,"/users"+FILE_FORMAT);
        List<User> users = new ArrayList<>();
        try {
            Scanner read = new Scanner(file);

            read.useDelimiter(",|;");

            while (read.hasNext()){
                users.add(new User(
                        read.next(),
                        read.next(),
                        Double.valueOf(read.next()),
                        Integer.valueOf(read.next()),
                        Boolean.valueOf(read.next())));
            }
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    /*
    *       BURGER:
    * */

    public void saveBurgersToFile(List<Burger> list) {
        File file = new File(FILE_PATH,"/burgers"+FILE_FORMAT);

        try( FileWriter fileWriter = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fileWriter);

             PrintWriter pw = new PrintWriter(bw)){

            for (Burger burger:list) {
                pw.print(burger.getName() + ",");
                pw.print(burger.getCreator() + ",");
                pw.print(burger.getOrdersNumber() + ",");
                pw.print(String.join(",",burger
                        .getIngredient()
                        .stream()
                        .map(Ingredient::getIngredientName)
                        .collect(Collectors.toList())));
                pw.print(","+burger.isAvailable()+";");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Burger> getBurgersFromFile(){
        File file = new File(FILE_PATH,"/burgers"+FILE_FORMAT);
        List<Burger> burgers = new ArrayList<>();
        IngredientController ic = new IngredientController();
        try {
            Scanner read = new Scanner(file);
            read.useDelimiter(",|;");

            while (read.hasNext()){
                String name = read.next();
                String author = read.next();
                int orders = Integer.valueOf(read.next());
                boolean isAvailable = true;
                List<Ingredient> ingredients = new ArrayList<>();
                double price = 0.0;
                do {
                    Ingredient ingredient = ic.getIngredientByName(read.next());
                    if (ingredient.getAmount() == 0){
                        isAvailable = false;
                    }
                    price = price + ingredient.getPrice();
                    ingredients.add(ingredient);
                } while (!read.hasNextBoolean());
                read.next();
                Burger burger = new Burger(name, author, price, orders,ingredients, isAvailable);
                burgers.add(burger);
            }
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return burgers;
    }

    /*
    *       INGREDIENT:
    * */

    public void saveIngredientsToFile(List<Ingredient> list) {
        File file = new File(FILE_PATH,"/ingredients"+FILE_FORMAT);

        try( FileWriter fileWriter = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fileWriter);

             PrintWriter pw = new PrintWriter(bw)){
            for (Ingredient ingredient:list) {
                pw.print(ingredient.getIngredientName() + ",");
                pw.print(ingredient.getPrice() + ",");
                pw.print(ingredient.getAmount() + ";");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public List<Ingredient> getIngredientsFromFile(){
        File file = new File(FILE_PATH,"/ingredients"+FILE_FORMAT);
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            Scanner read = new Scanner(file);
            read.useDelimiter(",|;");

            while (read.hasNext()){
                ingredients.add(new Ingredient(
                        read.next(),
                        Double.valueOf(read.next()),
                        Integer.valueOf(read.next())));
            }
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ingredients;
    }


}
