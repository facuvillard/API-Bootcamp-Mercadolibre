package com.mercadolibre.facundo_villard.unit.models;

import com.mercadolibre.facundo_villard.models.Discount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    Validator validator;
    Discount discount;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.discount = new Discount();
    }

    @Test
    void setDiscountTypeFailNull() {
        discount.setDiscountType(null);
        Set<ConstraintViolation<Discount>> violations = validator.validate(discount);
        assertFalse(violations.isEmpty());
        assertEquals("El código de descuento no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setDiscountTypeFailInvalid() {
        discount.setDiscountType("A123");
        Set<ConstraintViolation<Discount>> violations = validator.validate(discount);
        assertFalse(violations.isEmpty());
        assertEquals("El Código de descuento no cumple con el formato esperado", violations.iterator().next().getMessage());
    }

    @Test
    void setDiscountTypeOk() {
        discount.setDiscountType("A12");
        Set<ConstraintViolation<Discount>> violations = validator.validate(discount);
        assertTrue(violations.isEmpty());
    }

}