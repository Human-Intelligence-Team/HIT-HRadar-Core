package org.hit.hradar.global.code.command.domain.repository;

import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.aggregate.CommonCode;

import java.util.Optional;

public interface CommonCodeRepository {

    //활성 코드 존재 여부
    boolean existsActiveCode(String groupCode, String code);

    //활성코드 가져옴
    Optional<CommonCode> findActiveCode(String groupCode, String code);

    CommonCode save(CommonCode commonCode);
}
