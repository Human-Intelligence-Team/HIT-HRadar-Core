package org.hit.hradar.domain.user.command.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.hit.hradar.domain.user.command.domain.aggregate.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);



    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from UserAccount u where u.accId = :accId")
    Optional<UserAccount> findByIdForUpdate(Long userId);

}
