package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.view.JustTheCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT COUNT(*) as count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    JustTheCount getCount();


    ZooAnimals getCountOfAnimalPresenceAtZoos();
}
