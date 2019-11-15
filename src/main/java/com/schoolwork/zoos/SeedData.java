package com.schoolwork.zoos;

import com.schoolwork.zoos.model.Role;
import com.schoolwork.zoos.model.User;
import com.schoolwork.zoos.model.UserRoles;
import com.schoolwork.zoos.repository.RoleRepo;
import com.schoolwork.zoos.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        r3 = roleRepo.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                r1));
        admins.add(new UserRoles(new User(),
                r2));
        admins.add(new UserRoles(new User(),
                r3));
        User u1 = new User("admin",
                "password",
                admins);

        userRepo.save(u1);

        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                r3));
        datas.add(new UserRoles(new User(),
                r2));
        User u2 = new User("data",
                "data",
                datas);

        userRepo.save(u2);

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u3 = new User("user",
                "user!",
                users);

        userRepo.save(u3);
    }
}
