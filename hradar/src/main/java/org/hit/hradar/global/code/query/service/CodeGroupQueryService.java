package org.hit.hradar.global.code.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.CodeErrorCode;
import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.repository.CodeGroupRepository;
import org.hit.hradar.global.code.query.dto.response.CodeGroupResponseDto;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupQueryService {

    private final CodeGroupRepository codeGroupRepository;

    public List<CodeGroupResponseDto> getCodeGroups() {
        return codeGroupRepository.findAllActive()
                .stream()
                .map(CodeGroupResponseDto::from)
                .toList();
    }

    public CodeGroupResponseDto getCodeGroup(String groupCode) {
        CodeGroup codeGroup = codeGroupRepository
                .findActiveByGroupCode(groupCode)
                .orElseThrow(() ->
                        new BusinessException(CodeErrorCode.GROUP_NOT_FOUND)
                );

        return CodeGroupResponseDto.from(codeGroup);
    }
}
