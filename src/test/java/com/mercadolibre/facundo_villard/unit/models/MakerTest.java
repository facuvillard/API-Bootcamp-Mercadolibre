package com.mercadolibre.facundo_villard.unit.models;

import com.mercadolibre.facundo_villard.models.Maker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MakerTest {

    Validator validator;
    Maker maker = new Maker();

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void setNameFailInvalid() {
        maker.setName("invalido 1^23");
        Set<ConstraintViolation<Maker>> violations = validator.validate(maker);
        assertFalse(violations.isEmpty());
        assertEquals("La marca debe ser de hasta 100 caracteres alfanuméricos", violations.iterator().next().getMessage());
    }

    @Test
    void setNameFailNull() {
        maker.setName(null);
        Set<ConstraintViolation<Maker>> violations = validator.validate(maker);
        assertFalse(violations.isEmpty());
        assertEquals("La marca no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void setNameFailMax() {
        maker.setName("Esta marca no va a ser valida porque tiene mas de 100 caracteres " +
                "y el limite de caracteres es hasta 100 solamente");
        Set<ConstraintViolation<Maker>> violations = validator.validate(maker);
        assertFalse(violations.isEmpty());
        assertEquals("La marca debe ser de hasta 100 caracteres alfanuméricos", violations.iterator().next().getMessage());
    }

    @Test
    void setNameOK() {
        maker.setName("Marca que cumple con las validaciones");
        Set<ConstraintViolation<Maker>> violations = validator.validate(maker);
        assertTrue(violations.isEmpty());
    }
}