package com.exam.developer.file;

import com.exam.developer.file.interfaces.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class FileWriterImpl implements FileWriter {
    @Value("${fileName}")
    private String fileName;

    @Override
    public void write(List<String> lines) {
        Path file = Paths.get(fileName);

        try {
            log.info("Saving file");
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error saving file");
            e.printStackTrace();
        }
    }
}
