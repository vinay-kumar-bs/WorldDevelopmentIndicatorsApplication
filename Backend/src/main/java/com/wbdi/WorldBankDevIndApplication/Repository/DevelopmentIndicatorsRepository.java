package com.wbdi.WorldBankDevIndApplication.Repository;

import com.wbdi.WorldBankDevIndApplication.Model.DataId;
import com.wbdi.WorldBankDevIndApplication.Model.DevelopmentIndicatorsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevelopmentIndicatorsRepository extends JpaRepository<DevelopmentIndicatorsData, DataId> {

    @Query(value = "select distinct d.countryName from DevelopmentIndicatorsData d")
    List<String> findDistinctCountryNames();

    @Query(value = "select distinct d.year from DevelopmentIndicatorsData d")
    List<Integer> findDistinctYears();

    @Query(value = "select d from DevelopmentIndicatorsData d where d.countryName = :countryName and d.year = :year")
    Optional<DevelopmentIndicatorsData> findByCountryAndYear(String countryName, int year);

    @Query(value = "select d from DevelopmentIndicatorsData d where d.countryName = :countryName")
    List<DevelopmentIndicatorsData> findDevelopmentIndicatorsByCountry(String countryName);

}
