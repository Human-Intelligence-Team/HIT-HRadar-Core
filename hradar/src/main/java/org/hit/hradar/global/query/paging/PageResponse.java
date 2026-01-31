package org.hit.hradar.global.query.paging;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {
    private final List<T> items;
    private final PageInfo page;

    private PageResponse(List<T> items, PageInfo page) {
        this.items = items;
        this.page = page;
    }

    public static <T> PageResponse<T> of(List<T> items, int page, int size, long total) {
        return new PageResponse<>(items, PageInfo.of(page, size, total));
    }
}
