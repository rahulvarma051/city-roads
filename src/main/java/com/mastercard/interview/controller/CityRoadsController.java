package com.mastercard.interview.controller;

import com.mastercard.interview.service.CityRoadsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class exposes API's to determine road connectivity between cities.
 */
@RestController
@RequestMapping("/v1")   //It's a good practice to version the API's, which gives an additional flexibility for the service to upgrade themselves and perform version releases without any impact.
public class CityRoadsController {

    private CityRoadsService cityRoadsService;

    @Autowired
    public CityRoadsController(CityRoadsService cityRoadsService) {
        this.cityRoadsService = cityRoadsService;
    }

    /**
     * Get API to find the possibility of road way between origin and destination
     * @param correlationId
     * @param origin
     * @param destination
     * @return String ("Yes" or "No")
     */
    @ApiOperation(value = "Find possible roadway between 2 cities")
    @GetMapping(value = "/connected")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.OK)
    public String getRoadConnections(@RequestHeader(name = "correlation-id", required = false) String correlationId,
                                     @RequestParam(name = "origin") String origin,
                                     @RequestParam(name = "destination") String destination){
        return cityRoadsService.checkRoadConnectivity(origin, destination);
    }
}
