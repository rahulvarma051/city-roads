package com.mastercard.interview.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mastercard.interview.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service class containing methods that handle requests for determining city roads connections.
 */
@Service
public class CityRoadsService {

    private static final Logger log = LoggerFactory.getLogger(CityRoadsService.class);

    private ResourceLoader resourceLoader;
    //Using BiMap to give enough flexibility to retrieve pair with max performance of O(1) lookup
    //Example: get(Boston) -> NewYork
    //Similarly, we can do get(NewYork) -> Boston
    private BiMap<String, String> citySourceDestinationMap = HashBiMap.create();

    @Autowired
    public CityRoadsService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * PostConstruction initialize() method will load the contents of city.txt file into a collection
     */
    @PostConstruct
    public void initialize(){
        try {
            log.info("CityRoadsService: Trying to load city.txt contents..");

            Resource resource = resourceLoader.getResource("classpath:city.txt");
            File dbAsFile = resource.getFile();
            Files.lines(Paths.get(dbAsFile.toURI())).forEach(
                    s -> {
                        String[] splitString = s.split(",");
                        citySourceDestinationMap.put(splitString[0], splitString[1]);
                    }
            );
        } catch (IOException | NullPointerException e) {
            log.error("Unable to load city.txt file contents ", e);
        }
    }

    public String checkRoadConnectivity(String source, String destination){

        //@RequestParams are by default true. We may not need this.
        // Just a place holder if there is a need to utilize CustomExceptions and show them back to user.
        if(ObjectUtils.isEmpty(source) || ObjectUtils.isEmpty(destination)){
            throw new CustomException("Origin or Destination cannot be null or empty. Please provide valid input");
        }

        //check what direction of map holds the origin city to determine road connectivity
        //Example: In our city.txt, we have Boston -> NewYork and Newark -> Boston as direct pair
        // So, if the origin = Boston, destination = Newark, the answer should be Yes,
        // we need the flexibility of Inverse Map to determine this type of connections.
        if((!ObjectUtils.isEmpty(citySourceDestinationMap.get(source)) && determineRoadConnectivity(source, destination, citySourceDestinationMap)) ||
                (!ObjectUtils.isEmpty(citySourceDestinationMap.inverse().get(source)) && determineRoadConnectivity(source, destination, citySourceDestinationMap.inverse()))){
            return "Yes";
        }
        return "No";
    }

    private Boolean determineRoadConnectivity(String source, String destination, BiMap<String, String> citySourceDestinationMap){
        //Copy the contents into a collection which is separate per request, because we perform operations on the map
        BiMap<String, String> cityRoadMap = HashBiMap.create(citySourceDestinationMap);
        Boolean destinationFound = Boolean.FALSE;
        //Run through the loop until either of the conditions are met
        // destinationFound (or) Emptied the collection searching for connectivity
        while(!destinationFound && !CollectionUtils.isEmpty(cityRoadMap) && cityRoadMap.containsKey(source)) {
            String value = cityRoadMap.get(source);
            cityRoadMap.remove(source);
            if (!ObjectUtils.isEmpty(value) && value.equals(destination)) {
                log.info("Destination {} Found..", value);
                destinationFound = Boolean.TRUE;
            }
            source = value;
        }
        return destinationFound;
    }

}
