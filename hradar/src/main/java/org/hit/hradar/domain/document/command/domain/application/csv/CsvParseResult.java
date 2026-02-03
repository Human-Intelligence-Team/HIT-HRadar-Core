package org.hit.hradar.domain.document.command.domain.application.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CsvParseResult {

    private Map<String, Integer> headerIndex; // columnName → index
    private List<String[]> rows;              // data rows (header 제외)
}

