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

  public UserAccountListResponse getList(AuthUser authUser, UserAccountSearchCondition condition) {
    if (condition == null)
      condition = new UserAccountSearchCondition();
    if (condition.getIncludeDeleted() == null)
      condition.setIncludeDeleted(false);

    Long comId = authUser.companyId();

    // 권한 체크 로직 (Service Layer)
    // TODO: 명확한 플랫폼 관리자 식별 로직 필요 (현재는 임시로 role="admin"을 체크하거나 특정 companyId 1 등을
    // 체크해야 함)
    // 여기서는 "admin" 권한이 있고, 다른 회사를 조회하려 할 때 허용하는 로직을 보완해야 함.
    // 안전하게 하려면 authUser.companyId() == PLATFORM_ID (예: 1) 체크를 추가해야 함.

    boolean isPlatformAdmin = "admin".equalsIgnoreCase(authUser.role()); // && authUser.companyId() == 1L; (예시)

    if (isPlatformAdmin) {
      if (condition.getComId() != null) {
        comId = condition.getComId(); // 특정 회사 조회
      } else {
        // comId = null; // 전체 회사 조회 (필요 시 주석 해제)
        // 현재 요구사항: 회사 목록을 필터에 넣고... 회사 목록을 불러와서...
        // 필터에 회사가 없으면? 보통 "전체"를 의미하거나 "내 회사"를 의미함.
        // 일단 필터가 없으면 '내 회사' 대신 '전체'를 보려면 null로 설정.
        if (condition.getComId() == null)
          comId = null;
      }
    } else {
      // 일반 관리자: 자신의 회사만 조회 가능
      condition.setComId(null); // 필터 조작 방지
    }

    List<UserAccountListItemResponse> list = userAccountQueryMapper.findList(comId, condition);
    return UserAccountListResponse.of(list);
  }

  // 기존 메서드 호환성 유지 (필요 없다면 삭제하거나 private 위임)
  private UserAccountListResponse getListWithMessage(Long comId, UserAccountSearchCondition condition) {
    // ...
    return null;
  }

  public UserAccountDetailResponse getDetail(AuthUser authUser, Long accId) {

    Long comId = authUser.companyId();
    String role = authUser.role();

    // 플랫폼 관리자(admin)는 전체 회사를 조회할 수 있음 -> comId = null 로 설정하여 전체 조회
    if ("admin".equalsIgnoreCase(role)) {
      comId = null;
    }

    UserAccountDetailResponse res = userAccountQueryMapper.findById(comId, accId);
    if (res == null)
      throw new BusinessException(UserErrorCode.USER_NOT_FOUND);

    return res;
  }

  // admin => 전체 로그인 아이디 조회
  public AccountLoginIdResponse getLoginIdAsAdmin(Long comId, String role, Long accId) {

    if (comId == null)
      throw new BusinessException(UserErrorCode.FORBIDDEN);

    if (role == null || !role.equalsIgnoreCase("admin")) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    AccountLoginIdResponse res = userAccountQueryMapper.findLoginIdByAccId(comId, accId);
    if (res == null)
      throw new BusinessException(UserErrorCode.USER_NOT_FOUND);

    return res;
  }
}
