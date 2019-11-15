package com.schoolwork.zoos.repository;

import com.schoolwork.zoos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
