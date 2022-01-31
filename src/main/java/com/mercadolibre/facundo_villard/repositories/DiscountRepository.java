package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
