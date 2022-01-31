package com.mercadolibre.facundo_villard.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartsResponseDTO {
    Double total;
    List<PartResponseDTO> parts;
}