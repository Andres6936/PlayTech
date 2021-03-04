package man.app;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 6936), 0);
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
        } catch (IOException exception) {
            System.err.println("Error Initializing the Server");
        }
    }
}
