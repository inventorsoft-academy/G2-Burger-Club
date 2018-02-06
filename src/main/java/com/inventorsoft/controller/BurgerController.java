package com.inventorsoft.controller;


import com.inventorsoft.model.Burger;
import com.inventorsoft.service.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/burger")
public class BurgerController {

    private BurgerService burgerService;

    @Autowired
    public BurgerController(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Burger> getBurgers() {
        return burgerService.getBurgersList();
    }

    @RequestMapping(value = "/{productId:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<Burger> getById(@PathVariable int productId) {
         return new ResponseEntity<>(burgerService.getBurgerById(productId), HttpStatus.OK);
    }




}
