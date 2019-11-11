package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {
}
