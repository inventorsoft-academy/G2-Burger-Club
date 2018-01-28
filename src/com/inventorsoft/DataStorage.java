package com.inventorsoft;

import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;
import com.inventorsoft.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataStorage {

    private final static String FILE_PATH = "src/com/inventorsoft/data";
    private final static String BURGERS_FILE_PATH = "src/com/inventorsoft/data/burgers";

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

    public Burger getBurgerFromFile(File file) throws IOException{
        FileReader fileReader = new FileReader(BURGERS_FILE_PATH+"/"+file.getName());
        BufferedReader reader = new BufferedReader(fileReader);
        Burger burger = new Burger(reader.readLine(), Double.valueOf(reader.readLine()));
        String line;
        List<Ingredient> ingredients = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            ingredients.add(new Ingredient(line));
        }
        burger.setIngredient(ingredients);
        reader.close();
        return burger;
    }

    public List<Burger> getAllBurgersList() throws IOException{
        List<Burger> list = new ArrayList<>();
        File file = new File(BURGERS_FILE_PATH);
        File[] files = file.listFiles();
        for (File v : files) {
            list.add(getBurgerFromFile(v));
        }
        return list;

    }

    public void saveBurgerToFile(Burger burger) throws IOException{
        File file = new File(BURGERS_FILE_PATH,"/"+burger.getName()+FILE_FORMAT);

        if (!file.createNewFile()){
        }
        FileWriter fileWriter = new FileWriter(file,false);

        fileWriter.write(burger.getName() + "\r\n");
        fileWriter.write(burger.getPrice() + "\r\n");

        for (Ingredient sd:burger.getIngredient()) {
            fileWriter.write(sd.getIngredientName() + "\r\n");
        }

        fileWriter.close();
    }

    public void deleteBurgerFile(String burgerName){
        boolean isDeleted = false;
        File file = new File(BURGERS_FILE_PATH, "/"+burgerName+FILE_FORMAT);
        isDeleted = file.delete();
        if (isDeleted){
        }
    }


    /*
    *       INGREDIENT:
    * */


    public Ingredient getIngredientByNameString(String name) throws IOException{
        Ingredient ingr = new Ingredient();
        List<Ingredient> ingredients = getIngredientsFromFile();
        for (Ingredient ingredient: ingredients) {
            if (ingredient.getIngredientName().equals(name)){
                ingr.setIngredientName(ingredient.getIngredientName());
            }
        }
        return ingr;
    }


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
