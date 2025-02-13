package com.wbdi.WorldBankDevIndApplication.IntegrationTest;


import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Repository.DevelopmentIndicatorsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class DevelopmentIndicatorsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DevelopmentIndicatorsRepository repository;

    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/devIndicator";
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testCreateData_WhenValidData_ReturnsSuccess() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("American Samoa");
        testData.setCountryCode("ASM");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Upper middle income");
        testData.setYear(2020);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(0.00);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, testData, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Data Added Successfully.", response.getBody());
    }

    @Test
    public void testCreateData_WhenDuplicateData_ReturnsConflict() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("American Samoa");
        testData.setCountryCode("ASM");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Upper middle income");
        testData.setYear(1960);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(0.00);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        repository.save(testData);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, testData, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    public void testCreateData_WhenNullData_ReturnsRunTimeException() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, testData, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRetrieveData_WhenValidRequest_ReturnsSuccess() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Canada");
        testData.setCountryCode("CAN");
        testData.setRegion("North America");
        testData.setIncomeGroup("Upper middle income");
        testData.setYear(1970);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(0.00);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        repository.save(testData);

        ResponseEntity<DevelopmentIndicatorsData> response = restTemplate.getForEntity(baseUrl + "?countryName=Canada&year=1970", DevelopmentIndicatorsData.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Canada", response.getBody().getCountryName());
        assertEquals(1970, response.getBody().getYear());
    }

    @Test
    public void testRetrieveData_WhenDataNotExists_ReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "?countryName=nonExistingCountry&year=9999", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRetrieveData_MissingParameters_ReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testUpdateData_WhenValidData_ReturnsSuccess() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("India");
        testData.setCountryCode("IND");
        testData.setRegion("Middle Asia");
        testData.setIncomeGroup("middle income");
        testData.setYear(2020);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(12.576);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(22.347);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        repository.save(testData);

        testData.setGdp(867300000);

        restTemplate.put(baseUrl, testData);

        DevelopmentIndicatorsData updatedData = repository.findByCountryAndYear("India", 2020).orElse(null);

        assertNotNull(updatedData);
        assertEquals(867300000, updatedData.getGdp());
    }

    @Test
    public void testUpdateData_NonExistingData_ReturnsBadRequest() {
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("India");
        testData.setCountryCode("IND");
        testData.setRegion("Middle Asia");
        testData.setIncomeGroup("middle income");
        testData.setYear(1990);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(12.576);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(22.347);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        restTemplate.put(baseUrl, testData);

        // Verify that the data was not saved
        assertFalse(repository.findByCountryAndYear("India", 1990).isPresent());
    }

//    @Test
//    public void testUpdateData_MissingRequiredFields_ReturnsNullPointerException() {
//        DevelopmentIndicatorsData invalidData = new DevelopmentIndicatorsData(); // Missing required fields
//
//        restTemplate.put(baseUrl, invalidData);
//
//        // Verify that no data was saved
//        assertNull(invalidData);
//    }


    @Test
    public void testDeleteData_ReturnsSuccess() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("India");
        testData.setCountryCode("IND");
        testData.setRegion("Middle Asia");
        testData.setIncomeGroup("middle income");
        testData.setYear(2021);
        testData.setBirthRate(26.8);
        testData.setDeathRate(5.7);
        testData.setElectricPowerConsumption(12.576);
        testData.setGdp(78400000);
        testData.setGdpPerCapita(12439.5);
        testData.setInternetUsagePercent(22.347);
        testData.setMortalityRate(1.2);
        testData.setLifeExpectancy(34.56);
        testData.setPopulationDensity(312.45);
        testData.setUnemploymentPercent(34.87);

        repository.save(testData);

        restTemplate.delete(baseUrl + "?countryName=India&year=2021");

        assertFalse(repository.findByCountryAndYear("India", 2021).isPresent());
    }

    @Test
    public void testDeleteData_WhenInvalidCountryName_ReturnsBadRequest() {

//        DevelopmentIndicatorsData nullTestData = new DevelopmentIndicatorsData();

        restTemplate.delete(baseUrl + "?countryName=nonExistingCountry&year=2022");

        assertFalse(repository.findByCountryAndYear("nonExistingCountry", 2022).isPresent());
    }

    @Test
    public void testDeleteData_WhenInvalidYear_ReturnsBadRequest() {

//        DevelopmentIndicatorsData nullTestData = new DevelopmentIndicatorsData();

        restTemplate.delete(baseUrl + "?countryName=india&year=9999");

        assertFalse(repository.findByCountryAndYear("India", 9999).isPresent());
    }


    @Test
    public void testDeleteData_NonExistingData_ReturnsBadRequest() {
        restTemplate.delete(baseUrl + "?countryName=Canada&year=2016");

        // Verify that no data was deleted
        assertEquals(0, repository.count());
    }

    @Test
    public void testDeleteData_MissingParameters_ReturnsBadRequest() {
        restTemplate.delete(baseUrl);

        // Verify that no data was deleted
        assertEquals(0, repository.count());
    }

    @Test
    public void testGetUniqueCountries_ReturnsSuccess() {

        DevelopmentIndicatorsData testDataOne = new DevelopmentIndicatorsData();
        testDataOne.setCountryName("Canada");
        testDataOne.setCountryCode("CAN");
        testDataOne.setRegion("North America");
        testDataOne.setIncomeGroup("Upper middle income");
        testDataOne.setYear(1970);
        testDataOne.setBirthRate(26.8);
        testDataOne.setDeathRate(5.7);
        testDataOne.setElectricPowerConsumption(0.00);
        testDataOne.setGdp(78400000);
        testDataOne.setGdpPerCapita(12439.5);
        testDataOne.setInternetUsagePercent(0.00);
        testDataOne.setMortalityRate(1.2);
        testDataOne.setLifeExpectancy(34.56);
        testDataOne.setPopulationDensity(312.45);
        testDataOne.setUnemploymentPercent(34.87);

        DevelopmentIndicatorsData testDataTwo = new DevelopmentIndicatorsData();
        testDataTwo.setCountryName("India");
        testDataTwo.setCountryCode("IND");
        testDataTwo.setRegion("Middle Asia");
        testDataTwo.setIncomeGroup("middle income");
        testDataTwo.setYear(2021);
        testDataTwo.setBirthRate(26.8);
        testDataTwo.setDeathRate(5.7);
        testDataTwo.setElectricPowerConsumption(12.576);
        testDataTwo.setGdp(78400000);
        testDataTwo.setGdpPerCapita(12439.5);
        testDataTwo.setInternetUsagePercent(22.347);
        testDataTwo.setMortalityRate(1.2);
        testDataTwo.setLifeExpectancy(34.56);
        testDataTwo.setPopulationDensity(312.45);
        testDataTwo.setUnemploymentPercent(34.87);

        repository.save(testDataOne);
        repository.save(testDataTwo);

        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/countries", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains("India"));
        assertTrue(response.getBody().contains("Canada"));
    }


    @Test
    public void testGetUniqueCountries_WhenEmptyList_ReturnsNothing() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/countries", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }


    @Test
    public void testGetUniqueYears_ReturnsSuccess() {

        DevelopmentIndicatorsData testDataOne = new DevelopmentIndicatorsData();
        testDataOne.setCountryName("Canada");
        testDataOne.setCountryCode("CAN");
        testDataOne.setRegion("North America");
        testDataOne.setIncomeGroup("Upper middle income");
        testDataOne.setYear(1970);
        testDataOne.setBirthRate(26.8);
        testDataOne.setDeathRate(5.7);
        testDataOne.setElectricPowerConsumption(0.00);
        testDataOne.setGdp(78400000);
        testDataOne.setGdpPerCapita(12439.5);
        testDataOne.setInternetUsagePercent(0.00);
        testDataOne.setMortalityRate(1.2);
        testDataOne.setLifeExpectancy(34.56);
        testDataOne.setPopulationDensity(312.45);
        testDataOne.setUnemploymentPercent(34.87);

        DevelopmentIndicatorsData testDataTwo = new DevelopmentIndicatorsData();
        testDataTwo.setCountryName("India");
        testDataTwo.setCountryCode("IND");
        testDataTwo.setRegion("Middle Asia");
        testDataTwo.setIncomeGroup("middle income");
        testDataTwo.setYear(2021);
        testDataTwo.setBirthRate(26.8);
        testDataTwo.setDeathRate(5.7);
        testDataTwo.setElectricPowerConsumption(12.576);
        testDataTwo.setGdp(78400000);
        testDataTwo.setGdpPerCapita(12439.5);
        testDataTwo.setInternetUsagePercent(22.347);
        testDataTwo.setMortalityRate(1.2);
        testDataTwo.setLifeExpectancy(34.56);
        testDataTwo.setPopulationDensity(312.45);
        testDataTwo.setUnemploymentPercent(34.87);

        repository.saveAll(Arrays.asList(testDataOne, testDataTwo)); // Save multiple countries

        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/years", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains(2021));
        assertTrue(response.getBody().contains(1970));
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_ReturnsSuccess() {

        DevelopmentIndicatorsData testDataOne = new DevelopmentIndicatorsData();
        testDataOne.setCountryName("Canada");
        testDataOne.setCountryCode("CAN");
        testDataOne.setRegion("North America");
        testDataOne.setIncomeGroup("middle income");
        testDataOne.setYear(1970);
        testDataOne.setBirthRate(26.8);
        testDataOne.setDeathRate(5.7);
        testDataOne.setElectricPowerConsumption(0.00);
        testDataOne.setGdp(78400000);
        testDataOne.setGdpPerCapita(12439.5);
        testDataOne.setInternetUsagePercent(0.00);
        testDataOne.setMortalityRate(1.2);
        testDataOne.setLifeExpectancy(34.56);
        testDataOne.setPopulationDensity(312.45);
        testDataOne.setUnemploymentPercent(34.87);

        DevelopmentIndicatorsData testDataTwo = new DevelopmentIndicatorsData();
        testDataTwo.setCountryName("Canada");
        testDataTwo.setCountryCode("CAN");
        testDataTwo.setRegion("North America");
        testDataTwo.setIncomeGroup("upper middle income");
        testDataTwo.setYear(2021);
        testDataTwo.setBirthRate(26.8);
        testDataTwo.setDeathRate(5.7);
        testDataTwo.setElectricPowerConsumption(12.576);
        testDataTwo.setGdp(78400000);
        testDataTwo.setGdpPerCapita(12439.5);
        testDataTwo.setInternetUsagePercent(22.347);
        testDataTwo.setMortalityRate(1.2);
        testDataTwo.setLifeExpectancy(34.56);
        testDataTwo.setPopulationDensity(312.45);
        testDataTwo.setUnemploymentPercent(34.87);

        repository.save(testDataOne);
        repository.save(testDataTwo);

        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/country?countryName=Canada", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_NonExistingCountryName_ReturnsNotFound() {

        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/country?countryName=nonExistingCountry", List.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
//        assertEquals(0, response.getBody().size());
    }
}
