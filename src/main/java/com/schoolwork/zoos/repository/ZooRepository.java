package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.Zoo;
import org.springframework.data.repository.CrudRepository;

public interface ZooRepository extends CrudRepository<Zoo, Long> {
}
