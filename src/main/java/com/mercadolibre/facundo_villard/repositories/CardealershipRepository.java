package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.Cardealership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CardealershipRepository extends JpaRepository<Cardealership, Long> {
    @Query("FROM Cardealership cd WHERE cd.dealerNumber=:dealerNumber")
    Cardealership getByDealerNumber(@Param("dealerNumber") Integer dealerNumber);
}
