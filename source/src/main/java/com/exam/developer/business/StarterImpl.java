package com.exam.developer.business;

import com.exam.developer.business.interfaces.Executor;
import com.exam.developer.business.interfaces.Starter;
import com.exam.developer.business.interfaces.Validator;
import com.exam.developer.file.interfaces.FileReader;
import com.exam.developer.file.interfaces.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StarterImpl implements Starter {
    @Autowired
    private FileReader fileReader;

    @Autowired
    private FileWriter fileWriter;

    @Autowired
    private Validator validator;

    @Autowired
    private Executor executor;

    @Override
    public void run() {
        log.info("Running the app");

        List<String> output = new ArrayList<>();


        List<String> lines = fileReader.getLines();

        List<String> result = validator.validateInput(lines);

        if (validator.isValid()) {
            output = executor.processor(lines);
            fileWriter.write(output);
            for (String s : output) System.out.println(s);
        } else {
            fileWriter.write(result);
            for (String s : result) System.out.println(s);
        }
    }

}




