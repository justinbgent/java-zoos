package com.schoolwork.zoos.service;

import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.model.ZooAnimals;
import com.schoolwork.zoos.view.AnimalsCountZoos;

import java.util.List;

public interface DatabaseService {
    List<AnimalsCountZoos> getCountOfAnimalPresenceAtZoos();

    List<Zoo> getZoos();

    Zoo getZooById(long id);

    List<Zoo> getZoosByLikeName(String name);

    Zoo addZoo(Zoo zoo);

    Zoo updateZoo(long id, Zoo zoo);

    void deleteZoo(long id);

    void removeAnimalFromZoo(long zooid, long animalid);

    Animal addAnimalToZoo(long zooid, long animalid);
}
