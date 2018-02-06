package com.inventorsoft.controller;

import com.inventorsoft.model.User;
import com.inventorsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getBurgers() {
        return userService.getUsersList();
    }

    @RequestMapping(value = "/{productId:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable int productId) {
        return new ResponseEntity<>(userService.getUsersById(productId), HttpStatus.OK);
    }
}