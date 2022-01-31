package com.mercadolibre.facundo_villard.services;

import com.mercadolibre.facundo_villard.dtos.AccountDTO;
import javassist.NotFoundException;

public interface ISessionService {

    AccountDTO login(String username, String password) throws NotFoundException;
}
