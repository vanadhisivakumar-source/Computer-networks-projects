import java.net.*;
import java.util.Scanner;

public class DNSClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Server runs locally
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter domain name to resolve: ");
            String domain = scanner.nextLine();

            byte[] sendData = domain.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String ip = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Resolved IP: " + ip);

            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
