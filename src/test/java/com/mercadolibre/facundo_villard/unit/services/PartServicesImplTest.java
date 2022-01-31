package com.mercadolibre.facundo_villard.unit.services;

import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.repositories.*;
import com.mercadolibre.facundo_villard.services.PartServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PartServicesImplTest {

    PartRepository partRepository  = Mockito.mock(PartRepository.class);
    PriceRegisterRepository priceRepository  = Mockito.mock(PriceRegisterRepository.class);
    PartRegisterRepository partRegisterRepository = Mockito.mock(PartRegisterRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    StockCountryHouseRepository stockCountryHouseRepository = Mockito.mock(StockCountryHouseRepository.class);
    CountryHouseRepository countryHouseRepository = Mockito.mock(CountryHouseRepository.class);
    PartServicesImpl service;

    @BeforeEach
    void setUp(){
        this.service = new PartServicesImpl(partRepository,priceRepository,partRegisterRepository,
                accountRepository, stockCountryHouseRepository, countryHouseRepository,
                new ModelMapper());
    }

    @Test
    void listAllPartsNotResult() {
        when(partRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiException.class, ()-> service.listAllParts(new HashMap<>()),
                "No se encontraron resultados");
    }

    @Test
    void listAllPartsQueryCNotResult() {
        HashMap<String, String> filters = new HashMap<>();
        filters.put("queryType", "C");
        when(partRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiException.class, ()-> service.listAllParts(filters),
                "No se encontraron resultados");
    }


    @Test
    void listAllPartsQueryVNotResult() {
        HashMap<String, String> filters = new HashMap<>();
        filters.put("queryType", "V");
        filters.put("date", "2021-04-04");
        when(partRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiException.class, ()-> service.listAllParts(filters),
                "No se encontraron resultados");
    }

    @Test
    void listAllPartsQueryVInvalidDate() {
        HashMap<String, String> filters = new HashMap<>();
        filters.put("queryType", "V");
        filters.put("date", "2040-04-04");
        when(partRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiException.class, ()-> service.listAllParts(filters),
                "La fecha no puede estar en el futuro");
    }

    @Test
    void listAllPartsQueryInvalid() {
        HashMap<String, String> filters = new HashMap<>();
        filters.put("queryType", "J");
        filters.put("date", "2040-04-04");
        when(partRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiException.class, ()-> service.listAllParts(filters),
                "El querytype ingresado es diferente a P o V o es nulo");
    }



}