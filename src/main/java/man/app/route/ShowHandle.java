package man.app.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ShowHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            File page = new File(Objects.requireNonNull(
                    HomeHandler.class.getClassLoader().getResource("html/show.html")).getFile());
            FileInputStream inputStream = new FileInputStream(page);
            byte[] inputData = new byte[(int) page.length()];
            inputStream.read(inputData);
            inputStream.close();

            String contentPage = new String(inputData, StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, contentPage.length());

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(contentPage.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
