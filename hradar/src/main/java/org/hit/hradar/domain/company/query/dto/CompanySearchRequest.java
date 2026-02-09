package org.hit.hradar.domain.company.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.global.query.paging.PageRequest;

@Getter
@Setter
@NoArgsConstructor
public class CompanySearchRequest {
    private CompanySearchCondition cond = new CompanySearchCondition();
    private PageRequest page = new PageRequest();
}
