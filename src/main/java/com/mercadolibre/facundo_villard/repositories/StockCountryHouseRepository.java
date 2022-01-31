package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.StockCountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockCountryHouseRepository extends JpaRepository<StockCountryHouse, Long> {

    StockCountryHouse findByCountryHouseIdAndPartPartCode(Long countryHouseId, String partId);

    public List<StockCountryHouse> findByCountryHouseId(Long countryHouseId);
}
