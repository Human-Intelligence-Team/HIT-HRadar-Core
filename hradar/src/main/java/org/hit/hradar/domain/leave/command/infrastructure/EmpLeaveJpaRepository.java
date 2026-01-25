package org.hit.hradar.domain.leave.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpLeaveJpaRepository extends JpaRepository<EmpLeave, Long>  {

  Optional<EmpLeave> findByDocId(Long docId);

  boolean existsByDocId(Long docId);
}
