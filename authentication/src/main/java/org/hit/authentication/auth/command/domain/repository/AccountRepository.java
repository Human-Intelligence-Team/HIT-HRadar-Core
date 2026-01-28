package org.hit.authentication.auth.command.domain.repository;

import java.util.Optional;
import org.hit.authentication.auth.command.domain.aggregate.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByComCodeAndLoginIdAndIsDeleted(String companyCode, String loginId, char isDeleted);

  Optional<Account> findByAccIdAndIsDeleted(Long accId, Character isDeleted);


}
