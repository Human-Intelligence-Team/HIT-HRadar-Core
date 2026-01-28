package org.hit.hradar.global.query.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    public static PageInfo of(int page, int size, long totalCount) {
        int totalPages = (int) ((totalCount + size - 1) / size);
        return new PageInfo(page, size, totalCount, totalPages);
    }
}
