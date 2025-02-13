package com.wbdi.WorldBankDevIndApplication.Service;

import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Repository.DevelopmentIndicatorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class DevelopmentIndicatorsService {

    @Autowired
    private DevelopmentIndicatorsRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentIndicatorsService.class);


    //country name validation
    public boolean isValidCountryName(String countryName) {
        if (countryName == null || countryName.trim().isEmpty()) {
            logger.warn("Validation failed: Country name is null or empty.");
            return false; // Null or empty input is invalid
        }
        boolean isValid = countryName.trim().matches("^[a-zA-Z\\s]+$");
        if (!isValid) {
            logger.warn("Validation failed: Country name '{}' is invalid.", countryName);
        }
        return isValid;
    }


    //year validation
    public boolean isValidYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        boolean isValid = year >= 1960 && year <= currentYear;
        if (!isValid) {
            logger.warn("Validation failed: Year '{}' is out of valid range (1960-{}).", year, currentYear);
        }
        return isValid;
    }


    public void validateCountryAndYear(String countryName, int year) {
        if (!isValidCountryName(countryName) || !isValidYear(year)) {
            throw new IllegalArgumentException("Invalid country name or year.");
        }
    }


    public boolean isDataExists(String countryName, int year) {
        logger.info("Checking if data exists for country: {} and year: {}", countryName, year);
        return repository.findByCountryAndYear(countryName, year).isPresent();
    }


    public boolean isDataExistsByCountry(String countryName) {
        logger.info("Executing {} for checking if Data Exists by country name: {}", getClass(), countryName);
        List<DevelopmentIndicatorsData> dataByCountry = repository.findDevelopmentIndicatorsByCountry(countryName);
        return dataByCountry != null && !dataByCountry.isEmpty();
    }


    public List<String> getUniqueCountries() {
        logger.info("Executing {} for retrieving unique countries.", getClass());
        return repository.findDistinctCountryNames();
    }


    public List<Integer> getUniqueYears() {
        logger.info("Executing {} for retrieving unique years.", getClass());
        return repository.findDistinctYears();
    }


    public void createDevIndData(DevelopmentIndicatorsData data) {
        logger.info("Executing {} for creating data: {}", getClass(), data);
        if (data == null) {
            logger.error("Data is null");
            throw new NullPointerException("Data cannot be null.");
        }

        validateCountryAndYear(data.getCountryName(), data.getYear());

        if (isDataExists(data.getCountryName(), data.getYear())) {
            logger.error("Data already exists for country name: {} and year: {}", data.getCountryName(), data.getYear());
            throw new IllegalArgumentException("Data already exists for country name :" + data.getCountryName() + " and year :" + data.getYear() + ".");
        }
        logger.info("Data added successfully.");
        repository.save(data);
    }


    public Optional<DevelopmentIndicatorsData> retrieveDevIndData(String countryName, int year) {
        logger.info("Executing {} for retrieving the data for country name: {} and year: {}", getClass(), countryName, year);

        validateCountryAndYear(countryName, year);

        return repository.findByCountryAndYear(countryName, year).or(() -> {
            logger.error("Data does not exist for country name: {} and year: {}", countryName, year);
            throw new IllegalArgumentException("No data found for corresponding country name and year.");
        });
    }


    public void updateDevIndData(DevelopmentIndicatorsData data) {
        logger.info("Updating data: {}", data);
        if (data == null) {
            logger.error("Data for country is null");
            throw new NullPointerException("Data cannot be null.");
        }

        validateCountryAndYear(data.getCountryName(), data.getYear());

        Optional<DevelopmentIndicatorsData> existingData = repository.findByCountryAndYear(data.getCountryName(), data.getYear());
        if (existingData.isEmpty()) {
            logger.error("Data does not exist for update.");
            throw new IllegalArgumentException("Data to be updated for country name: " + data.getCountryCode() + " and year: " + data.getYear() + " does not exist.");
        }

        logger.info("Data updated successfully.");
        repository.save(data);
    }

    public void deleteDevIndData(String countryName, int year) {
        logger.info("Deleting data with country name: {} and year: {}", countryName, year);

        validateCountryAndYear(countryName, year);

        Optional<DevelopmentIndicatorsData> dataToDelete = repository.findByCountryAndYear(countryName, year);
        if (dataToDelete.isEmpty()) {
            logger.error("Data not available for country name: {} and year: {}", countryName, year);
            throw new IllegalArgumentException("Data for country name and year does not exist.");
        }

        logger.info("Data deleted successfully.");
        repository.delete(dataToDelete.get());
    }


    public List<DevelopmentIndicatorsData> getDevelopmentIndicatorsDataByCountry(String countryName) {
        logger.info("Executing {} for getting data by country name: {}", getClass(), countryName);
        if (!isValidCountryName(countryName)) {
            logger.error("Invalid country name for getting data by country name: {}", countryName);
            throw new IllegalArgumentException("Invalid country name.");
        }
        if (!isDataExistsByCountry(countryName)) {
            logger.error("Data does not exists for country name: {}", countryName);
            throw new IllegalArgumentException("Data does not exists for this country name");
        }
        logger.info("Data is available");
        return repository.findDevelopmentIndicatorsByCountry(countryName);
    }
}
