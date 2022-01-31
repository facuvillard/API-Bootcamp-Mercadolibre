package com.mercadolibre.facundo_villard.unit.services;


import com.mercadolibre.facundo_villard.dtos.AccountDTO;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.models.Account;
import com.mercadolibre.facundo_villard.repositories.AccountRepository;
import com.mercadolibre.facundo_villard.services.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SessionServiceImplTest {

    AccountRepository repository = Mockito.mock(AccountRepository.class);
    SessionServiceImpl service;

    @BeforeEach
    void setUp(){
        this.service = new SessionServiceImpl(repository);
    }


    @Test
    void loginFail() {
        when(repository.findByUsernameAndPassword("user", "invalid")).thenReturn(null);
        assertThrows(ApiException.class, () -> service.login("user", "invalid"),
                "Usuario y/o contraseña incorrecto");
    }

    @Test
    void loginOk() throws Exception {
        Account account = new Account(null, "User", "Pass", null, null);
        when(repository.findByUsernameAndPassword("User", "Pass")).thenReturn(account);
        AccountDTO accountDTO = service.login("User","Pass");
        assertEquals("User", accountDTO.getUsername());
        assertTrue(accountDTO.getToken().startsWith("Bearer"));
    }

}