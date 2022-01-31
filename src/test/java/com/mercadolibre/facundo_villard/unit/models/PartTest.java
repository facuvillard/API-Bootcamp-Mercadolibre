package com.mercadolibre.facundo_villard.unit.models;

import com.mercadolibre.facundo_villard.models.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    Validator validator;
    Part part;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        this.part = new Part();
    }

    @Test
    void setPartCodeFailNull() {
        part.setPartCode(null);
        Set<ConstraintViolation<Part>> violations = validator.validate(part);
        assertFalse(violations.isEmpty());
        assertEquals("El código no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setPartCodeFailInvalid() {
        part.setPartCode("123D456d");
        Set<ConstraintViolation<Part>> violations = validator.validate(part);
        assertFalse(violations.isEmpty());
        assertEquals("El código debe ser de ocho caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void setPartCodeFailMax() {
        part.setPartCode("123456789");
        Set<ConstraintViolation<Part>> violations = validator.validate(part);
        assertFalse(violations.isEmpty());
        assertEquals("El código debe ser de ocho caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void setPartCodeOk() {
        part.setPartCode("00000008");
        Set<ConstraintViolation<Part>> violations = validator.validate(part);
        assertTrue(violations.isEmpty());
    }
}