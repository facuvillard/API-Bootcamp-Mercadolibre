package com.mercadolibre.facundo_villard.dtos.response;

import com.mercadolibre.facundo_villard.dtos.PartDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
public class PartsListResponseDTO {
    private List<PartDTO> parts;
}
