package org.hit.hradar.domain.notice.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.query.dto.NoticeCategoryResponse;
import org.hit.hradar.domain.notice.query.mapper.NoticeCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeCategoryQueryService {

    private final NoticeCategoryMapper mapper;

    public List<NoticeCategoryResponse> findByCompany(Long companyId) {
        return mapper.findByCompany(companyId);
    }
}
