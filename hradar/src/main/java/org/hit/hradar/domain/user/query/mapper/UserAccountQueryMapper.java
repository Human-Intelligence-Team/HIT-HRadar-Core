package org.hit.hradar.domain.user.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.user.query.dto.*;

@Mapper
public interface UserAccountQueryMapper {

  List<UserAccountListItemResponse> findList(
      @Param("comId") Long comId,
      @Param("condition") UserAccountSearchCondition condition
  );

  UserAccountDetailResponse findById(
      @Param("comId") Long comId,
      @Param("accId") Long accId
  );

  AccountLoginIdResponse findLoginIdByAccId(
      @Param("comId") Long comId,
      @Param("accId") Long accId
  );
}
