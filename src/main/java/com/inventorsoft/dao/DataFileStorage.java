package com.inventorsoft.dao;

import java.util.List;

public interface DataFileStorage<T> {

    String FILE_PATH = "src/main/resources/data";
    String FILE_FORMAT = ".txt";

    List<T> getDataFromFileByList();
    void saveDataToFileByList(List<T> list);
}
