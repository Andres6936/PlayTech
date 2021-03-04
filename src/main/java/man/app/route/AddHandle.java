package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class AddHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            File page = new File(Objects.requireNonNull(
                    HomeHandler.class.getClassLoader().getResource("html/add.html")).getFile());

            String contentPage = new String(Files.readAllBytes(page.toPath()));

            exchange.sendResponseHeaders(200, contentPage.length());

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentPage.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
