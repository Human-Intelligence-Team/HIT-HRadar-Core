package org.hit.hradar.global.code.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.command.domain.repository.CommonCodeRepository;
import org.hit.hradar.global.code.query.dto.response.CommonCodeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonCodeQueryService {

    private final CommonCodeRepository commonCodeRepository;

    public List<CommonCodeResponseDto> getCommonCodes(String groupCode) {
        return commonCodeRepository.findActiveCodesByGroupCode(groupCode)
                .stream()
                .map(CommonCodeResponseDto::from)
                .toList();
    }
}
