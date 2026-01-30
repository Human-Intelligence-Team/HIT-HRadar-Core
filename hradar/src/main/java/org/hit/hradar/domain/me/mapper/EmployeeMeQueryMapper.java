package org.hit.hradar.domain.me.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.me.dto.MeResponse;

@Mapper
public interface EmployeeMeQueryMapper {

  Optional<MeResponse> findMe(
      @Param("comId") Long comId,
      @Param("empId") Long empId
  );
}
