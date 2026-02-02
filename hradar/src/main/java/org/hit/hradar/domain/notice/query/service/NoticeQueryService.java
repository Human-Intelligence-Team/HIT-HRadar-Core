package org.hit.hradar.domain.notice.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.NoticeErrorCode;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchCondition;
import org.hit.hradar.domain.notice.query.dto.response.NoticeDetailResponse;
import org.hit.hradar.domain.notice.query.dto.response.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.mapper.NoticeMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.file.FileStorageClient;
import org.hit.hradar.global.query.paging.PageResponse;
import org.hit.hradar.global.query.search.KeywordCondition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

        private final NoticeMapper mapper;
        private final FileStorageClient storageClient;

        @Value("${file.storage.type}")
        private String storageType;

        @Value("${file.local.base-url:}")
        private String localBaseUrl;

        @Value("${file.s3.base-url:}")
        private String s3BaseUrl;

        private static final Logger bizLog = LoggerFactory.getLogger("business");

        public PageResponse<NoticeListItemResponse> search(
                        NoticeSearchRequest req) {

                NoticeSearchCondition cond = req.getCond();
                KeywordCondition keywordCond = KeywordCondition.of(cond.getKeyword());

                int page = req.getPage().safePage();
                int size = req.getPage().safeSize();
                int offset = req.getPage().offset();

                bizLog.info(
                                "[NOTICE SEARCH PAGE] page={}, size={}, offset={}",
                                page,
                                size,
                                req.getPage().offset());

                bizLog.info(
                                "[NOTICE SEARCH QUERY] cond={}, offset={}, size={}",
                                req.getCond(),
                                req.getPage().offset(),
                                size);

                String keywordLike = keywordCond != null ? keywordCond.like() : null;

                List<NoticeListItemResponse> items = mapper.search(cond, keywordLike, offset, size);

                long total = mapper.count(cond, keywordLike);

                return PageResponse.of(items, page, size, total);
        }

        public NoticeDetailResponse getDetail(
                        Long noticeId,
                        Long companyId) {
                NoticeDetailResponse result = mapper.findDetail(noticeId, companyId);

                if (result == null) {
                        throw new BusinessException(NoticeErrorCode.NOT_FOUND_NOTICE);
                }

                // 1. 본문 이미지 프록시 URL 변환
                if (result.getContent() != null && !result.getContent().isBlank()) {
                        Document doc = Jsoup.parseBodyFragment(result.getContent());
                        Elements imgs = doc.select("img");
                        for (Element img : imgs) {
                                String storedName = storageClient.extractStoredName(img.attr("src"));
                                if (storedName != null && !storedName.isBlank()) {
                                        img.attr("src", "/api/v1/files/images/" + storedName);
                                }
                        }
                        result.setContent(doc.body().html());
                }

                // 2. 첨부파일 프록시 URL 변환
                if (result.getAttachments() != null) {
                        for (NoticeDetailResponse.AttachmentResponse att : result.getAttachments()) {
                                String storedName = storageClient.extractStoredName(att.getUrl());
                                if (storedName != null && !storedName.isBlank()) {
                                        att.setUrl("/api/v1/files/attachments/" + storedName);
                                }
                        }
                }

                return result;
        }
}
