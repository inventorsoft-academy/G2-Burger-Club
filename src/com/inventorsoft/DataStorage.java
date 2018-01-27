package com.inventorsoft;

import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;
import com.inventorsoft.Model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DataStorage {

    private final static String USERS_FILE_PATH = "src/com/inventorsoft/data";
    private final static String BURGERS_FILE_PATH = "src/com/inventorsoft/data/burgers";
    private final static String INGREDIENTS_FILE_PATH = "src/com/inventorsoft/data";

    private final static String FILE_FORMAT = ".txt";

    public List<Ingredient> getIngredientsFromFile() throws IOException {

        List<Ingredient> ingredients = new ArrayList<>();
        FileReader fileReader = new FileReader(INGREDIENTS_FILE_PATH + "/ingredients.txt");
        Scanner scan = new Scanner(fileReader);
        while (scan.hasNextLine()) {
            ingredients.add(new Ingredient(scan.nextLine()));
        }
        return ingredients;

    }

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

    public boolean isIngredientExists(Ingredient ingredient) throws IOException {
        boolean isExists = false;
        FileReader fileReader = new FileReader(INGREDIENTS_FILE_PATH + "/ingredients.txt");
        Scanner scan = new Scanner(fileReader);
        while (scan.hasNextLine()) {
            if (ingredient.getIngredientName().equals(scan.nextLine())){
                isExists = true;
                break;
            }
        }
        return isExists;

    }

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
            System.out.println("Not saved to file!");
        }
        System.out.println(file.getAbsolutePath());
        FileWriter fileWriter = new FileWriter(file,false);

        fileWriter.write(burger.getName() + "\r\n");
        fileWriter.write(burger.getPrice() + "\r\n");

        for (Ingredient sd:burger.getIngredient()) {
            fileWriter.write(sd.getIngredientName() + "\r\n");
        }

        fileWriter.close();
    }



    public User getUserFromFileString(String userName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH+"/"+userName+FILE_FORMAT));
        User user = new User(
                reader.readLine(),
                reader.readLine(),
                Double.valueOf(reader.readLine()),
                Boolean.valueOf(reader.readLine()));
        reader.close();
        return user;
    }

    public User getUserFromFile(File file) throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH+"/"+file.getName()));
        User user = new User(
                reader.readLine(),
                reader.readLine(),
                Double.valueOf(reader.readLine()),
                Boolean.valueOf(reader.readLine()));
        reader.close();
        return user;
    }

    public void deleteBurgerFile(String burgerName){
        boolean isDeleted = false;
        File file = new File(BURGERS_FILE_PATH, "/"+burgerName+FILE_FORMAT);
        System.out.println(file.getName());
        isDeleted = file.delete();
        if (isDeleted){
            System.out.println("file: "+burgerName+FILE_FORMAT+" deleted");
        }
    }

    public void saveUserToFile(User user) throws IOException {
        File file = new File(USERS_FILE_PATH,"/"+user.getLogin()+FILE_FORMAT);

        if (file.createNewFile()) {
            System.out.println(file.getAbsolutePath());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(user.getLogin() + "\r\n");
            fileWriter.write(user.getPassword() + "\r\n");
            fileWriter.write(user.getMoney() + "\r\n");
            fileWriter.write(String.valueOf(user.isAdmin()) + "\r\n");
            fileWriter.close();
        }

        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(
                Paths.get(USERS_FILE_PATH + "/"+user.getLogin()+"2"+FILE_FORMAT)))) {

            IntStream.range(0, 5000)
                    .mapToObj(String::valueOf)
                    .forEach(pw::print);
        }
    }
    public void saveIngredientToFile(Ingredient ingredient) throws IOException{
        File file = new File(INGREDIENTS_FILE_PATH,"/ingredients.txt");

        if (!file.createNewFile()){
            System.out.println("File not created!");
        }

        System.out.println(file.getAbsolutePath());
        FileWriter fileWriter = new FileWriter(file,true);

        if (isIngredientExists(ingredient)){
            System.out.println(ingredient.getIngredientName()+" already exists");
        } else {
            fileWriter.write(ingredient.getIngredientName() + "\r\n");
            fileWriter.close();
        }
    }

    public static void saveUserToFile2(List<User> list) {
        File file = new File(USERS_FILE_PATH,"/users.txt");


            try( FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            PrintWriter pw = new PrintWriter(bw)){
                System.out.println("pw"+list.get(0).getPassword());
                for (User user:list) {
                    pw.print(user.getLogin() + ",");
                    System.out.println(user.getLogin());
                    pw.print(user.getPassword() + ",");
                    pw.print(user.getMoney() + ",");
                    pw.print(String.valueOf(user.isAdmin()) + ";");
                }
            } catch (IOException e){
                e.printStackTrace();
            }


    }

    public static List<User> getAllUsers(){
        File file = new File(USERS_FILE_PATH,"/users.txt");
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


}
