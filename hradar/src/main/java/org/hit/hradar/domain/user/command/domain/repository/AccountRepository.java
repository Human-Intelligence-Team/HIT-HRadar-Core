package org.hit.hradar.domain.user.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;

public interface AccountRepository {

  Account save(Account account);

  Optional<Account> findByAccIdAndIsDeleted(Long accId, Character isDeleted);

  Optional<Account> findByAccIdAndComIdAndStatus(Long accId, Long comId, AccountStatus status);

  boolean existsByComIdAndLoginIdAndStatus(Long comId, String loginId, AccountStatus status);

  boolean existsByComIdAndEmailAndStatus(Long ComId, String email, AccountStatus status);

  Optional<Account> findByEmpIdAndComIdAndIsDeleted(Long empId, Long comId, char isDeleted);

  boolean existsByLoginIdAndComIdAndIsDeleted(String loginId, Long comId, Character isDeleted);

}
