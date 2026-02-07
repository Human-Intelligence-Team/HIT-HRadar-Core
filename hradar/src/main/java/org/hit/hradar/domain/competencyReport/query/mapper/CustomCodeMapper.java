package org.hit.hradar.domain.competencyReport.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.CustomCodeDTO;

@Mapper
public interface CustomCodeMapper {


  List<CustomCodeDTO> findGroupCodes(Long comId);

  List<CustomCodeDTO> findAllCustomCodeByGroupCode(CustomCodeSearchRequest request);

  List<CustomCodeDTO> findAllCustomCodes(
      @Param("comId") Long comId,
      @Param("groupCode")  String groupCode);

}
