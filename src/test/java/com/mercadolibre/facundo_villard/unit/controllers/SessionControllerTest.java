package com.mercadolibre.facundo_villard.unit.controllers;

import com.mercadolibre.facundo_villard.controller.SessionController;
import com.mercadolibre.facundo_villard.dtos.AccountDTO;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.services.ISessionService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SessionControllerTest {

    SessionController controller;
    ISessionService service = Mockito.mock(ISessionService.class);

    @BeforeEach
    void setUp() throws NotFoundException {
        when(service.login("user_one", "contra12"))
                .thenThrow(new ApiException("404", "Usuario y/o contraseña incorrecto", 404));
        when(service.login("user_one", "contra123"))
                .thenReturn(new AccountDTO("user_one", "contra123", "TOKEN"));
        controller = new SessionController(service);
    }

    @Test
    void loginFail() throws Exception {
        assertThrows(ApiException.class, () -> controller.login("user_one","contra12"),
                "Usuario y/o contraseña incorrecto");
    }

    @Test
    void loginOk() throws Exception {
        AccountDTO accountDTO = controller.login("user_one","contra123");
        assertEquals("user_one", accountDTO.getUsername());
        assertEquals("contra123", accountDTO.getPassword());
        assertEquals("TOKEN", accountDTO.getToken());
    }
}