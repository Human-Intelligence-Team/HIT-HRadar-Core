package org.hit.hradar.domain.notice.query.service;

import org.hit.hradar.domain.notice.query.dto.response.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchCondition;
import org.hit.hradar.domain.notice.query.dto.request.NoticeSearchRequest;
import org.hit.hradar.domain.notice.query.mapper.NoticeMapper;
import org.hit.hradar.global.query.paging.PageRequest;
import org.hit.hradar.global.query.paging.PageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeQueryServiceTest {

    @InjectMocks
    private NoticeQueryService service;

    @Mock
    private NoticeMapper mapper;

    @Test
    void search_success_defaultPaging() {
        NoticeSearchCondition cond = new NoticeSearchCondition();
        PageRequest page = new PageRequest();

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        NoticeListItemResponse item1 =
                noticeItem(1L, "공지 1", "공지", LocalDateTime.now());
        NoticeListItemResponse item2 =
                noticeItem(2L, "공지 2", "이벤트", LocalDateTime.now());

        when(mapper.search(cond, null, 0, 20))
                .thenReturn(List.of(item1, item2));
        when(mapper.count(cond, null))
                .thenReturn(42L);

        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getPage().getPage()).isEqualTo(1);
        assertThat(response.getPage().getSize()).isEqualTo(20);
        assertThat(response.getPage().getTotalCount()).isEqualTo(42L);
        assertThat(response.getPage().getTotalPages()).isEqualTo(3);

        verify(mapper).search(cond, null, 0, 20);
        verify(mapper).count(cond, null);
    }

    @Test
    void search_safePage_applied() {
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "page", -5);
        setField(page, "size", 10);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(any(), isNull(), anyInt(), anyInt()))
                .thenReturn(List.of());
        when(mapper.count(any(), isNull()))
                .thenReturn(0L);

        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        assertThat(response.getPage().getPage()).isEqualTo(1);
        assertThat(response.getPage().getSize()).isEqualTo(10);
    }

    @Test
    void search_safeSize_applied() {
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "size", 10_000);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(any(), isNull(), anyInt(), anyInt()))
                .thenReturn(List.of());
        when(mapper.count(any(), isNull()))
                .thenReturn(0L);

        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        assertThat(response.getPage().getSize()).isEqualTo(200);
    }

    @Test
    void search_offset_calculation() {
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "page", 3);
        setField(page, "size", 10);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(cond, null, 20, 10))
                .thenReturn(List.of());
        when(mapper.count(cond, null))
                .thenReturn(0L);

        service.search(req);

        verify(mapper).search(cond, null, 20, 10);
    }

    // ---------------- util ----------------

    private NoticeListItemResponse noticeItem(
            Long id, String title, String categoryName, LocalDateTime createdAt
    ) {
        NoticeListItemResponse item = new NoticeListItemResponse();
        setField(item, "id", id);
        setField(item, "title", title);
        setField(item, "categoryName", categoryName);
        setField(item, "createdAt", createdAt);
        return item;
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
