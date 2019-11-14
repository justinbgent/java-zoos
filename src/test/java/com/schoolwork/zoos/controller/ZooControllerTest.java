package com.schoolwork.zoos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolwork.zoos.model.Animal;
import com.schoolwork.zoos.model.Zoo;
import com.schoolwork.zoos.model.ZooAnimals;
import com.schoolwork.zoos.service.DatabaseService;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestController.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ZooControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseService databaseService;

    private List<Zoo> zooList;

    @Before
    public void setUp() throws Exception {
        zooList = new ArrayList<>();

        Animal animal = new Animal("Penguin");
        animal.setAnimalid(1);

        Zoo zoo = new Zoo("Zoo");
        zoo.setZooid(1);
        zoo.getZooanimals().add(new ZooAnimals());
        zoo.setZooid(2);

        ZooAnimals zooAnimals = new ZooAnimals();
        zooAnimals.setAnimal(animal);
        zooAnimals.setZoo(zoo);
        List<ZooAnimals> zooAnimalsList = new ArrayList<>();
        zooAnimalsList.add(zooAnimals);

        animal.setZooanimals(zooAnimalsList);
        zoo.setZooanimals(zooAnimalsList);

//        Zoo emptyZoo = new Zoo();
//        emptyZoo.setZooid(2);
//        Animal emptyAnimal = new Animal();

        zooList.add(zoo);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getZoos() throws Exception {
        Mockito.when(databaseService.getZoos()).thenReturn(zooList);

        String apiUrl = "/zoos/zoos";

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(rb).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(zooList);

        assertEquals(expected, actual);
    }

    @Test
    public void getZooById() throws Exception {
        Mockito.when(databaseService.getZooById(1)).thenReturn(zooList.get(0));

        String apiUrl = "/zoos/zoo/1";

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(rb).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(zooList.get(0));

        assertEquals(expected, actual);
    }

    @Test
    public void getZoosByLikeName() throws Exception {
        Mockito.when(databaseService.getZoosByLikeName("Zoo")).thenReturn(zooList);

        String apiUrl = "/zoos/zoo/namelike/Zoo";

        ObjectMapper mapper = new ObjectMapper();
        String zooList = mapper.writeValueAsString(this.zooList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(rb).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        assertEquals(zooList, actual);
    }

    @Test
    public void addZoo() throws Exception {
        Zoo zoo = new Zoo("NewZoo");
        zoo.setZooid(3);

        Mockito.when(databaseService.addZoo(any(Zoo.class))).thenReturn(zoo);

        String apiUrl = "/zoos/zoo";

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(zoo);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(zooString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateZoo() throws Exception {
        Zoo zoo = zooList.get(0);

        Mockito.when(databaseService.updateZoo(1, zoo)).thenReturn(zoo);

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(zoo);

        String apiUrl = "/zoos/zoo/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(zooString);

        mockMvc.perform(rb).andExpect(status().isOk());
    }

    @Test
    public void deleteZoo() throws Exception {
        String apiUrl = "/zoos/zoo/{id}";

        RequestBuilder rb = MockMvcRequestBuilders
                .delete(apiUrl, "1")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeAnimalFromZoo() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";

        RequestBuilder rb = MockMvcRequestBuilders
                .delete(apiUrl, 1, 1)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addAnimalToZoo() throws Exception {
        Mockito.when(databaseService.addAnimalToZoo(1, 1)).thenReturn(zooList.get(0).getZooanimals().get(0).getAnimal());

        String apiUrl = "/zoos/zoo/1/animals/1";

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(zooList.get(0).getZooanimals().get(0).getAnimal());

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(expected);

        mockMvc.perform(rb).andExpect(status().isOk());
    }
}