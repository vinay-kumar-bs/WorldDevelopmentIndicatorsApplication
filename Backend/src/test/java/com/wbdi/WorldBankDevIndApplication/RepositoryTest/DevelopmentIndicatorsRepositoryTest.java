package com.wbdi.WorldBankDevIndApplication.RepositoryTest;

import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import com.wbdi.WorldBankDevIndApplication.Repository.DevelopmentIndicatorsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DevelopmentIndicatorsRepositoryTest {

    @Mock
    private DevelopmentIndicatorsRepository repository;

    @InjectMocks
    private DevelopmentIndicatorsRepositoryTest testClass;

    @Before
    public void setUp() {
        DevelopmentIndicatorsData testDataOneIndia = new DevelopmentIndicatorsData();
        testDataOneIndia.setCountryName("India");
        testDataOneIndia.setCountryCode("IND");
        testDataOneIndia.setRegion("Middle Asia");
        testDataOneIndia.setIncomeGroup("Upper Middle Income");
        testDataOneIndia.setYear(2005);
        testDataOneIndia.setBirthRate(31.67);
        testDataOneIndia.setDeathRate(19.56);
        testDataOneIndia.setElectricPowerConsumption(0.00);
        testDataOneIndia.setGdp(760934000);
        testDataOneIndia.setGdpPerCapita(218.469);
        testDataOneIndia.setMortalityRate(0.00);
        testDataOneIndia.setPopulationDensity(183.7);
        testDataOneIndia.setInternetUsagePercent(41.647);
        testDataOneIndia.setLifeExpectancy(81.958);
        testDataOneIndia.setUnemploymentPercent(23.70);

        DevelopmentIndicatorsData testDataTwoIndia = new DevelopmentIndicatorsData();
        testDataTwoIndia.setCountryName("India");
        testDataTwoIndia.setCountryCode("IND");
        testDataTwoIndia.setRegion("Middle Asia");
        testDataTwoIndia.setIncomeGroup("Upper Middle Income");
        testDataTwoIndia.setYear(2009);
        testDataTwoIndia.setBirthRate(31.67);
        testDataTwoIndia.setDeathRate(28.56);
        testDataTwoIndia.setElectricPowerConsumption(0.00);
        testDataTwoIndia.setGdp(760934089);
        testDataTwoIndia.setGdpPerCapita(212.469);
        testDataTwoIndia.setMortalityRate(0.00);
        testDataTwoIndia.setPopulationDensity(183.7);
        testDataTwoIndia.setInternetUsagePercent(31.647);
        testDataTwoIndia.setLifeExpectancy(81.788);
        testDataTwoIndia.setUnemploymentPercent(23.70);

        DevelopmentIndicatorsData testDataTwo = new DevelopmentIndicatorsData();
        testDataTwo.setCountryName("Canada");
        testDataTwo.setCountryCode("CAN");
        testDataTwo.setRegion("North America");
        testDataTwo.setIncomeGroup("Upper Middle Income");
        testDataTwo.setYear(2010);
        testDataTwo.setBirthRate(31.67);
        testDataTwo.setDeathRate(19.56);
        testDataTwo.setElectricPowerConsumption(0.00);
        testDataTwo.setGdp(760934000);
        testDataTwo.setGdpPerCapita(218.469);
        testDataTwo.setMortalityRate(0.00);
        testDataTwo.setPopulationDensity(183.7);
        testDataTwo.setInternetUsagePercent(41.647);
        testDataTwo.setLifeExpectancy(81.958);
        testDataTwo.setUnemploymentPercent(23.70);

        when(repository.findDistinctCountryNames()).thenReturn(Arrays.asList("India", "Canada"));
        when(repository.findDistinctYears()).thenReturn(Arrays.asList(2005, 2010, 2009));
        when(repository.findByCountryAndYear("India", 2005)).thenReturn(Optional.ofNullable(testDataOneIndia));
        when(repository.findByCountryAndYear("Africa", 2006)).thenReturn(null);
        when(repository.findDevelopmentIndicatorsByCountry("India")).thenReturn(Arrays.asList(testDataOneIndia, testDataTwoIndia));
        when(repository.findDevelopmentIndicatorsByCountry("nonExistingCountry")).thenReturn(Arrays.asList());
    }

    @Test
    public void testFindDistinctCountryNames() {
        List<String> countryNames = repository.findDistinctCountryNames();

        assertThat(countryNames).isNotEmpty();
        assertThat(countryNames).contains("India", "Canada");
        assertThat(countryNames).doesNotContain("Afghanistan");
    }

    @Test
    public void testFindDistinctYears() {
        List<Integer> years = repository.findDistinctYears();

        assertThat(years).isNotEmpty();
        assertThat(years).contains(2005, 2010, 2009);
        assertThat(years).doesNotContain(2000);
    }

    @Test
    public void testFindByCountryAndYear() {
        Optional<DevelopmentIndicatorsData> savedData = repository.findByCountryAndYear("India", 2005);
        Optional<DevelopmentIndicatorsData> unsavedData = repository.findByCountryAndYear("Africa", 2006);

        assertThat(savedData).isNotNull();
        assertThat(savedData.get().getYear()).isEqualTo(2005);
        assertThat(savedData.get().getCountryName()).isEqualTo("India");
        assertThat(unsavedData).isNull();
    }

    @Test
    public void testFindDevelopmentIndicatorsByCountry() {
        List<DevelopmentIndicatorsData> dataOfIndia = repository.findDevelopmentIndicatorsByCountry("India");
        List<DevelopmentIndicatorsData> dataOfUnknown = repository.findDevelopmentIndicatorsByCountry("nonExistingCountry");

        assertThat(dataOfIndia).isNotEmpty();
        assertThat(dataOfIndia.get(0).getCountryName()).isEqualTo("India");
        assertThat(dataOfIndia.get(1).getYear()).isEqualTo(2009);
        assertThat(dataOfUnknown).isEmpty();
    }
}
