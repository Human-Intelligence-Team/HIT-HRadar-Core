package org.hit.hradar.domain.leave.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpLeaveRepository {

EmpLeave save(EmpLeave leave);

Optional<EmpLeave> findByDocId(Long docId);

boolean existsByDocId(Long docId);

}
