package com.mercadolibre.facundo_villard.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartsRequestDTO {
    Boolean isUrgent;
    List<PartRequestDTO> parts;
}
