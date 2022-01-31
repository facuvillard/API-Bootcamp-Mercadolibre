package com.mercadolibre.facundo_villard.unit.models;

import com.mercadolibre.facundo_villard.models.StockWhereHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StockWhereHouseTest {

    Validator validator;
    StockWhereHouse stockWhereHouse = new StockWhereHouse();

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        LocalDate date = LocalDate.of(2021, Month.APRIL, 20);
    }

    @Test
    void setQuantityFailNull() {
        stockWhereHouse.setQuantity(null);
        Set<ConstraintViolation<StockWhereHouse>> violations = validator.validate(stockWhereHouse);
        assertFalse(violations.isEmpty());
        assertEquals("La cantidad no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void setQuantityFailMax() {
        stockWhereHouse.setQuantity(999999999);
        Set<ConstraintViolation<StockWhereHouse>> violations = validator.validate(stockWhereHouse);
        assertFalse(violations.isEmpty());
        assertEquals("La cantidad disponible en stock debe ser de hasta 8 caracteres numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void setQuantityOk() {
        stockWhereHouse.setQuantity(99999999);
        Set<ConstraintViolation<StockWhereHouse>> violations = validator.validate(stockWhereHouse);
        assertTrue(violations.isEmpty());
   }

}