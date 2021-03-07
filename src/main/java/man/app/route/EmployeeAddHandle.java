package man.app.route;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EmployeeAddHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            try {

                // REQUEST Headers
                Headers requestHeaders = exchange.getRequestHeaders();

                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                // REQUEST Body
                InputStream is = exchange.getRequestBody();

                byte[] data = new byte[contentLength];
                int length = is.read(data);

                // Send RESPONSE Headers
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                // RESPONSE Body
                OutputStream os = exchange.getResponseBody();
                os.write(data);
                exchange.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
