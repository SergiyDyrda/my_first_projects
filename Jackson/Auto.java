package com.javarush.test.level33.lesson05.Jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonAutoDetect
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="className")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "com.javarush.test.level33.lesson05.Jackson.Car"),
        @JsonSubTypes.Type(value = Motorbike.class, name = "com.javarush.test.level33.lesson05.Jackson.Motorbike"),
        @JsonSubTypes.Type(value = RaceBike.class, name = "com.javarush.test.level33.lesson05.Jackson.RaceBike")
})
public abstract class Auto {
    protected String name;
    protected String owner;
    protected int age;
}