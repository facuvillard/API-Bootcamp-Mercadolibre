package com.mercadolibre.facundo_villard.unit.repositories;


import com.mercadolibre.facundo_villard.models.Part;
import com.mercadolibre.facundo_villard.repositories.PartRepository;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PartRepositoryTest {
    PartRepository partRepository = mock(PartRepository.class);
    @Test
    void findAllPartialSearch() {
        List<Part> partList = new LinkedList<>();
        partList.add(new Part());
        when(partRepository.findAll()).thenReturn(partList);
        assertEquals(1,partRepository.findAll().size());
    }

}