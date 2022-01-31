package com.mercadolibre.facundo_villard.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;


@Data
@Validated
public class PartRegisterDTO {

    @JsonIgnore
    private Long id;
    private String description;
    private String maker;
    private Float tallDimension;
    private Float widthDimension;
    private Float longDimension;
    private Float netWeight;
    private Integer quantity;
    private LocalDate dateModification;
}
