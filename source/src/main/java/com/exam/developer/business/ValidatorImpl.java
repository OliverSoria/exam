package com.exam.developer.business;

import com.exam.developer.business.interfaces.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Service
@Slf4j
public class ValidatorImpl implements Validator {
    @Value("${commands}")
    private String commands;
    private boolean valid = true;

    @Override
    public List<String> validateInput(List<String> input) {
        log.info("Validating input file");
        List<String> output = new ArrayList<>();

        int dependLastPosition = -1;
        int installFirstPosition = -1;

        if (input.isEmpty()) {
            output.add("Input file is empty");
            valid = false;
        }

        List commandsArr = Arrays.asList(commands.split("\\s+"));

        EVALUATION:
        for (int line = 1; line <= input.size(); line++) {
            if (input.get(line - 1).length() > 80) {
                valid = false;
                output.add("Line " + line + " has more than 80 characters");
                continue EVALUATION;
            }

            String[] splited = input.get(line - 1).split("\\s+");
            if (!isStringUpperCase(splited[0])) {
                valid = false;
                output.add("Command in line " + line + " must be uppercase");
                continue EVALUATION;
            }

            if (!commandsArr.contains(splited[0])) {
                valid = false;
                output.add("Unknown command in line " + line);
                continue EVALUATION;
            }

        }

        for (int i = 0; i < input.size(); i++) {
            String[] splited = input.get(i).split("\\s+");

            if (splited[0].equals("DEPEND")) {
                dependLastPosition = i;
            }
        }

        for (int i = 0; i < input.size(); i++) {
            String[] splited = input.get(i).split("\\s+");
            if (splited[0].equals("INSTALL")) {
                installFirstPosition = i;
                break;
            }
        }

        if( dependLastPosition != -1 && installFirstPosition != -1) {
            if (dependLastPosition > installFirstPosition) {
                valid = false;
                output.add("DEPEND commands must appear before the occurrence of any INSTALL dependencies");
            }
        }

        return output;
    }

    @Override
    public boolean isStringUpperCase(String str) {
        log.info("Validating command");
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i]))
                return false;
        }

        return true;
    }

    @Override
    public boolean isValid() {
        return valid;
    }
}



