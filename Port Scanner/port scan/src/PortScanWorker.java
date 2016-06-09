import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Segiy on 23.05.2016.
 */
public class PortScanWorker implements Runnable {
    static int globalId = 1;

    private int id;
    private List<Integer> ports;
    private List<Integer> openPorts;
    private List<Integer> filteredPorts;
    private InetAddress inetAddress;
    private int timeout = 200;
    CyclicBarrier barrier;

    public PortScanWorker() {
        id = globalId++;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getFilteredPorts() {
        return filteredPorts;
    }

    public void setFilteredPorts(List<Integer> filteredPorts) {
        this.filteredPorts = filteredPorts;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public List<Integer> getOpenPorts() {
        return openPorts;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public void run() {
        //Started thread with id = globalId
        scan(inetAddress);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            return;
        }
    }

    void scan(InetAddress inetAddress) {
        openPorts = new ArrayList<>();
        filteredPorts = new ArrayList<>();
        //System.out.println("scanning ports: ");
        for (Integer port : ports) {
            //System.out.print(port);
            try {
                InetSocketAddress isa = new InetSocketAddress(inetAddress,port);
                Socket socket = new Socket();
                socket.connect(isa,timeout);
                System.out.println("Found opened port: " + port);
                openPorts.add(port);
                socket.close();
            } catch (IOException e) {
                if (e instanceof SocketException)
                    System.out.println(e.getMessage() + ": " + port);

                if (e instanceof SocketTimeoutException) {
                    filteredPorts.add(port);
//                    System.out.println("Port is filtered(firewalled): " + port);
                }
                
            }
        }
    }
}
