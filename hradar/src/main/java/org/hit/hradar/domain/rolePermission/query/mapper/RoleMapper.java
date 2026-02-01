package org.hit.hradar.domain.rolePermission.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.hit.hradar.domain.rolePermission.query.dto.RoleAssignedMember;
import org.hit.hradar.domain.rolePermission.query.dto.RoleListRequest;
import org.hit.hradar.domain.rolePermission.query.dto.RoleResponse;

@Mapper
public interface RoleMapper {

  // 목록(필터 포함)
  List<RoleResponse> selectRoles(
      @Param("comId") Long comId,
      @Param("req") RoleListRequest req);

  // 단건
  RoleResponse selectRole(@Param("comId") Long comId, @Param("roleId") Long roleId);

  // roleId 기준 권한
  List<PermissionResponse> selectRolePermissions(@Param("roleId") Long roleId);

  // 권한 전체(체크박스용)
  List<PermissionResponse> selectAllPermissions();

  // 역할별 할당 인원 조회
  List<RoleAssignedMember> selectAssignedMembers(@Param("roleId") Long roleId);

  // 사원별 전체 권한 키(perm_key) 목록 조회
  List<String> selectPermissionKeysByEmpId(@Param("empId") Long empId);
}
