package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.PriceRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRegisterRepository extends JpaRepository<PriceRegister, Long> {
    PriceRegister findFirstByPartIdOrderByDateModificationDesc(Long partId);
}
