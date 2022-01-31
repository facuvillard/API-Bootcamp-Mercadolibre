package com.mercadolibre.facundo_villard.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTotalDTO {

    private Double totalUrgentPrice;
    private Double totalSalePrice;
    private Double totalNormalPrice;
}
