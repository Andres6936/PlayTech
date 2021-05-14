package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class OrderViewHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            File page = new File(ClassLoader.getSystemResource("html/show.html").getFile());

            String contentPage = new String(Files.readAllBytes(page.toPath()));

            exchange.sendResponseHeaders(200, contentPage.length());

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentPage.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
