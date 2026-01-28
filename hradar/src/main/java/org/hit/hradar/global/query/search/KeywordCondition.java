package org.hit.hradar.global.query.search;

import lombok.Getter;

@Getter
public class KeywordCondition {
    private String keyword;

    public boolean hasKeyword() {
        return keyword != null && !keyword.isBlank();
    }

    public String likeKeyword() {
        if (!hasKeyword()) return null;
        return "%" + keyword.trim() + "%";
    }
}
