package org.hit.hradar.domain.user.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<Account, Long>, UserRepository {

  Optional<Account> findByAccIdAndComIdAndStatus(Long accId, Long comId, AccountStatus status);

  boolean existsByComIdAndLoginIdAndStatus(Long comId, String loginId, AccountStatus status);

  boolean existsByComIdAndEmailAndStatus(Long ComId, String email, AccountStatus status);

}
