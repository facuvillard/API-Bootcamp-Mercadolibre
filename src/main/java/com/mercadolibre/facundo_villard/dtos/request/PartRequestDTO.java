package com.mercadolibre.facundo_villard.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartRequestDTO {
    private String partCode;
    private Integer quantity;
    private Boolean urgent;
}
