package org.hit.hradar.domain.document.command.domain.application.csv;

import org.hit.hradar.domain.document.DocsErrorCode;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvParserTest {

    private final CsvParser csvParser = new CsvParser();

    @Test
    @DisplayName("유효하지 않은 CSV 형식이면 INVALID_CSV_FORMAT 예외가 발생한다")
    void parse_invalidCsv_throwException() {
        // given
        // CSV with mismatched quotes or binary content
        String invalidContent = "col1,col2\n\"unclosed quote, val2";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                invalidContent.getBytes(StandardCharsets.UTF_8));

        // when & then
        assertThatThrownBy(() -> csvParser.parse(file))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(DocsErrorCode.INVALID_CSV_FORMAT);
    }
}
