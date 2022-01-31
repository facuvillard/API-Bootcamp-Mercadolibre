package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("FROM Account a where a.username=:username and a.password=:password ")
    Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    Account findByUsernameAndCountryHouseId(String username, Long countryHouseId);
}
