package org.hit.hradar.domain.notice.query.service;

import org.hit.hradar.domain.notice.query.dto.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.NoticeSearchCondition;
import org.hit.hradar.domain.notice.query.dto.NoticeSearchRequest;
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

    /**
     * 기본 검색 + 페이징 성공
     */
    @Test
    void search_success_defaultPaging() {
        // given
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest(); // 기본값 page=1, size=20

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        NoticeListItemResponse item1 = noticeItem(
                1L, "공지사항 1", "공지", LocalDateTime.now()
        );
        NoticeListItemResponse item2 = noticeItem(
                2L, "공지사항 2", "이벤트", LocalDateTime.now().minusDays(1)
        );

        when(mapper.search(cond, 0, 20))
                .thenReturn(List.of(item1, item2));
        when(mapper.count(cond)).thenReturn(42L);

        // when
        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        // then
        assertThat(response.getItems()).hasSize(2);

        assertThat(response.getPage().getPage()).isEqualTo(1);
        assertThat(response.getPage().getSize()).isEqualTo(20);
        assertThat(response.getPage().getTotalCount()).isEqualTo(42L);
        assertThat(response.getPage().getTotalPages()).isEqualTo(3); // 42 / 20 = 3

        verify(mapper).search(cond, 0, 20);
        verify(mapper).count(cond);
    }

    /**
     * page < 1 인 경우 safePage = 1 적용
     */
    @Test
    void search_safePage_applied() {
        // given
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "page", -5);
        setField(page, "size", 10);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(any(), anyInt(), anyInt()))
                .thenReturn(List.of());
        when(mapper.count(cond)).thenReturn(0L);

        // when
        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        // then
        assertThat(response.getPage().getPage()).isEqualTo(1);
        assertThat(response.getPage().getSize()).isEqualTo(10);
        assertThat(response.getItems()).isEmpty();
    }

    /**
     * size > 200 인 경우 safeSize = 200 적용
     */
    @Test
    void search_safeSize_applied() {
        // given
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "page", 1);
        setField(page, "size", 10_000);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(any(), anyInt(), anyInt()))
                .thenReturn(List.of());
        when(mapper.count(cond)).thenReturn(0L);

        // when
        PageResponse<NoticeListItemResponse> response =
                service.search(req);

        // then
        assertThat(response.getPage().getSize()).isEqualTo(200);
    }

    /**
     * offset 계산 검증 (page=3, size=10 → offset=20)
     */
    @Test
    void search_offset_calculation() {
        // given
        NoticeSearchCondition cond = new NoticeSearchCondition();

        PageRequest page = new PageRequest();
        setField(page, "page", 3);
        setField(page, "size", 10);

        NoticeSearchRequest req = mock(NoticeSearchRequest.class);
        when(req.getCond()).thenReturn(cond);
        when(req.getPage()).thenReturn(page);

        when(mapper.search(cond, 20, 10))
                .thenReturn(List.of());
        when(mapper.count(cond)).thenReturn(0L);

        // when
        service.search(req);

        // then
        verify(mapper).search(cond, 20, 10);
    }

    // ------------------------------------------------
    // test util
    // ------------------------------------------------

    private NoticeListItemResponse noticeItem(
            Long id,
            String title,
            String categoryName,
            LocalDateTime createdAt
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
