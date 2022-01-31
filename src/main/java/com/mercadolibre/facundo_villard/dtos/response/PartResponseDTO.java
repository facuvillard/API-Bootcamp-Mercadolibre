package com.mercadolibre.facundo_villard.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartResponseDTO {
    private String partCode;
    private String description;
    private String maker;
    private Integer quantity;
    private Float price;
    private Float total;
}
