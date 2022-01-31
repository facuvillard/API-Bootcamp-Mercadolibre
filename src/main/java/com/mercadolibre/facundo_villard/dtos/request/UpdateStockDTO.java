package com.mercadolibre.facundo_villard.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockDTO {

    @NotNull(message = "El id de casa pais es requerido")
    private Long countryHouseId;

    @NotNull(message = "El código de la parte es requerido")
    private String partCode;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 0, message = "La cantidad edebe ser un número positivo")
    private Integer quantity;
}