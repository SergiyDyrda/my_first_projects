import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Segiy on 23.05.2016.
 */
public class Main {
    private static final int MIN_PORTS_PER_THREAD = 20;
    private static final int MAX_THREADS = 0xFF;

    static InetAddress inetAddress;
    static List<Integer> allPorts;

    static List<Integer> allOpenPorts = new ArrayList<>();
    static List<PortScanWorker> workers = new ArrayList<>(MAX_THREADS);

    static Date startTime;
    static Date endTime;

    public static void main(String[] args) {
        startTime = new Date();

        processArgs(args);

        List<Integer> threads = new ArrayList<>();
        if (allPorts.size() / MIN_PORTS_PER_THREAD > MAX_THREADS ) {
            final int PORTS_PER_THREAD = allPorts.size() / MAX_THREADS;

            fillThreadPorts(PORTS_PER_THREAD, threads);
        } else {
            fillThreadPorts(MIN_PORTS_PER_THREAD, threads);
        }

        System.out.println("Ports to scan: " + allPorts.size());
        System.out.println("Threads to work: " + workers.size());

        Runnable summarizer = new Runnable() {
            public void run()
            {
                System.out.println("Scanning stopped...");

                int filteredPorts = 0;
                for (PortScanWorker psw : workers) {
                    List<Integer> openPorts = psw.getOpenPorts();
                    filteredPorts = psw.getFilteredPorts().size();
//                    allOpenPorts.addAll(openPorts);
                }

                System.out.println("Number of filtered ports: " + filteredPorts);
//                Collections.sort(allOpenPorts);

//                System.out.println("List of opened ports:");
//                for (Integer openedPort : allOpenPorts) {
//                    System.out.println(openedPort);
//                }

                endTime = new Date();

                System.out.println("Time of run: " + (endTime.getTime() - startTime.getTime()) + " ms");
            }
        };

        CyclicBarrier barrier = new CyclicBarrier(workers.size(), summarizer);

        for (PortScanWorker psw : workers) {
            psw.setBarrier(barrier);
        }

        System.out.println("Start scanning...");

        for (PortScanWorker psw : workers) {
            new Thread(psw).start();
        }
    }

    private static void fillThreadPorts(int PORTS_PER_THREAD, List<Integer> threadPorts) {
        for (int i=0,counter=0; i<allPorts.size();i++,counter++) {
            if (counter<PORTS_PER_THREAD) {
                threadPorts.add(allPorts.get(i));
            } else {
                PortScanWorker psw = new PortScanWorker();
                psw.setInetAddress(inetAddress);
                psw.setPorts(new ArrayList<>(threadPorts));
                workers.add(psw);
                threadPorts.clear();
                counter=0;
            }
        }
        if (workers.size() < 1) {
            PortScanWorker psw = new PortScanWorker();
            psw.setInetAddress(inetAddress);
            psw.setPorts(new ArrayList<>(threadPorts));
            workers.add(psw);
        }
    }

    private static void processArgs(String[] args) {
        if (args.length < 1) {
            usage();
            System.exit(1);
        }

        String host = args[0];
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (IOException ioe) {
            System.out.println("Error when resolving host!");
            System.exit(2);
        }

        System.out.println("Scanning host "+host);

        int minPort = 0;
        int maxPort = 0x10000-1;

        if (args.length==2) {
            if (args[1].indexOf("-")>-1) {
                // range of ports pointed out
                String[] ports = args[1].split("-");
                try {
                    minPort = Integer.parseInt(ports[0]);
                    maxPort = Integer.parseInt(ports[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("Wrong ports!");
                    System.exit(3);
                }
            } else {
                // one port pointed out
                try {
                    minPort = Integer.parseInt(args[1]);
                    maxPort = minPort;
                } catch (NumberFormatException nfe) {
                    System.out.println("Wrong port!");
                    System.exit(3);
                }
            }
        }

        allPorts = new ArrayList<>(maxPort-minPort+1);

        for (int i=minPort; i<=maxPort; i++) {
            allPorts.add(i);
        }
    }

    private static void usage() {
        System.out.println("Java Port Scanner usage: ");
        System.out.println("java Main host port");
        System.out.println("Examples:");
        System.out.println("java Main 192.168.1.1 1-1024");
        System.out.println("java Main 192.168.1.1 1099");
        System.out.println("java Main 192.168.1.1 (this scans all ports from 0 to 65535)");
    }
}
