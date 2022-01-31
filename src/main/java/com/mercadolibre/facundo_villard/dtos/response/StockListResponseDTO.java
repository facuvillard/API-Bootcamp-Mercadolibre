package com.mercadolibre.facundo_villard.dtos.response;

import com.mercadolibre.facundo_villard.dtos.StockCountryHouseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
public class StockListResponseDTO {

    private List<StockCountryHouseDTO> stocks;

}