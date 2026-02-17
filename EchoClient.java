import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        String hostname = "localhost"; // server address
        int port = 4000;               // must match server port

        try (Socket socket = new Socket(hostname, port);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to Echo Server. Type messages... (type 'exit' to quit)");

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}