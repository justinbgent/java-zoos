package com.schoolwork.zoos.service;

import com.schoolwork.zoos.controller.AnimalController;
import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.model.Telephone;
import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.repository.AnimalRepository;
import com.schoolwork.zoos.repository.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements DatabaseService {
    @Autowired
    private AnimalRepository animalRepo;

    @Autowired
    private ZooRepository zooRepo;

    @Override
    public ZooAnimals getCountOfAnimalPresenceAtZoos() {
        return ;
    }

    @Override
    public List<Zoo> getZoos() {
        List<Zoo> zoos = new ArrayList<>();
        for (Zoo z : zooRepo.findAll()){
            zoos.add(z);
        }
        return zoos;
    }

    @Override
    public Zoo getZooById(long id) {
        if (zooRepo.findById(id).isPresent()){
            return zooRepo.findById(id).get();
        }
        throw new EntityNotFoundException("Zoo id " + id + " not found!");
    }

    @Override
    public List<Zoo> getZoosByLikeName(String name) {
        return zooRepo.findByZoonameContainingIgnoreCase(name);
    }

    @Override
    public Zoo addZoo(Zoo zoo) {
        return zooRepo.save(zoo);
    }

    @Override
    public Zoo updateZoo(long id, Zoo zoo) {
        Zoo currentZoo = getZooById(id);

        if (zoo.getAnimals() != null && !zoo.getAnimals().isEmpty()){
            for (Animal a: zoo.getAnimals()){
                currentZoo.getAnimals().add(a);
            }
        }

        if (zoo.getTelephones() != null && !zoo.getTelephones().isEmpty()){
            for (Telephone t: zoo.getTelephones()){
                currentZoo.getTelephones().add(t);
            }
        }

        if (zoo.getZooname() != null){
            currentZoo.setZooname(zoo.getZooname());
        }

        return zooRepo.save(currentZoo);
    }

    @Override
    public void deleteZoo(long id) {
        if (getZooById(id) != null){
            zooRepo.deleteById(id);
            //zooRepo.delete(getZooById(id));
        }
        else {
            throw new EntityNotFoundException("Zoo id " + id + " not found!");
        }
    }

    @Override
    public void removeAnimalFromZoo(long zooid, long animalid) {
        List<Animal> zooAnimals = getZooById(zooid).getAnimals();

        for (Animal a: zooAnimals){
            if (a.getAnimalid() == animalid){
                zooAnimals.remove(a);
            }
        }
    }

    @Override
    public Animal addAnimalToZoo(long zooid, long animalid) {
        List<Animal> zooAnimals = getZooById(zooid).getAnimals();

        for (Animal a: zooAnimals){
            if (a.getAnimalid() == animalid){
                zooAnimals.remove(a);
            }
        }
    }
}
