package org.hit.hradar.domain.leave.query.mapper;


import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.leave.query.dto.response.LeaveListDto;
import org.springframework.data.repository.query.Param;

@Mapper
public interface LeaveListMapper {

  List<LeaveListDto> findByEmpId(@Param("empId") Long empId);

  boolean existsOverlap(
      @Param("empId") Long empId,
      @Param("start") LocalDate start,
      @Param("end") LocalDate end
  );
}
