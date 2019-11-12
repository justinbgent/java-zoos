package com.schoolwork.zoos.service;

import com.schoolwork.zoos.controller.AnimalController;
import com.schoolwork.zoos.exceptions.ResourceNotFoundException;
import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.model.Telephone;
import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.model.ZooAnimals;
import com.schoolwork.zoos.repository.AnimalRepository;
import com.schoolwork.zoos.repository.ZooRepository;
import com.schoolwork.zoos.view.AnimalsCountZoos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "databaseService")
public class ServiceImpl implements DatabaseService {
    @Autowired
    private AnimalRepository animalRepo;

    @Autowired
    private ZooRepository zooRepo;

    @Override
    public List<AnimalsCountZoos> getCountOfAnimalPresenceAtZoos() {
        return animalRepo.getCountAnimalsAtZoos();
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
        Zoo zoo = zooRepo.findByZooid(id);

        if (zoo != null){
            return zoo;
        }
        throw new ResourceNotFoundException("Zoo id " + id + " not found!");
    }

    @Override
    public List<Zoo> getZoosByLikeName(String name) {
        return zooRepo.findByZoonameContainingIgnoreCase(name);
    }

    @Transactional
    @Override
    public Zoo addZoo(Zoo zoo) {
        return zooRepo.save(zoo);
    }

    @Transactional
    @Override
    public Zoo updateZoo(long id, Zoo zoo) {
        if (getZooById(id) == null){
            throw new ResourceNotFoundException("Zoo Id Doesn't exist");
        }

        Zoo currentZoo = getZooById(id);

        if (zoo.getZooanimals() != null){
            for (ZooAnimals za : zoo.getZooanimals())
            currentZoo.getZooanimals().add(za);
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

    @Transactional
    @Override
    public void deleteZoo(long id) {
        if (getZooById(id) != null){
            zooRepo.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Zoo id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public void removeAnimalFromZoo(long zooid, long animalid) {
        animalRepo.findById(animalid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));
        zooRepo.findById(zooid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));

        animalRepo.deleteZooAnimals(zooid, animalid);
    }

    @Transactional
    @Override
    public Animal addAnimalToZoo(long zooid, long animalid) {
        animalRepo.findById(animalid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));
        zooRepo.findById(zooid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));

        Animal newAnimal = animalRepo.findByAnimalid(animalid);

        animalRepo.insertZooAnimals(zooid, animalid);
        return newAnimal;
    }
}
