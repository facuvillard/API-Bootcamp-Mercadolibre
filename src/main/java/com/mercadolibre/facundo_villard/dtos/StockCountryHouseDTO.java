package com.mercadolibre.facundo_villard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Validated
@Data
@AllArgsConstructor
public class StockCountryHouseDTO {

    @NotNull(message = "PartCode es requerido")
    @Pattern(regexp = "[0-9]{8}", message = "PartCode debe tener 8 caracteres numericos")
    private String partCode;

    @NotNull(message = "Quantity es requerido")
    @Max(value = 99999999, message = "Quantity puede tener hasta 8 digitos")
    private Integer quantity;

}