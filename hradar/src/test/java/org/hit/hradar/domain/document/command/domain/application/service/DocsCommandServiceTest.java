package org.hit.hradar.domain.document.command.domain.application.service;

import org.hit.hradar.domain.document.DocsErrorCode;
import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParseResult;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParser;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentCreateRequest;
import org.hit.hradar.domain.document.command.domain.application.dto.response.DocumentPreviewResponse;
import org.hit.hradar.domain.document.command.domain.repository.DocumentChunkRepository;
import org.hit.hradar.domain.document.command.domain.repository.DocumentRepository;
import org.hit.hradar.domain.document.command.infrastructure.vector.VectorIndexClient;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocsCommandServiceTest {

    @InjectMocks
    private DocsCommandService docsCommandService;

    @Mock
    private CsvParser csvParser;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentChunkRepository chunkRepository;

    @Mock
    private VectorIndexClient vectorIndexClient;

    /**
     * 문서 미리보기 유닛 테스트
     */
    @Test
    void preview_success() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);

        Map<String, Integer> header = Map.of(
                "category", 0,
                "doc_title", 1,
                "section", 2,
                "content", 3
        );

        List<String[]> rows = List.of(
                new String[]{"HR", "연차 규정", "연차 발생", "연차는 1년에 15일"},
                new String[]{"HR", "연차 규정", "미사용", "미사용 연차는 소멸"}
        );

        CsvParseResult result = new CsvParseResult(header, rows);
        when(csvParser.parse(file)).thenReturn(result);

        // when
        DocumentPreviewResponse response = docsCommandService.preview(file);

        // then
        assertThat(response.getDocTitle()).isEqualTo("연차 규정");
        assertThat(response.getTotalChunks()).isEqualTo(2);
        assertThat(response.getChunks().size()).isEqualTo(2);
    }

    @Test
    void preview_emptyFile_throwException() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        assertThatThrownBy(() -> docsCommandService.preview(file))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(DocsErrorCode.EMPTY_FILE);
    }

    @Test
    void preview_invalidHeader_throwException() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);

        CsvParseResult result = new CsvParseResult(
                Map.of("doc_title", 0),
                List.of()
        );

        when(csvParser.parse(file)).thenReturn(result);

        assertThatThrownBy(() -> docsCommandService.preview(file))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(DocsErrorCode.INVALID_HEADER);
    }

    /**
     * 문서 생성 유닛 테스트
     */
    @Test
    void create_success() {
        // given
        Long companyId = 1L;
        Long actorId = 10L;

        DocumentCreateRequest request = mock(DocumentCreateRequest.class);

        DocumentCreateRequest.ChunkRequest chunk1 =
                mock(DocumentCreateRequest.ChunkRequest.class);
        when(chunk1.getSection()).thenReturn("연차 발생");
        when(chunk1.getContent()).thenReturn("내용1");

        DocumentCreateRequest.ChunkRequest chunk2 =
                mock(DocumentCreateRequest.ChunkRequest.class);
        when(chunk2.getSection()).thenReturn("미사용");
        when(chunk2.getContent()).thenReturn("내용2");

        when(request.getDocTitle()).thenReturn("연차 규정");
        when(request.getChunks()).thenReturn(List.of(chunk1, chunk2));

        Document savedDoc = mock(Document.class);
        when(savedDoc.getId()).thenReturn(100L);

        when(documentRepository.save(any(Document.class)))
                .thenReturn(savedDoc);

        // when
        docsCommandService.create(request, companyId, actorId);

        // then
        verify(documentRepository).save(any(Document.class));
        verify(chunkRepository).saveAll(anyList());
        verify(vectorIndexClient)
                .indexAsync(eq(companyId), eq(100L), anyList());
    }

    /**
     * 문서 수정 유닛 테스트
     */
    @Test
    void update_success() {
        Long companyId = 1L;
        Long actorId = 10L;
        Long documentId = 100L;

        Document document = mock(Document.class);
        when(document.getId()).thenReturn(documentId);

        when(documentRepository.findByIdAndCompanyId(documentId, companyId))
                .thenReturn(Optional.of(document));

        DocumentCreateRequest request = mock(DocumentCreateRequest.class);

        DocumentCreateRequest.ChunkRequest chunk =
                mock(DocumentCreateRequest.ChunkRequest.class);
        when(chunk.getSection()).thenReturn("섹션");
        when(chunk.getContent()).thenReturn("내용");

        when(request.getDocTitle()).thenReturn("수정된 제목");
        when(request.getChunks()).thenReturn(List.of(chunk));

        // when
        docsCommandService.update(documentId, request, companyId, actorId);

        // then
        verify(document).updateTitle("수정된 제목", actorId);
        verify(chunkRepository).deleteByDocumentId(documentId);
        verify(chunkRepository).saveAll(anyList());
        verify(vectorIndexClient).deleteIndex(companyId, documentId);
        verify(vectorIndexClient)
                .indexAsync(eq(companyId), eq(documentId), anyList());
    }

    /**
     * 문서 삭제 유닛 테스트
     */
    @Test
    void delete_success() {
        Long companyId = 1L;
        Long documentId = 100L;

        // when
        docsCommandService.delete(documentId, companyId);

        // then
        verify(chunkRepository).deleteByDocumentId(documentId);
        verify(documentRepository).deleteByIdAndCompanyId(documentId, companyId);
        verify(vectorIndexClient).deleteIndex(companyId, documentId);
    }
}