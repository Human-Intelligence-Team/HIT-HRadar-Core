package org.hit.hradar.domain.user.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.query.dto.*;
import org.hit.hradar.domain.user.query.mapper.UserAccountQueryMapper;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAccountQueryService {

  private final UserAccountQueryMapper userAccountQueryMapper;

  public List<UserAccountListItemResponse> getList(AuthUser authUser, UserAccountSearchCondition condition) {
    if (condition == null) condition = new UserAccountSearchCondition();
    if (condition.getIncludeDeleted() == null) condition.setIncludeDeleted(false);

    Long comId = authUser.companyId();
    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    return userAccountQueryMapper.findList(comId, condition);
  }

  public UserAccountDetailResponse getDetail(AuthUser authUser, Long accId) {
    Long comId = authUser.companyId();
    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    UserAccountDetailResponse res = userAccountQueryMapper.findById(comId, accId);
    if (res == null) throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
    return res;
  }

  // admin => 전체 로그인 아이디 조회
  @Transactional(readOnly = true)
  public AccountLoginIdResponse getLoginIdAsAdmin(AuthUser authUser, Long accId) {
    Long comId = authUser.companyId();
    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    if (authUser.role() == null || !authUser.role().equalsIgnoreCase("admin")) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    AccountLoginIdResponse res = userAccountQueryMapper.findLoginIdByAccId(comId, accId);
    if (res == null) throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
    return res;
  }
}
