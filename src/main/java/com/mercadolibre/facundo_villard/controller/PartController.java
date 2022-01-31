package com.mercadolibre.facundo_villard.controller;


import com.mercadolibre.facundo_villard.dtos.request.UpdateStockDTO;
import com.mercadolibre.facundo_villard.dtos.response.PartsListResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.StockListResponseDTO;
import com.mercadolibre.facundo_villard.services.IPartServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1")
@RestController
public class PartController {

    public final IPartServices partServices;

    public PartController(IPartServices partServices) {
        this.partServices = partServices;
    }

    @GetMapping("/parts/list")
    public PartsListResponseDTO getAllParts( @RequestParam Map<String, String> params){
        return (PartsListResponseDTO) partServices.listAllParts(params);
    }

    @PostMapping("/parts")
    public ResponseEntity<StockListResponseDTO> updatePart(@RequestHeader(value="Authorization") String token,
                                                           @RequestBody UpdateStockDTO updateStockDTO){
        return partServices.updateStock(updateStockDTO, token);
    }

}
