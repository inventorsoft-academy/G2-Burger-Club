package com.inventorsoft.Service;

import java.util.List;

public interface DataFileStorage<T> {

    String FILE_PATH = "src/com/inventorsoft/data";
    String FILE_FORMAT = ".txt";

    List<T> getDataFromFileByList();
    void saveDataToFileByList(List<T> list);
}
