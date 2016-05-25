package com.javarush.test.level39.lesson09.LogParser;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("D:\\IdeaProjects\\JavaRushHomeWork\\src\\com\\javarush\\test\\level39\\lesson09\\LogParser\\logs\\example.log"));

//        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));

        for (String user : logParser.getAllUsers()) {
            System.out.println(user);
        }
        System.out.println("getNumberOfUsers - " + logParser.getNumberOfUsers(null, null));
        System.out.println("getNumberOfUserEvents (Eduard Petrovich Morozko) - " + logParser.getNumberOfUserEvents("Eduard Petrovich Morozko", null, null));
        System.out.println("getNumberOfUserEvents (Amigo) - " + logParser.getNumberOfUserEvents("Amigo", null, null));
        System.out.println("getUsersForIP (127.0.0.1) - " + logParser.getUsersForIP("127.0.0.1", null, null));
        System.out.println("getLoggedUsers - " + logParser.getLoggedUsers(new Date(), null));
        System.out.println("getDownloadedPluginUsers - " + logParser.getDownloadedPluginUsers(null, null));
        System.out.println("getWroteMessageUsers - " + logParser.getWroteMessageUsers(null, null));
        System.out.println("getSolvedTaskUsers - " + logParser.getSolvedTaskUsers(null, null));
        System.out.println("getSolvedTaskUsers (task) - " + logParser.getSolvedTaskUsers(null, null, 18));
        System.out.println("getDoneTaskUsers - " + logParser.getDoneTaskUsers(null, null));
        System.out.println("getDoneTaskUsers (task) - " + logParser.getDoneTaskUsers(null, null, 15));

    }

}
