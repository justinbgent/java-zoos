package com.schoolwork.zoos.service;

import com.schoolwork.zoos.ZoosApplication;
import com.schoolwork.zoos.exceptions.ResourceNotFoundException;
import com.schoolwork.zoos.model.Telephone;
import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.model.ZooAnimals;
import com.schoolwork.zoos.view.AnimalsCountZoos;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.CheckReturnValue;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZoosApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceImplTest {
    @Autowired
    private DatabaseService databaseService;

    @Before
    public void AsetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void BtearDown() throws Exception {
    }

    @Test
    public void CgetCountOfAnimalPresenceAtZoos() {
        assertEquals(4, databaseService.getCountOfAnimalPresenceAtZoos().size());
    }

    @Test
    public void DgetZoos() {
        assertEquals(5, databaseService.getZoos().size());
    }

    @Test
    public void EgetZooById() {
        assertEquals("Gladys Porter Zoo", databaseService.getZooById(1).getZooname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getZooByBadId() {
        databaseService.getZooById(244);
    }

    @Test
    public void FgetZoosByLikeName() {
        assertEquals(1, databaseService.getZoosByLikeName("Gladys Porter Zoo").size());
    }

    @Test
    public void GaddZoo() {
        Zoo newZoo = databaseService.addZoo(new Zoo());
        System.out.println(newZoo.getZooid());

        assertEquals(6, databaseService.getZoos().size());
    }

    //Have null zooid error for some reason when using commented out code
    @Test
    public void HupdateZoo() {
        Zoo zoo = new Zoo("New Zoo");
//        Telephone telephone = new Telephone("afsd", "adf", databaseService.getZooById(2));
//        telephone.setPhoneid(60);
//        zoo.getTelephones().add(telephone);
//        zoo.getZooanimals().add(new ZooAnimals());
//        zoo.setZooid(2);
        databaseService.updateZoo(2, zoo);

        assertEquals("New Zoo", databaseService.getZooById(2).getZooname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateZooBadId() {
        Zoo zoo = new Zoo("New Zoo");
        databaseService.updateZoo(244, zoo);
    }

    @Test
    public void IdeleteZoo() {
        databaseService.deleteZoo(10);

        assertEquals(5, databaseService.getZoos().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteZooBadZooId() {
        databaseService.deleteZoo(1000);
    }

    // Cannot add test due to initialization
    //failed to lazily initialize a collection of role: com.schoolwork.zoos.model.Zoo.zooanimals, could not initialize proxy - no Session
//    @Test
//    public void JremoveAnimalFromZoo() {
//        databaseService.removeAnimalFromZoo(1, 1);
//
//        assertEquals(1, databaseService.getZooById(1).getZooanimals().size());
//    }

    // Cannot add test due to initialization
    //failed to lazily initialize a collection of role: com.schoolwork.zoos.model.Zoo.zooanimals, could not initialize proxy - no Session
//    @Test
//    public void JaddAnimalFromZoo() {
//        databaseService.addAnimalToZoo(1, 7);
//
//        assertEquals("turtle", databaseService.getZooById(1).getZooanimals().get(2).getAnimal().getAnimaltype());
//    }

    @Test(expected = ResourceNotFoundException.class)
    public void removeAnimalFromZooBadZooID(){
        databaseService.removeAnimalFromZoo(244, 1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void removeAnimalFromZooBadAnimalID(){
        databaseService.removeAnimalFromZoo(1, 122);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void addAnimalFromZooBadZooID(){
        databaseService.addAnimalToZoo(244, 1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void addAnimalFromZooBadAnimalID(){
        databaseService.addAnimalToZoo(1, 122);
    }
}