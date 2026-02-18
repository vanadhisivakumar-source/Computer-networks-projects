import java.net.*;

public class DNSServer {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
            System.out.println("DNS Server running on port 9876...");

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String domain = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received domain: " + domain);

                // Hardcoded resolution for testing
                String ip;
                switch (domain.toLowerCase()) {
                    case "example.com":
                        ip = "93.184.216.34";
                        break;
                    case "google.com":
                        ip = "142.250.190.14";
                        break;
                    default:
                        ip = "Domain not found";
                }

                byte[] sendData = ip.getBytes();
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