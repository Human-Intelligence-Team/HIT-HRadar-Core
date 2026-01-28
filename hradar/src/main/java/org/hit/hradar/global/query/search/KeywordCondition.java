package org.hit.hradar.global.query.search;

import lombok.Getter;

@Getter
public class KeywordCondition {

    private final String keyword;

    public KeywordCondition(String keyword) {
        this.keyword = keyword;
    }


    public static KeywordCondition of(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return new KeywordCondition(keyword.trim());
    }

    public String like() {
        return "%" + keyword + "%";
    }
}
