package com.mercadolibre.facundo_villard.unit.models;


import com.mercadolibre.facundo_villard.models.Maker;
import com.mercadolibre.facundo_villard.models.Part;
import com.mercadolibre.facundo_villard.models.PartRegister;
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

class PartRegisterTest {

    Validator validator;
    PartRegister partRegister;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        LocalDate date = LocalDate.of(2021, Month.APRIL, 20);

        this.partRegister = new PartRegister(1L, "Descripcion", 1F,
                1F, 1F, 1F, date, new Part(), new Maker());
    }

    @Test
    void setDescriptionFailInvalid() {
        partRegister.setDescription("invalido 1^23");
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("La descripción debe ser de hasta 100 caracteres alfanuméricos", violations.iterator().next().getMessage());
    }

    @Test
    void setDescriptionFailNull() {
        partRegister.setDescription(null);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("La descripción no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void setDescriptionFailMax() {
        partRegister.setDescription("Esta descripcion no va a ser valida porque tiene mas de 100 caracteres " +
                "y el limite de caracteres es hasta 100 solamente");
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("La descripción debe ser de hasta 100 caracteres alfanuméricos", violations.iterator().next().getMessage());
    }

    @Test
    void setDescriptionOK() {
        partRegister.setDescription("Descripcion que cumple con las validaciones");
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    void setTallDimensionFailNull() {
        partRegister.setTallDimension(null);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("La altura del repuesto no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void setTallDimensionFailMax() {
        partRegister.setTallDimension(99999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("La altura del repuesto debe ser de hasta 4 caracteres numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void setTallDimensionOk() {
        partRegister.setTallDimension(9999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertTrue(violations.isEmpty());
    }

    @Test
    void setWidthDimensionFailNull() {
        partRegister.setWidthDimension(null);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El ancho del repuesto no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setWidthDimensionFailMax() {
        partRegister.setWidthDimension(99999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El ancho del repuesto debe ser de hasta 4 caracteres numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void setWidthDimensiontOk() {
        partRegister.setWidthDimension(9999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertTrue(violations.isEmpty());
    }

    @Test
    void setLongDimensionFailNull() {
        partRegister.setLongDimension(null);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El largo del repuesto no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setLongDimensionFailMax() {
        partRegister.setLongDimension(99999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El largo del repuesto debe ser de hasta 4 caracteres numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void setLongDimensionOk() {
        partRegister.setLongDimension(9999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertTrue(violations.isEmpty());
    }

    @Test
    void setNetWeightFailNull() {
        partRegister.setNetWeight(null);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El Peso neto del repuesto no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void setNetWeightFailMax() {
        partRegister.setNetWeight(999999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertFalse(violations.isEmpty());
        assertEquals("El Peso neto del repuesto debe ser de hasta 5 caracteres numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void setNetWeightOk() {
        partRegister.setNetWeight(99999F);
        Set<ConstraintViolation<PartRegister>> violations = validator.validate(partRegister);
        assertTrue(violations.isEmpty());
    }

}