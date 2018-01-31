package com.inventorsoft.Service;

import com.inventorsoft.Controller.IngredientController;
import com.inventorsoft.Model.Burger;
import com.inventorsoft.Model.Ingredient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataFileStorageBurger implements DataFileStorage<Burger> {

    private final static String FILE_NAME = "/burgers";

    @Override
    public List<Burger> getDataFromFileByList() {
        File file = new File(FILE_PATH,FILE_NAME+FILE_FORMAT);
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

    @Override
    public void saveDataToFileByList(List<Burger> list) {
        File file = new File(FILE_PATH,FILE_NAME+FILE_FORMAT);

        try(FileWriter fileWriter = new FileWriter(file, false);
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
}
