package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.Zoo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZooRepository extends CrudRepository<Zoo, Long> {
    List<Zoo> findByZoonameContainingIgnoreCase(String name);

    Zoo findByZooid(long id);
}
