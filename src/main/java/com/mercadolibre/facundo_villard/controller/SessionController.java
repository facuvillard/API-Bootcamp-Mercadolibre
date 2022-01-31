package com.mercadolibre.facundo_villard.controller;

import com.mercadolibre.facundo_villard.dtos.AccountDTO;
import com.mercadolibre.facundo_villard.services.ISessionService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }

    @PostMapping("/sign-in")
    public AccountDTO login(@RequestParam("username") String username, @RequestParam("password") String password) throws NotFoundException {
        return service.login(username, password);
    }

}