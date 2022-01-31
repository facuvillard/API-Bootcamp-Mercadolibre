package com.mercadolibre.facundo_villard.unit.models;

import com.mercadolibre.facundo_villard.models.PriceRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PriceRegisterTest {

    Validator validator;
    PriceRegister priceRegister;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.priceRegister = new PriceRegister(null, null, 1.2F,
                null,1.34F, null,null);
    }

    @Test
    void setNormalPriceFailNull() {
        priceRegister.setNormalPrice(null);
        Set<ConstraintViolation<PriceRegister>> violations = validator.validate(priceRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El precio normal no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setNormalPriceFailInvalid() {
        priceRegister.setNormalPrice(12344.123F);
        Set<ConstraintViolation<PriceRegister>> violations = validator.validate(priceRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El precio normal no cumple con el formato esperado", violations.iterator().next().getMessage());
    }

    @Test
    void setNormalPriceOk() {
        priceRegister.setNormalPrice(4.20F);
        Set<ConstraintViolation<PriceRegister>> violations = validator.validate(priceRegister);
        assertTrue(violations.isEmpty());
    }

    @Test
    void setUrgentPrice() {
    }
}