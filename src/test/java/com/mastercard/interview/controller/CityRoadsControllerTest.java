package com.mastercard.interview.controller;

import com.mastercard.interview.exception.CustomException;
import com.mastercard.interview.service.CityRoadsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRoadsControllerTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private CityRoadsService cityRoadsService;

    private MockMvc mockMvc;

    private String cityRoadsConnectionUrl = "/v1/connected";
    private String origin;
    private String destination;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getRoadConnections() throws Exception {
        //arrange
        origin = "Boston";
        destination = "Newark";
        cityRoadsConnectionUrl = cityRoadsConnectionUrl + "?origin=" +origin+ "&destination=" +destination;
        System.out.println(cityRoadsConnectionUrl);
        given(cityRoadsService.checkRoadConnectivity(origin, destination)).willReturn("Yes");
        //act & assert
        mockMvc.perform(get(cityRoadsConnectionUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoadConnections_InvalidOrigin() throws Exception {
        //arrange
        destination = "Newark";
        cityRoadsConnectionUrl = cityRoadsConnectionUrl + "?destination=" +destination;
        //act & assert
        mockMvc.perform(get(cityRoadsConnectionUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRoadConnections_InvalidDestination() throws Exception {
        //arrange
        origin = "Boston";
        cityRoadsConnectionUrl = cityRoadsConnectionUrl + "?origin=" +origin;
        //act & assert
        mockMvc.perform(get(cityRoadsConnectionUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRoadConnections_InvalidOriginAndDestination() throws Exception {
        //act & assert
        mockMvc.perform(get(cityRoadsConnectionUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRoadConnections_MethodNotAllowed() throws Exception {
        //arrange
        origin = "Boston";
        destination = "Newark";
        cityRoadsConnectionUrl = cityRoadsConnectionUrl + "?origin=" +origin+ "&destination=" +destination;
        //act & assert
        mockMvc.perform(post(cityRoadsConnectionUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

}