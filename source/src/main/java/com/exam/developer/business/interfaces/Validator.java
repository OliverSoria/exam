package com.exam.developer.business.interfaces;

import java.util.List;

public interface Validator {
    List<String> validateInput(List<String> input);
    boolean isStringUpperCase(String str);
    boolean isValid();
}
