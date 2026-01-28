package org.hit.hradar.domain.user.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.query.dto.*;
import org.hit.hradar.domain.user.query.mapper.UserAccountQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAccountQueryService {

  private final UserAccountQueryMapper userAccountQueryMapper;

  public UserAccountListResponse getList(Long comId, UserAccountSearchCondition condition) {

    if (condition == null) condition = new UserAccountSearchCondition();
    if (condition.getIncludeDeleted() == null) condition.setIncludeDeleted(false);

    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    List<UserAccountListItemResponse> list = userAccountQueryMapper.findList(comId, condition);
    return UserAccountListResponse.of(list);
  }

  public UserAccountDetailResponse getDetail(Long comId, Long accId) {

    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    UserAccountDetailResponse res = userAccountQueryMapper.findById(comId, accId);
    if (res == null) throw new BusinessException(UserErrorCode.USER_NOT_FOUND);

    return res;
  }

  // admin => 전체 로그인 아이디 조회
  public AccountLoginIdResponse getLoginIdAsAdmin(Long comId, String role, Long accId) {

    if (comId == null) throw new BusinessException(UserErrorCode.FORBIDDEN);

    if (role == null || !role.equalsIgnoreCase("admin")) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    AccountLoginIdResponse res = userAccountQueryMapper.findLoginIdByAccId(comId, accId);
    if (res == null) throw new BusinessException(UserErrorCode.USER_NOT_FOUND);

    return res;
  }
}
