package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PartRepository extends JpaRepository<Part, Long> {
    @Query("select p from Part p inner join p.partRegisters ptr inner join p.priceRegisters pcr where ptr.dateModification >= :fromDate OR pcr.dateModification >= :fromDate")
    Set<Part> findAllPartialSearch(LocalDate fromDate);

    @Query("select p from Part p inner join p.priceRegisters pcr where pcr.dateModification >= :fromDate")
    Set<Part> findAllLastVariation(LocalDate fromDate);

    @Query("select p  from Part p inner join p.partRegisters ptr inner join p.priceRegisters pcr where ptr.dateModification >= :fromDate OR pcr.dateModification >= :fromDate order by p.partCode ASC")
    Set<Part> findAllPartialSearchAsc(LocalDate fromDate);

    @Query(" select p from Part p inner join p.partRegisters ptr inner join p.priceRegisters pcr where ptr.dateModification >= :fromDate OR pcr.dateModification >= :fromDate  order by p.partCode DESC")
    Set<Part> findAllPartialSearchDesc(LocalDate fromDate);

    @Query("select p from Part p inner join p.partRegisters ptr inner join p.priceRegisters pcr where ptr.dateModification >= :fromDate OR pcr.dateModification >= :fromDate order by ptr.dateModification DESC")
    Set<Part> findAllPartialSearchLastModification(LocalDate fromDate);

    @Query("select p from Part p inner join p.priceRegisters pcr where pcr.dateModification >= :fromDate order by p.partCode ASC ")
    Set<Part> findAllPriceVariationSearchAsc(LocalDate fromDate);

    @Query("select p from Part p inner join p.priceRegisters pcr where pcr.dateModification >= :fromDate order by p.partCode DESC ")
    Set<Part> findAllPriceVariationSearchDesc(LocalDate fromDate);

    @Query("select p from Part p inner join p.priceRegisters pcr where pcr.dateModification >= :fromDate order by pcr.dateModification DESC ")
    Set<Part> findAllPriceVariationSearchLastVariationDate(LocalDate fromDate);

    List<Part> findByStockCountryHouseListId(Long countryHouseId);

    Optional<Part> findByPartCode(String partCode);
}


