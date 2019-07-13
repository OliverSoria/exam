package com.exam.developer.business.interfaces;

import java.util.List;

public interface Executor {
    List<String> processor(List<String> commands);
    List<String> depend(String command);
    List<String> install(String command);
    List<String> remove(String command);
    List<String> list(String command);
    String end(String command);
}
