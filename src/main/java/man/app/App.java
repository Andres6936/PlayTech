package man.app;

import com.sun.net.httpserver.HttpServer;
import man.app.route.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 6936), 0);
            // Manage the static files.
            server.createContext("/css", new StaticHandle());
            server.createContext("/js", new StaticHandle());
            // Manage the routes.
            server.createContext("/employee/all", new EmployeeAllHandle());
            server.createContext("/employee/add", new EmployeeAddHandle());
            server.createContext("/home", new HomeHandler());
            server.createContext("/show", new ShowHandle());
            server.createContext("/add", new AddHandle());
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            System.out.println("START: 'localhost:6936'");
        } catch (IOException exception) {
            System.err.println("Error Initializing the Server");
        }
    }
}
