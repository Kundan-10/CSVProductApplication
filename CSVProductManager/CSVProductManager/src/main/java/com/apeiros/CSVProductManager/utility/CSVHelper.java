package com.apeiros.CSVProductManager.utility;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class CSVHelper {

    public static List<String[]> readCsv(InputStream inputStream) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {
            List<String[]> records = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                records.add(StreamSupport.stream(csvRecord.spliterator(), false)
                        .toArray(String[]::new));
            }
            return records;
        }
    }
}
