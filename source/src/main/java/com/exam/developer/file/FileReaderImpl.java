package com.exam.developer.file;

import com.exam.developer.file.interfaces.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileReaderImpl implements FileReader {
    @Value("${file}")
    private String path;

    @Value("proga.dat")
    private ClassPathResource resource;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public List<String> getLines() {
        log.info("Obtaining lines from file");
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader
                     = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            lines.add("File not found");
            log.error("File not found");
            e.printStackTrace();
        }

        return lines;
    }
}
