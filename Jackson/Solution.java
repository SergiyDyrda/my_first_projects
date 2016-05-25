package com.javarush.test.level33.lesson05.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/* Определение имени класса
НЕОБХОДИМО: подключенные библиотеки Jackson Core, Bind и Annotation версии 2.6.1

Расставьте Json аннотации так, чтобы результат выполнения метода main был следующий:
{
    "className" : ".Parking",
    "name" : "Super Parking",
    "city" : "Kyiv",
    "autos" : [ {
        "className" : "com.javarush.test.level33.lesson05.Jackson.RaceBike",
        "name" : "Simba",
        "owner" : "Peter",
        "age" : 2
    }, {
        "className" : "com.javarush.test.level33.lesson05.Jackson.Motorbike",
        "name" : "Manny",
        "owner" : null
    }, {
        "className" : "com.javarush.test.level33.lesson05.Jackson.Car"
    } ]
}

Подсказка: это всего два класса
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        com.javarush.test.level33.lesson05.Jackson.Parking parking = new com.javarush.test.level33.lesson05.Jackson.Parking("Super Parking", "Kyiv");
        com.javarush.test.level33.lesson05.Jackson.RaceBike raceBike = new com.javarush.test.level33.lesson05.Jackson.RaceBike("Simba", "Peter", 2);
        com.javarush.test.level33.lesson05.Jackson.Motorbike motorbike = new com.javarush.test.level33.lesson05.Jackson.Motorbike("Manny");
        Car car = new Car();
        List<com.javarush.test.level33.lesson05.Jackson.Auto> autos = new ArrayList<>();
        autos.add(raceBike);
        autos.add(motorbike);
        autos.add(car);
        parking.setAutos(autos);
        convertToJson(parking);
    }

    public static void convertToJson(com.javarush.test.level33.lesson05.Jackson.Parking parking) throws IOException {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, parking);
        System.out.println(writer.toString());
    }
}