package com.exam.developer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Node {
    private String name;
    private boolean transitory;
}