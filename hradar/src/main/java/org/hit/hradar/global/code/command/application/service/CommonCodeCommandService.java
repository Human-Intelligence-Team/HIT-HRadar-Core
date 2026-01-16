package org.hit.hradar.global.code.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.CodeErrorCode;
import org.hit.hradar.global.code.command.application.dto.request.CreateCommonCodeRequestDto;
import org.hit.hradar.global.code.command.application.dto.request.UpdateCommonCodeRequestDto;
import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.aggregate.CommonCode;
import org.hit.hradar.global.code.command.domain.repository.CodeGroupRepository;
import org.hit.hradar.global.code.command.domain.repository.CommonCodeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonCodeCommandService {

    private final CodeGroupRepository codeGroupRepository;
    private final CommonCodeRepository commonCodeRepository;

    //코드 등록
    @Transactional
    public void createCommonCode(CreateCommonCodeRequestDto request) {

        // 그룹 존재 검증
        CodeGroup codeGroup = codeGroupRepository.findByGroupCode(request.getGroupCode())
                .orElseThrow(() ->
                        new BusinessException(CodeErrorCode.GROUP_NOT_FOUND)
                );

        //중복 코드 체크
        if (commonCodeRepository.existsActiveCode(
                request.getGroupCode(), request.getCode())) {
            throw new BusinessException(CodeErrorCode.CODE_EXIST);
        }

        //공통 코드 생성
        CommonCode commonCode = CommonCode.builder()
                .code(request.getCode())
                .groupCode(codeGroup)
                .codeName(request.getCodeName())
                .codeOrder(request.getOrder())
                .lang(request.getLang())
                .build();

        commonCodeRepository.save(commonCode);
    }

    //공통코드 수정
    @Transactional
    public void updateCommonCode(
            String groupCode,
            String code,
            UpdateCommonCodeRequestDto request
    ) {
        CommonCode commonCode = commonCodeRepository
                .findActiveCode(groupCode, code)
                .orElseThrow(() ->
                        new BusinessException(CodeErrorCode.CODE_NOT_FOUND)
                );

        commonCode.update(
                request.getCodeName(),
                request.getOrder()
        );
    }

    //공통코드 삭제
    @Transactional
    public void deleteCommonCode(String groupCode, String code) {
        CommonCode commonCode = commonCodeRepository
                .findActiveCode(groupCode, code)
                .orElseThrow(() ->
                        new BusinessException(CodeErrorCode.CODE_NOT_FOUND)
                );

        commonCode.delete();
    }
}
