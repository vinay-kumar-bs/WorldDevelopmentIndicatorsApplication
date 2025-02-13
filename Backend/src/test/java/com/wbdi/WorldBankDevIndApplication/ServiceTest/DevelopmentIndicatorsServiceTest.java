package com.wbdi.WorldBankDevIndApplication.ServiceTest;

import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Repository.DevelopmentIndicatorsRepository;
import com.wbdi.WorldBankDevIndApplication.Service.DevelopmentIndicatorsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DevelopmentIndicatorsServiceTest {

    @Mock
    DevelopmentIndicatorsRepository repository;

    @InjectMocks
    DevelopmentIndicatorsService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsValidCountryName_ValidName() {
        // Reflection to test private methods (optional, but for coverage)
        assertTrue(service.isValidCountryName("United States"));
        assertTrue(service.isValidCountryName("India"));
    }

    @Test
    public void testIsValidCountryName_InvalidName() {
        assertFalse(service.isValidCountryName("1234"));
        assertFalse(service.isValidCountryName("!@# Country"));
        assertFalse(service.isValidCountryName(""));
    }

    @Test
    public void testIsValidYear_ValidYear() {
        assertTrue(service.isValidYear(2000));
        assertTrue(service.isValidYear(1960));
    }

    @Test
    public void testIsValidYear_InvalidYear() {
        assertFalse(service.isValidYear(1959)); // Below valid range
        assertFalse(service.isValidYear(2050)); // Future year
    }

    @Test
    public void testIsDataExists_DataExists() {
        String country = "India";
        int year = 2020;
        when(repository.findByCountryAndYear(country, year)).thenReturn(Optional.of(new DevelopmentIndicatorsData()));
        assertTrue(service.isDataExists(country, year));
    }

    @Test
    public void testIsDataExists_DataDoesNotExist() {
        String country = "India";
        int year = 2020;
        when(repository.findByCountryAndYear(country, year)).thenReturn(Optional.empty());
        assertFalse(service.isDataExists(country, year));
    }


    @Test
    public void testCreateData_WhenDataGiven_ReturnsSuccess() {

        //Creating data to pass in the create data method
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("China");
        testData.setCountryCode("CHN");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(2020);
        testData.setBirthRate(34.67);
        testData.setDeathRate(8.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(998034000);
        testData.setGdpPerCapita(118.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(83.87);
        testData.setInternetUsagePercent(51.647);
        testData.setLifeExpectancy(84.958);
        testData.setUnemploymentPercent(0.00);

        //Arrange
        when(repository.findByCountryAndYear(testData.getCountryName(), testData.getYear())).thenReturn(Optional.empty());
        when(repository.save(testData)).thenReturn(testData);

        //Act
        service.createDevIndData(testData);

        //Assert
        verify(repository, times(1)).save(testData);
    }

    @Test
    public void testCreateData_WhenNullDataPassed_ThrowsException() {

        DevelopmentIndicatorsData nullData = null;

        // Act
        Exception exception = assertThrows(NullPointerException.class, () -> service.createDevIndData(nullData));
        assertEquals("Data cannot be null.", exception.getMessage());

        // Assert
        verify(repository, times(0)).save(any(DevelopmentIndicatorsData.class));
    }


    @Test
    public void testCreateData_WhenDataExists_ThrowsException() {

        //Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("China");
        testData.setCountryCode("CHN");
        testData.setRegion("East Asia & Pacific");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(2015);
        testData.setBirthRate(34.67);
        testData.setDeathRate(8.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(998034000);
        testData.setGdpPerCapita(118.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(83.87);
        testData.setInternetUsagePercent(51.647);
        testData.setLifeExpectancy(84.958);
        testData.setUnemploymentPercent(0.00);

        //Arrange
        when(repository.findByCountryAndYear(testData.getCountryName(), testData.getYear())).thenReturn(Optional.of(testData));

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.createDevIndData(testData));
        //Assert
        verify(repository, times(0)).save(testData);
    }

    @Test
    public void testRetrieveData_ReturnsSuccess() {

        //Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Brazil");
        testData.setCountryCode("BRA");
        testData.setRegion("Latin America & Caribbean");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(1990);
        testData.setBirthRate(31.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(798034000);
        testData.setGdpPerCapita(238.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(831.7);
        testData.setInternetUsagePercent(41.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(0.00);

        //Mocking my repo layer for business logic
        when(repository.findByCountryAndYear(testData.getCountryName(), testData.getYear())).thenReturn(Optional.of(testData));

        //Act
        Optional<DevelopmentIndicatorsData> data = service.retrieveDevIndData("Brazil", 1990);

        //Assert
        assertNotNull(data);
        assertEquals("Brazil", testData.getCountryName());
        assertEquals(1990, testData.getYear());
        verify(repository, times(1)).findByCountryAndYear("Brazil", 1990);
    }

    @Test
    public void testRetrieveData_WhenDataNotFound_ThrowsException() {
        // Mock repository to return null
        when(repository.findByCountryAndYear("nonExistingCountry", 1970)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.retrieveDevIndData("nonExistingCountry", 1970));
        assertEquals("No data found for corresponding country name and year.", exception.getMessage());

        verify(repository, times(1)).findByCountryAndYear("nonExistingCountry", 1970);
    }


    @Test
    public void testUpdateData_ReturnsSuccess() {
        // Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Brazil");
        testData.setCountryCode("BRA");
        testData.setRegion("Latin America & Caribbean");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(1990);
        testData.setBirthRate(31.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(760934000);
        testData.setGdpPerCapita(218.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(183.7);
        testData.setInternetUsagePercent(41.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        DevelopmentIndicatorsData updatedData = new DevelopmentIndicatorsData();
        updatedData.setCountryName("Brazil");
        updatedData.setCountryCode("BRA");
        updatedData.setRegion("Latin America & Caribbean");
        updatedData.setIncomeGroup("Upper Middle Income");
        updatedData.setYear(1990);
        updatedData.setBirthRate(31.67);
        updatedData.setDeathRate(19.86);
        updatedData.setElectricPowerConsumption(768.58);
        updatedData.setGdp(760934000);
        updatedData.setGdpPerCapita(218.469);
        updatedData.setMortalityRate(76.483);
        updatedData.setPopulationDensity(183.7);
        updatedData.setInternetUsagePercent(41.647);
        updatedData.setLifeExpectancy(81.958);
        updatedData.setUnemploymentPercent(23.70);

        when(repository.findByCountryAndYear(testData.getCountryName(), testData.getYear())).thenReturn(Optional.of(testData));

        // Act
        service.updateDevIndData(updatedData);

        // Assert
        verify(repository, times(1)).save(updatedData);
    }


    @Test
    public void testUpdateDevIndData_WhenNullDataPassed_ThrowsException() {
        // Arrange: Passing null data
        DevelopmentIndicatorsData nullData = null;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> service.updateDevIndData(nullData));
        assertEquals("Data cannot be null.", exception.getMessage());
    }


    @Test
    public void testUpdateData_WhenCountryDataDoesNotExists_ThrowsException() {

        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("nonExistingCountry");
        testData.setCountryCode("BRA");
        testData.setRegion("Latin America & Caribbean");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(2021);
        testData.setBirthRate(31.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(760934000);
        testData.setGdpPerCapita(218.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(183.7);
        testData.setInternetUsagePercent(41.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        when(repository.findByCountryAndYear("nonExistingCountry", 2021)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.updateDevIndData(testData));
        assertEquals("Data to be updated for country name: " + testData.getCountryCode() + " and year: " + testData.getYear() + " does not exist.", exception.getMessage());

        verify(repository, times(0)).save(testData);
    }

    @Test
    public void testDeleteData_ReturnsSuccess() {

        //Arrange
        DevelopmentIndicatorsData testData = new DevelopmentIndicatorsData();
        testData.setCountryName("Brazil");
        testData.setCountryCode("BRA");
        testData.setRegion("Latin America & Caribbean");
        testData.setIncomeGroup("Upper Middle Income");
        testData.setYear(1990);
        testData.setBirthRate(31.67);
        testData.setDeathRate(19.56);
        testData.setElectricPowerConsumption(0.00);
        testData.setGdp(760934000);
        testData.setGdpPerCapita(218.469);
        testData.setMortalityRate(0.00);
        testData.setPopulationDensity(383.7);
        testData.setInternetUsagePercent(41.647);
        testData.setLifeExpectancy(81.958);
        testData.setUnemploymentPercent(23.70);

        when(repository.findByCountryAndYear("Brazil", 1990)).thenReturn(Optional.of(testData));

        //Act
        service.deleteDevIndData("Brazil", 1990);

        //Assert
        verify(repository, times(1)).delete(testData);
    }


    @Test
    public void testDeleteData_WhenDataNotFound_ThrowsException() {
        // Mock repository to return null
        when(repository.findByCountryAndYear("nonExistingCountry", 1970)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.deleteDevIndData("nonExistingCountry", 1970));
        assertEquals("Data for country name and year does not exist.", exception.getMessage());

        // Assert
        verify(repository, times(0)).delete(any(DevelopmentIndicatorsData.class));
    }


    @Test
    public void testFindUniqueCountries_ReturnsSuccess() {

        when(repository.findDistinctCountryNames()).thenReturn(Arrays.asList("India", "Canada", "Brazil"));

        //Act
        List<String> countries = service.getUniqueCountries();

        //Assert
        assertNotNull(countries);
        assertEquals(3, countries.size());
        assertTrue(countries.contains("India"));
        assertTrue(countries.contains("Canada"));
        assertEquals("India", "India");
        assertNotEquals("Afghanistan", "Canada");
        verify(repository, times(1)).findDistinctCountryNames();
    }

    @Test
    public void testFindUniqueCountries_WhenNoCountriesFound_ReturnsEmptyList() {

        when(repository.findDistinctCountryNames()).thenReturn(Arrays.asList());

        // Act
        List<String> countries = service.getUniqueCountries();

        // Assert
        assertTrue(countries.isEmpty());
        assertEquals(0, countries.size());
        verify(repository, times(1)).findDistinctCountryNames();
    }

    @Test
    public void testFindUniqueYears_ReturnsSuccess() {

        //Mock - what I need in return for years(mocking repo layer for business logic)
        when(repository.findDistinctYears()).thenReturn(Arrays.asList(2005, 2010, 2000, 1980));

        //Act
        List<Integer> years = service.getUniqueYears();

        //Assert
        assertNotNull(years);
        assertEquals(4, years.size());
        assertTrue(years.contains(2005));
        assertTrue(years.contains(2010));
        assertNotEquals(2001, 2005);
        verify(repository, times(1)).findDistinctYears();
    }


    @Test
    public void testFindUniqueYears_WhenNoYearsFound_ReturnsEmptyList() {
        // Mock repository to return an empty list
        when(repository.findDistinctYears()).thenReturn(Arrays.asList());

        // Act
        List<Integer> years = service.getUniqueYears();

        // Assert
        assertTrue(years.isEmpty());
        assertEquals(0, years.size());
        verify(repository, times(1)).findDistinctYears();
    }


    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_ReturnsSuccess() {

        DevelopmentIndicatorsData testDataOneAfrica = new DevelopmentIndicatorsData();
        testDataOneAfrica.setCountryName("Africa");
        testDataOneAfrica.setCountryCode("BRA");
        testDataOneAfrica.setRegion("Latin America & Caribbean");
        testDataOneAfrica.setIncomeGroup("Upper Middle Income");
        testDataOneAfrica.setYear(1990);
        testDataOneAfrica.setBirthRate(31.67);
        testDataOneAfrica.setDeathRate(19.56);
        testDataOneAfrica.setElectricPowerConsumption(0.00);
        testDataOneAfrica.setGdp(760934000);
        testDataOneAfrica.setGdpPerCapita(218.469);
        testDataOneAfrica.setMortalityRate(0.00);
        testDataOneAfrica.setPopulationDensity(183.7);
        testDataOneAfrica.setInternetUsagePercent(41.647);
        testDataOneAfrica.setLifeExpectancy(81.958);
        testDataOneAfrica.setUnemploymentPercent(23.70);

        DevelopmentIndicatorsData testDataTwoAfrica = new DevelopmentIndicatorsData();
        testDataTwoAfrica.setCountryName("Africa");
        testDataTwoAfrica.setCountryCode("BRA");
        testDataTwoAfrica.setRegion("Latin America & Caribbean");
        testDataTwoAfrica.setIncomeGroup("Upper Middle Income");
        testDataTwoAfrica.setYear(1990);
        testDataTwoAfrica.setBirthRate(31.67);
        testDataTwoAfrica.setDeathRate(19.56);
        testDataTwoAfrica.setElectricPowerConsumption(0.00);
        testDataTwoAfrica.setGdp(760934000);
        testDataTwoAfrica.setGdpPerCapita(218.469);
        testDataTwoAfrica.setMortalityRate(0.00);
        testDataTwoAfrica.setPopulationDensity(183.7);
        testDataTwoAfrica.setInternetUsagePercent(41.647);
        testDataTwoAfrica.setLifeExpectancy(81.958);
        testDataTwoAfrica.setUnemploymentPercent(23.70);

        when(repository.findDevelopmentIndicatorsByCountry("Africa")).thenReturn(List.of(testDataOneAfrica, testDataTwoAfrica));

        List<DevelopmentIndicatorsData> countryData = service.getDevelopmentIndicatorsDataByCountry("Africa");

        //two times wanted because we are checking if data exists for the country in the ifdataexists method it again fetches data using repository before giving data
        assertFalse(countryData.isEmpty());
        assertTrue(countryData.contains(testDataOneAfrica));
        assertTrue(countryData.contains(testDataTwoAfrica));
        verify(repository, times(2)).findDevelopmentIndicatorsByCountry("Africa");
    }

    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_WhenInvalidCountryName_ThrowsException() {
        // Arrange
        String invalidCountryName = "123Invalid";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.getDevelopmentIndicatorsDataByCountry(invalidCountryName));
        assertEquals("Invalid country name.", exception.getMessage());
        verify(repository, times(0)).findDevelopmentIndicatorsByCountry(any());
    }


    @Test
    public void testGetDevelopmentIndicatorsDataByCountry_WhenDataNotFound_ThrowsException() {

        when(repository.findDevelopmentIndicatorsByCountry("nonExistingCountry")).thenReturn(Arrays.asList());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.getDevelopmentIndicatorsDataByCountry("nonExistingCountry"));
        assertEquals("Data does not exists for this country name", exception.getMessage());
        verify(repository, times(1)).findDevelopmentIndicatorsByCountry("nonExistingCountry");
    }
}