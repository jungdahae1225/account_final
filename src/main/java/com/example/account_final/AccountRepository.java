package com.example.account_final;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)

public interface AccountRepository extends JpaRepository<Account,Long> {
    //Account findByUsername(String username);

    Account findByEmail(String email);

    //boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    //Account findByNickname(String nickname);
}
