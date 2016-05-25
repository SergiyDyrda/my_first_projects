package com.javarush.test.level39.lesson09.LogParser;

import com.javarush.test.level39.lesson09.LogParser.query.IPQuery;
import com.javarush.test.level39.lesson09.LogParser.query.UserQuery;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogParser implements IPQuery, UserQuery {
    private Path logDir;
    private List<LogRecord> logRecords = new ArrayList<>();

    public List<LogRecord> getLogRecords() {
        return logRecords;
    }

    public LogParser(Path logDir) {
        this.logDir = logDir;
        try {
            parseLogRecords();
        }
        catch (Exception e) {
//            System.err.print("Oops, error while reading logs");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private List<String> readAllLogLines() throws IOException {
        List<String> linesOfAllLogFiles = new ArrayList<>();

        final List<Path> logsPaths = new ArrayList<>();
        Files.walkFileTree(logDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".log"))
                    logsPaths.add(file);

                return FileVisitResult.CONTINUE;
            }
        });

        for (Path path : logsPaths) {
            linesOfAllLogFiles.addAll(Files.readAllLines(path, Charset.defaultCharset()));
        }

        return linesOfAllLogFiles;
    }

    private void parseLogRecords() throws IOException, ParseException {
        Pattern ipPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        Pattern datePattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}");


        List<String> linesOfAllLogFiles = readAllLogLines();

        for (String logLine : linesOfAllLogFiles) {
            Matcher ipMatcher = ipPattern.matcher(logLine);
            Matcher dateMatcher = datePattern.matcher(logLine);

            ipMatcher.find();
            String ip = logLine.substring(ipMatcher.start(), ipMatcher.end());

            dateMatcher.find();
            String dateString = logLine.substring(dateMatcher.start(), dateMatcher.end());

            String user = logLine.substring(ipMatcher.end(), dateMatcher.start()).trim();

            String eventAndStatus = logLine.substring(dateMatcher.end()).trim();
            String[] tmp = eventAndStatus.split("\\s");

            Event event;
            int taskNumber = 0;
            Status status;

            if (tmp.length == 3)
                taskNumber = Integer.parseInt(tmp[1]);

            event = Event.valueOf(tmp[0]);
            status = Status.valueOf(tmp[tmp.length - 1]);

            SimpleDateFormat dataFormat = new SimpleDateFormat("dd.MM.yyyy k:mm:ss");
            Date date = dataFormat.parse(dateString);

            LogRecord logRecord = new LogRecord(ip, user, date, event, taskNumber, status);

            logRecords.add(logRecord);
        }

    }

    private boolean isDateBetweenDates(Date date, Date before, Date after) {

        if (before == null & after == null)
            return true;

        if (after == null)
            return (date.equals(before) | date.before(before));


        if (before == null)
            return (date.equals(after) | date.after(after));


        if (date.equals(before) | date.equals(after))
            return true;

        if (date.after(before) & date.before(after))
            return true;

        return false;
    }

    /*IPQuery*/
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> ips = new HashSet<>();

        for (LogRecord logRecord : logRecords) {
            if (isDateBetweenDates(logRecord.getDate(), before, after))
                ips.add(logRecord.getIp());
        }

        return ips;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> ips = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getUser().equals(user) && isDateBetweenDates(logRecord.getDate(), before, after))
                ips.add(logRecord.getIp());
        }
        return ips;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> ips = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(event) && isDateBetweenDates(logRecord.getDate(), before, after))
                ips.add(logRecord.getIp());
        }
        return ips;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> ips = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getStatus().equals(status) && isDateBetweenDates(logRecord.getDate(), before, after))
                ips.add(logRecord.getIp());
        }
        return ips;
    }

    /*UserQuery*/

    @Override
    public Set<String> getAllUsers() {
        Set<String> users = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            users.add(logRecord.getUser());
        }
        return users;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (isDateBetweenDates(logRecord.getDate(), before, after))
                users.add(logRecord.getUser());
        }

        return users.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<Event> userEvents = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getUser().equals(user) &&
                    isDateBetweenDates(logRecord.getDate(), before, after))
                userEvents.add(logRecord.getEvent());
        }
        return userEvents.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> usersForIp = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getIp().equals(ip) && isDateBetweenDates(logRecord.getDate(), before, after))
                usersForIp.add(logRecord.getUser());
        }
        return usersForIp;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> loggedUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.LOGIN)
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                loggedUsers.add(logRecord.getUser());
        }
        return loggedUsers;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> downloadedPluginUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.DOWNLOAD_PLUGIN)
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                downloadedPluginUsers.add(logRecord.getUser());
        }
        return downloadedPluginUsers;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> wroteMessageUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.WRITE_MESSAGE)
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                wroteMessageUsers.add(logRecord.getUser());
        }
        return wroteMessageUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.SOLVE_TASK)
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                solvedTaskUsers.add(logRecord.getUser());
        }
        return solvedTaskUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.SOLVE_TASK)
                    && logRecord.getTaskNumber() == task
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                solvedTaskUsers.add(logRecord.getUser());
        }
        return solvedTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.DONE_TASK)
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                solvedTaskUsers.add(logRecord.getUser());
        }
        return solvedTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (LogRecord logRecord : logRecords) {
            if (logRecord.getEvent().equals(Event.DONE_TASK)
                    && logRecord.getTaskNumber() == task
                    && isDateBetweenDates(logRecord.getDate(), before, after))
                solvedTaskUsers.add(logRecord.getUser());
        }
        return solvedTaskUsers;
    }


    public class LogRecord {
        private String ip;
        private String user;
        private Date date;
        private Event event;
        private int taskNumber;
        private Status status;

        public LogRecord(String ip, String user, Date date, Event event, int taskNumber, Status status) {
            this.ip = ip;
            this.user = user;
            this.date = date;
            this.event = event;
            this.taskNumber = taskNumber;
            this.status = status;
        }

        public String getIp() {
            return ip;
        }

        public String getUser() {
            return user;
        }

        public Date getDate() {
            return date;
        }

        public Event getEvent() {
            return event;
        }

        public int getTaskNumber() {
            return taskNumber;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "LogRecord{" +
                    "ip='" + ip + '\'' +
                    ", user='" + user + '\'' +
                    ", date=" + date +
                    ", event=" + event +
                    ", taskNumber=" + taskNumber +
                    ", status=" + status +
                    '}';
        }
    }
}
