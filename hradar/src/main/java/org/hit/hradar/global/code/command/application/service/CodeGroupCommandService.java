package org.hit.hradar.global.code.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.CodeErrorCode;
import org.hit.hradar.global.code.command.application.dto.request.CreateCodeGroupRequestDto;
import org.hit.hradar.global.code.command.application.dto.request.UpdateCodeGroupRequestDto;
import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.repository.CodeGroupRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeGroupCommandService {

    private final CodeGroupRepository codeGroupRepository;

    //코드 등록
    @Transactional
    public void createCodeGroup(CreateCodeGroupRequestDto request) {
        if(codeGroupRepository.existsByGroupCode(request.getGroupCode())) {
            throw new BusinessException(CodeErrorCode.GROUP_EXIST);
        }

        CodeGroup codeGroup = CodeGroup.builder()
                .groupCode(request.getGroupCode())
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .build();

        codeGroupRepository.save(codeGroup);
    }

    //코드 수정
    @Transactional
    public void updateCodeGroup(
            String groupCode,
            UpdateCodeGroupRequestDto request
    ){
        CodeGroup codeGroup = codeGroupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new BusinessException(CodeErrorCode.GROUP_NOT_FOUND));

        codeGroup.update(
                request.getGroupName(),
                request.getDescription()
        );
    }

    //코드 그룹 삭제 (soft-delete)
    @Transactional
    public void deleteCodeGroup(String groupCode) {
        CodeGroup codeGroup = codeGroupRepository.findByGroupCode(groupCode)
                .orElseThrow(() ->
                        new BusinessException(CodeErrorCode.GROUP_NOT_FOUND)
                );

        codeGroup.delete();
    }
}
