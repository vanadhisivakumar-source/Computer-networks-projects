Clientrarp.java
import java.io.*;
import java.net.*;

public class clientrarp {
    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName("127.0.0.1");

            byte[] sendByte;
            byte[] receiveByte = new byte[1024];

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the Physical Address: ");
            String str = in.readLine();

            sendByte = str.getBytes();
            DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, 1309);
            client.send(sender);

            DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
            client.receive(receiver);

            String s = new String(receiver.getData(), 0, receiver.getLength());
            System.out.println("The Logical Address is: " + s.trim());

            client.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
Serverrarp.java
import java.io.*;
import java.net.*;

public class serverrarp {
    public static void main(String args[]) {
        try {
            DatagramSocket server = new DatagramSocket(1309);
            System.out.println("Server started... waiting for client");

            while (true) {
                byte[] receiveByte = new byte[1024];
                DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
                server.receive(receiver);

                String str = new String(receiver.getData(), 0, receiver.getLength()).trim();
                InetAddress addr = receiver.getAddress();
                int port = receiver.getPort();

                // RARP table simulation
                String[] ip = {"10.0.3.186"};
                String[] mac = {"D4:3D:7E:12:A3:D9"};

                boolean found = false;
                for (int i = 0; i < mac.length; i++) {
                    if (str.equals(mac[i])) {
                        byte[] sendByte = ip[i].getBytes();
                        DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, port);
                        server.send(sender);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    byte[] sendByte = "Address not found".getBytes();
                    DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, port);
                    server.send(sender);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
