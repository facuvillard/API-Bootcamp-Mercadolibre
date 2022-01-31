package com.mercadolibre.facundo_villard.services;

import com.mercadolibre.facundo_villard.dtos.request.UpdateStockDTO;
import com.mercadolibre.facundo_villard.dtos.response.StockListResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IPartServices<DTO> {
        DTO listAllParts(Map<String, String> params);

        ResponseEntity<StockListResponseDTO> updateStock(UpdateStockDTO updateStock, String token);
}


