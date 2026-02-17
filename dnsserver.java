import java.net.*;
import java.util.*;

public class DNSServer {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(8080);
            byte[] receiveData = new byte[1021];
            byte[] sendData;

            // Predefined DNS table (domain → IP)
            Map<String, String> dnsTable = new HashMap<>();
            dnsTable.put("example.com", "68.180.206.184");
            dnsTable.put("google.com", "209.85.148.19");
            dnsTable.put("microsoft.com", "80.168.92.140");
            dnsTable.put("annauniv.edu", "69.69.189.16");

            System.out.println("DNS Server is running on port 8080...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String domain = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received query for: " + domain);

                // Lookup domain
                String ip = dnsTable.getOrDefault(domain, "Domain not found");

                sendData = ip.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}