package org.hit.hradar.domain.document.command.domain.application.csv;

import com.opencsv.CSVReader;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class CsvParser {

    public CsvParseResult parse(MultipartFile file) {

        try (
                CSVReader reader = new CSVReader(
                        new InputStreamReader(
                                new BOMInputStream(file.getInputStream()),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            List<String[]> all = reader.readAll();

            if (all.isEmpty()) {
                return new CsvParseResult(Collections.emptyMap(), List.of());
            }

            // header
            String[] headerRow = all.get(0);
            Map<String, Integer> headerIndex = new HashMap<>();

            for (int i = 0; i < headerRow.length; i++) {
                headerIndex.put(headerRow[i].trim(), i);
            }

            // data rows
            List<String[]> rows = all.subList(1, all.size());

            return new CsvParseResult(headerIndex, rows);

        } catch (Exception e) {
            throw new IllegalStateException("CSV 파싱 중 오류가 발생했습니다.", e);
        }
    }
}
