package org.hit.hradar.domain.user.command.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<Account, Long>, UserRepository {

  Optional<Account> findByAccIdAndComIdAndStatus(Long accId, Long comId, AccountStatus status);

  Optional<Account> findByComIdAndLoginIdAndStatus(Long comId, String loginId, AccountStatus status);
  boolean existsByComIdAndLoginIdAndStatus(Long comId, String loginId, AccountStatus status);

  Optional<Account> findByEmailAndStatus(String email, AccountStatus status);
  boolean existsByEmailAndStatus(String email, AccountStatus status);

  Optional<Account> findByComIdAndEmailAndStatus(Long ComId, String email, AccountStatus status);
  boolean existsByComIdAndEmailAndStatus(Long ComId, String email, AccountStatus status);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select u from Account u where u.accId = :accId")
  Optional<Account> findByIdForUpdate(Long accId); // Renamed userId to accId for consistency

  Optional<Account> findByLoginIdAndStatus(String loginId, AccountStatus status); // Used by CustomUserDetailsService
}
