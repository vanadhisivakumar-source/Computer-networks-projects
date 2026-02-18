import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

public class EchoTest {
    private static Thread serverThread;

    @BeforeAll
    static void startServer() {
        serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(4000)) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    out.println("Echo: " + inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @Test
    void testEchoClientServerCommunication() throws Exception {
        try (Socket socket = new Socket("localhost", 4000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send test message
            String testMessage = "Hello JUnit";
            out.println(testMessage);

            // Read response
            String response = in.readLine();

            // Validate response
            assertEquals("Echo: " + testMessage, response);
        }
    }

    @AfterAll
    static void stopServer() {
        serverThread.interrupt();
    }
}