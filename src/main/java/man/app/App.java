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
            server.createContext("/table/all", new EmployeeAllHandle());
            server.createContext("/table/view", new EmployeeAddHandle());
            server.createContext("/order/all", new ShowHandle());
            server.createContext("/order/add", new ShowHandle());
            server.createContext("/order/view", new AddHandle());
            server.createContext("/order/update", new ShowHandle());
            server.createContext("/order/remove", new ShowHandle());
            server.createContext("/waiter/all", new ShowHandle());
            server.createContext("/waiter/add", new ShowHandle());
            server.createContext("/waiter/view", new ShowHandle());
            server.createContext("/waiter/update", new ShowHandle());
            server.createContext("/waiter/remove", new ShowHandle());
            server.createContext("/daily/menu", new AddHandle());
            server.createContext("/home", new HomeHandler());
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            System.out.println("START: 'localhost:6936'");
        } catch (IOException exception) {
            System.err.println("Error Initializing the Server");
        }
    }
}
