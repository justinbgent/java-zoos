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

        Role r1 = new Role("ADMIN");
        Role r2 = new Role("ANIMALDATA");
        Role r3 = new Role("ZOODATA");
        Role r4 = new Role("MGR");

        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        r3 = roleRepo.save(r3);
        r4 = roleRepo.save(r4);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                r1));
        admins.add(new UserRoles(new User(),
                r2));
        admins.add(new UserRoles(new User(),
                r3));
        admins.add(new UserRoles(new User(),
                r4));
        User u1 = new User("admin",
                "password",
                admins);

        userRepo.save(u1);

        ArrayList<UserRoles> managers = new ArrayList<>();
        managers.add(new UserRoles(new User(),
                r4));
        managers.add(new UserRoles(new User(),
                r3));
        managers.add(new UserRoles(new User(),
                r2));
        User u2 = new User("manager",
                "manager",
                managers);

        userRepo.save(u2);

        ArrayList<UserRoles> zoodata = new ArrayList<>();
        zoodata.add(new UserRoles(new User(),
                r3));
        User u3 = new User("zoodata",
                "zoodata!",
                zoodata);

        userRepo.save(u3);

        ArrayList<UserRoles> animaldata = new ArrayList<>();
        animaldata.add(new UserRoles(new User(),
                r2));
        User u4 = new User("animaldata",
                "animaldata!",
                animaldata);

        userRepo.save(u4);
    }
}
