package com.inventorsoft.dao;

import com.inventorsoft.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataFileStorageUser implements DataFileStorage<User> {

    private final static String FILE_NAME = "/users";

    @Override
    public List<User> getDataFromFileByList() {
        File file = new File(FILE_PATH, FILE_NAME + FILE_FORMAT);
        List<User> users = new ArrayList<>();
        try {
            Scanner read = new Scanner(file);

            read.useDelimiter(",|;");

            while (read.hasNext()) {
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

    @Override
    public void saveDataToFileByList(List<User> list) {

        File file = new File(FILE_PATH, FILE_NAME + FILE_FORMAT);

        try (FileWriter fileWriter = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fileWriter);

             PrintWriter pw = new PrintWriter(bw)) {
            for (User user : list) {
                pw.print(user.getLogin() + ",");
                pw.print(user.getPassword() + ",");
                pw.print(user.getMoney() + ",");
                pw.print(user.getCommissions() + ",");
                pw.print(String.valueOf(user.isAdmin()) + ";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}