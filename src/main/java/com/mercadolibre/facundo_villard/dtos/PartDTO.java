package com.mercadolibre.facundo_villard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated
@Data
@AllArgsConstructor
public class PartDTO {

    @NotNull(message = "PartCode es requerido")
    @Pattern(regexp = "[0-9]{8}", message = "PartCode debe tener 8 caracteres numericos")
    private String partCode;

    @NotNull(message = "Description es requerido")
    @Size(max = 100, message = "Description debe tener hasta 100 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String description;

    @NotNull(message = "Maker es requerido")
    @Size(max = 100, message = "Maker debe tener hasta 100 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String maker;

    @NotNull(message = "Quantity es requerido")
    @Max(value = 99999999, message = "Quantity puede tener hasta 8 digitos")
    private Integer quantity;

    @NotNull(message = "DiscountType es requerido")
    @Pattern(regexp = "[A-Z]{1}[0-9]{2}", message = "DiscountType debe ser 1 letra mayuscula + 2 digitos")
    private String discountType;

    @NotNull(message = "NormalPrice es requerido")
    @Max(value = 999999, message = "NormalPrice puede tener hasta 6 caracteres, con 2 decimales")
    private Float normalPrice;

    @NotNull(message = "UrgentPrice es requerido")
    @Max(value = 999999, message = "UrgentPrice puede tener hasta 6 caracteres, con 2 decimales")
    private Float urgentPrice;

    @NotNull(message = "NetWeight es requerido")
    @Max(value = 99999, message = "NetWeight puede tener hasta 5 digitos")
    private Float netWeight;

    @NotNull(message = "LongDimension es requerido")
    @Max(value = 9999, message = "LongDimension puede tener hasta 4 digitos")
    private Float longDimension;

    @NotNull(message = "WidthDimension es requerido")
    @Max(value = 9999, message = "WidthDimension puede tener hasta 4 digitos")
    private Float widthDimension;

    @NotNull(message = "TallDimension es requerido")
    @Max(value = 9999, message = "TallDimension puede tener hasta 4 digitos")
    private Float tallDimension;

    @NotNull(message = "LastModification es requerido")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String lastModification;

}