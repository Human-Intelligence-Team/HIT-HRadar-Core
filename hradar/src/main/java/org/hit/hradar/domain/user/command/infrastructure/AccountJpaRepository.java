package org.hit.hradar.domain.user.command.infrastructure;

import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountJpaRepository extends JpaRepository<Account, Long>, AccountRepository {

    @Modifying
    @Query("UPDATE Account a SET a.status = 'INACTIVE', a.isDeleted = 'Y' WHERE a.comId = :comId AND a.isDeleted = 'N'")
    void deactivateAccountsByComId(@Param("comId") Long comId);

}
