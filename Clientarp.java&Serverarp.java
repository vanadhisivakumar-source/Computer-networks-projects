
Clientarp.java
import java.io.*;
import java.net.*;

class Clientarp {
    public static void main(String args[]) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Socket clsct = new Socket("127.0.0.1", 139);

            DataInputStream din = new DataInputStream(clsct.getInputStream());
            DataOutputStream dout = new DataOutputStream(clsct.getOutputStream());

            System.out.println("Enter the Logical address (IP):");
            String str1 = in.readLine();

            dout.writeBytes(str1 + "\n");  // send IP to server
            String str = din.readLine();   // receive MAC from server

            System.out.println("The Physical Address is: " + str);

            clsct.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
Serverarp.java
import java.io.*;
import java.net.*;

class Serverarp {
    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(139);
            System.out.println("Server started... waiting for client");

            Socket client = server.accept();
            System.out.println("Client connected");

            DataInputStream din = new DataInputStream(client.getInputStream());
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());

            // ARP table simulation
            String[] ip = {"165.165.80.80", "165.165.79.1"};
            String[] mac = {"6A:08:AA:C2", "8A:BC:E3:FA"};

            while (true) {
                String str = din.readLine();
                if (str == null) break; // client closed

                System.out.println("Received IP: " + str);

                boolean found = false;
                for (int i = 0; i < ip.length; i++) {
                    if (str.equals(ip[i])) {
                        dout.writeBytes(mac[i] + "\n");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    dout.writeBytes("Address not found\n");
                }
            }

            client.close();
            server.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
