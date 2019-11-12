package com.schoolwork.zoos.controller;

import com.schoolwork.zoos.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    // http://localhost:2019/animals/count
    @GetMapping(value = "/count", produces = "application/json")
    private ResponseEntity<?> getAnimalPresenceZooCount(){
        logger.info("Accessed count of animals in zoo.");
        return new ResponseEntity<>(databaseService.getCountOfAnimalPresenceAtZoos(), HttpStatus.OK);
    }
}
