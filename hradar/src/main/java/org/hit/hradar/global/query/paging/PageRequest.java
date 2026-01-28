package org.hit.hradar.global.query.paging;

import lombok.Getter;

@Getter
public class PageRequest {
    private int page = 1;
    private int size = 20;

    public int safeSize() {
        return Math.min(Math.max(size, 1), 200);
    }

    public int safePage() {
        return Math.max(page, 1);
    }

    public int offset() {
        return (safePage() - 1) * safeSize();
    }
}
