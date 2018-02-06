package com.inventorsoft.dao;

import com.inventorsoft.model.Ingredient;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class DataFileStorageIngredient implements DataFileStorage<Ingredient> {

    private final static String FILE_NAME = "/ingredients";

    @Override
    public List<Ingredient> getDataFromFileByList() {
        File file = new File(FILE_PATH,FILE_NAME+FILE_FORMAT);
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

    @Override
    public void saveDataToFileByList(List<Ingredient> list) {
        File file = new File(FILE_PATH,FILE_NAME+FILE_FORMAT);

        try(FileWriter fileWriter = new FileWriter(file, false);
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
}
