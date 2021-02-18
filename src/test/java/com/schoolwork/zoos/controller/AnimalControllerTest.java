package com.schoolwork.zoos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolwork.zoos.service.DatabaseService;
import com.schoolwork.zoos.view.AnimalsCountZoos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestController.class)
public class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseService databaseService;

    private List<AnimalsCountZoos> animalsCountList;

    @Before
    public void setUp() throws Exception {
        animalsCountList = new ArrayList<>();

        AnimalsCountZoos animalsStuff = new AnimalsCountZoos() {
            @Override
            public String getAnimaltyperpt() {
                return "null";
            }

            @Override
            public int getCountZoos() {
                return 1;
            }
        };

        animalsCountList.add(animalsStuff);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAnimalPresenceZooCount() throws Exception{
        Mockito.when(databaseService.getCountOfAnimalPresenceAtZoos()).thenReturn(animalsCountList);

        String apiUrl = "/animals/count";

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(rb).andReturn();
        String actual = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(animalsCountList);

        assertEquals("Return List", expected, actual);
    }
}