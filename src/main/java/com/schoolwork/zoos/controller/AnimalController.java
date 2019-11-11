package com.schoolwork.zoos.controller;

import com.schoolwork.zoos.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/animals")
public class AnimalController {
    @Autowired
    DatabaseService databaseService;

    // http://localhost:2019/animals/count
    @GetMapping(value = "/count", produces = "application/json")
    private ResponseEntity<?> getAnimalPresenceZooCount(){
        return new ResponseEntity<>(databaseService.getCountOfAnimalPresenceAtZoos(), HttpStatus.OK);
    }
}
