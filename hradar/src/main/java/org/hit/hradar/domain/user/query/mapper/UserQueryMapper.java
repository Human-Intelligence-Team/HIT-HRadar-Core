package org.hit.hradar.domain.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.user.query.dto.UserAccountResponse;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserQueryMapper {

    Optional<UserAccountResponse> findUserAccountById(Long accId, Long comId);

    List<UserAccountResponse> findAllUserAccountsByCompany(Long comId);

    Optional<UserAccountResponse> findUserAccountByLoginId(String loginId, Long comId);
}
