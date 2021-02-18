package com.schoolwork.zoos.controller;

import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/zoos")
public class ZooController {
    @Autowired
    DatabaseService databaseService;

    private static final Logger logger = LoggerFactory.getLogger(ZooController.class);

    // http://localhost:2019/zoos/zoos
    @GetMapping(value = "/zoos", produces = "application/json")
    ResponseEntity<?> getZoos(){
        logger.info("Accessed get zoos endpoint");
        return new ResponseEntity<>(databaseService.getZoos(), HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo/{id}
    @GetMapping(value = "/zoo/{id}", produces = "application/json")
    ResponseEntity<?> getZooById(@PathVariable long id){
        logger.info("zoos/zoo/{id} Access get zoo by Id " + id);
        Zoo zoo = databaseService.getZooById(id);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo/namelike/{name}
    @GetMapping(value = "/zoo/namelike/{name}", produces = "application/json")
    ResponseEntity<?> getZoosByLikeName(@PathVariable String name){
        logger.info("Access zoos with similar name to '" + name + "'. zoos/zoo/namelike/{name}");
        List<Zoo> zoos = databaseService.getZoosByLikeName(name);
        return new ResponseEntity<>(zoos, HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo
    @PostMapping(value = "/zoo", consumes = "application/json")
    ResponseEntity<?> addZoo(@Valid @RequestBody Zoo zoo){
        Zoo newZoo = databaseService.addZoo(zoo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zooid}")
                .buildAndExpand(newZoo.getZooid())
                .toUri();
        responseHeaders.setLocation(newRoleURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // http://localhost:2019/zoos/zoo/{id}
    @PutMapping(value = "/zoo/{id}", consumes = "application/json")
    ResponseEntity<?> updateZoo(@PathVariable long id, @RequestBody Zoo zoo){
        databaseService.updateZoo(id, zoo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo/{id}
    @DeleteMapping(value = "/zoo/{id}")
    ResponseEntity<?> deleteZoo(@PathVariable long id){
        databaseService.deleteZoo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo/{zooid}/animals/{animalid}
    @DeleteMapping(value = "/zoo/{zooid}/animals/{animalid}")
    ResponseEntity<?> removeAnimalFromZoo(@PathVariable long zooid, @PathVariable long animalid){
        databaseService.removeAnimalFromZoo(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/zoos/zoo/{zooid}/animals/{animalid}
    @PostMapping(value = "/zoo/{zooid}/animals/{animalid}")
    ResponseEntity<?> addAnimalToZoo(@PathVariable long zooid, @PathVariable long animalid){
        databaseService.addAnimalToZoo(zooid, animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
