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
            server.createContext("/table/all", new TableAllHandle());
            server.createContext("/table/view", new TableViewHandle());
            server.createContext("/order/all", new OrderAllHandle());
            server.createContext("/order/add", new OrderAddHandle());
            server.createContext("/order/view", new OrderViewHandle());
            server.createContext("/order/update", new OrderUpdateHandle());
            server.createContext("/order/remove", new OrderRemoveHandle());
            server.createContext("/waiter/all", new WaiterAllHandle());
            server.createContext("/waiter/add", new WaiterAddHandle());
            server.createContext("/waiter/view", new WaiterViewHandle());
            server.createContext("/waiter/update", new WaiterUpdateHandle());
            server.createContext("/waiter/remove", new WaiterRemoveHandle());
            server.createContext("/daily/menu", new DailyMenuHandle());
            server.createContext("/home", new HomeHandler());
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            System.out.println("START: 'localhost:6936'");
        } catch (IOException exception) {
            System.err.println("Error Initializing the Server");
        }
    }
}
