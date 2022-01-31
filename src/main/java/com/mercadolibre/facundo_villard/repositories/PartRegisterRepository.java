package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.PartRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRegisterRepository extends JpaRepository<PartRegister, Long> {
    PartRegister findFirstByPartIdOrderByDateModificationDesc(Long partId);
}

