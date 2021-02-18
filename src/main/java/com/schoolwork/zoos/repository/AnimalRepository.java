package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.view.AnimalsCountZoos;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    Animal findByAnimalid(long id);

    @Query(value = "SELECT a.animaltype as animaltyperpt, count(za.animalid) as countzoos FROM animals a JOIN zooanimals za ON a.animalid = za.animalid GROUP BY a.animaltype", nativeQuery = true)
    List<AnimalsCountZoos> getCountAnimalsAtZoos();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    void deleteZooAnimals(long zooid, long animalid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ZooAnimals(zooid, animalid) VALUES (:zooid, :animalid)", nativeQuery = true)
    void insertZooAnimals(long zooid, long animalid);
}