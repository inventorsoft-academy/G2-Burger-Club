package com.inventorsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class,args);

    }
}

/*


        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new ConsoleInterface();
    * ПАКЕТИ МАЛЕНЬКИМИ БУКВАМИ
    * ДОБАВИТИ ЛОГЕР У ФАЙЛ
    * ДОБАВИТИ LOG OUT
    * ПРИ ЛОГАУТ І ПРИ ІНІЦІАЛІЗАЦІЇ РОБОТА З ФАЙЛАМИ
    * data В ПАПЦІ РЕСУРСИ src/main/resources
    * РІЗНУ ВЕРИФИКАЦІЮ ДЛЯ РІЗНИХ СУЩНОСТЕЙ
    * КОНСОЛЬНИЙ ІНТЕРФЕЙС МОЖЕ ПОЛУЧАТИ ОБЄКТИ ЩОБ ЇХ ВИВОДИТИ
    * ОГРАНИЧИТИ ДВОМА ЗНАКАМИ ПІСЛЯ КОМИ ПИ ВИВОДІ ГРОШЕЙ
    * ВИВІД ТОП ОГРАНИЧИТИ ДО ТОП 5
    * НАЗВИ МЕТОДІВ ДЕТАЛЬНІ
    * Sergey не може получити бонус за свій же бургер
    *
    * */
