package com.example.catmash.entity;

import lombok.Value;

@Value
public class Cat {
    long id;
    String name;
    String url;
    long rating;
}
