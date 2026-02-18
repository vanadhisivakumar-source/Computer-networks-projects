import java.net.*;

public class DNSClientTest {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Predefined test domains
            String[] testDomains = {"example.com", "google.com", "unknown.com"};

            for (String domain : testDomains) {
                // Send domain name
                byte[] sendData = domain.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
                clientSocket.send(sendPacket);

                // Receive response
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String ip = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Domain: " + domain + " | Resolved IP: " + ip);
            }

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}