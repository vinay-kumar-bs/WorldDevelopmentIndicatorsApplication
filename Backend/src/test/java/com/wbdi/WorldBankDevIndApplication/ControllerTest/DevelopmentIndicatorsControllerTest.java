package com.wbdi.WorldBankDevIndApplication.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbdi.WorldBankDevIndApplication.Controller.DevelopmentIndicatorsController;
import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Service.DevelopmentIndicatorsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class DevelopmentIndicatorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DevelopmentIndicatorsService service;

    @InjectMocks
    private DevelopmentIndicatorsController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateData_ReturnsSuccess() throws Exception {
        //Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Argentina");
        testData.setCountryCode("ARG");
        testData.setRegion("North America");
        testData.setIncomeGroup("Higher Income");
        testData.setYear(2018);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);


        mockMvc.perform(post("/api/devIndicator")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Data Added Successfully."));

        verify(service, times(1)).createDevIndData(any(DevelopmentIndicatorsData.class));
    }


    @Test
    public void testCreateData_WhenMissingFields_ReturnsBadRequest() throws Exception {

        DevelopmentIndicatorsData emptyTestData = new DevelopmentIndicatorsData();

        mockMvc.perform(post("/api/devIndicator")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyTestData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateData_DuplicateData_ReturnsConflict() throws Exception {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Argentina");
        testData.setCountryCode("ARG");
        testData.setRegion("North America");
        testData.setIncomeGroup("Higher Income");
        testData.setYear(2018);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        doThrow(new IllegalArgumentException("Duplicate entry")).when(service).createDevIndData(any());

        mockMvc.perform(post("/api/devIndicator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Duplicate entry"));
    }

    @Test
    public void testCreateData_ServerError_ReturnsNothing() throws Exception {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Argentina");
        testData.setCountryCode("ARG");
        testData.setRegion("North America");
        testData.setIncomeGroup("Higher Income");
        testData.setYear(2018);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        doThrow(new RuntimeException("Database error")).when(service).createDevIndData(any());

        mockMvc.perform(post("/api/devIndicator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error Database error"));
    }


    @Test
    public void testRetrieveData_ReturnsData() throws Exception {
        //Arrange
        DevelopmentIndicatorsData testDataTwo = new DevelopmentIndicatorsData();
        testDataTwo.setCountryName("Italy");
        testDataTwo.setCountryCode("ITA");
        testDataTwo.setRegion("Europe & Central Asia");
        testDataTwo.setIncomeGroup("Higher Income: OCED");
        testDataTwo.setYear(2015);
        testDataTwo.setBirthRate(76.67);
        testDataTwo.setDeathRate(19.56);
        testDataTwo.setElectricPowerConsumption(0.00);
        testDataTwo.setGdp(999934000);
        testDataTwo.setGdpPerCapita(758.469);
        testDataTwo.setMortalityRate(7.10);
        testDataTwo.setPopulationDensity(83.7);
        testDataTwo.setInternetUsagePercent(56.647);
        testDataTwo.setLifeExpectancy(81.958);
        testDataTwo.setUnemploymentPercent(23.70);

        when(service.retrieveDevIndData("Italy", 2015)).thenReturn(Optional.of(testDataTwo));

        mockMvc.perform(get("/api/devIndicator")
                        .param("countryName", "Italy")
                        .param("year", "2015"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryName").value("Italy"))
                .andExpect(jsonPath("$.year").value(2015));

        verify(service, times(1)).retrieveDevIndData("Italy", 2015);
    }


    @Test
    public void testRetrieveData_WhenNonExistingData_ReturnsBadRequest() throws Exception {

        when(service.retrieveDevIndData("NonExistingCountry", 9999)).thenThrow(new IllegalArgumentException("No data found"));

        mockMvc.perform(get("/api/devIndicator")
                        .param("countryName", "NonExistingCountry")
                        .param("year", "9999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No data found"));
    }


    @Test
    public void testRetrieveData_MissingFields_ReturnsBadRequest() throws Exception {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();

        mockMvc.perform(get("/api/devIndicator")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testRetrieveData_ServerError_ReturnsError() throws Exception {
        when(service.retrieveDevIndData(anyString(), anyInt()))
                .thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/devIndicator")
                        .param("countryName", "USA")
                        .param("year", "2020"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.Error").value("Database error"));
    }


    @Test
    public void testUpdateData_ReturnsSuccess() throws Exception {

        //Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Japan");
        testData.setCountryCode("JPN");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Higher Income: OCED");
        testData.setYear(2015);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(7.10);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);


        //Act & Assert
        mockMvc.perform(put("/api/devIndicator")
                        .content(objectMapper.writeValueAsString(testData))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Data Updated Successfully."));

        verify(service, times(1)).updateDevIndData(any(DevelopmentIndicatorsData.class));
    }

    @Test
    public void testUpdateData_NonExistingData_ReturnsBadRequest() throws Exception {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Japan");
        testData.setCountryCode("JPN");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Higher Income: OCED");
        testData.setYear(2015);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(7.10);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        doThrow(new IllegalArgumentException("Data not found")).when(service).updateDevIndData(any());

        mockMvc.perform(put("/api/devIndicator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Data not found"));
    }


    @Test
    public void testDevelopmentController_DeleteData_ReturnsSuccess() throws Exception {

        doNothing().when(service).deleteDevIndData("Kenya", 2010);

        //Act & Assert
        mockMvc.perform(delete("/api/devIndicator")
                        .param("countryName", "Kenya")
                        .param("year", String.valueOf(2010)))
                .andExpect(status().isOk())
                .andExpect(content().string("Data Deleted Successfully."));

        verify(service, times(1)).deleteDevIndData("Kenya", 2010);
    }

    @Test
    public void testDeleteData_NonExistingData_ReturnsNotFound() throws Exception {

        doThrow(new IllegalArgumentException("Data not found")).when(service).deleteDevIndData("NonExistingCountry", 9999);

        mockMvc.perform(delete("/api/devIndicator")
                        .param("countryName", "NonExistingCountry")
                        .param("year", "9999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Data not found"));
    }


    @Test
    public void testGetUniqueCountries_ReturnsData() throws Exception {

        when(service.getUniqueCountries()).thenReturn(Arrays.asList("USA", "India"));

        mockMvc.perform(get("/api/devIndicator/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("USA"))
                .andExpect(jsonPath("$[1]").value("India"));

        verify(service, times(1)).getUniqueCountries();
    }


    @Test
    public void testGetUniqueCountries_WhenNoData_ReturnsEmptyList() throws Exception {

        when(service.getUniqueCountries()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/devIndicator/countries"))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetCountries_ServerError_ReturnsException() throws Exception {
        when(service.getUniqueCountries()).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/api/devIndicator/countries"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetUniqueYears_ReturnsData() throws Exception {

        when(service.getUniqueYears()).thenReturn(Arrays.asList(2000, 2010));

        mockMvc.perform(get("/api/devIndicator/years"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2000))
                .andExpect(jsonPath("$[1]").value(2010));

        verify(service, times(1)).getUniqueYears();
    }

    @Test
    public void testGetUniqueYears_WhenNoData_ReturnsEmptyList() throws Exception {

        when(service.getUniqueYears()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/devIndicator/years"))
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    public void testGetUniqueYears_ServerError_ReturnsException() throws Exception {
        when(service.getUniqueYears()).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(get("/api/devIndicator/years"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_ReturnsData() throws Exception {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Japan");
        testData.setCountryCode("JPN");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Higher Income: OCED");
        testData.setYear(2015);
        testData.setBirthRate(76.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(999934000);
        testData.setGdpPerCapita(758.469);
        testData.setMortalityRate(7.10);
        testData.setPopulationDensity(83.7);
        testData.setInternetUsagePercent(56.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        List<DevelopmentIndicatorsData> dataList = Arrays.asList(testData);

        when(service.getDevelopmentIndicatorsDataByCountry("Italy")).thenReturn(dataList);

        mockMvc.perform(get("/api/devIndicator/country")
                        .param("countryName", "Italy"))
                .andExpect(status().isOk());

        verify(service, times(1)).getDevelopmentIndicatorsDataByCountry("Italy");
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_WhenDataNotFound_ReturnsEmptyList() throws Exception {

        when(service.getDevelopmentIndicatorsDataByCountry("nonExistingCountry")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/devIndicator/country")
                        .param("countryName", "nonExistingCountry"))
                .andExpect(status().isOk());

        verify(service, times(1)).getDevelopmentIndicatorsDataByCountry("nonExistingCountry");
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_WhenServerError_ReturnsException() throws Exception {

        when(service.getDevelopmentIndicatorsDataByCountry(anyString())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/devIndicator/country")
                        .param("countryName", "any"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_WhenInvalidCountry_ReturnsException() throws Exception {

        when(service.getDevelopmentIndicatorsDataByCountry(anyString())).thenThrow(new IllegalArgumentException("No data found"));

        mockMvc.perform(get("/api/devIndicator/country")
                        .param("countryName", "any"))
                .andExpect(status().isNotFound());
    }

}
