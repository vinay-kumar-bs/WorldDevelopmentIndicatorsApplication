package com.wbdi.WorldBankDevIndApplication.Controller;

import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Service.DevelopmentIndicatorsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/devIndicator")
public class DevelopmentIndicatorsController {

    @Autowired
    private DevelopmentIndicatorsService service;

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentIndicatorsController.class);


    @PostMapping
    public ResponseEntity<String> createData(@Valid @RequestBody DevelopmentIndicatorsData data) {
        logger.info("Executing {} with data: {}", getClass(), data);
        try {
            service.createDevIndData(data);
            logger.info("Data added successfully.");
            return ResponseEntity.ok("Data Added Successfully.");

        } catch (IllegalArgumentException e) {
            logger.warn("Conflict While adding the data: {}", e.getMessage());
            return ResponseEntity.status(409).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error while adding the data: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<Object> retrieveData(@Valid @RequestParam String countryName, @RequestParam int year) {
        logger.info("Executing {} to retrieve data for country: {}, year: {}", getClass(), countryName, year);
        try {
            logger.info("Data retrieved successfully.");
            return ResponseEntity.ok().body(service.retrieveDevIndData(countryName, year));

        } catch (IllegalArgumentException e) {
            logger.warn("Bad request while retrieving data: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error in retrieving data: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of("Error", e.getMessage()));
        }
    }


    @PutMapping
    public ResponseEntity<String> updateData(@Valid @RequestBody DevelopmentIndicatorsData data) {
        logger.info("Executing {} to update data: {}", getClass(), data);
        try {
            service.updateDevIndData(data);
            logger.info("Data updated successfully");
            return ResponseEntity.ok("Data Updated Successfully.");

        } catch (IllegalArgumentException e) {
            logger.warn("Bad request for updating data: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error while updating the data: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error " + e.getMessage());          //We cannot use body method without using status method first.
        }
    }


    @DeleteMapping
    public ResponseEntity<String> deleteData(@Valid @RequestParam String countryName, @RequestParam int year) {
        logger.info("Executing {} for deleting data of country name: {} and year: {}", getClass(), countryName, year);
        try {
            service.deleteDevIndData(countryName, year);
            logger.info("Data deleted successfully.");
            return ResponseEntity.ok("Data Deleted Successfully.");

        } catch (IllegalArgumentException e) {
            logger.warn("Bad request while deleting data: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error while deleting the data: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }


    @GetMapping("/countries")
    public ResponseEntity<List<String>> getCountry() {
        logger.info("Executing {} for retrieving unique countries.", getClass());
        try {
            logger.info("Unique countries retrieved successfully.");
            return ResponseEntity.ok(service.getUniqueCountries());

        } catch (Exception e) {
            logger.error("Error while retrieving unique countries: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Collections.emptyList());
        }

    }


    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYear() {
        logger.info("Executing {} for retrieving unique years.", getClass());
        try {
            logger.info("Unique years retrieved successfully.");
            return ResponseEntity.ok(service.getUniqueYears());

        } catch (Exception e) {
            logger.error("Error while retrieving unique years: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @GetMapping("/country")
    public ResponseEntity<List<DevelopmentIndicatorsData>> getDevelopmentIndicatorsDataByCountry(@RequestParam String countryName) {
        logger.info("Executing {} for retrieving data of country name: {}", getClass(), countryName);
        try {
            logger.info("Data retrieved for country name {} successfully.", countryName);
            return ResponseEntity.ok(service.getDevelopmentIndicatorsDataByCountry(countryName));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid country name for retrieving country name {}", e.getMessage());
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.error("Error While retrieving the data by country name {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
}
