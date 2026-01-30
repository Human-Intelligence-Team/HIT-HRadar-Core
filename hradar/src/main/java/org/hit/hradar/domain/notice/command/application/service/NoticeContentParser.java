package org.hit.hradar.domain.notice.command.application.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NoticeContentParser {

    private NoticeContentParser() {}

    // <img src="..."> 또는 <img src='...'>
    private static final Pattern IMG_SRC_PATTERN =
            Pattern.compile("<img[^>]*\\s+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
                    Pattern.CASE_INSENSITIVE);

    public static Set<String> extractImageUrls(String content) {
        if (content == null || content.isBlank()) {
            return Collections.emptySet();
        }

        Set<String> urls = new HashSet<>();
        Matcher m = IMG_SRC_PATTERN.matcher(content);
        while (m.find()) {
            String url = m.group(1);
            if (url != null && !url.isBlank()) {
                urls.add(url.trim());
            }
        }
        return urls;
    }
}

