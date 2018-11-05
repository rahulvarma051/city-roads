package com.mastercard.interview.service;

import com.mastercard.interview.exception.CustomException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest //My idea of using @SpringBootTest is to load the Spring container so that ResourceLoader of PostConstruct initialization will be tested as part of this.
                // @PostConstruct will copy the contents of city.txt under /test/resources folder
public class CityRoadsServiceTest {

    @Autowired
    private CityRoadsService cityRoadsService;

    private String source;
    private String destination;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkRoadConnectivity() {
        //arrange
        source = "Boston";
        destination ="Chicago";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("Yes", response);
    }

    @Test
    public void checkRoadConnectivity_MultiConnection() {
        //arrange
        source = "Albany";
        destination ="Newark";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("Yes", response);
    }

    @Test
    public void checkRoadConnectivity_MultiConnections() {
        //arrange
        source = "Boston";
        destination ="Albany";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("Yes", response);
    }

    @Test
    public void checkRoadConnectivity_NoConnection() {
        //arrange
        source = "Chicago";
        destination ="LA";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("No", response);
    }

    @Test
    public void checkRoadConnectivity_NoMultiConnection() {
        //arrange
        source = "Albany";
        destination ="Minneapolis";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("No", response);
    }

    @Test
    public void checkRoadConnectivity_NoMultiConnections() {
        //arrange
        source = "Boston";
        destination ="Miami";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("No", response);
    }

    @Test(expected = CustomException.class)
    public void checkRoadConnectivity_InvalidSource() {
        //arrange
        destination ="Chicago";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("Yes", response);
    }

    @Test(expected = CustomException.class)
    public void checkRoadConnectivityInvalidDestination() {
        //arrange
        source = "Boston";
        //act
        String response = cityRoadsService.checkRoadConnectivity(source, destination);
        //assert
        assertEquals("Yes", response);
    }
}